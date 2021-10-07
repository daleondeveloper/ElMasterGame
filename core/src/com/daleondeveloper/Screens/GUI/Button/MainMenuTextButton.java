package com.daleondeveloper.Screens.GUI.Button;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.tools.Level.Level;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;

public class MainMenuTextButton extends GameTextButton {

    private GameSettings gameSettings;

    public MainMenuTextButton() {
        super(Assets.getInstance().getI18NElementMaster().getI18NElmasterBundle().format("button.mainMenu"));
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
                Level.savedLevel.delete();
                ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK);
            }
        }));
    }
}
