package com.daleondeveloper.Screens;

import com.daleondeveloper.Game.ElMaster;


public class ScreenManager {
    private static final String TAG = ScreenManager.class.getName();

    // Singleton: unique instance
    private static ScreenManager instance;

    // Reference to the game
    private ElMaster game;

    // Singleton: prevent instantiation from other classes
    private ScreenManager() {
    }

    // Singleton: retrieve instance
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    // Initialization with the game class
    public void initialize(ElMaster game) {
        this.game = game;
    }

    // Show in the game the screen which enum type is received
    public void showScreen(ScreenEnum screenEnum, ScreenTransitionEnum screenTransitionEnum, Object... params) {
        // Show new screen
        if (screenTransitionEnum != null) {
            game.setScreen(screenEnum.getScreen(game, params), screenTransitionEnum.getScreenTransition());
        } else {
            game.setScreen(screenEnum.getScreen(game, params));
        }
    }
}