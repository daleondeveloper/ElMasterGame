package com.daleondeveloper.Screens.GUI.Button;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.Screens.ListenerHelper;

public class SettingsTextButton extends GameTextButton {

    private GameSettings gameSettings;
    private MenuScreen menuScreen;

    public SettingsTextButton(MenuScreen menuScreen) {
        super(Assets.getInstance().getI18NElementMaster().getI18NElmasterBundle().format("button.settings"));
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
                menuScreen.setSettingsScreen();
            }
        }));
    }
}
