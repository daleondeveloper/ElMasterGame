package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.daleondeveloper.Screens.GUI.Button.GameButton;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;

public class HelpMenuButton extends GameButton {

    private GUIOverlayAbstractScreen guiOverlayAbstractScreen;

    public HelpMenuButton(TextureRegionDrawable textureRegionDrawable,
                          GUIOverlayAbstractScreen guiOverlayAbstractScreen) {
        super(textureRegionDrawable);
        this.guiOverlayAbstractScreen = guiOverlayAbstractScreen;
    }

    @Override
    public void defineTexture() {

    }

    @Override
    public void addAction() {
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                guiOverlayAbstractScreen.show();
            }
        });
    }
}
