package com.daleondeveloper.Sprites.Blocks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetBlock;
import com.daleondeveloper.Effects.ParticleEffectManager;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.GameGrid;
import com.daleondeveloper.Game.tools.WorldContactListner;
import com.daleondeveloper.Sprites.AbstractDynamicObject;
import com.daleondeveloper.Sprites.AbstractGameObject;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;
import com.daleondeveloper.Sprites.GameSensor;
import com.daleondeveloper.Sprites.Hero.WaterElement;

import java.util.HashSet;
import java.util.Set;

public class Block extends AbstractDynamicObject {
    private static final String TAG = Block.class.getName();

    protected enum State{
        FALL, IDLE, PUSH, DESTROY, DISPOSE
    }
    protected enum BlockType{
        CLASSIC,DARK,FIRE,WATER,LIGHT,SNOW
    }

    protected GameWorld gameWorld;
    protected Body body;
    protected GameGrid gameGrid;
    protected com.daleondeveloper.Sprites.BlockControllers.BlockController blockController;

    protected Array<TextureRegion> assetBlocks;
    protected TextureRegion textureRegionBlock;
    protected Animation<TextureRegion> destroyAnimation;

    protected State currentState;
    protected BlockType blockType;
    private int blockTypeNumber;
    protected float stateTime;
    protected boolean statePosition;
    protected float checkTime;

    protected float pushImpulse;
    protected float returnCellsPosition;
    protected float returnCellsPositionY;
    protected float positionInBlocksMasX;
    protected float positionInBlocksMasY;

    protected ParticleEffectPool.PooledEffect effect;
    protected ParticleEffectManager effectManager;

    //sensors contacted objects
        //Sets
    protected Set<AbstractGameObject> contactLeftBlockList ;
    protected Set<AbstractGameObject> contactRightBlockList ;
    protected Set<AbstractGameObject> contactUpList ;
    protected Set<AbstractGameObject> contactDownList ;
    protected WaterElement contactHero;

    public Block(GameWorld gameWorld, BlockController blockController, float x, float y, float width, float height){
        this.gameWorld = gameWorld;
        this.blockController = blockController;
        effectManager = ParticleEffectManager.getInstance();
        gameGrid = gameWorld.getGameGrid();

        assetBlocks = new Array<TextureRegion>();
        AssetBlock assets =  Assets.getInstance().getAssetBlock();
        assetBlocks.add(assets.getBlockWater());
        assetBlocks.add(assets.getBlockSnow());
        assetBlocks.add(assets.getBlockLight());
        assetBlocks.add(assets.getBlockWater());
        assetBlocks.add(assets.getBlockFire());
        assetBlocks.add(assets.getBlockWater());

        destroyAnimation = assets.getDestroyWater();

        textureRegionBlock = assets.getBlockClassic();
        this.blockTypeNumber = 0;
        blockType = BlockType.CLASSIC;

        // Sets initial values for position, width and height and initial frame as jumperStand.
        setBounds(x, y, width, height);
        setRegion(textureRegionBlock);
        stateTime = 0;
        checkTime = 0;
        statePosition = false;
        positionInGameGrid = new Vector2();

        pushImpulse = 10;
        returnCellsPosition = x + 5;
        returnCellsPositionY = y + 5;
        positionInBlocksMasX = -1;
        positionInBlocksMasY = -1;

        //Initial sensor values
        contactLeftBlockList = new HashSet<AbstractGameObject>();
        contactRightBlockList = new HashSet<AbstractGameObject>();
        contactDownList = new HashSet<AbstractGameObject>();
        contactUpList = new HashSet<AbstractGameObject>();
        contactHero = null;

        defineBlock();

        currentState = State.IDLE;
        updatePositionInCells();
        gameGrid.addObject(this,(int)positionInBlocksMasX,(int)positionInBlocksMasY);
    }

    private void defineBlock(){
        BodyDef blockDef = new BodyDef();
        blockDef.position.set((getX() + getWidth()/2), getY() + getHeight()/2);
        blockDef.type = BodyDef.BodyType.DynamicBody;
        body =gameWorld.createBody(blockDef);
        body.setFixedRotation(true);
        body.setGravityScale(1);

        FixtureDef fixture = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getWidth()/2f,getHeight()/2f);
        fixture.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_BIT;
        fixture.filter.maskBits = WorldContactListner.MASK_ALL;
        fixture.shape = polygonShape;
        fixture.density = 1f;
        fixture.friction = 0f;
        fixture.restitution = 0f;
        fixture.isSensor = false;

        body.createFixture(fixture).setUserData(this);

        defineSensors();

    }
    private void defineSensors(){
        //Sensor Left
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.1f,(getHeight()/2)*0.95f, new Vector2((-getWidth()/2)+0.03f,0),0);
        FixtureDef sensorLeft = new FixtureDef();
        sensorLeft.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_SENSOR_LEFT_BIT;
        sensorLeft.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorLeft.shape = polygonShape;
        sensorLeft.isSensor = true;
        body.createFixture(sensorLeft).setUserData(this);

        //Sensor Right
        polygonShape.setAsBox(0.1f,(getHeight()/2)*0.95f, new Vector2((getWidth()/2)-0.03f,0),0);
        FixtureDef sensorRight = new FixtureDef();
        sensorRight.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_SENSOR_RIGHT_BIT;
        sensorRight.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorRight.shape = polygonShape;
        sensorRight.isSensor = true;
        body.createFixture(sensorRight).setUserData(this);

        //Sensor Down
        polygonShape.setAsBox((getWidth()/2)*0.95f,0.3f, new Vector2(0,(-getHeight()/2)),0);
//        polygonShape.setAsBox(10,10, new Vector2(getWidth()/2,getHeight()/2),0);
        FixtureDef sensorDown = new FixtureDef();
        sensorDown.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_SENSOR_DOWN_BIT;
        sensorDown.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorDown.shape = polygonShape;
        sensorDown.isSensor = true;
        body.createFixture(sensorDown).setUserData(this);

        //Sensor Up
        polygonShape.setAsBox((getWidth()/2)*0.95f,0.3f, new Vector2(0,(getHeight()/2)),0);
        FixtureDef sensorUp = new FixtureDef();
        sensorUp.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_SENSOR_UP_BIT;
        sensorUp.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorUp.shape = polygonShape;
        sensorUp.isSensor = true;
        body.createFixture(sensorUp).setUserData(this);
    }


    //Change of state through methods with additional functionality necessary before change of state
    public void idle(){
        currentState = State.IDLE;
        stateTime = 0;
    }
    public void stopFall(){
        if(currentState == State.FALL) {
            currentState = State.IDLE;
            stateTime = 0;
            statePosition = false;
        }
    }
    public void fall(){
        currentState = State.FALL;
        stateTime = 0;
    }
    public void push(Float turnImpulse){
        if(currentState == State.IDLE && contactUpList.isEmpty()) {
            currentState = State.PUSH;
            stateTime = 0;
        }
        pushImpulse = turnImpulse;
    }
    public void delete(){
        currentState = State.DESTROY;
        stateTime = 0;
    }

    //Update method, and update methods depending on the state of the object
    @Override
    public void update(float deltaTime) {
        checkTime += deltaTime;
        if(effect != null) {
            effect.update(deltaTime);
        }
        updatePositionInCells();
        gameGrid.addObject(this,(int)positionInGameGrid.x,(int)positionInGameGrid.y);
        findDownContacts();

        switch (currentState){
            case IDLE:
                stateIdle(deltaTime);
                break;
            case PUSH:
                statePush(deltaTime);
                break;
            case FALL:
                stateFall(deltaTime);
                if(effect == null){
                    effect = effectManager.getPoolParticleEffect(ParticleEffectManager.FALL_EFFECT);
                }
                effect.setPosition(getX(), getBodyPosition().y);
                break;
            case DESTROY:
                stateDestroy(deltaTime);
                break;
            case DISPOSE:
                break;
        }
        if(currentState != State.FALL){
            effect =null;
        }

    }
    protected void stateIdle(float deltaTime){
        body.setType(BodyDef.BodyType.StaticBody);
        body.setTransform(returnCellsPosition,returnCellsPositionY,0);
        if(contactDownList.size() == 0){fall();return;}

        // Update this Sprite to correspond with the position of the Box2D body.
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(textureRegionBlock);
        stateTime += deltaTime;
    }
    protected void statePush(float deltaTime){
        body.setType(BodyDef.BodyType.DynamicBody);
        if(currentState == State.FALL || getContactUpList().size() > 0 || !gameWorld.getWaterElement().isPush()){
            idle();
            gameWorld.getWaterElement().idle();
            return;
        }
        if(contactDownList.size() == 0){
            fall();
            gameWorld.getWaterElement().idle();
            return;
        }
        body.setLinearVelocity(pushImpulse, body.getLinearVelocity().y);
        // Update this Sprite to correspond with the position of the Box2D body.
        body.setTransform(body.getPosition().x,returnCellsPositionY,0);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(textureRegionBlock);
        stateTime += deltaTime;
    }
    protected void stateFall(float deltaTime){
        body.setType(BodyDef.BodyType.DynamicBody);
        if(contactDownList.size() > 0 ){stopFall();return;}

        //Change fall velocity
        body.setLinearVelocity(0,blockController.getBlockFallVelocity());
        body.setTransform(returnCellsPosition,body.getPosition().y,0);

        // Update this Sprite to correspond with the position of the Box2D body.
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(textureRegionBlock);
        stateTime += deltaTime;
    }
    protected void stateDestroy(float deltaTime){
        TextureRegion textureRegion =destroyAnimation.getKeyFrame(stateTime, true);
        setRegion(textureRegion);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2,
                textureRegion.getRegionWidth() * 0.12f,textureRegion.getRegionHeight() * 0.12f);
        stateTime += deltaTime;
        if(stateTime > destroyAnimation.getAnimationDuration()) {
            gameGrid.deleteObjectFromGrid(this);
            //Change body type and check the main allegations to change the state to another immediately
            body.setType(BodyDef.BodyType.KinematicBody);

            // Remove a block from the GameSensor object's contact list
            GameSensor gameSensor = gameWorld.getFirstLineBlockChecker();
            gameSensor.getFirstLineBlocks().remove(this);

            // Remove a block from the contact list of all its contacts
            Set<AbstractGameObject> objToDelete = new HashSet<AbstractGameObject>();
            objToDelete.addAll(fixOnContact);
            for (AbstractGameObject abstractGO : objToDelete) {
                removeFixOnContact(this, abstractGO);
            }

            gameWorld.destroyBody(body);
            body = null;
            currentState = State.DISPOSE;
        }
    }
    protected void stateDead(float deltaTime){

    }
    @Override
    public void render(SpriteBatch spriteBatch) {
        draw(spriteBatch);
    }

    public void renderEffect(SpriteBatch spriteBatch) {
        if (effect != null) {
            effect.draw(spriteBatch);
        }
    }
    //Methods of checking the state of the object

    public boolean isIdle(){
        return(currentState == State.IDLE);
    }
    public boolean isIFall(){
        return(currentState == State.FALL);
    }
    public boolean isPush(){
        return (currentState == State.PUSH);
    }
    public boolean isDestroy(){
        return (currentState == State.DESTROY);
    }
    @Override
    public boolean isDisposable() {
        return (currentState == State.DISPOSE);
    }
    private void findDownContacts(){
        getContactDownList().clear();
        AbstractDynamicObject downObj = gameGrid.findObjNearObj(this,0,-1);
        if(downObj != null){
            getContactDownList().add(downObj);
        }
        if((int)positionInGameGrid.y == 0 && body.getPosition().y < 160){
            getContactDownList().add(gameWorld.getRegionDown());
        }
    }

    private void updatePositionInCells(){
        updateReturnPositionX();
        updateReturnPositionY();
        updatePositionInGrid();
    }
    protected void updatePositionInGrid(){
        positionInGameGrid.set((returnCellsPosition / 10) - 5, (returnCellsPositionY / 10) - 15);
    }
    private void updateReturnPositionX(){
        float x = body.getPosition().x;
        int leftReg = 53, rightReg = 57;
        for(int i = 0; i < 10; i++){
            if(x >= leftReg && x < rightReg){
                returnCellsPosition = (rightReg + leftReg) >> 1;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }
    }
    private void updateReturnPositionY(){
        float y = body.getPosition().y - 2;
        int leftReg = 153,rightReg = 157;
        for(int i = 0; i < 20; i++){
            if(y > leftReg && y < rightReg){
                returnCellsPositionY = (rightReg + leftReg) >> 1;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }
        if(getY() < 0){
            returnCellsPositionY = 160;
        }
    }

    //вНЕСТИ ОПИС МЕТОДА
    //вНЕСТИ ОПИС МЕТОДА
    //вНЕСТИ ОПИС МЕТОДА
    //вНЕСТИ ОПИС МЕТОДА
    //вНЕСТИ ОПИС МЕТОДА
    //вНЕСТИ ОПИС МЕТОДА
    //вНЕСТИ ОПИС МЕТОДА
    //вНЕСТИ ОПИС МЕТОДА
    @Override
    public Set<AbstractGameObject> removeFixOnContact(AbstractGameObject mainFix, AbstractGameObject contactFix) {
        if(contactFix instanceof Block){
            Block block = (Block) contactFix;
            block.getContactDownList().remove(this);
            block.getContactUpList().remove(this);
            block.getContactLeftBlockList().remove(this);
            block.getContactRightBlockList().remove(this);
        }
        if(contactFix instanceof WaterElement){
            WaterElement hero = (WaterElement) contactFix;
            hero.getSensorDown().remove(this);
            hero.getSensorUp().remove(this);
            hero.getSensorLeft().remove(this);
            hero.getSensorRight().remove(this);
        }

        return super.removeFixOnContact(mainFix, contactFix);
    }

    // Getters and Setters
    public boolean isBody(){return body != null;}
    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

    public Set<AbstractGameObject> getContactLeftBlockList() {
        return contactLeftBlockList;
    }

    public Set<AbstractGameObject> getContactRightBlockList() {
        return contactRightBlockList;
    }

    public Set<AbstractGameObject> getContactUpList() {
        return contactUpList;
    }

    public Set<AbstractGameObject> getContactDownList() {
        return contactDownList;
    }

    public WaterElement getContactHero() {
        return contactHero;
    }

    public void setContactHero(WaterElement contactHero) {
        this.contactHero = contactHero;
    }

    public float getReturnCellsPosition() {
        return returnCellsPosition;
    }

    public float getReturnCellsPositionY() {
        return returnCellsPositionY;
    }

    public int getBlockTypeNumber() {
        return blockTypeNumber;
    }

    public void setBlockTypeNumber(int blockTypeNumber) {
        this.blockTypeNumber = blockTypeNumber;
    }

    @Override
    public String toString() {
        return "<block>" +
                "<type : " + blockType.toString() +  ">" +
                "<position : " + positionInGameGrid.x + ","  + positionInGameGrid.y + ">" +
                "</block>";
    }
}
