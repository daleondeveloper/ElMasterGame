package com.daleondeveloper.Screens.Play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.*;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.BackgroundScreen;
import com.daleondeveloper.Screens.GUI.GatesScreen;
import com.daleondeveloper.Screens.GUI.Hud;
import com.daleondeveloper.Screens.GUI.InfoScreen;
import com.daleondeveloper.Screens.GUI.Menu.MenuScreen;
import com.daleondeveloper.Screens.GUIAbstractScreen;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.tools.AudioManager;

public class PlayScreen extends GUIAbstractScreen {
    private static final String TAG = PlayScreen.class.getName();

    private static final float SHAKE_DURATION = 2.0f;

    private Hud hud;
    private InfoScreen infoScreen;
    private MenuScreen menuScreen;
    private BackgroundScreen backgroundScreen;
    private GatesScreen gatesScreen;
    private boolean gameStart;

    private Image background;
    private Image startButton;
    private WorldController worldController;
    private GameWorld gameWorld;
    private WorldRenderer worldRenderer;
    private com.daleondeveloper.Game.Settings.GameSettings prefs;
    private boolean endGame;
    private boolean levelCompleted;

    public PlayScreen(ElMaster game) {
        super(game);

        hud = new Hud(game,this);
        infoScreen = new InfoScreen(game,this);
        menuScreen = new MenuScreen(game,this);
        backgroundScreen = new BackgroundScreen(game,this);
        gatesScreen = new GatesScreen(game);
        gameStart = false;


        worldController = new WorldController(this);
        gameWorld = worldController.getGameWorld();
        worldRenderer = new WorldRenderer(gameWorld,game.getGameBatch(),game.getGameShapeRenderer(),game.getBox2DDebugRenderer());
        prefs = GameSettings.getInstance();
        endGame = false;
        levelCompleted = false;

        AudioManager.getInstance().playMusic(Assets.getInstance().getAssetMusic().getSongGame());
    }


    @Override
    public void show(){
        backgroundScreen.build();
        gatesScreen.build();
        hud.build();
        infoScreen.build();
        menuScreen.build();
        background = new Image(Assets.getInstance().getAssetGates().getStaticMain());
        startButton = new Image(Assets.getInstance().getAssetGUI().getButtonStart());
        //setStateRunning();
        startButton.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                resume();
            }
        }));
        //stage.addActor(startButton);
    }


    @Override
    public void render(float deltaTime){
        //Update logic
        stage.act();
        backgroundScreen.update(deltaTime);

        menuScreen.update(deltaTime);
        gatesScreen.update(deltaTime);
        hud.update(deltaTime);
        infoScreen.update(deltaTime);
        if(isPlayScreenStateRunning()){
            worldController.update(deltaTime);
        }


        //Render logic
    //    AbstractScreen.clearScr();


       // gameWorld.getGameCamera().setScreenViewport();
        backgroundScreen.render();
//        Viewport viewport = gameWorld.getGameCamera().setFitViewPort();
        worldRenderer.render();
        gatesScreen.render();
        hud.render();
        infoScreen.render();
        menuScreen.render();
//        viewport.update(viewport.getScreenWidth(),viewport.getScreenHeight());
        stage.draw();
        //Analys game result
        if(guiScreenState == GUIScreenState.RUNNING){
            gameResults();
        }

        if(!gameStart){
            doPause();
        }

    }

    private void gameResults(){

        if (!endGame) {
            // We evaluate mutual exclusion conditions.
            // A boolean value is used to avoid nested if/else sentences.
            boolean finish = false;

            // Show game controllers help
//            finish = !finish && prefs.mustShowHelp();
//            if (finish) {
//                infoScreen.showHelp();
//                prefs.setShowHelp(false);
//            }

            finish = !finish && levelCompleted;
            if (finish) {
                gameWorld.addLevel();
                levelCompleted = false;
            }

            finish = !finish && worldController.isGameOver();
            if (finish) {
                // Advertisement
                if (hud.isScoreAboveAverage()) {
                    prefs.decreaseCountdownAd();
                    if (prefs.isCountdownAdFinish()) {
                        prefs.resetCountdownAd();
                        showInterstitialAd();
                    }
                }

                // Game over
//                gameWorld.getGameCamera().shake(SHAKE_DURATION, true);
              //  gameWorld.getWaterElement().onDead();

                infoScreen.showGameOver();
                endGame = true;
            }
        }
    }

    @Override
    public void resize(int width, int height){


        float w = gameWorld.getGameCamera().getWorldHeight(); // Same as stage.getViewport().getWorldWidth()
        float h = gameWorld.getGameCamera().getWorldHeight();

        // Place the menu background in the middle of the screen
        background.setX(0);
        background.setY(0);
        background.setWidth(width);
        background.setHeight(height);
        startButton.setPosition(w / 2, h / 2);
        backgroundScreen.resize(width,height);
        gatesScreen.resize(width,height);
        hud.resize(width, height);
        infoScreen.resize(width, height);
        menuScreen.resize(width, height);
        gameWorld.getGameCamera().resize(width, height);
    }

    @Override
    public boolean isPlayScreenStateRunning() {
        return super.isPlayScreenStateRunning();
    }

    @Override
    public void pause() {
        AudioManager.getInstance().pauseMusic();
        doPause();
        prefs.setHero(gameWorld.getWaterElement());
        prefs.setBlockController(gameWorld.getBlockController());
     //   prefs.save();
    }

    public void doPause() {
        hideBannerAd();
        super.pause();
    }

    public void setStatePaused() {
        menuScreen.showMenuScreen(MenuScreen.MenuState.PAUSE);
       doPause();
    }

    public void setStateRunning() {
        gameStart = true;
        menuScreen.hideMenuScreen();
        Gdx.input.setInputProcessor(getInputProcessor());
        resume();
    }

    @Override
    public void resume() {
        AudioManager.getInstance().resumeMusic();
        if (!menuScreen.isMenuScreenVisible()) {
            showBannerAd();
            super.resume();
        }
    }

    @Override
    public void hide() {
        backgroundScreen.dispose();
        hud.dispose();
        infoScreen.dispose();
        menuScreen.dispose();
        worldController.dispose();
    }

    @Override
    protected void updateLogic(float deltaTime) {

    }

    @Override
    protected void renderLogic() {

    }

    @Override
    protected void goBack() {

    }

    @Override
    public InputProcessor getInputProcessor() {
        return worldController.getInputProcessor(new GameController(gameWorld,this));
    }

    @Override
    public void applyViewport() {
        hud.applyViewport();
        infoScreen.applyViewport();
        menuScreen.applyViewport();
        gameWorld.getGameCamera().applyViewport();
    }

    public Hud getHud() {
        return hud;
    }

    public InfoScreen getInfoScreen() {
        return infoScreen;
    }

    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public void setLevelIsCompleted() {
        levelCompleted = true;
    }
}
