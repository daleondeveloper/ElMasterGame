package com.daleondeveloper.Screens.GUI.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;
import com.daleondeveloper.Screens.ListenerHelper;

public class HighScoreScreen extends GUIOverlayAbstractScreen {
    private static final String TAG = SettingsScreen.class.getName();

    private static final float DIM_ALPHA = 0.8f;
    private static final int LAST_SCORE_PAGE = 6;


    private MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;

    private Label.LabelStyle labelStyleBig;
    private Label.LabelStyle labelStyleMedium;

    private int pageShow;

    private Image menuWindow;
    private Image back;
    private Image nextScoreImage;
    private Image previsionScoreImage;

    private Label highScoreLabel;
    private Label bestHighScoreLabel;
    private Label modeNameLabel;


    public HighScoreScreen(ElMaster game, MenuScreen menuScreen){
        super(game);
        this.menuScreen = menuScreen;
        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        // Styles
        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = assets.getAssetFonts().getBig();
        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assets.getAssetFonts().getNormal();


        pageShow = 0;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);


//        highScoreLabel.setFontScale(width / 500, height / 1000);
        highScoreLabel.setPosition(menuWindow.getX() + 120  ,
                menuWindow.getY() + 270 );
        modeNameLabel.setPosition(menuWindow.getX() + 199 - bestHighScoreLabel.getPrefWidth() /2,
                menuWindow.getY() + 220 );
        bestHighScoreLabel.setPosition( menuWindow.getX() + 199 - bestHighScoreLabel.getPrefWidth() /2,
                menuWindow.getY() + 125);
        back.setHeight(24);
        back.setWidth(24);
        back.setPosition(menuWindow.getX() + menuWindow.getWidth() - 60 ,
                menuWindow.getY() + menuWindow.getHeight() - 40);

        nextScoreImage.setWidth(50);
        nextScoreImage.setHeight(58);
        nextScoreImage.setPosition(menuWindow.getX() + 300, menuWindow.getY() + 35);
        previsionScoreImage.setWidth(50);
        previsionScoreImage.setHeight(58);
        previsionScoreImage.setPosition(menuWindow.getX() + 55 , menuWindow.getY() + 35);
    }

    @Override
    public void build() {
        menuWindow = menuScreen.getPauseWindow();

        highScoreLabel = new Label(i18NGameThreeBundle.format("highScoreScreen.title"), labelStyleMedium);
        modeNameLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.classicMode"), labelStyleMedium);
        bestHighScoreLabel = new Label(String.valueOf(prefs.getHighScoreClassic()), labelStyleBig);
        defineButtons();

        stage.addActor(bestHighScoreLabel);
        stage.addActor(highScoreLabel);
        stage.addActor(modeNameLabel);

        stage.addActor(back);
        stage.addActor(nextScoreImage);
        stage.addActor(previsionScoreImage);
    }
    private void defineButtons(){
        back  =new Image(new TextureRegionDrawable(assetGUI.getButtonX()));
        nextScoreImage = new Image(assetGUI.getButtonLeft());
        previsionScoreImage = new Image(assetGUI.getButtonRight());

        back.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.hideMenuScreen();
            }
        }));


        nextScoreImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                if(pageShow < LAST_SCORE_PAGE){
                    pageShow++;
                }else{
                    pageShow = 0;
                }
            }
        }));

        previsionScoreImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                if(pageShow > 0){
                    pageShow--;
                }else{
                    pageShow = LAST_SCORE_PAGE;
                }
            }
        }));
    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        if(menuScreen.getMenuState() == MenuScreen.MenuState.HIGH_SCORE){
            setVisible(true);
        }else{
            setVisible(false);
        }
        switch (pageShow){
            case 0:
                modeNameLabel.setText("classic");
                bestHighScoreLabel.setText(prefs.getHighScoreClassic());
                break;
            case 1:
                modeNameLabel.setText("Light");
                bestHighScoreLabel.setText(prefs.getHighScoreLight());
                break;
            case 2:
                modeNameLabel.setText("Snow");
                bestHighScoreLabel.setText(prefs.getHighScoreSnow());
                break;
            case 3:
                modeNameLabel.setText("Fire");
                bestHighScoreLabel.setText(prefs.getHighScoreFire());
                break;
            case 4:
                modeNameLabel.setText("Water");
                bestHighScoreLabel.setText(prefs.getHighScoreWater());
                break;
            case 5:
                modeNameLabel.setText("Dark");
                bestHighScoreLabel.setText(prefs.getHighScoreDark());
                break;
            case 6:
                modeNameLabel.setText("Special");
                bestHighScoreLabel.setText(prefs.getHighScoreSpecial());
                break;
            default:
                modeNameLabel.setText("Mode");
                bestHighScoreLabel.setText(prefs.getHighScoreClassic());
        }

    }

    @Override
    public void render() {
        stage.draw();
    }

    public boolean isHelpScreenVisible(){
        return highScoreLabel.isVisible();
    }
    public void showHighScoreScreen() {
        if (!isHelpScreenVisible()) {
            setVisible(true);

            // Only PauseScreen responds to events
            Gdx.input.setInputProcessor(stage);
        }
    }

    private void setVisible(boolean  visible){
        bestHighScoreLabel.setVisible(visible);
        highScoreLabel.setVisible(visible);
        modeNameLabel.setVisible(visible);

        back.setVisible(visible);
        nextScoreImage.setVisible(visible);
        previsionScoreImage.setVisible(visible);
    }
}
