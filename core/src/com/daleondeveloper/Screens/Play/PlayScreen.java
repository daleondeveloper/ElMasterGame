package com.daleondeveloper.Screens.Play;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.*;
import com.daleondeveloper.Screens.GUI.Hud;
import com.daleondeveloper.Screens.GUI.InfoScreen;
import com.daleondeveloper.Screens.GUI.PauseScreen;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.tools.AudioManager;

public class PlayScreen extends PlayAbstractScreen{
    private static final String TAG = PlayScreen.class.getName();

    private static final float SHAKE_DURATION = 2.0f;

    private Hud hud;
    private InfoScreen infoScreen;
    private PauseScreen pauseScreen;
    private Image background;
    private WorldController worldController;
    private GameWorld gameWorld;
    private WorldRenderer worldRenderer;
    private GameSettings prefs;
    private boolean endGame;
    private boolean levelCompleted;

    public PlayScreen(ElMaster game) {
        super(game);

        hud = new Hud(game);
        infoScreen = new InfoScreen(game,this);
        pauseScreen = new PauseScreen(game,this);


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
        hud.build();
        infoScreen.build();
        pauseScreen.build();
        background = new Image(Assets.getInstance().getAssetGame().getGameFon());

    }

    @Override
    public void render(float deltaTime){
        //Update logic
        pauseScreen.update(deltaTime);
        if(isPlayScreenStateRunning()){
            hud.update(deltaTime);
            infoScreen.update(deltaTime);
            worldController.update(deltaTime);
        }

        //Render logic

        worldRenderer.render();
        hud.render();
        infoScreen.render();
        pauseScreen.render();

        //Analys game result
        if(playScreenState == PlayScreenState.RUNNING){
            gameResults();
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
                //gameWorld.getGameCamera().shake(SHAKE_DURATION, true);
                gameWorld.getWaterElement().onDead();
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
        background.setWidth(w);
        background.setHeight(h);
        hud.resize(width, height);
        infoScreen.resize(width, height);
        pauseScreen.resize(width, height);
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
    }

    public void doPause() {
        hideBannerAd();
        super.pause();
    }

    public void setGameStatePaused() {
        pauseScreen.showPauseScreen();
    }

    public void setGameStateRunning() {
        pauseScreen.hidePauseScreen();
    }

    @Override
    public void resume() {
        AudioManager.getInstance().resumeMusic();
        if (!pauseScreen.isPauseScreenVisible()) {
            showBannerAd();
            super.resume();
        }
    }

    @Override
    public void hide() {
        hud.dispose();
        infoScreen.dispose();
        pauseScreen.dispose();
        worldController.dispose();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return worldController.getInputProcessor(new GameController(gameWorld,this));
    }

    @Override
    public void applyViewport() {
        hud.applyViewport();
        infoScreen.applyViewport();
        pauseScreen.applyViewport();
        gameWorld.getGameCamera().applyViewport();
    }

    public Hud getHud() {
        return hud;
    }

    public InfoScreen getInfoScreen() {
        return infoScreen;
    }

    public PauseScreen getPauseScreen() {
        return pauseScreen;
    }

    public void setLevelIsCompleted() {
        levelCompleted = true;
    }
}
