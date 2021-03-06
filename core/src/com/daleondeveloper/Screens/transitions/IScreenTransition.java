package com.daleondeveloper.Screens.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IScreenTransition {
    float getDuration();
    void render(SpriteBatch batch, Texture currScreen, Texture nextScreen, float alpha);
}
