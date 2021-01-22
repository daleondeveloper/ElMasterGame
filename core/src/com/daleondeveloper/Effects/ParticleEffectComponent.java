package com.daleondeveloper.Effects;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;


public class ParticleEffectComponent implements Pool.Poolable {
    private static final  String TAG = ParticleEffectComponent.class.getName();

    public ParticleEffectPool.PooledEffect particleEffect;
    public boolean isAttached = false;
    public float xOffSet = 0;
    public float yOffSet = 0;
    public float timeTilDeath = 0.5f;
    public boolean isDead = false;
    public Body attachedBody;

    @Override
    public void reset() {
        particleEffect.free();
        particleEffect = null;
        xOffSet = 0;
        yOffSet = 0;
        isAttached = false;
        isDead = false;
        attachedBody = null;
        timeTilDeath = 0.5f;
    }
}
