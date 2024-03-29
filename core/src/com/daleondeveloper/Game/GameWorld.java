package com.daleondeveloper.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.tools.GameGrid;
import com.daleondeveloper.Game.tools.Level.Level;
import com.daleondeveloper.Game.tools.Level.LvlEndConditionController;
import com.daleondeveloper.Game.tools.Level.ScoreCheker;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderConstats;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.AbstractGameObject;
import com.daleondeveloper.Sprites.Background;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;
import com.daleondeveloper.Sprites.BlockControllers.BlockTouchLineCheсker;
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
    private Level levelGenerator;
    private LvlEndConditionController lvlEndConditionController;
    private GameGrid gameGrid;
    private BlockController blockController;
    private BlockTouchLineCheсker blockTouchLineCheсker;
    private ScoreCheker scoreCheker;

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
    private float completeLvlTime;

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
        UpgraderConstats.resetParameter();

        gameGrid = new GameGrid(GameConstants.WORLD_WIDTH_CELLS,GameConstants.WORLD_HEIGHT_CELLS);
        levelGenerator = new Level(level);
        blockController = new BlockController(this);
        lvlEndConditionController = new LvlEndConditionController();
        levelGenerator.getLevelTasks(lvlEndConditionController,blockController);
        blockTouchLineCheсker = new BlockTouchLineCheсker(blockController,gameGrid);
        if(level == -1){
            this.level = levelGenerator.getLevelNumber();
            levelGenerator.setUpgradeConstants();

        }
        scoreCheker = new ScoreCheker(playScreen,this);
        gameSettings.setAdsContinueCount(1);

        playScreen.getHud().setScore(levelGenerator.getScore());
        moveCamera = false;
        pauseCamera = false;
        firstLauch = true;
        completeLvlTime = 0;

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
        //checkBlockLine();

        blockController.update(deltaTime);

        checkPressedButtons();

        lvlEndConditionController.update(deltaTime);
        if(lvlEndConditionController.checkComplianceConditions() &&
        !gameSettings.isInfinityLvl()){
            completeLvlTime += deltaTime;
//                    if(completeLvlTime > 2) {
//                       level = nextLvl();
//                    }
        }
        if(blockTouchLineCheсker.check()){
            ArrayList<AbstractGameObject> destroyBlocks = blockTouchLineCheсker.getBlockToDestroy();
            for(AbstractGameObject abstractGameObject : destroyBlocks){
                Block block = (Block) abstractGameObject;
                block.delete();
            }
            setScore(destroyBlocks.size());
        }

        this.gameCamera.update(deltaTime);

        if(timeToSave < 0){
            saveLevel();
            timeToSave = TIME_TO_SAVE;
        }

    }
    private void setScore(int i ){
        int scoreToAdd = (i - 4) * 2 + 1;
        playScreen.getHud().addScore(scoreToAdd);
        scoreCheker.checkScore(playScreen.getHud().getScore());
    }
    public int nextLvl() {
        if (level < GameConstants.MAX_LEVEL) {
            level++;
            scoreCheker.getNewChecker();
//            levelGenerator = new Level(++level);
//            blockController.cleatBlockSpawner();
//            levelGenerator.getBlockSpawner(blockController);
//            lvlEndConditionController.cleatTasks();
//            levelGenerator.getLevelTasks(lvlEndConditionController, blockController);
        }
        return level;
    }
    public void resumeGame(){
        playScreen.setStateRunning();
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
        waterElement = levelGenerator.getHero(this);
        levelGenerator.getBlock(blockController);
        levelGenerator.getBlockSpawner(blockController);
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

    public void checkBlockLine(){
        for(int y = 0; y < 10; y++){
            for(int x = 0; x < gameGrid.getGridWidthLength(); x++){
                if(gameGrid.getBlockByCordinate(x,y) == null ||
                !gameGrid.getBlockByCordinate(x,y).isIdle()){
                    break;
                }
                if(x == gameGrid.getGridWidthLength() - 1){
                    for(int secondX = 0; secondX < gameGrid.getGridWidthLength(); secondX++){
                        gameGrid.getBlockByCordinate(secondX,y).delete();
                    }
                    playScreen.getHud().addScore(10);
                }
            }
        }
    }
    public void revive(){
        int x = (int)waterElement.getPositionInGameGrid().x;
        int y = (int)waterElement.getPositionInGameGrid().y;
        for(int i = y; i < gameGrid.getGridHeightLength();i++){
            Block block = gameGrid.getBlockByCordinate(x,i);
            if(block != null){
                block.delete();
            }
        }
        waterElement.revive();
    }
    public Body createBody(BodyDef bodyDef) {
        return box2DWorld.createBody(bodyDef);
    }
    private void saveLevel(){
        String level = "<level ";
        level += "number =  \""+ levelGenerator.getLevelNumber() +"\" ";
        level += "score = \"" + playScreen.getHud().getScore() + "\" ";
        level += "> \n";
        level += waterElement.save();
        level += blockController.save();
        level += lvlEndConditionController.save();
        level += UpgraderConstats.save();
        level += "</level>";
        levelGenerator.saveLevel(level);
    }
    private void updateBlock(float deltaTime){
        timeCreateBlock += deltaTime;
        if(timeCreateBlock > 100){
            timeCreateBlock = 0;
            //getBlockController().addBlock();
        }
        List<Block> arrayBlock = new ArrayList<Block>();

        for(Block block: blockController.getArrayBlock()){
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
    public void gameOver(){

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

    public ScoreCheker getScoreCheker() {
        return scoreCheker;
    }
}
