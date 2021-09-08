package com.daleondeveloper.Screens.GUI.Button;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class GameButton extends ImageButton {

    public GameButton(TextureRegionDrawable textureRegionDrawable){
        super(textureRegionDrawable);
        build();

    }
    public void build(){
        defineTexture();
        addAction();
    }
    public abstract void defineTexture();
    public abstract void addAction();
}
