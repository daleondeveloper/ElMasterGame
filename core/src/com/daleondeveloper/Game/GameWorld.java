package com.daleondeveloper.Game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.help.AssetHelp;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.*;
import com.daleondeveloper.Sprites.Hero.WaterElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameWorld {
    private static final String TAG = GameWorld.class.getName();

    private static final float DOWN_REGION = 150;
    private PlayScreen playScreen;
    private World box2DWorld;
    private GameSettings gameSettings;
    private int level;
    private GameCamera gameCamera;
    private  boolean moveCamera;
    private boolean pauseCamera;
    private boolean firstLauch;

    private BlockController blockController;
    private PlatformController platformController;

    private com.daleondeveloper.Sprites.Hero.WaterElement waterElement;
    private Platform regionDown;
    private Platform regionLeft;
    private Platform regionRight;
    private Background background;
    private Background gates;
    private Background backgroundGameFon;
    private GameSensor firstLineBlockChecker;

    private float timeCreateBlock;
    private float offSetY;

    private boolean rightButtonPressed;
    private boolean leftButtonPressed;
    private boolean buttonPushPressed;


    private Array<AbstractGameObject> gameObjectToCreate;

    public GameWorld(PlayScreen playScreen, World box2DWorld, int level){
        this.playScreen = playScreen;
        this.box2DWorld = box2DWorld;
        this.level = level;
        gameCamera = new GameCamera();
        moveCamera = false;
        pauseCamera = false;
        firstLauch = true;

        rightButtonPressed = false;
        leftButtonPressed = false;
        buttonPushPressed = false;

        timeCreateBlock = 101;
        offSetY = 0;

        createSprites();
        createBackground();

        gameObjectToCreate = new Array<AbstractGameObject>();



    }

    private void createSprites(){
        //Block(create block factory  to create block)
        blockController = new BlockController(playScreen,this);
        GameSettings.getInstance().setBlockController(blockController);
        platformController = new PlatformController(playScreen,this);

        //WaterHero(create player controller hero wich created in center of screen)
        waterElement = new WaterElement(playScreen,this,gameCamera.getWorldWidth()/2,DOWN_REGION + 10);

        firstLineBlockChecker = new GameSensor(playScreen,this,55,DOWN_REGION + 5,90,1);


        //Regions ( create regions around the playing zone for player and game element)
        regionDown = new Platform(this,0,DOWN_REGION - 10,gameCamera.getWorldWidth(),10);
        regionLeft = new Platform(this,45,0,5,gameCamera.getWorldHeight());
        regionRight = new Platform(this,150,0,5,gameCamera.getWorldHeight());
        System.out.println(gameCamera.getWorldWidth() + "////" + gameCamera.getWorldHeight());


    }
    private void createBackground(){

        backgroundGameFon = new Background(this,40,140,120,220);
        backgroundGameFon.setRegionGameFon();
        if (!DebugConstants.HIDE_BACKGROUND) {
            loadBackground();
        }
    }

    private void loadBackground(){
        AssetHelp assetBackground = Assets.getInstance().getAssetHelp();
        GameSettings prefs = GameSettings.getInstance();
        int backgroundid = prefs.getBackgroundId();
        prefs.setBackgroundId((backgroundid % 5)+1);
     //   prefs.save();

    }

    private void loadGames(){
       waterElement.load();
       blockController.load();

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

    public void handleGameObjectsToCreate(){
        while(gameObjectToCreate.size > 0){
            AbstractGameObject  gameObject = gameObjectToCreate.pop();
        }
    }
    public void createGameObject(AbstractGameObject gameObject){gameObjectToCreate.add(gameObject);}
    public Body createBody(BodyDef bodyDef) {
        return box2DWorld.createBody(bodyDef);
    }

    public void update(float deltaTime){
        if(firstLauch){
            GameSettings.getInstance().load();
            GameSettings.getInstance().setHero(waterElement);
            GameSettings.getInstance().setBlockController(blockController);
            loadGames();
            firstLauch = false;
        }
        waterElement.update(deltaTime);
        regionLeft.update(deltaTime);
        regionRight.update(deltaTime);
        regionDown.update(deltaTime);

        backgroundGameFon.update(deltaTime);
        updateBlock(deltaTime);
        updatePlatform(deltaTime);
        centerCamera(deltaTime);
        firstLineBlockChecker.update(deltaTime);

        blockController.update(deltaTime);

        checkPressedButtons();
        this.gameCamera.update(deltaTime);

    }

    private void checkPressedButtons(){
        if(isLeftButtonPressed()){
            waterElement.turn(-20);
        }
        if(isRightButtonPressed()){
            waterElement.turn(20);
        }
        if(isButtonPushPressed()){
            waterElement.push(30f);
        }
    }
    private void updateBlock(float deltaTime){
        timeCreateBlock += deltaTime;
        if(timeCreateBlock > 100111){
            timeCreateBlock = 0;
            getBlockController().addBlock();
        }
        List<Block> arrayBlock = new ArrayList<Block>();
        for(Block block: blockController.getArrayBlock()){
            block.update(deltaTime);
            if(block.isDisposable()){
                arrayBlock.add(block);
            }
        }
        blockController.getArrayBlock().removeAll(arrayBlock);
    }
private void updatePlatform(float deltaTime){
        Array<Platform> arrayPlatform = platformController.getPlatforms();
        for(Platform platform: arrayPlatform){
            platform.update(deltaTime);
            if(platform.isDisposable()){
                arrayPlatform.removeValue(platform,false);
            }
        }
    }

    private void centerCamera(float deltaTime) {
        if (moveCamera && !pauseCamera) {
            //gameCamera.position().x = gameCamera.position().x + CAMERA_VELOCITY * deltaTime;
            //moveCamera = gameCamera.position().x - gameCamera.getWorldWidth() / 2 <= jumper.getBodyPosition().x - jumper.getWidth() / 2;
        }
    }

    public void render(SpriteBatch batch) {
        // This order is important.
        // This determines if a sprite has to be drawn in front or behind another sprite.
     //   background.render(batch);
        backgroundGameFon.render(batch);
        waterElement.render(batch);
      //  regionLeft.render(batch);
      //  regionRight.render(batch);
       // regionDown.render(batch);
        renderBlock(batch);
        renderPlatform(batch);
      //  firstLineBlockChecker.render(batch);

    }

    private void renderBlock(SpriteBatch batch) {
        for (Block block : blockController.getArrayBlock()) {
            block.render(batch);
//            if(block.getUpPlatform() != null){
//                block.getUpPlatform().render(batch);
//            }
        }
    }

private void renderPlatform(SpriteBatch batch) {
        for (Platform platform : platformController.getPlatforms()) {
             platform.render(batch);
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

    public PlatformController getPlatformController() {
        return platformController;
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
}
