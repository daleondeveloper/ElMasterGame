package com.daleondeveloper.Assets.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Assets.Assets;

public class AssetGates implements IAssetSprite {
    private static final String TAG = AssetGates.class.getName();

    private static final float SCALE = 0.7f;

    private enum DragonName{
        RED_DRAGON,BLUE_DRAGON,BLACK_DRAGON,YELLOW_DRAGON;
    }
    private DragonName dragonName;

    private TextureRegion staticMain;
    private TextureRegion closedGates;
    private TextureRegion dragonHead;
    private TextureRegion dragonLeftHandWithSphere;
    private TextureRegion dragonRightHandWithSphere;
    private TextureRegion redDragonHead;
    private TextureRegion redDragonLeftHandWithSphere;
    private TextureRegion redDragonRightHandWithSphere;
    private TextureRegion yellowDragonHead;
    private TextureRegion yellowDragonLeftHandWithSphere;
    private TextureRegion yellowDragonRightHandWithSphere;
    private TextureRegion blueDragonHead;
    private TextureRegion blueDragonLeftHandWithSphere;
    private TextureRegion blueDragonRightHandWithSphere;
    private TextureRegion blackDragonHead;
    private TextureRegion blackDragonLeftHandWithSphere;
    private TextureRegion blackDragonRightHandWithSphere;

    private Animation portal;
    private Animation leftFireBowl;
    private Animation rightFireBowl;
    private Animation mainDragonEyes;
    private Animation leftDragonEyes;
    private Animation rightDragonEyes;
    private Animation openGates;
    private Animation closeGates;


    public AssetGates(TextureAtlas atlasGates){
        Array<TextureAtlas.AtlasRegion> regions;

        dragonName = DragonName.BLACK_DRAGON;
        staticMain = atlasGates.findRegion("gates_stairs_colum&dragon");
        closedGates = atlasGates.findRegion("open/gate_open",1);

        //animation
        regions = atlasGates.findRegions("portal/portal");
        portal =new Animation(2.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        //BOWL
        regions = atlasGates.findRegions("bowl/left/left_bowl");
        leftFireBowl =new Animation(3.0f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("bowl/right/right_bowl");
        rightFireBowl =new Animation(3.0f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();


        //gates
        regions = atlasGates.findRegions("open/gate_open");
        openGates =new Animation(0.5f/24.0f,regions, Animation.PlayMode.NORMAL);
        regions.clear();

        regions = atlasGates.findRegions("open/gate_open");
        closeGates =new Animation(0.5f/24.0f,regions, Animation.PlayMode.REVERSED);
        regions.clear();

        dragonEyes(atlasGates);
        redDragon(atlasGates);
        yellowDragon(atlasGates);
        blueDragon(atlasGates);
        blackDragon(atlasGates);

        changeBlackDragon();

    }
    public void changeBlackDragon(){
        dragonName = DragonName.BLACK_DRAGON;
        dragonHead = blackDragonHead;
        dragonLeftHandWithSphere = blackDragonLeftHandWithSphere;
        dragonRightHandWithSphere = blackDragonRightHandWithSphere;
    }
    public void changeBlueDragon(){
        dragonName = DragonName.BLUE_DRAGON;
        dragonHead = blueDragonHead;
        dragonLeftHandWithSphere = blueDragonLeftHandWithSphere;
        dragonRightHandWithSphere = blueDragonRightHandWithSphere;
    }public void changeYellowDragon(){
        dragonName = DragonName.YELLOW_DRAGON;
        dragonHead = yellowDragonHead;
        dragonLeftHandWithSphere = yellowDragonLeftHandWithSphere;
        dragonRightHandWithSphere = yellowDragonRightHandWithSphere;
    }public void changeRedDragon(){
        dragonName = DragonName.RED_DRAGON;
        dragonHead = redDragonHead;
        dragonLeftHandWithSphere = redDragonLeftHandWithSphere;
        dragonRightHandWithSphere = redDragonRightHandWithSphere;
    }
    private void dragonEyes(TextureAtlas atlasGates){
        Array<TextureAtlas.AtlasRegion> regions;

        //dragon
        regions = atlasGates.findRegions("eyes_backlight/right/backlight_dragon_right");
        rightDragonEyes =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("eyes_backlight/left/backlight_dragon_left");
        leftDragonEyes =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("eyes_backlight/main/backlight_dragon_main");
        mainDragonEyes =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

    }
    private void redDragon(TextureAtlas atlasGates){
        redDragonHead = atlasGates.findRegion("dragon/red/red_dragon_head");
        redDragonLeftHandWithSphere = atlasGates.findRegion("dragon/red/red_dragon_left_hand_sphere");
        redDragonRightHandWithSphere = atlasGates.findRegion("dragon/red/red_dragon_right_hand_sphere");
    }
    private void yellowDragon(TextureAtlas atlasGates){
        yellowDragonHead = atlasGates.findRegion("dragon/yellow/yellow_dragon_head");
        yellowDragonLeftHandWithSphere = atlasGates.findRegion("dragon/yellow/yellow_dragon_left_hand_sphere");
        yellowDragonRightHandWithSphere = atlasGates.findRegion("dragon/yellow/yellow_dragon_right_hand_sphere");
    }
    private void blueDragon(TextureAtlas atlasGates){
        blueDragonHead = atlasGates.findRegion("dragon/blue/blue_dragon_head");
        blueDragonLeftHandWithSphere = atlasGates.findRegion("dragon/blue/blue_dragon_left_hand_sphere");
        blueDragonRightHandWithSphere = atlasGates.findRegion("dragon/blue/blue_dragon_right_hand_sphere");
    }
    private void blackDragon(TextureAtlas atlasGates){
        blackDragonHead = atlasGates.findRegion("dragon/black/black_dragon_head");
        blackDragonLeftHandWithSphere = atlasGates.findRegion("dragon/black/black_dragon_left_hand_sphere");
        blackDragonRightHandWithSphere = atlasGates.findRegion("dragon/black/black_dragon_right_hand_sphere");
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

    public DragonName getDragonName() {
        return dragonName;
    }

    public TextureRegion getDragonHead() {
        return dragonHead;
    }

    public TextureRegion getDragonLeftHandWithSphere() {
        return dragonLeftHandWithSphere;
    }

    public TextureRegion getDragonRightHandWithSphere() {
        return dragonRightHandWithSphere;
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

    public Animation getOpenGates() {
        return openGates;
    }

    public Animation getCloseGates() {
        return closeGates;
    }


}
