package com.daleondeveloper.Game.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSettings {
    private static final String TAG = GameSettings.class.getName();

    private static final int DEF_COUNT_AD = 3;
    private static final int DEFAULT_HIGH_SCORE = 0;
    private static final int INITIAL_BACKGROUND_ID = 1;
    private static final String SETTINGS = "elMasterSettings";
    private static final String HIGH_SCORE_CLASSIC = "highScoreClassic";
    private static final String HIGH_COMPLETED_LEVEL = "highCompletedLevel";
    private static final String LAST_PLAY_SCORE = "lastPlayScore";
    private static final String BACKGROUND_ID = "backgroundId";
    private static final String AUDIO = "audio";

    private static final String LEVEL_SAVED_1 = "levelSaved1";
    private static final String INFINITY_LVL = "infinityLvl";
    // Singleton: unique instance
    private static GameSettings instance;

    private String savedLevel;
    private int countdownAd; // No need to persist it
    private boolean showHelp; // No need to persist it
    private Preferences prefs;
    private int highScore;
    private int lastPlayScore;
    private int backgroundId;
    private boolean audio;
    private boolean push_button_show;
    private boolean gameSave;
    private boolean[] helpModeShow;
    private boolean infinityLvl;
    private int level;
    private int highCompletedLvl;

    // Singleton: prevent instantiation from other classes
    private GameSettings() {
        countdownAd = DEF_COUNT_AD;
        showHelp = true;
        prefs = Gdx.app.getPreferences(SETTINGS);
        push_button_show = false;
        helpModeShow = new boolean[7];
        level = 0;
        highCompletedLvl = 0;
        infinityLvl = false;
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
        highScore = prefs.getInteger(HIGH_SCORE_CLASSIC, DEFAULT_HIGH_SCORE);
        audio = prefs.getBoolean(AUDIO, true);
        lastPlayScore = prefs.getInteger(LAST_PLAY_SCORE,0);
        highCompletedLvl = 30;
                //prefs.getInteger(HIGH_COMPLETED_LEVEL);
        if(lastPlayScore > 0){
            gameSave = true;
        }
        for(int i = 0; i < 7; i ++){
            helpModeShow[i] = prefs.getBoolean("HELP_MODE_SHOW" + i,true);
        }
    }

    public String loadSavedLevel(){
        savedLevel = prefs.getString(LEVEL_SAVED_1);
        return savedLevel;
    }
    public void loadIsInfinityLevel(){
        infinityLvl = prefs.getBoolean(INFINITY_LVL);
    }

    public void saveSetting(){
        prefs.putInteger(HIGH_SCORE_CLASSIC, highScore);
        prefs.putInteger(LAST_PLAY_SCORE,lastPlayScore);
        prefs.putInteger(BACKGROUND_ID, backgroundId);
        prefs.putBoolean(AUDIO, audio);
        for(int i = 0; i < 7; i ++){
            prefs.putBoolean("HELP_MODE_SHOW" + i,helpModeShow[i]);
        }
        prefs.flush();
    }
    public void saveNewBestScore(int score){
        prefs.putInteger(HIGH_SCORE_CLASSIC,score);
        highScore = score;
        prefs.flush();
    }
    public void saveCurrentLevel(String level){
        prefs.putString(LEVEL_SAVED_1,level);
        savedLevel = level;
        prefs.putBoolean(INFINITY_LVL,infinityLvl);
        prefs.putInteger(HIGH_COMPLETED_LEVEL,highCompletedLvl);
        prefs.flush();
    }
    public void clearSaveLevel(){
        prefs.putString(LEVEL_SAVED_1,"");
        savedLevel = "";
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

    public int getHighScoreClassic() {
        return highScore;
    }

    public void setHighScoreClassic(int highScoreClassic) {
        this.highScore = highScoreClassic;
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

    public boolean isPush_button_show() {
        return push_button_show;
    }

    public void setPush_button_show(boolean push_button_show) {
        this.push_button_show = push_button_show;
    }

    public boolean isGameSave() {
        return gameSave;
    }

    public boolean[] getHelpModeShow() {
        return helpModeShow;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHighCompletedLvl() {
        return highCompletedLvl;
    }

    public void setHighCompletedLvl(int highCompletedLvl) {
        this.highCompletedLvl = highCompletedLvl;
    }

    public void setSavedLevel(String savedLevel) {
        this.savedLevel = savedLevel;
    }

    public String getSavedLevel() {
        return savedLevel;
    }

    public boolean isInfinityLvl() {
        return infinityLvl;
    }

    public void setInfinityLvl(boolean infinityLvl) {
        this.infinityLvl = infinityLvl;
    }
}
