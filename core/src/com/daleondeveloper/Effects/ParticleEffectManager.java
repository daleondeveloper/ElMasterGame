package com.daleondeveloper.Effects;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.IntMap;
import com.daleondeveloper.Assets.Assets;

public class ParticleEffectManager{
    private static final String TAG = ParticleEffectManager.class.getName();

    public static final int FIRE = 0;
    public static final int COLD_BLOCK_EFFECT = 1;
    public static final int FALL_EFFECT= 2;

    private static ParticleEffectManager instance;
    private IntMap<ParticleEffect> particleEffectIntMap;
    private IntMap<ParticleEffectPool> particleEffectPoolIntMap;

    private ParticleEffectManager (){
        particleEffectIntMap = new IntMap<ParticleEffect>();
        particleEffectPoolIntMap = new IntMap<ParticleEffectPool>();
        addParticleEffect(ParticleEffectManager.FIRE, Assets.getInstance().getAssetManager().get("effect/fire/fireeffect.p", ParticleEffect.class));
        addParticleEffect(ParticleEffectManager.COLD_BLOCK_EFFECT, Assets.getInstance().getAssetManager().get("effect/fire/cold_effect_under_block", ParticleEffect.class));
        addParticleEffect(ParticleEffectManager.FALL_EFFECT, Assets.getInstance().getAssetManager().get("effect/fire/fall_effect", ParticleEffect.class),0.05f);

    }
    public static ParticleEffectManager getInstance(){
        if(instance == null){
            instance = new ParticleEffectManager();
        }
        return instance;
    }
    public void addParticleEffect(int type, ParticleEffect party){
        addParticleEffect(type,party,1);
    }

    public void addParticleEffect(int type, ParticleEffect party, float scale ){
        addParticleEffect(type,party,scale,2,10);

    }

    public  void addParticleEffect(int type, ParticleEffect particleEffect, float scale, int startCapacity, int maxCapacity){
        particleEffect.scaleEffect(scale);
        particleEffectIntMap.put(type,particleEffect);
        particleEffectPoolIntMap.put(type,new ParticleEffectPool(particleEffect,startCapacity,maxCapacity));
    }

    public ParticleEffectPool.PooledEffect getPoolParticleEffect(int type){
        return particleEffectPoolIntMap.get(type).obtain();
    }
}
