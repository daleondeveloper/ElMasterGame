package com.daleondeveloper.Effects;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.IntMap;

public class ParticleEffectManager {
    private static final String TAG = ParticleEffectManager.class.getName();

    public static final int FIRE = 0;

    private IntMap<ParticleEffect> particleEffectIntMap;
    private IntMap<ParticleEffectPool> particleEffectPoolIntMap;

    public ParticleEffectManager (){
        particleEffectIntMap = new IntMap<ParticleEffect>();
        particleEffectPoolIntMap = new IntMap<ParticleEffectPool>();
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
