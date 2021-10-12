package com.daleondeveloper.Screens.GUI.Button;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.daleondeveloper.Assets.Assets;

public abstract class GameTextButton extends ImageTextButton {

    public GameTextButton(String text, BitmapFont bitmapFont){
        super(text,new ImageTextButton.ImageTextButtonStyle(
                new TextureRegionDrawable(Assets.getInstance().getAssetGUI().getButtonForPauseWindow()),
                new TextureRegionDrawable(Assets.getInstance().getAssetGUI().getButtonForPauseWindow()),
                new TextureRegionDrawable(Assets.getInstance().getAssetGUI().getButtonForPauseWindow()),
                bitmapFont));
        build();
    }
    public GameTextButton(String text){
        this(text, Assets.getInstance().getAssetFonts().getNormal());
    }


    public void build(){
        defineTexture();
        addAction();
    }
    public abstract void defineTexture();
    public abstract void addAction();
}
