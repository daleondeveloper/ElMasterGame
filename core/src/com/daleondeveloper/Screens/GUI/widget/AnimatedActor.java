package com.daleondeveloper.Screens.GUI.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnimatedActor extends Actor {
    private static final String TAG = AnimatedActor.class.getName();

    private Animation<TextureRegion> animation;
    private TextureRegion textureRegion;
    private float stateTime;
    private Color tint;

    public AnimatedActor(Animation<TextureRegion> animation) {
        init(animation, false, 1.0f, Color.WHITE);
    }

    public AnimatedActor(Animation<TextureRegion> animation, boolean randomStart) {
        init(animation, randomStart, 1.0f, Color.WHITE);
    }

    public AnimatedActor(Animation<TextureRegion> animation, boolean randomStart, float scale) {
        init(animation, randomStart, scale, Color.WHITE);
    }

    public AnimatedActor(Animation<TextureRegion> animation, boolean randomStart, float scale, Color tint) {
        init(animation, randomStart, scale, tint);
    }

    private void init(Animation<TextureRegion> animation, boolean randomStart, float scale, Color tint) {
        this.animation = animation;
        stateTime = randomStart ? MathUtils.random(0, animation.getAnimationDuration()) : 0;
        textureRegion = (TextureRegion) animation.getKeyFrame(0f);
        setSize(textureRegion.getRegionWidth() * scale, textureRegion.getRegionHeight() * scale);
        this.tint = tint;
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        textureRegion = (TextureRegion) animation.getKeyFrame(stateTime);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(tint);
        batch.draw(textureRegion, getX(), getY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }
}
