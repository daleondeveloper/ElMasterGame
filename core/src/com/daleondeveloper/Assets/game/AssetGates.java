package com.daleondeveloper.Assets.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.utils.Array;

public class AssetGates implements IAssetSprite {
    private static final String TAG = AssetGates.class.getName();

    private static final float SCALE = 0.7f;

    private TextureRegion staticMain;
    private TextureRegion closedGates;

    private Animation portal;
    private Animation leftFireBowl;
    private Animation rightFireBowl;
    private Animation mainDragonEyes;
    private Animation leftDragonEyes;
    private Animation rightDragonEyes;
    private Animation redDragonHead;
    private Animation redDragonLeftHandWithSphere;
    private Animation redDragonRightHandWithSphere;
    private Animation yellowDragonHead;
    private Animation yellowDragonLeftHandWithSphere;
    private Animation yellowDragonRightHandWithSphere;
    private Animation blueDragonHead;
    private Animation blueDragonLeftHandWithSphere;
    private Animation blueDragonRightHandWithSphere;
    private Animation blackDragonHead;
    private Animation blackDragonLeftHandWithSphere;
    private Animation blackDragonRightHandWithSphere;
    private Animation openGates;
    private Animation closeGates;


    public AssetGates(TextureAtlas atlasGates){
        Array<TextureAtlas.AtlasRegion> regions;

        staticMain = atlasGates.findRegion("");
        closedGates = atlasGates.findRegion("");

        //animation
        regions = atlasGates.findRegions("fire/destroy/destroy");
        portal =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        //BOWL
        regions = atlasGates.findRegions("fire/destroy/destroy");
        leftFireBowl =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("fire/destroy/destroy");
        rightFireBowl =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();


        //gates
        regions = atlasGates.findRegions("fire/destroy/destroy");
        openGates =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("fire/destroy/destroy");
        closeGates =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        dragonEyes(atlasGates);
        redDragon(atlasGates);
        yellowDragon(atlasGates);
        blueDragon(atlasGates);
        blackDragon(atlasGates);

    }
    private void dragonEyes(TextureAtlas atlasGates){
        Array<TextureAtlas.AtlasRegion> regions;

        //dragon
        regions = atlasGates.findRegions("fire/destroy/destroy");
        rightDragonEyes =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("fire/destroy/destroy");
        leftDragonEyes =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("fire/destroy/destroy");
        mainDragonEyes =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

    }
    private void redDragon(TextureAtlas atlasGates){
        Array<TextureAtlas.AtlasRegion> regions;

        //RED DRAGON
        regions = atlasGates.findRegions("fire/destroy/destroy");
        redDragonHead =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("fire/destroy/destroy");
        redDragonLeftHandWithSphere =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("fire/destroy/destroy");
        redDragonRightHandWithSphere =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

    }
    private void yellowDragon(TextureAtlas atlasGates){
        Array<TextureAtlas.AtlasRegion> regions;

        //YELLOW DRAGON
        regions = atlasGates.findRegions("fire/destroy/destroy");
        yellowDragonHead =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("fire/destroy/destroy");
        yellowDragonLeftHandWithSphere =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("fire/destroy/destroy");
        yellowDragonRightHandWithSphere =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

    }
    private void blueDragon(TextureAtlas atlasGates){
        Array<TextureAtlas.AtlasRegion> regions;

        //BLUE DRAGON
        regions = atlasGates.findRegions("fire/destroy/destroy");
        blueDragonHead =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("fire/destroy/destroy");
        blueDragonLeftHandWithSphere =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("fire/destroy/destroy");
        blueDragonRightHandWithSphere =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

    }
    private void blackDragon(TextureAtlas atlasGates){
        Array<TextureAtlas.AtlasRegion> regions;

        //BLACK DRAGON
        regions = atlasGates.findRegions("fire/destroy/destroy");
        blackDragonHead =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("fire/destroy/destroy");
        blackDragonLeftHandWithSphere =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("fire/destroy/destroy");
        blueDragonRightHandWithSphere =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();
    }


    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    public TextureRegion getStaticMain() {
        return staticMain;
    }

    public TextureRegion getClosedGates() {
        return closedGates;
    }

    public Animation getPortal() {
        return portal;
    }

    public Animation getLeftFireBowl() {
        return leftFireBowl;
    }

    public Animation getRightFireBowl() {
        return rightFireBowl;
    }

    public Animation getMainDragonEyes() {
        return mainDragonEyes;
    }

    public Animation getLeftDragonEyes() {
        return leftDragonEyes;
    }

    public Animation getRightDragonEyes() {
        return rightDragonEyes;
    }

    public Animation getRedDragonHead() {
        return redDragonHead;
    }

    public Animation getRedDragonLeftHandWithSphere() {
        return redDragonLeftHandWithSphere;
    }

    public Animation getRedDragonRightHandWithSphere() {
        return redDragonRightHandWithSphere;
    }

    public Animation getYellowDragonHead() {
        return yellowDragonHead;
    }

    public Animation getYellowDragonLeftHandWithSphere() {
        return yellowDragonLeftHandWithSphere;
    }

    public Animation getYellowDragonRightHandWithSphere() {
        return yellowDragonRightHandWithSphere;
    }

    public Animation getBlueDragonHead() {
        return blueDragonHead;
    }

    public Animation getBlueDragonLeftHandWithSphere() {
        return blueDragonLeftHandWithSphere;
    }

    public Animation getBlueDragonRightHandWithSphere() {
        return blueDragonRightHandWithSphere;
    }

    public Animation getBlackDragonHead() {
        return blackDragonHead;
    }

    public Animation getBlackDragonLeftHandWithSphere() {
        return blackDragonLeftHandWithSphere;
    }

    public Animation getBlackDragonRightHandWithSphere() {
        return blackDragonRightHandWithSphere;
    }

    public Animation getOpenGates() {
        return openGates;
    }

    public Animation getCloseGates() {
        return closeGates;
    }
}
