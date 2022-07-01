package com.daleondeveloper.Assets.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AssetGates implements IAssetSprite {
    private static final String TAG = AssetGates.class.getName();

    private static final float SCALE = 0.7f;

    private enum DragonName{
        RED_DRAGON,BLUE_DRAGON,BLACK_DRAGON,YELLOW_DRAGON;
    }
    private DragonName dragonName;

    private TextureRegion staticMain;
    private TextureRegion play_background;
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

    private Animation<TextureRegion> portal;
    private TextureRegion leftFireBowl;
    private TextureRegion leftFireBowlUp;
    private TextureRegion rightFireBowl;
    private TextureRegion rightFireBowlUp;
    private Animation<TextureRegion> mainDragonEyes;
    private Animation<TextureRegion> leftDragonEyes;
    private Animation<TextureRegion> rightDragonEyes;
    private Animation<TextureRegion> openGates;
    private Animation<TextureRegion> closeGates;


    public AssetGates(TextureAtlas atlasGates){
        Array<TextureAtlas.AtlasRegion> regions;

        dragonName = DragonName.BLACK_DRAGON;
        staticMain = atlasGates.findRegion("gates_stairs_colum&dragon");
        closedGates = atlasGates.findRegion("gate_open",1);
        play_background =atlasGates.findRegion("play_block_background");

        //animation
        regions = atlasGates.findRegions("portal");
        portal =new Animation<TextureRegion>(0.5f/24.0f,regions, Animation.PlayMode.NORMAL);
        regions.clear();

        //BOWL
        leftFireBowl = atlasGates.findRegion("left/left_bowl_back");
        leftFireBowlUp = atlasGates.findRegion("left/left_bowl_up");

        rightFireBowl = atlasGates.findRegion("right/right_bowl_back");
        rightFireBowlUp = atlasGates.findRegion("right/right_bowl_up");

        //gates
        regions = atlasGates.findRegions("open/gate_open");
        openGates =new Animation<TextureRegion>(0.5f/24.0f,regions, Animation.PlayMode.NORMAL);
        regions.clear();

        regions = atlasGates.findRegions("gate_open");
        closeGates =new Animation<TextureRegion>(0.5f/24.0f,regions, Animation.PlayMode.REVERSED);
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
        regions = atlasGates.findRegions("right/backlight_dragon_right");
        rightDragonEyes =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("left/backlight_dragon_left");
        leftDragonEyes =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGates.findRegions("main/backlight_dragon_main");
        mainDragonEyes =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

    }
    private void redDragon(TextureAtlas atlasGates){
        redDragonHead = atlasGates.findRegion("red/red_dragon_head");
        redDragonLeftHandWithSphere = atlasGates.findRegion("red/red_dragon_left_hand_sphere");
        redDragonRightHandWithSphere = atlasGates.findRegion("red/red_dragon_right_hand_sphere");
    }
    private void yellowDragon(TextureAtlas atlasGates){
        yellowDragonHead = atlasGates.findRegion("yellow/yellow_dragon_head");
        yellowDragonLeftHandWithSphere = atlasGates.findRegion("yellow/yellow_dragon_left_hand_sphere");
        yellowDragonRightHandWithSphere = atlasGates.findRegion("yellow/yellow_dragon_right_hand_sphere");
    }
    private void blueDragon(TextureAtlas atlasGates){
        blueDragonHead = atlasGates.findRegion("blue/blue_dragon_head");
        blueDragonLeftHandWithSphere = atlasGates.findRegion("blue/blue_dragon_left_hand_sphere");
        blueDragonRightHandWithSphere = atlasGates.findRegion("blue/blue_dragon_right_hand_sphere");
    }
    private void blackDragon(TextureAtlas atlasGates){
        blackDragonHead = atlasGates.findRegion("black/black_dragon_head");
        blackDragonLeftHandWithSphere = atlasGates.findRegion("black/black_dragon_left_hand_sphere");
        blackDragonRightHandWithSphere = atlasGates.findRegion("black/black_dragon_right_hand_sphere");
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

    public TextureRegion getPlay_background() {
        return play_background;
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

    public Animation<TextureRegion> getPortal() {
        return portal;
    }

    public TextureRegion getLeftFireBowl() {
        return leftFireBowl;
    }

    public TextureRegion getLeftFireBowlUp() {
        return leftFireBowlUp;
    }

    public TextureRegion getRightFireBowl() {
        return rightFireBowl;
    }

    public TextureRegion getRightFireBowlUp() {
        return rightFireBowlUp;
    }

    public Animation<TextureRegion> getMainDragonEyes() {
        return mainDragonEyes;
    }

    public Animation<TextureRegion> getLeftDragonEyes() {
        return leftDragonEyes;
    }

    public Animation<TextureRegion> getRightDragonEyes() {
        return rightDragonEyes;
    }

    public Animation<TextureRegion> getOpenGates() {
        return openGates;
    }

    public Animation<TextureRegion> getCloseGates() {
        return closeGates;
    }


}
