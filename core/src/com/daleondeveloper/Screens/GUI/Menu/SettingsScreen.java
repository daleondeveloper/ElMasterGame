package com.daleondeveloper.Screens.GUI.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.daleondeveloper.Screens.Play.PlayScreen;

public class SettingsScreen extends GUIOverlayAbstractScreen {
    private static final String TAG = SettingsScreen.class.getName();

    private static final float DIM_ALPHA = 0.8f;


    private MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;

    private Label.LabelStyle labelStyleBig;
    private Label.LabelStyle labelStyleSmall;

    private Image menuWindow;
    private Image back;

    private Label settingsLabel;


    public SettingsScreen(ElMaster game, MenuScreen menuScreen){
        super(game);
        this.menuScreen = menuScreen;
        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        // Styles
        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = assets.getAssetFonts().getBig();
        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assets.getAssetFonts().getSmall();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);


//        settingsLabel.setFontScale(width / 500, height / 1000);
        settingsLabel.setPosition(menuWindow.getX() + menuWindow.getWidth()/2 - settingsLabel.getPrefWidth() / 2 ,
                menuWindow.getY()  + menuWindow.getHeight() * 1.9f  - settingsLabel.getPrefHeight() / 2);
    }

    @Override
    public void build() {
        menuWindow = menuScreen.getPauseWindow();

        settingsLabel = new Label(i18NGameThreeBundle.format("settingsScreen.title"), labelStyleBig);

        defineButtons();

        stage.addActor(settingsLabel);
        stage.addActor(back);
    }
    private void defineButtons(){
        back  =new Image(new TextureRegionDrawable(assetGUI.getButtonX()));

        back.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.closeMenu();
            }
        }));
    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        if(menuScreen.getMenuState() == MenuScreen.MenuState.SETTINGS){
            show();
        }else{
            hide();
        }

    }

    @Override
    public void render() {
        stage.draw();
    }

    private void show(){
        settingsLabel.setVisible(true);
        back.setVisible(true);
    }
    private void hide(){
        settingsLabel.setVisible(false);
        back.setVisible(false);
    }
}
