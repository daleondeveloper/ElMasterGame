package com.daleondeveloper.Screens.GUI.Button;

import com.daleondeveloper.Game.Ads.AdsShower;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.Button.GameTextButton;
import com.daleondeveloper.Screens.ListenerHelper;

public class ReviveTextButton extends GameTextButton {

    private GameSettings gameSettings;

    public ReviveTextButton() {
        super("Revive (Ads)");
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
                AdsShower.getInstance().showAds();
            }
        }));
    }
}
