package com.daleondeveloper.Screens.GUI.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.GameSettings;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;
import com.daleondeveloper.Screens.ListenerHelper;

public class HighScoreScreen extends GUIOverlayAbstractScreen {
    private static final String TAG = SettingsScreen.class.getName();

    private static final float DIM_ALPHA = 0.8f;


    private MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;

    private Label.LabelStyle labelStyleBig;
    private Label.LabelStyle labelStyleMedium;

    private Image menuWindow;
    private Image back;

    private Label highScoreLabel;
    private Label bestHighScoreLabel;


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

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);


//        highScoreLabel.setFontScale(width / 500, height / 1000);
        highScoreLabel.setPosition(menuWindow.getX() + 120  ,
                menuWindow.getY() + 270 );
        bestHighScoreLabel.setPosition( 215 - bestHighScoreLabel.getPrefWidth() /2, 385);
        back.setHeight(24);
        back.setWidth(24);
        back.setPosition(menuWindow.getX() + menuWindow.getWidth() - 60 ,
                menuWindow.getY() + menuWindow.getHeight() - 40);
    }

    @Override
    public void build() {
        menuWindow = menuScreen.getPauseWindow();

        highScoreLabel = new Label(i18NGameThreeBundle.format("highScoreScreen.title"), labelStyleMedium);
        bestHighScoreLabel = new Label(String.valueOf(prefs.getHighScore()), labelStyleBig);
        defineButtons();

        stage.addActor(bestHighScoreLabel);
        stage.addActor(highScoreLabel);
        stage.addActor(back);
    }
    private void defineButtons(){
        back  =new Image(new TextureRegionDrawable(assetGUI.getButtonX()));

        back.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.hideMenuScreen();
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
        back.setVisible(visible);
    }
}
