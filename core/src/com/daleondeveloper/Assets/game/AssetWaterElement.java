package com.daleondeveloper.Assets.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Game.GameCamera;

public class AssetWaterElement implements IAssetSprite {
    private static final String TAG = AssetWaterElement.class.getName();

    private static final float SCALE = 0.7f;

    private TextureRegion elementOfWater;
    private TextureRegion newHero;
    private Animation elementOfWaterPush;
    private Animation elementOfWaterStand;
    private Animation elementOfWaterWalk;

    public AssetWaterElement(TextureAtlas atlas, TextureAtlas newAtlas){
        Array<TextureAtlas.AtlasRegion> regions;

        newHero = newAtlas.findRegion("Hero");

        elementOfWater = atlas.findRegion("elementOfWater",1);
        //animation
        regions = atlas.findRegions("elementOfWaterStand");
        elementOfWaterStand =new Animation(12f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        //animation
        regions = atlas.findRegions("elementOfWaterPush");
        elementOfWaterPush =new Animation(12f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        //animation
        regions = atlas.findRegions("elementOfWaterWalk");
        elementOfWaterWalk =new Animation(12f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();
    }

    @Override
    public float getWidth() {
        return (elementOfWater.getRegionWidth() / GameCamera.PPM) * SCALE;
    }

    @Override
    public float getHeight() {
        return (elementOfWater.getRegionHeight() / GameCamera.PPM) * SCALE;
    }

    public TextureRegion getElementOfWater() {
        return elementOfWater;
    }

    public TextureRegion getNewHero() {
        return newHero;
    }

    public Animation getElementOfWaterPush() {
        return elementOfWaterPush;
    }

    public Animation getElementOfWaterStand() {
        return elementOfWaterStand;
    }

    public Animation getElementOfWaterWalk() {
        return elementOfWaterWalk;
    }
}
