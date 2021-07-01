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
    private TextureRegion blockDark;
    private TextureRegion blockWhite;
    private TextureRegion blockStar;
    private TextureRegion blockClassic;

    private Animation<TextureRegion> destroyFire;
    private Animation<TextureRegion> destroySnow;
    private Animation<TextureRegion> destroyWater;
    private Animation<TextureRegion> destroyLight;

    public  AssetBlock(TextureAtlas atlas){
        Array<TextureAtlas.AtlasRegion> regions;
        blockFire = atlas.findRegion("fire/main");
        blockSnow = atlas.findRegion("snow/main");
        blockWater = atlas.findRegion("water/main");
        blockLight = atlas.findRegion("light/light_main");
        blockDark = atlas.findRegion("dark/main");
        blockWhite = atlas.findRegion("white/main");
        blockStar = atlas.findRegion("star/main");
        blockClassic = atlas.findRegion("main");

        //animation
        regions = atlas.findRegions("fire/destroy/destroy");
        destroyFire =new Animation<TextureRegion>(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlas.findRegions("snow/destroy/snow");
        destroySnow =new Animation<TextureRegion>(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlas.findRegions("water/destroy/destroy");
        destroyWater =new Animation<TextureRegion>(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlas.findRegions("light/destroy/destroy");
        destroyLight =new Animation<TextureRegion>(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
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

    public TextureRegion getBlockDark() {
        return blockDark;
    }

    public TextureRegion getBlockWhite() {
        return blockWhite;
    }

    public TextureRegion getBlockClassic() {
        return blockClassic;
    }

    public TextureRegion getBlockStar() {
        return blockStar;
    }

    public Animation<TextureRegion> getDestroyFire() {
        return destroyFire;
    }

    public Animation<TextureRegion> getDestroySnow() {
        return destroySnow;
    }

    public Animation<TextureRegion> getDestroyWater() {
        return destroyWater;
    }

    public Animation<TextureRegion> getDestroyLight() {
        return destroyLight;
    }
}
