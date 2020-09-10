package com.daleondeveloper.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.help.AssetHelp;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.*;
import com.daleondeveloper.Sprites.Hero.WaterElement;

public class GameWorld {
    private static final String TAG = GameWorld.class.getName();

    private static final float CAMERA_VELOCITY = 4.0f;
    private static final float ENEMY_PROBABILITY = 0.3f; // 30%

    private PlayScreen playScreen;
    private World box2DWorld;
    private int level;
    private GameCamera gameCamera;
    private  boolean moveCamera;
    private boolean pauseCamera;
    //private ParallaxSB parallaxSB;
    private BlockController blockController;
    private com.daleondeveloper.Sprites.Hero.WaterElement waterElement;
    private Platform regionDown;
    private Platform regionLeft;
    private Platform regionRight;
    private Background background;

    private Array<AbstractGameObject> gameObjectToCreate;

    public GameWorld(PlayScreen playScreen, World box2DWorld, int level){
        this.playScreen = playScreen;
        this.box2DWorld = box2DWorld;
        this.level = level;
        gameCamera = new GameCamera();
        moveCamera = false;
        pauseCamera = false;

        createSprites();
        createBackground();

        gameObjectToCreate = new Array<AbstractGameObject>();
    }

    private void createSprites(){
        //Blocks
        blockController = new BlockController(playScreen,this);


        //WaterHero
        waterElement = new com.daleondeveloper.Sprites.Hero.WaterElement(playScreen,this,gameCamera.getWorldWidth()/2,gameCamera.getWorldHeight()/2);

        //Regions
        regionDown = new Platform(this,0,8,gameCamera.getWorldWidth(),2);
        regionLeft = new Platform(this,0,0,2,gameCamera.getWorldHeight());
        regionRight = new Platform(this,gameCamera.getWorldWidth()-2,0,2,gameCamera.getWorldHeight());
        System.out.println(gameCamera.getWorldWidth() + "////" + gameCamera.getWorldHeight());
        background = new Background(this,0, 0,gameCamera.getWorldWidth(),gameCamera.getWorldHeight());
    }
    private void createBackground(){
       // parallaxSB = new ParallaxSB(gameCamera);
        //background = new Image(Assets.getInstance().getAssetGame().getGameFon());

        if (!DebugConstants.HIDE_BACKGROUND) {
            loadBackground();
        }
    }

    private void loadBackground(){
        AssetHelp assetBackground = Assets.getInstance().getAssetHelp();
        GameSettings prefs = GameSettings.getInstance();
        int backgroundid = prefs.getBackgroundId();
        prefs.setBackgroundId((backgroundid % 5)+1);
        prefs.save();
        switch (backgroundid){
            case 1:
                assetBackground.getHelp_1();
                break;
            case 2:
                assetBackground.getHelp_2();
                break;
            case 3:
                assetBackground.getHelp_1();
                break;
            case 4:
                assetBackground.getHelp_1();
                break;
            case 5:
                assetBackground.getHelp_1();
                break;
            default:
                assetBackground.getHelp_1();
                break;
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
        waterElement.update(deltaTime);
        regionLeft.update(deltaTime);
        regionRight.update(deltaTime);
        regionDown.update(deltaTime);
        background.update(deltaTime);
        updateBlock(deltaTime);
        centerCamera(deltaTime);


        gameCamera.update(deltaTime);
    }

    private void updateBlock(float deltaTime){
        Array<Block> arrayBlock = blockController.getArrayBlock();
        for(Block block: arrayBlock){
            block.update(deltaTime);
            if(block.isDisposable()){
                arrayBlock.removeValue(block,true);
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
        background.render(batch);
        waterElement.render(batch);
        regionLeft.render(batch);
        regionRight.render(batch);
        regionDown.render(batch);
        renderBlock(batch);

    }

    private void renderBlock(SpriteBatch batch) {
        for (Block block : blockController.getArrayBlock()) {
            block.render(batch);
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

}
