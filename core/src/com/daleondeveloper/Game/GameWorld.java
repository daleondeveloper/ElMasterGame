package com.daleondeveloper.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.help.AssetHelp;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.*;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerClassicMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerDarkMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerFireMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerLightMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerSnowMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerSpecialMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerWaterMode;
import com.daleondeveloper.Sprites.Blocks.Block;
import com.daleondeveloper.Sprites.Hero.WaterElement;

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
    private com.daleondeveloper.Game.Settings.GameSettings gameSettings;
    private int level;
    private GameCamera gameCamera;

    private  boolean moveCamera;
    private boolean pauseCamera;
    private boolean firstLauch;

    private GameMode gameMode;

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
    private float timeToSave;

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
        gameSettings = com.daleondeveloper.Game.Settings.GameSettings.getInstance();

        gameMode = GameMode.CLASSIC;

        rightButtonPressed = false;
        leftButtonPressed = false;
        buttonPushPressed = false;

        timeCreateBlock = 101;
        offSetY = 0;
        timeToSave = TIME_TO_SAVE;

        createSprites();
        createBackground();

        gameObjectToCreate = new Array<AbstractGameObject>();



    }

    private void loadParameters(){
        switch (gameSettings.getGameModeDragon()){
            case 0:
                gameMode = GameMode.CLASSIC;
                blockController = new BlockControllerClassicMode(playScreen,this);
                break;
                case 1:
                gameMode = GameMode.LIGHT_MODE;
                    blockController = new BlockControllerLightMode(playScreen,this);
                    break;
                case 2:
                gameMode = GameMode.SNOW_MODE;
                    blockController = new BlockControllerSnowMode(playScreen,this);
                    break;
                case 3:
                gameMode = GameMode.FIRE_MODE;
                    blockController = new BlockControllerFireMode(playScreen,this);
                    break;
                case 4:
                gameMode = GameMode.WATER_MODE;
                    blockController = new BlockControllerWaterMode(playScreen,this);
                    break;
                case 5:
                gameMode = GameMode.DARK_MODE;
                    blockController = new BlockControllerDarkMode(playScreen,this);
                    break;
                case 6:
                gameMode = GameMode.SPECIAL_MODE;
                    blockController = new BlockControllerSpecialMode(playScreen,this);
                    break;
        }
    }

    private void createSprites(){
        //Block(create block factory  to create block)
        blockController = new com.daleondeveloper.Sprites.BlockControllers.BlockController(playScreen,this);

        loadParameters();

        com.daleondeveloper.Game.Settings.GameSettings.getInstance().setBlockController(blockController);
        platformController = new PlatformController(playScreen,this);

        //WaterHero(create player controller hero wich created in center of screen)
        waterElement = new WaterElement(playScreen,this,gameCamera.getWorldWidth()*2,DOWN_REGION + 10);

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
        com.daleondeveloper.Game.Settings.GameSettings prefs = com.daleondeveloper.Game.Settings.GameSettings.getInstance();
        int backgroundid = prefs.getBackgroundId();
        prefs.setBackgroundId((backgroundid % 5)+1);
     //   prefs.save();

    }

    private void loadGames(){
       waterElement.load();
       blockController.load();

       for (com.daleondeveloper.Sprites.Blocks.Block blockA : blockController.getArrayBlock()){
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
        timeToSave -= deltaTime;

        if(firstLauch){
            com.daleondeveloper.Game.Settings.GameSettings.getInstance().loadSettings();
            com.daleondeveloper.Game.Settings.GameSettings.getInstance().setHero(waterElement);
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

        if(timeToSave < 0){
            gameSettings.save();
        }

    }

    private void checkPressedButtons(){
        if(isLeftButtonPressed()){
            waterElement.turn(-30);
            if(!gameSettings.isPush_button_show()){
                //waterElement.push(30f);
            }
        }
        if(isRightButtonPressed()){
            waterElement.turn(30);
            if(!gameSettings.isPush_button_show()){
               //waterElement.push(30f);
            }
        }
        if(!isLeftButtonPressed() && !isRightButtonPressed()){
            if(!gameSettings.isPush_button_show()){
               // waterElement.idle();
            }
        }
        if(isButtonPushPressed()){
            waterElement.push(50f);
        }
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
        for (com.daleondeveloper.Sprites.Blocks.Block block : blockController.getArrayBlock()) {
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

    public com.daleondeveloper.Sprites.BlockControllers.BlockController getBlockController() {
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

    public Platform getRegionDown() {
        return regionDown;
    }

    public Platform getRegionLeft() {
        return regionLeft;
    }

    public Platform getRegionRight() {
        return regionRight;
    }
}
