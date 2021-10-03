package com.daleondeveloper.Screens.GUI.Button;

import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.tools.Level.Level;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.Screens.ListenerHelper;

public class ChooseCheckpointButton extends GameTextButton {

    private GameSettings gameSettings;
    private MenuScreen menuScreen;

    public ChooseCheckpointButton(MenuScreen menuScreen) {
        super("CHOOSE CHECKPOINT");
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
                gameSettings.setInfinityLvl(false);
                Level.savedLevel.delete();
                menuScreen.setGameModeChangeScreen();
            }
        }));
    }
}
