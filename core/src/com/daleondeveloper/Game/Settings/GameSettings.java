package com.daleondeveloper.Game.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;
import com.daleondeveloper.Sprites.Hero.WaterElement;

import java.util.ArrayList;
import java.util.List;

public class GameSettings {
    private static final String TAG = GameSettings.class.getName();

    private static final int DEF_COUNT_AD = 3;
    private static final String SETTINGS = "elMasterSettings";
    private static final String HIGH_SCORE_CLASSIC = "highScoreClassic";
    private static final String HIGH_SCORE_FIRE = "highScoreFire";
    private static final String HIGH_SCORE_LIGHT = "highScoreLight";
    private static final String HIGH_SCORE_SNOW = "highScoreSnow";
    private static final String HIGH_SCORE_WATER = "highScoreWater";
    private static final String HIGH_SCORE_DARK = "highScoreDark";
    private static final String HIGH_SCORE_SPECIAL = "highScoreSpecial";
    private static final String LAST_PLAY_SCORE = "lastPlayScore";
    private static final int DEFAULT_HIGH_SCORE = 0;
    private static final String BACKGROUND_ID = "backgroundId";
    private static final int INITIAL_BACKGROUND_ID = 1;
    private static final String AUDIO = "audio";
    private static final String PUSH_BUTTON_SHOW = "pushButtonShow";

    private static final String GAME_MODE_DRAGON = "gameModeDragon";

    // Singleton: unique instance
    private static GameSettings instance;

    private PlayScreen playScreen;
    private int countdownAd; // No need to persist it
    private boolean showHelp; // No need to persist it
    private Preferences prefs;
    private int highScoreClassic;
    private int highScoreFire;
    private int highScoreLight;
    private int highScoreSnow;
    private int highScoreWater;
    private int highScoreDark;
    private int highScoreSpecial;
    private int lastPlayScore;
    private int backgroundId;
    private boolean audio;
    private boolean push_button_show;
    private boolean gameSave;
    private boolean[] helpModeShow;

    private WaterElement hero;
    private float heroX;
    private float heroY;

    private BlockController blockController;
    private List<BlockLoad> blockVector;

    private int gameModeDragon;

    // Singleton: prevent instantiation from other classes
    private GameSettings() {
        countdownAd = DEF_COUNT_AD;
        showHelp = true;
        prefs = Gdx.app.getPreferences(SETTINGS);
        blockController = new BlockController(null);
        heroX = 100;
        heroY = 170;
        push_button_show = false;
        blockVector = new ArrayList<BlockLoad>();
        helpModeShow = new boolean[7];
    }

    // Singleton: retrieve instance
    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    public void loadSettings() {
        push_button_show = false;
        highScoreClassic = prefs.getInteger(HIGH_SCORE_CLASSIC, DEFAULT_HIGH_SCORE);
        highScoreFire = prefs.getInteger(HIGH_SCORE_FIRE, DEFAULT_HIGH_SCORE);
        highScoreLight = prefs.getInteger(HIGH_SCORE_LIGHT, DEFAULT_HIGH_SCORE);
        highScoreSnow = prefs.getInteger(HIGH_SCORE_SNOW, DEFAULT_HIGH_SCORE);
        highScoreWater = prefs.getInteger(HIGH_SCORE_WATER, DEFAULT_HIGH_SCORE);
        highScoreDark = prefs.getInteger(HIGH_SCORE_DARK, DEFAULT_HIGH_SCORE);
        highScoreSpecial = prefs.getInteger(HIGH_SCORE_SPECIAL, DEFAULT_HIGH_SCORE);
        backgroundId = prefs.getInteger(BACKGROUND_ID, INITIAL_BACKGROUND_ID);
        audio = prefs.getBoolean(AUDIO, true);
        lastPlayScore = prefs.getInteger(LAST_PLAY_SCORE,0);
        if(lastPlayScore > 0){
            gameSave = true;
        }
        for(int i = 0; i < 7; i ++){
            helpModeShow[i] = prefs.getBoolean("HELP_MODE_SHOW" + i,true);
        }
        loadGameWorldParameters();
    }
    public void loadGameWorldParameters(){
        gameModeDragon = prefs.getInteger(GAME_MODE_DRAGON,0);
    }
    public void loadHero(){
        heroX = prefs.getFloat("HERO_POSITION_X");
        heroY = prefs.getFloat("HERO_POSITION_Y");
        if(heroX < 40) {
            heroX = 100;
        }
        if(heroY < 140) {
            heroY = 155;
        }
    }
    public void loadBlock(){
        blockVector.clear();
        for(int i = 0; i < prefs.getInteger("BLOCK_COUNT"); i++){
            gameSave = true;
            blockVector.add(new BlockLoad(prefs.getFloat("BLOCK_" + i + "_POSITION_X"),
                    prefs.getFloat("BLOCK_" + i + "_POSITION_Y"),
                    prefs.getInteger("BLOCK_" + i + "_TYPE")));
        }
    }

    public void save() {
        //prefs.clear();
        saveSetting();
        saveGameWorldObjects();
        saveGameWorldParameters();
        prefs.flush();
    }
    public void deleteSave(){
      // prefs.clear();
        for(int i = 0; i < prefs.getInteger("BLOCK_COUNT"); i++){
            prefs.remove("BLOCK_" + i + "_POSITION_X");
            prefs.remove("BLOCK_" + i + "_POSITION_Y");
            prefs.remove("BLOCK_" + i + "_TYPE");
        }
        prefs.putInteger("BLOCK_COUNT", 0);

        blockVector.clear();

        prefs.putInteger(LAST_PLAY_SCORE,0);

        prefs.flush();
        lastPlayScore = 0;

        gameSave = false;
    }

    public void saveSetting(){
        blockVector.clear();
        prefs.putInteger(HIGH_SCORE_CLASSIC, highScoreClassic);
        prefs.putInteger(HIGH_SCORE_FIRE, highScoreFire);
        prefs.putInteger(HIGH_SCORE_LIGHT, highScoreLight);
        prefs.putInteger(HIGH_SCORE_SNOW, highScoreSnow);
        prefs.putInteger(HIGH_SCORE_WATER, highScoreWater);
        prefs.putInteger(HIGH_SCORE_DARK, highScoreDark);
        prefs.putInteger(HIGH_SCORE_SPECIAL, highScoreSpecial);
        prefs.putInteger(LAST_PLAY_SCORE,lastPlayScore);
        prefs.putInteger(BACKGROUND_ID, backgroundId);
        prefs.putBoolean(AUDIO, audio);
        for(int i = 0; i < 7; i ++){
            prefs.putBoolean("HELP_MODE_SHOW" + i,helpModeShow[i]);
        }
    }
    public void saveGameWorldParameters(){
        prefs.putInteger(GAME_MODE_DRAGON,gameModeDragon);
    }
    public void saveGameWorldObjects(){
        if(hero != null && !hero.isDisposable() && hero.getBodyPosition() != null) {
            if(hero.getBodyPosition().x > 40) {
                prefs.putFloat("HERO_POSITION_X", hero.getX());
            }else {
                prefs.putInteger("HERO_POSITION_X", 100);

            }
            if(hero.getBodyPosition().y > 140) {
                prefs.putFloat("HERO_POSITION_Y", hero.getY());
            }else{
                prefs.putFloat("HERO_POSITION_Y", 170);

            }
        }
        if(blockController != null) {
            gameSave = true;
            prefs.putInteger("BLOCK_COUNT", blockController.getArrayBlock().size());
            for (int i = 0; i < blockController.getArrayBlock().size(); i++) {
                prefs.putFloat("BLOCK_" + i + "_POSITION_X", blockController.getArrayBlock().get(i).getX());
                prefs.putFloat("BLOCK_" + i + "_POSITION_Y", blockController.getArrayBlock().get(i).getY());
                prefs.putInteger("BLOCK_" + i + "_TYPE",blockController.getArrayBlock().get(i).getBlockTypeNumber());
            }
        }
    }

    public void decreaseCountdownAd() {
        countdownAd = countdownAd > 0 ? countdownAd - 1 : 0;
    }

    public void resetCountdownAd() {
        countdownAd = DEF_COUNT_AD;
    }

    public boolean isCountdownAdFinish() {
        return countdownAd <= 0;
    }

    public boolean mustShowHelp() {
        return showHelp;
    }

    public void setShowHelp(boolean showHelp) {
        this.showHelp = showHelp;
    }

    public int getHighScoreClassic() {
        return highScoreClassic;
    }

    public void setHighScoreClassic(int highScoreClassic) {
        this.highScoreClassic = highScoreClassic;
    }

    public int getHighScoreFire() {
        return highScoreFire;
    }

    public void setHighScoreFire(int highScoreFire) {
        this.highScoreFire = highScoreFire;
    }

    public int getHighScoreLight() {
        return highScoreLight;
    }

    public void setHighScoreLight(int highScoreLight) {
        this.highScoreLight = highScoreLight;
    }

    public int getHighScoreSnow() {
        return highScoreSnow;
    }

    public void setHighScoreSnow(int highScoreSnow) {
        this.highScoreSnow = highScoreSnow;
    }

    public int getHighScoreWater() {
        return highScoreWater;
    }

    public void setHighScoreWater(int highScoreWater) {
        this.highScoreWater = highScoreWater;
    }

    public int getHighScoreDark() {
        return highScoreDark;
    }

    public void setHighScoreDark(int highScoreDark) {
        this.highScoreDark = highScoreDark;
    }

    public int getHighScoreSpecial() {
        return highScoreSpecial;
    }

    public void setHighScoreSpecial(int highScoreSpecial) {
        this.highScoreSpecial = highScoreSpecial;
    }

    public int getLastPlayScore() {
        return lastPlayScore;
    }

    public void setLastPlayScore(int lastPlayScore) {
        this.lastPlayScore = lastPlayScore;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public boolean isAudio() {
        return audio;
    }

    public void setAudio(boolean audio) {
        this.audio = audio;
    }

    public BlockController getBlockController() {
        return blockController;
    }

    public void setBlockController(BlockController blockController) {
        this.blockController = blockController;
    }

    public WaterElement getHero() {
        return hero;
    }

    public void setHero(WaterElement hero) {
        this.hero = hero;
    }

    public float getHeroX() {
        return heroX;
    }

    public float getHeroY() {
        return heroY;
    }

    public List<BlockLoad> getBlockVector() {
        return blockVector;
    }

    public boolean isPush_button_show() {
        return push_button_show;
    }

    public void setPush_button_show(boolean push_button_show) {
        this.push_button_show = push_button_show;
    }

    public int getGameModeDragon() {
        return gameModeDragon;
    }

    public void setGameModeDragon(int gameModeDragon) {
        this.gameModeDragon = gameModeDragon;
    }

    public boolean isGameSave() {
        return gameSave;
    }

    public boolean[] getHelpModeShow() {
        return helpModeShow;
    }
}
