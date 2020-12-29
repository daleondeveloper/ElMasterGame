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

public class HelpScreen extends GUIOverlayAbstractScreen {
    private static final String TAG = SettingsScreen.class.getName();

    private static final float DIM_ALPHA = 0.8f;


    private MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;

    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;

    private Image menuWindow;
    private Image back;

    private Label helpLabel;


    public HelpScreen(ElMaster game, MenuScreen menuScreen){
        super(game);
        this.menuScreen = menuScreen;
        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        // Styles
        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assets.getAssetFonts().getNormal();
        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assets.getAssetFonts().getSmall();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);



//        helpLabel.setFontScale(width / 500, height / 1000);
        helpLabel.setPosition(menuWindow.getX() + 170, menuWindow.getY() + 270);
        back.setHeight(24);
        back.setWidth(24);
        back.setPosition(menuWindow.getX() + menuWindow.getWidth() - 60 ,
                menuWindow.getY() + menuWindow.getHeight() - 40);

    }

    @Override
    public void build() {

        menuWindow = menuScreen.getPauseWindow();

        helpLabel = new Label(i18NGameThreeBundle.format("helpScreen.title"), labelStyleMedium);

        defineButtons();

        stage.addActor(helpLabel);
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
        if(menuScreen.getMenuState() == MenuScreen.MenuState.HELP){
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
        return helpLabel.isVisible();
    }
    public void showHelpScreen() {
        if (!isHelpScreenVisible()) {
            setVisible(true);

            // Only PauseScreen responds to events
            Gdx.input.setInputProcessor(stage);
        }
    }

    private void setVisible(boolean  visible){
        helpLabel.setVisible(visible);
        back.setVisible(visible);
    }
}
