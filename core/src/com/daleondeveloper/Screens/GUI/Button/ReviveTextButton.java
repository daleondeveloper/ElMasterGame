package com.daleondeveloper.Screens.GUI.Button;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.Ads.AdsShower;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.ListenerHelper;

public class ReviveTextButton extends GameTextButton {

    private GameSettings gameSettings;

    public ReviveTextButton() {
        super(Assets.getInstance().getI18NElementMaster().getI18NElmasterBundle().format("button.revive"));
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
