package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.filler.CreditMenuFiller;
import com.daleondeveloper.Screens.GUI.filler.GameModeChangeMenuFiller;
import com.daleondeveloper.Screens.GUI.filler.HelpMenuFiller;
import com.daleondeveloper.Screens.GUI.filler.HighScoreMenuFiller;
import com.daleondeveloper.Screens.GUI.filler.PauseMenuFiller;
import com.daleondeveloper.Screens.GUI.filler.SettingsMenuFiller;
import com.daleondeveloper.Screens.GUIAbstractScreen;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;
import com.daleondeveloper.Screens.ListenerHelper;

public class MenuScreen extends GUIOverlayAbstractScreen {
    private final static String TAG = MenuScreen.class.getName();

    private static final float TITLE_OFFSET_Y = 200.0f;



    public enum MenuState{
        CLOSE,PAUSE,SETTINGS,HIGH_SCORE,HELP,CREDIT,GAMEMODECHOOSE
    }
    private static final float DIM_ALPHA = 0.8f;


    private GUIAbstractScreen guiAbstractScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private MenuState menuState;

    private HelpMenuFiller helpMenuFiller;
    private HighScoreMenuFiller highScoreMenuFiller;
    private PauseMenuFiller pauseMenuFiller;
    private SettingsMenuFiller settingsMenuFiller;
    private CreditMenuFiller creditMenuFiller;
    private GameModeChangeMenuFiller gameModeChangeMenuFiller;

    private Image screenBg;
    private Image pauseWindow;
    private Image back;
    private Table mainTable;
    private Table windowTable;

    public MenuScreen (ElMaster game, GUIAbstractScreen guiAbstractScreen){
        super(game);

        this.guiAbstractScreen  = guiAbstractScreen;

        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();

        helpMenuFiller = new HelpMenuFiller(this);
        highScoreMenuFiller = new HighScoreMenuFiller(this);
        pauseMenuFiller = new PauseMenuFiller(this);
        settingsMenuFiller = new SettingsMenuFiller(this);
        creditMenuFiller = new CreditMenuFiller(this);
        gameModeChangeMenuFiller = new GameModeChangeMenuFiller(this);

        menuState = MenuState.CLOSE;
    }

    @Override
    public void build() {
        mainTable = new Table();
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }
        mainTable.setFillParent(true);
        windowTable = new Table();
        mainTable.add(windowTable).center().width(400).height(342);
        windowTable.setBackground(new TextureRegionDrawable(assetGUI.getPauseWindow()));
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(255, 0, 0, DIM_ALPHA);
        pixmap.fill();
        TextureRegion dim = new TextureRegion(new Texture(pixmap));
        pixmap.dispose();
        screenBg = new Image(dim);
        screenBg.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                hideMenuScreen();
            }
        }));


        pauseWindow = new Image(new TextureRegionDrawable(assetGUI.getPauseWindow()));

        stage.addActor(screenBg);
        stage.addActor(mainTable);
//        stage.addActor(pauseWindow);

        helpMenuFiller.build();
        highScoreMenuFiller.build();
        settingsMenuFiller.build();
        creditMenuFiller.build();
        gameModeChangeMenuFiller.build();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float w = stage.getWidth(); // Same as stage.getViewport().getWorldWidth()
        float h = stage.getHeight();

        // Resize background
        screenBg.setSize(w, h);

            pauseWindow.setWidth(400);
            pauseWindow.setHeight(342);
            pauseWindow.setX((w - pauseWindow.getWidth()) / 2);
            pauseWindow.setY((h - pauseWindow.getHeight()) / 2);

    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        if(menuState == MenuState.CLOSE){
            setVisible(false);
        }else{
            setVisible(true);
        }

        helpMenuFiller.update(deltaTime);
        highScoreMenuFiller.update(deltaTime);
        gameModeChangeMenuFiller.update(deltaTime);

    }

    @Override
    public void render() {
        stage.draw();

    }

    @Override
    public void dispose() {
    }

    public void showMenuScreen(MenuState menuState){
        if (!isMenuScreenVisible()) {
            setVisible(true);
            this.menuState = menuState;
            switch (menuState){
                case SETTINGS: setSettingsScreen();break;
                case HELP:setHelpScreen();break;
                case PAUSE: setPauseScreen(); break;
                case HIGH_SCORE:setHighScoreScreen();break;
                case CREDIT:setCreditScreen();break;
                case GAMEMODECHOOSE:setGameModeChangeScreen();break;
            }
        }
    }
    public void hideMenuScreen(){
        if(isMenuScreenVisible()){
            setVisible(false);
            menuState = MenuState.CLOSE;
            guiAbstractScreen.setStateRunning();
            windowTable.clearChildren();
        }
    }

    private void setVisible(boolean visible){
        screenBg.setVisible(visible);
        pauseWindow.setVisible(visible);
        mainTable.setVisible(visible);

    }
    public boolean isMenuScreenVisible(){
        return pauseWindow.isVisible();
    }

    public void closeMenu(){menuState = MenuState.CLOSE;}
    public void setHelpScreen(HelpMenuFiller.HELP_TYPE_SHOW help_type_show){
        helpMenuFiller.setHelp_type_show(help_type_show);
        setHelpScreen();

    }
    public void setHelpScreen(){
        menuState = MenuState.HELP;
        helpMenuFiller.build();
        Gdx.input.setInputProcessor(stage);
    }
    public void setHighScoreScreen(){
        menuState = MenuState.HIGH_SCORE;
        highScoreMenuFiller.build();
        Gdx.input.setInputProcessor(stage);
    }
    public void setPauseScreen(){
        menuState = MenuState.PAUSE;
        pauseMenuFiller.build();

         Gdx.input.setInputProcessor(stage);

    }
    public void setSettingsScreen(){
        menuState = MenuState.SETTINGS;
        settingsMenuFiller.build();
        Gdx.input.setInputProcessor(stage);

    }
    public void setCreditScreen(){
        menuState = MenuState.CREDIT;
        creditMenuFiller.build();
        Gdx.input.setInputProcessor(stage);

    }
    public void setGameModeChangeScreen(){
        menuState = MenuState.GAMEMODECHOOSE;
        gameModeChangeMenuFiller.build();
        Gdx.input.setInputProcessor(stage);

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

    public HelpMenuFiller getHelpScreen() {
        return helpMenuFiller;
    }

    public Table getWindowTable() {
        return windowTable;
    }
}
