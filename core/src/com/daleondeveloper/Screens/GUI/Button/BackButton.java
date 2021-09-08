package com.daleondeveloper.Screens.GUI.Button;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.Screens.GUI.Button.GameButton;
import com.daleondeveloper.Screens.ListenerHelper;

public class BackButton extends GameButton {

    private MenuScreen menuScreen;

    public BackButton(MenuScreen menuScreen) {
        super(new TextureRegionDrawable(Assets.getInstance().getAssetGUI().getButtonX()));
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
                menuScreen.hideMenuScreen();
            }
        }));
    }
}
