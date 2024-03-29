package com.daleondeveloper.Screens.Play;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Effects.ParticleEffectManager;
import com.daleondeveloper.Game.Ads.AdsShower;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.GameController;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.WorldController;
import com.daleondeveloper.Game.WorldRenderer;
import com.daleondeveloper.Screens.GUI.BackgroundScreen;
import com.daleondeveloper.Screens.GUI.GatesScreen;
import com.daleondeveloper.Screens.GUI.Hud;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.Screens.GUI.TeachingHud;
import com.daleondeveloper.Screens.GUI.filler.UpgradeLevelMenuFiller;
import com.daleondeveloper.Screens.GUIAbstractScreen;
import com.daleondeveloper.tools.AudioManager;

public class PlayScreen extends GUIAbstractScreen {
    private static final String TAG = PlayScreen.class.getName();

    private static final float SHAKE_DURATION = 2.0f;

    private Hud hud;
    private TeachingHud teachingHud;
    private MenuScreen menuScreen;
    private BackgroundScreen backgroundScreen;
    private GatesScreen gatesScreen;
    private boolean gameStart;
    private ParticleEffectManager pef;

    private float stateTime;

    private Array<ParticleEffectPool.PooledEffect> pooledEffects = new Array<ParticleEffectPool.PooledEffect>();
    private Image background;
    private WorldController worldController;
    private GameWorld gameWorld;
    private WorldRenderer worldRenderer;
    private GameSettings prefs;

    public PlayScreen(ElMaster game) {
        super(game);


        hud = new Hud(game,this,this);
        teachingHud = new TeachingHud(game,this);
        menuScreen = new MenuScreen(game,this);
        backgroundScreen = new BackgroundScreen(game,this);
        gatesScreen = new GatesScreen(game,this);
        gameStart = false;
        pef = ParticleEffectManager.getInstance();

        stateTime = 0;


        worldController = new WorldController(this);
        gameWorld = worldController.getGameWorld();
        worldRenderer = new WorldRenderer(gameWorld,game.getGameBatch(),game.getGameShapeRenderer(),game.getBox2DDebugRenderer());

        prefs = GameSettings.getInstance();

        AudioManager.getInstance().playMusic(Assets.getInstance().getAssetMusic().getSongGame());
    }


    @Override
    public void show(){
        Assets.getInstance().getAssetGates().changeBlackDragon();
        backgroundScreen.build();
        gatesScreen.build();
        hud.build();
        teachingHud.build();
        menuScreen.build();
        background = new Image(Assets.getInstance().getAssetGates().getStaticMain());
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            game.getAnaliticsController().levelStart(prefs.getLevel());
        }
    }


    @Override
    public void render(float deltaTime){
        stateTime += deltaTime;
        //Update logic
        stage.act();
        updateLogic(deltaTime);
        backgroundScreen.update(deltaTime);

        menuScreen.update(deltaTime);
        gatesScreen.update(deltaTime);
        hud.update(deltaTime);
        if(isPlayScreenStateRunning()){
            worldController.update(deltaTime);
        }
        backgroundScreen.render();
        worldRenderer.render();
        gatesScreen.render();
        hud.render();
        if(prefs.getLevel() == 0){
            teachingHud.update(deltaTime);
            teachingHud.render();
        }
        menuScreen.render();
        stage.draw();
        //Analys game result
        if(guiScreenState == GUIScreenState.RUNNING){
            gameResults();
        }
//        if(stateTime > 1f && stateTime < 1.2f && prefs.getLevel() >= 0){
//            menuScreen.setTeacherMenuFiller();
//            doPause();
//
//        }
        if(!gameStart){
            doPause();
        }

        if(menuScreen.getMenuState() == MenuScreen.MenuState.GAME_OVER){
            if(AdsShower.getInstance().isAdsWatched()){
                gameWorld.revive();
                AdsShower.getInstance().setAdsWatched(false);
                setStateRunning();
                System.out.println("revive");
            }
        }
    }
    public void upgradeLevel(){
        menuScreen.setScreen(new UpgradeLevelMenuFiller(menuScreen,gameWorld.getScoreCheker()));
        doPause();
    }
    private void gameResults(){

        if (worldController.isGameOver()) {
            menuScreen.setGameOverScreen();
            doPause();
        }
    }

    public void showNextLvlMenu(){
        pause();
        menuScreen.setLvlCompleteMF();
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            game.getAnaliticsController().levelEnd(prefs.getLevel(), (long) stateTime);
        }
    }

    @Override
    public void resize(int width, int height){
        // Place the menu background in the middle of the screen
        background.setX(0);
        background.setY(0);
        background.setWidth(width);
        background.setHeight(height);
        backgroundScreen.resize(width,height);
        gatesScreen.resize(width,height);
        hud.resize(width, height);
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
    }

    public void doPause() {
        hideBannerAd();

        worldController.pause();
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

    public void showAds(){
        if(game.getAdsController() != null){
            game.getAdsController().showRewardedVideo();
        }
    }

    @Override
    public InputProcessor getInputProcessor() {
        return worldController.getInputProcessor(new GameController(gameWorld,this));
    }

    @Override
    public void applyViewport() {
        hud.applyViewport();
        menuScreen.applyViewport();
        gameWorld.getGameCamera().applyViewport();
    }

    public Hud getHud() {
        return hud;
    }

    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    public TeachingHud getTeachingHud() {
        return teachingHud;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public ParticleEffectManager getPef() {
        return pef;
    }
}
