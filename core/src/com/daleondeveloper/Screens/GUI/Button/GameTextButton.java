package com.daleondeveloper.Screens.GUI.Button;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.daleondeveloper.Assets.Assets;

public abstract class GameTextButton extends ImageTextButton {

    public GameTextButton(String text){
        super(text,new ImageTextButton.ImageTextButtonStyle(
                new TextureRegionDrawable(Assets.getInstance().getAssetGUI().getButtonForPauseWindow()),
                new TextureRegionDrawable(Assets.getInstance().getAssetGUI().getButtonForPauseWindow()),
                new TextureRegionDrawable(Assets.getInstance().getAssetGUI().getButtonForPauseWindow()),
                Assets.getInstance().getAssetFonts().getSmall()));
        build();

    }
    public void build(){
        defineTexture();
        addAction();
    }
    public abstract void defineTexture();
    public abstract void addAction();
}
