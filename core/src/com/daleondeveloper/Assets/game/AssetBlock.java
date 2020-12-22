package com.daleondeveloper.Assets.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Game.GameCamera;

public class AssetBlock implements IAssetSprite {
    private static final String TAG = AssetBlock.class.getName();

    private static final float SCALE = 0.7f;

    private TextureRegion blockFire;
    private TextureRegion blockSnow;
    private TextureRegion blockWater;
    private TextureRegion blockLight;

    private Animation destroyFire;
    private Animation destroySnow;
    private Animation destroyWater;
    private Animation destroyLight;

    public  AssetBlock(TextureAtlas atlas){
        Array<TextureAtlas.AtlasRegion> regions;

        blockFire = atlas.findRegion("fire/main");
        blockSnow = atlas.findRegion("snow/main");
        blockWater = atlas.findRegion("water/main");
        blockLight = atlas.findRegion("light/light_main");

        //animation
        regions = atlas.findRegions("fire/destroy/destroy");
        destroyFire =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlas.findRegions("snow/destroy/snow");
        destroySnow =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlas.findRegions("water/destroy/destroy");
        destroyWater =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlas.findRegions("light/destroy/destroy");
        destroyLight =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

    }

    @Override
    public float getWidth() {
        return (blockFire.getRegionWidth() / GameCamera.PPM) * SCALE;
    }

    @Override
    public float getHeight() {
        return (blockFire.getRegionWidth() / GameCamera.PPM) * SCALE;
    }

    public TextureRegion getBlockFire() {
        return blockFire;
    }

    public TextureRegion getBlockSnow() {
        return blockSnow;
    }

    public TextureRegion getBlockWater() {
        return blockWater;
    }

    public TextureRegion getBlockLight() {
        return blockLight;
    }

    public Animation getDestroyFire() {
        return destroyFire;
    }

    public Animation getDestroySnow() {
        return destroySnow;
    }

    public Animation getDestroyWater() {
        return destroyWater;
    }

    public Animation getDestroyLight() {
        return destroyLight;
    }
}
