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
import com.daleondeveloper.Screens.GUIAbstractScreen;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;
import com.daleondeveloper.Screens.Play.PlayScreen;

public class MenuScreen extends GUIOverlayAbstractScreen {
    private final static String TAG = MenuScreen.class.getName();

    private static final float TITLE_OFFSET_Y = 200.0f;

    public enum MenuState{
        CLOSE,PAUSE,SETTINGS,HIGH_SCORE,HELP
    }
    private static final float DIM_ALPHA = 0.8f;


    private GUIAbstractScreen guiAbstractScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private MenuState menuState;

    private HelpScreen helpScreen;
    private HighScoreScreen highScoreScreen;
    private PauseScreen pauseScreen;
    private SettingsScreen settingsScreen;

    private Image screenBg;
    private Image pauseWindow;
    private Image back;

    public MenuScreen (ElMaster game, GUIAbstractScreen guiAbstractScreen){
        super(game);

        this.guiAbstractScreen  = guiAbstractScreen;

        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();

        helpScreen = new HelpScreen(game,this);
        highScoreScreen = new HighScoreScreen(game,this);
        pauseScreen = new PauseScreen(game,this);
        settingsScreen = new SettingsScreen(game,this);

        menuState = MenuState.PAUSE;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float w = stage.getWidth(); // Same as stage.getViewport().getWorldWidth()
        float h = stage.getHeight();

        // Resize background
        screenBg.setSize(w, h);

        if(guiAbstractScreen instanceof  PlayScreen) {
            // Place the title
            pauseWindow.setWidth(w);
            pauseWindow.setHeight(h * 0.425f);
            pauseWindow.setX((w - pauseWindow.getWidth()) / 2);
            pauseWindow.setY((h - pauseWindow.getHeight()) / 2 + TITLE_OFFSET_Y);
        }else {
            pauseWindow.setWidth(400);
            pauseWindow.setHeight(342);
            pauseWindow.setX((w - pauseWindow.getWidth()) / 2);
            pauseWindow.setY((h - pauseWindow.getHeight()) / 2);
        }
        helpScreen.resize(width,height);
        highScoreScreen.resize(width,height);
        pauseScreen.resize(width,height);
        settingsScreen.resize(width,height);
    }

    @Override
    public void build() {
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(255, 0, 0, DIM_ALPHA);
        pixmap.fill();
        TextureRegion dim = new TextureRegion(new Texture(pixmap));
        pixmap.dispose();
        screenBg = new Image(dim);

        pauseWindow = new Image(new TextureRegionDrawable(assetGUI.getPauseWindow()));

        stage.addActor(screenBg);
        stage.addActor(pauseWindow);

        helpScreen.build();
        highScoreScreen.build();
        pauseScreen.build();
        settingsScreen.build();
    }

    @Override
    public void update(float deltaTime) {
        stage.act();

        helpScreen.update(deltaTime);
        highScoreScreen.update(deltaTime);
        pauseScreen.update(deltaTime);
        settingsScreen.update(deltaTime);
    }

    @Override
    public void render() {
        stage.draw();

        helpScreen.render();
        highScoreScreen.render();
        pauseScreen.render();
        settingsScreen.render();
    }

    @Override
    public void dispose() {
        helpScreen.dispose();
        highScoreScreen.dispose();
        pauseScreen.dispose();
        settingsScreen.dispose();
    }

    public void showMenuScreen(MenuState menuState){
        if (!isMenuScreenVisible()) {
            setVisible(true);
            this.menuState = menuState;
            switch (menuState){
                case SETTINGS:
                    setSettingsScreen();
//                    hideHelpScreen();
//                  hideHighScoreScreen();
//                 hidePauseScreen();
                    break;
                case HELP:
                    setHelpScreen();
//                   hideHighScoreScreen();
//                   hidePauseScreen();
//                    hideSettingsScreen();
                    break;
                case PAUSE:
                    setPauseScreen();
//                    hideHelpScreen();
//                    hideHighScoreScreen();
//                  hideSettingsScreen();
                    break;
                case HIGH_SCORE:
                    setHighScoreScreen();
//                    hideHelpScreen();
//                   hidePauseScreen();
//                    hideSettingsScreen();
                    break;
            }
        }
    }
    public void hideMenuScreen(){
        if(isMenuScreenVisible()){
            setVisible(false);
            menuState = MenuState.CLOSE;
            guiAbstractScreen.setStateRunning();
//            hideHelpScreen();
//            hideHighScoreScreen();
//            hidePauseScreen();
//            hideSettingsScreen();
        }
    }

    private void setVisible(boolean visible){
        screenBg.setVisible(visible);
        pauseWindow.setVisible(visible);

    }
    public boolean isMenuScreenVisible(){
        return pauseWindow.isVisible();
    }

    public void closeMenu(){menuState = MenuState.CLOSE;}
    public void setHelpScreen(){
        menuState = MenuState.HELP;
        helpScreen.showHelpScreen();
    }
    public void setHighScoreScreen(){
        menuState = MenuState.HIGH_SCORE;
        highScoreScreen.showHighScoreScreen();
    }
    public void setPauseScreen(){
        menuState = MenuState.PAUSE;
        pauseScreen.showPauseScreen();
    }
    public void setSettingsScreen(){
        menuState = MenuState.SETTINGS;
        settingsScreen.showSettingsScreen();
    }

    public GUIAbstractScreen getGuiAbstractScreen() {
        return guiAbstractScreen;
    }

    public MenuState getMenuState() {
        return menuState;
    }

    public Image getPauseWindow() {
        return pauseWindow;
    }

}
