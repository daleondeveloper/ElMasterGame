package com.daleondeveloper.Screens.GUI.Button;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Screens.ListenerHelper;

public class UpgradeTextButton extends GameTextButton {

    private GameSettings gameSettings;
    private Upgrader upgrader;

    public UpgradeTextButton(Upgrader upgrader) {
        super(upgrader.getInfo(), Assets.getInstance().getAssetFonts().getSmall());
        this.upgrader = upgrader;
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
                upgrader.upgradeLevel();

            }
        }));
    }
}
