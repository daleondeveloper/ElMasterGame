package com.daleondeveloper.Screens.GUI.Button;

import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.tools.Level.Level;

import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;

public class LevelButton extends GameTextButton {

    private GameSettings gameSettings;
    private int level;

    public LevelButton(int level) {
        super(String.valueOf(level));
        gameSettings = GameSettings.getInstance();
        this.level = level;
    }

    @Override
    public void defineTexture() {

    }

    @Override
    public void addAction() {
        this.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                gameSettings.setLevel(level);
                gameSettings.setAdsContinueCount(1);
                Level.savedLevel.delete();
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_WHITE);
            }
        }));
    }
}
