package com.daleondeveloper.Screens.GUI.Button;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.tools.Level.Level;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;

public class TutorialTextButton extends GameTextButton {

    private GameSettings gameSettings;
    private MenuScreen menuScreen;

    public TutorialTextButton(MenuScreen menuScreen) {
        super(Assets.getInstance().getI18NElementMaster().getI18NElmasterBundle().format("button.tutorial"));
        gameSettings = GameSettings.getInstance();
        this.menuScreen = menuScreen;
    }

    @Override
    public void defineTexture() {

    }

    @Override
    public void addAction() {
        this.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                gameSettings.setLevel(0);
                gameSettings.setAdsContinueCount(1);
                Level.savedLevel.delete();
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_WHITE);
            }
        }));
    }
}
