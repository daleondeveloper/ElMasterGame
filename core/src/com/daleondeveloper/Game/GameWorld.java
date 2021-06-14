package com.daleondeveloper.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.tools.GameGrid;
import com.daleondeveloper.Game.tools.LevelGenerator;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.Background;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;
import com.daleondeveloper.Sprites.Blocks.Block;
import com.daleondeveloper.Sprites.GameSensor;
import com.daleondeveloper.Sprites.Hero.WaterElement;
import com.daleondeveloper.Sprites.Platform;
import com.daleondeveloper.tools.GameConstants;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {
    private static final String TAG = GameWorld.class.getName();

    private static final float DOWN_REGION = 150;
    private static final float TIME_TO_SAVE = 30;

    private enum GameMode{
        CLASSIC, LIGHT_MODE, SNOW_MODE, FIRE_MODE, WATER_MODE, DARK_MODE, SPECIAL_MODE;
    }

    private PlayScreen playScreen;
    private World box2DWorld;
    private GameSettings gameSettings;
    private GameCamera gameCamera;

    //Елементи управління грою
    private LevelGenerator levelGenerator;
    private GameGrid gameGrid;
    private BlockController blockController;

    //поки так TODO
    private int level;
    private  boolean moveCamera;
    private boolean pauseCamera;
    private boolean firstLauch;
    private GameMode gameMode;


    //Ігрові фізичні елементи
    private WaterElement waterElement;
    private Platform regionDown;
    private Platform regionLeft;
    private Platform regionRight;
    private GameSensor firstLineBlockChecker;

    //Фон і інші графічні едементи
    private Background backgroundGameFon;

    //Timers
    private float timeCreateBlock;
    private float timeToSave;

    //Buttons
    private boolean rightButtonPressed;
    private boolean leftButtonPressed;
    private boolean buttonPushPressed;

    public GameWorld(PlayScreen playScreen, World box2DWorld, int level){
        this.playScreen = playScreen;
        this.box2DWorld = box2DWorld;
        gameCamera = new GameCamera();
        gameSettings = GameSettings.getInstance();
        this.level = level;

        gameGrid = new GameGrid(GameConstants.WORLD_WIDTH_CELLS,GameConstants.WORLD_HEIGHT_CELLS);
        levelGenerator = new LevelGenerator(this,level);
        level = levelGenerator.getLevelNumber();
        blockController = levelGenerator.getBlockController();

        moveCamera = false;
        pauseCamera = false;
        firstLauch = true;

        rightButtonPressed = false;
        leftButtonPressed = false;
        buttonPushPressed = false;

        timeCreateBlock = 101;
        timeToSave = TIME_TO_SAVE;

        createSprites();
        createBackground();
    }
    public void update(float deltaTime){
        timeToSave -= deltaTime;

        waterElement.update(deltaTime);
        regionLeft.update(deltaTime);
        regionRight.update(deltaTime);
        regionDown.update(deltaTime);

        backgroundGameFon.update(deltaTime);
        updateBlock(deltaTime);
        firstLineBlockChecker.update(deltaTime);

        blockController.update(deltaTime);

        checkPressedButtons();
        this.gameCamera.update(deltaTime);

        if(timeToSave < 0){
            saveLevel();
            timeCreateBlock = TIME_TO_SAVE;
        }

    }
    public void render(SpriteBatch batch) {
        // This order is important.
        // This determines if a sprite has to be drawn in front or behind another sprite.
        backgroundGameFon.render(batch);
        waterElement.render(batch);
        blockController.render(batch);
    }
    public void pause(){
        saveLevel();
    }

    private void createSprites(){
        waterElement = levelGenerator.getHero();
        firstLineBlockChecker = new GameSensor(playScreen,this,55,DOWN_REGION + 5,90,1);

        //Regions ( create regions around the playing zone for player and game element)
        regionDown = new Platform(this,0,DOWN_REGION - 10,gameCamera.getWorldWidth(),10);
        regionLeft = new Platform(this,45,0,5,gameCamera.getWorldHeight());
        regionRight = new Platform(this,150,0,5,gameCamera.getWorldHeight());
    }
    private void createBackground(){
        backgroundGameFon = new Background(this,40,140,120,220);
        backgroundGameFon.setRegionGameFon();
    }

    public Body createBody(BodyDef bodyDef) {
        return box2DWorld.createBody(bodyDef);
    }
    private void loadGames(){

       for (Block blockA : blockController.getArrayBlock()){
           for(Block blockB : blockController.getArrayBlock()){
               if(blockA == blockB)continue;
               if(Math.abs(blockA.getBodyPosition().x - blockB.getBodyPosition().x) * 1.1f <= (blockA.getWidth() + blockB.getWidth())/2 &&
               Math.abs(blockA.getBodyPosition().y - blockB.getBodyPosition().y) * 0.95f <= (blockA.getHeight() + blockB.getHeight())/2){
                   if(blockA.getBodyPosition().x - blockB.getBodyPosition().x >= 0){
                       blockA.getContactLeftBlockList().add(blockB);
                       blockB.getContactRightBlockList().add(blockA);
                   }else {
                       blockA.getContactRightBlockList().add(blockB);
                       blockB.getContactLeftBlockList().add(blockA);
                   }
               }
               if(Math.abs(blockA.getBodyPosition().y - blockB.getBodyPosition().y) * 1.1f <= (blockA.getHeight() + blockB.getHeight()) &&
                       Math.abs(blockA.getBodyPosition().x - blockB.getBodyPosition().x) * 0.9f <= (blockA.getWidth() + blockB.getWidth())/2){
                   if(blockA.getBodyPosition().y - blockB.getBodyPosition().y >= 0){
                       blockA.getContactDownList().add(blockB);
                       blockB.getContactUpList().add(blockA);
                   }else{
                       blockA.getContactUpList().add(blockB);
                       blockB.getContactDownList().add(blockA);
                   }
               }
           }
       }
    }

    private void saveLevel(){
        String level = "";
        level += "<lvlNmb>" + level + "</lvlNmb>";
        level += blockController.toString();
        level += waterElement.toString();
        for(Block block : blockController.getArrayBlock()){
            level += block;
        }
        gameSettings.saveCurrentLevel(level);
        Gdx.app.debug(TAG, "Game saved" + level);
    }
    private void updateBlock(float deltaTime){
        timeCreateBlock += deltaTime;
        if(timeCreateBlock > 100){
            timeCreateBlock = 0;
            //getBlockController().addBlock();
        }
        List<com.daleondeveloper.Sprites.Blocks.Block> arrayBlock = new ArrayList<com.daleondeveloper.Sprites.Blocks.Block>();
        for(com.daleondeveloper.Sprites.Blocks.Block block: blockController.getArrayBlock()){
            if(block.isBody()) {
                block.update(deltaTime);
            }else {
                arrayBlock.add(block);
                continue;
            }
            if(block.isDisposable()){
                arrayBlock.add(block);
            }
        }
        blockController.getArrayBlock().removeAll(arrayBlock);
    }
    private void checkPressedButtons(){
        if(isLeftButtonPressed()){
            waterElement.turnLeft();
            waterElement.turn(30);
            if(!gameSettings.isPush_button_show()){
                //waterElement.push(30f);
            }
        }
        if(isRightButtonPressed()){
            waterElement.turnRight();
            waterElement.turn(30);
            if(!gameSettings.isPush_button_show()){

            }
        }
        if(!isLeftButtonPressed() && !isRightButtonPressed()){
            if(!gameSettings.isPush_button_show() &&
            waterElement.isPush()){
               waterElement.turn(0);
            }
        }
        if(isButtonPushPressed()){
            waterElement.push(50f);
        }
    }

    public void renderSpriteDebug(ShapeRenderer shapeRenderer) {

    }
    public void renderBox2DDebug(Box2DDebugRenderer box2DDebugRenderer) {
        box2DDebugRenderer.render(box2DWorld, gameCamera.getCombined());
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public PlayScreen getPlayScreen() {
        return playScreen;
    }

    public BlockController getBlockController() {
        return blockController;
    }

    public WaterElement getWaterElement() {
        return waterElement;
    }

    public void addLevel() {
        level++;
        moveCamera = true;
        newLevel();
    }

    private void newLevel() {

    }

    public int getLevel() {
        return level;
    }


    public void destroyBody (Body body) {
        if (!box2DWorld.isLocked()) {
            box2DWorld.destroyBody(body);
        }
    }

    public void pauseCamera() {
        pauseCamera = true;
    }

    public void resumeCamera() {
        pauseCamera = false;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public void setRightButtonPressed(boolean rightButtonPressed) {
        this.rightButtonPressed = rightButtonPressed;
        if(rightButtonPressed){
            leftButtonPressed = false;
        }
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public void setLeftButtonPressed(boolean leftButtonPressed) {
        this.leftButtonPressed = leftButtonPressed;
        if(leftButtonPressed){
            rightButtonPressed = false;
        }
    }

    public GameSensor getFirstLineBlockChecker() {
        return firstLineBlockChecker;
    }

    public float getTimeCreateBlock() {
        return timeCreateBlock;
    }

    public void setTimeCreateBlock(float timeCreateBlock) {
        this.timeCreateBlock = timeCreateBlock;
    }

    public boolean isButtonPushPressed() {
        return buttonPushPressed;
    }

    public void setButtonPushPressed(boolean buttonPushPressed) {
        this.buttonPushPressed = buttonPushPressed;
    }

    public Platform getRegionDown() {
        return regionDown;
    }

    public Platform getRegionLeft() {
        return regionLeft;
    }

    public Platform getRegionRight() {
        return regionRight;
    }

    public GameGrid getGameGrid() {
        return gameGrid;
    }
}
