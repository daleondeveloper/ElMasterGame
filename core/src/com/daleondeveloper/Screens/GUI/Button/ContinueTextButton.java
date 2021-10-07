package com.daleondeveloper.Screens.GUI.Button;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;

public class ContinueTextButton extends GameTextButton {

    private GameSettings gameSettings;

    public ContinueTextButton() {
        super(Assets.getInstance().getI18NElementMaster().getI18NElmasterBundle().format("button.continue"));
        gameSettings = GameSettings.getInstance();
    }

    @Override
    public void defineTexture() {

    }

    @Override
    public void addAction() {
        this.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                gameSettings.setLevel(-1);
                gameSettings.setAdsContinueCount(1);
                gameSettings.loadIsInfinityLevel();
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_WHITE);
            }
        }));
    }
}
