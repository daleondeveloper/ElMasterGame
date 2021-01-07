package com.daleondeveloper.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.Block;
import com.daleondeveloper.Sprites.BlockController;
import com.daleondeveloper.Sprites.Hero.WaterElement;


import java.util.ArrayList;
import java.util.List;

public class GameSettings {
    private static final String TAG = GameSettings.class.getName();

    private static final int DEF_COUNT_AD = 3;
    private static final String SETTINGS = "powerJumpSettings";
    private static final String HIGH_SCORE = "highScore";
    private static final String LAST_PLAY_SCORE = "lastPlayScore";
    private static final int DEFAULT_HIGH_SCORE = 0;
    private static final String BACKGROUND_ID = "backgroundId";
    private static final int INITIAL_BACKGROUND_ID = 1;
    private static final String AUDIO = "audio";
    private static final String PUSH_BUTTON_SHOW = "PUSH_BUTTON_SHOW";

    // Singleton: unique instance
    private static GameSettings instance;

    private PlayScreen playScreen;
    private int countdownAd; // No need to persist it
    private boolean showHelp; // No need to persist it
    private Preferences prefs;
    private int highScore;
    private int lastPlayScore;
    private int backgroundId;
    private boolean audio;
    private boolean push_button_show;

    private WaterElement hero;
    private float heroX;
    private float heroY;

    private BlockController blockController;
    private List<Vector2> blockVector;

    // Singleton: prevent instantiation from other classes
    private GameSettings() {
        countdownAd = DEF_COUNT_AD;
        showHelp = true;
        prefs = Gdx.app.getPreferences(SETTINGS);
        blockController = new BlockController(null,null);
        heroX = 100;
        heroY = 170;
        push_button_show = false;
        blockVector = new ArrayList<Vector2>();

    }

    // Singleton: retrieve instance
    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    public void load() {
        push_button_show = false;
        highScore = prefs.getInteger(HIGH_SCORE, DEFAULT_HIGH_SCORE);
        backgroundId = prefs.getInteger(BACKGROUND_ID, INITIAL_BACKGROUND_ID);
        audio = prefs.getBoolean(AUDIO, true);
        lastPlayScore = prefs.getInteger(LAST_PLAY_SCORE,0);
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
        for(int i = 0; i < prefs.getInteger("BLOCK_COUNT"); i++){
            blockVector.add(new Vector2(prefs.getFloat("BLOCK_" + i + "_POSITION_X"),
                    prefs.getFloat("BLOCK_" + i + "_POSITION_Y")));
        }
    }

    public void save() {
        prefs.clear();
        blockVector.clear();
        prefs.putInteger(HIGH_SCORE, highScore);
        prefs.putInteger(LAST_PLAY_SCORE,lastPlayScore);
        prefs.putInteger(BACKGROUND_ID, backgroundId);
        prefs.putBoolean(AUDIO, audio);
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
            prefs.putInteger("BLOCK_COUNT", blockController.getArrayBlock().size());
            for (int i = 0; i < blockController.getArrayBlock().size(); i++) {
                prefs.putFloat("BLOCK_" + i + "_POSITION_X", blockController.getArrayBlock().get(i).getX());
                prefs.putFloat("BLOCK_" + i + "_POSITION_Y", blockController.getArrayBlock().get(i).getY());
            }
        }
        prefs.flush();
    }
    public void deleteSave(){
        prefs.clear();
        prefs.putInteger(HIGH_SCORE, highScore);
        blockVector.clear();
//        prefs.putInteger("BLOCK_COUNT", 0);
//
//        prefs.putInteger("HERO_POSITION_X", 100);
//        prefs.putInteger("HERO_POSITION_Y", 200);

        prefs.flush();
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

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
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

    public List<Vector2> getBlockVector() {
        return blockVector;
    }

    public boolean isPush_button_show() {
        return push_button_show;
    }

    public void setPush_button_show(boolean push_button_show) {
        this.push_button_show = push_button_show;
    }
}
