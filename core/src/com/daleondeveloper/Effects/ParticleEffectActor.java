package com.daleondeveloper.Effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {
    ParticleEffect particleEffect;


    public ParticleEffectActor(ParticleEffect particleEffect) {
        super();
        this.particleEffect = particleEffect;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        particleEffect.draw(batch);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        particleEffect.update(delta);
    }

    public void start() {
        particleEffect.start();
    }

    public void allowCompletion() {
        particleEffect.allowCompletion();
    }
}
