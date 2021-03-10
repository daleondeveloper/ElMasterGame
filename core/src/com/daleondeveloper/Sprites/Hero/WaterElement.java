package com.daleondeveloper.Sprites.Hero;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetHero;
import com.daleondeveloper.Exception.NoPushBlocks;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.tools.WorldContactListner;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.AbstractDynamicObject;
import com.daleondeveloper.Sprites.AbstractGameObject;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;
import com.daleondeveloper.Sprites.Blocks.Block;
import com.daleondeveloper.Sprites.Platform;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class WaterElement extends AbstractDynamicObject {
    private static final String TAG = WaterElement.class.getName();

    private static float IMPULSE_Y = 50f;
    private static float IMPULSE_FALL = -30f;
    private static final float MIN_SPEAK_TIME = 7.0f;
    private static final float MAX_SPEAK_TIME = 10.0f;
    private static final float SPEED_MULTIPIER_IDLE = 0f;
    private static final float SPEED_MULTIPIER_WALK = 2f;
    private static final float SPEED_MULTIPIER_PUSH = 1f;
    private static final float SPEED_MULTIPIER_IN_AIR = 0.66f;


    private enum State{
        IDLE,WALK,JUMP,FALL,PUSH,DEAD,DISPOSE
    }

    private PlayScreen playScreen;
    private GameWorld gameWorld;
    private BlockController blockController;

    private TextureRegion elemTexReg;
    private TextureRegion newHero;
    private Animation<TextureRegion> elemStandAnim;
    private Animation<TextureRegion> elemWalkAnim;
    private Animation<TextureRegion> elemPushAnim;
    private Animation<TextureRegion> elemJumpAnim;
    private Animation<TextureRegion> elemDeathAnim;
    private float stateTime;
    private Body body;
    private State currentState;
    private boolean activateElem;
    private boolean moveRight;
    private boolean pushRight;
    private Platform currentPlatform;
    private float speakTime;
    private float timeToSpeak;
    private boolean turn;
    private float turnImpulse;
    private float pushImpulse;

    private Block blockInRightSide;
    private Block blockInLeftSide;

    private Vector2 returnPosition;

    private FixtureDef fixtureSkill;
    private CircleShape circleShapeSkillFixture;

    private Block pushBlock;

    //marks
    private Set<AbstractGameObject> sensorRight;
    private Set<AbstractGameObject> sensorLeft;
    private Set<AbstractGameObject> sensorUp;
    private Set<AbstractGameObject> sensorDown;

    private State debugState;

    public WaterElement (PlayScreen playScreen, GameWorld gameWorld, float x, float y){
        this.playScreen = playScreen;
        this.gameWorld = gameWorld;
        blockController = gameWorld.getBlockController();

        AssetHero assetWaterElem = Assets.getInstance().getAssetHero();
        elemStandAnim = assetWaterElem.getHeroStand();
        elemWalkAnim = assetWaterElem.getHeroRun();
        elemPushAnim = assetWaterElem.getHeroPush();
        elemJumpAnim = assetWaterElem.getHeroJump();
        elemDeathAnim = assetWaterElem.getHeroDeath();
        elemTexReg = assetWaterElem.getHeroStandStatic();

        setBounds(x,y, 8f,14f);
        setRegion(elemTexReg);
        stateTime = 0;

        turnImpulse = 0f;
        pushImpulse = 30f;
        defineWaterElem();

        currentState = State.IDLE;
        debugState = currentState;
        moveRight = true;
        turn = false;
        activateElem = true;
        currentPlatform = null;
        pushBlock = null;
        returnPosition = new Vector2();
        positionInGameGrid = new Vector2();

        sensorDown = new HashSet<AbstractGameObject>();
        sensorLeft = new HashSet<AbstractGameObject>();
        sensorRight = new HashSet<AbstractGameObject>();
        sensorUp = new HashSet<AbstractGameObject>();

        initVoice();
    }

    private void initVoice(){
        speakTime = 0;
        timeToSpeak = MathUtils.random(MIN_SPEAK_TIME,MAX_SPEAK_TIME);
    }

    private void defineWaterElem(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX() + getWidth() / 2, getY() + getHeight() / 2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = gameWorld.createBody(bodyDef);
        body.setFixedRotation(true);
        body.setGravityScale(10);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getWidth()/2f,getHeight()/2f );
        fixtureDef.filter.categoryBits = WorldContactListner.CATEGORY_WATER_ELEM_BIT;
        fixtureDef.filter.maskBits = WorldContactListner.MASK_ALL;
        fixtureDef.shape = polygonShape;
        //fixtureDef.density = 0.0f;
        body.createFixture(fixtureDef).setUserData(this);

        defineSensors();

    }

    private void defineSensors(){
        //Sensor Left
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.1f,(getHeight()/2)*0.95f, new Vector2((-getWidth()/2)+0.05f,0),0);
        FixtureDef sensorLeft = new FixtureDef();
        sensorLeft.filter.categoryBits = WorldContactListner.CATEGORY_WATER_ELEM_SENSOR_LEFT_BIT;
        sensorLeft.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorLeft.shape = polygonShape;
        sensorLeft.isSensor = true;
        body.createFixture(sensorLeft).setUserData(this);

        //Sensor Right
        polygonShape.setAsBox(0.1f,(getHeight()/2)*0.95f, new Vector2((getWidth()/2)-0.05f,0),0);
        FixtureDef sensorRight = new FixtureDef();
        sensorRight.filter.categoryBits = WorldContactListner.CATEGORY_WATER_ELEM_SENSOR_RIGHT_BIT;
        sensorRight.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorRight.shape = polygonShape;
        sensorRight.isSensor = true;
        body.createFixture(sensorRight).setUserData(this);

        //Sensor Down
        polygonShape.setAsBox((getWidth()/2)*0.95f,1.2f, new Vector2(0,(-getHeight()/2)),0);
        FixtureDef sensorDown = new FixtureDef();
        sensorDown.filter.categoryBits = WorldContactListner.CATEGORY_WATER_ELEM_SENSOR_DOWN_BIT;
        sensorDown.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorDown.shape = polygonShape;
        sensorDown.isSensor = true;
        body.createFixture(sensorDown).setUserData(this);

        //Sensor Up
        polygonShape.setAsBox((getWidth()/2)*0.95f,0.1f, new Vector2(0,(getHeight()/2)-0.05f),0);
        FixtureDef sensorUp = new FixtureDef();
        sensorUp.filter.categoryBits = WorldContactListner.CATEGORY_WATER_ELEM_SENSOR_UP_BIT;
        sensorUp.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorUp.shape = polygonShape;
        sensorUp.isSensor = true;
        body.createFixture(sensorUp).setUserData(this);
    }


    public void turnLeft(){
        moveRight = false;
    }
    public void turnRight(){
        moveRight = true;
    }
    private void setPushSpriteSide(){
        if(pushBlock != null){
            if(blockInRightSide == pushBlock){
                pushRight = true;
            }else if(blockInLeftSide == pushBlock){
                pushRight = false;
            }
        }
    }
    public void turn(float impulse){
            if (isIdle()) {
                setStateWalk();
                turnImpulse = impulse;
                return;
            }
            if (isJump() || isFall()) {
                if (isBlockInTurnDirection()) {
                    turnImpulse = 0;
                }
                setTurnVelocityByMultiplier(SPEED_MULTIPIER_IN_AIR);
            }
            if(isPush()){
               //setPushSpriteSide();
            }
    }
    private boolean isBlockInTurnDirection(){
        if ((turnImpulse > 0 && sensorRight.size() > 0) ||
                (turnImpulse < 0 && sensorLeft.size() > 0)) {
            return true;
        }
        return false;
    }
    public void stopWalk(){
        if(currentState == State.WALK){
            idle();
        }
    }
    private boolean isPossibleToPush(){
        if(!moveRight
                && blockInLeftSide == null){
            return false;
        }
        if(moveRight
                && blockInRightSide == null){
            return false;
        }
        return true;
    }

    private Block choiceBlockForPush(){
        if(blockInRightSide != null && moveRight && sensorRight.size() > 0 &&
        isPossibleToPushBlock(blockInRightSide)){
            pushRight = true;
            return blockInRightSide;
        }else if(blockInLeftSide != null && !moveRight && sensorLeft.size() > 0 &&
                isPossibleToPushBlock(blockInLeftSide)){
            pushRight = false;
            return blockInLeftSide;
        }
        return null;
    }
    private boolean isPossibleToPushBlock(Block blockToPush){
        if(blockToPush != null &&
                !blockToPush.isIdle()
                || blockController.getUpBlock(blockToPush) != null ||
                (moveRight && blockController.getRightBlock(blockToPush) != null) ||
                (!moveRight && blockController.getLeftBlock(blockToPush) != null)){
            return false;
        }
        return true;
    }
    private void setStatePush(){
        if(pushBlock != null){
            currentState = State.PUSH;
            pushImpulse = turnImpulse;
            stateTime = 0;
        }
    }
    public void push(float impulse){
        if(isIdle() || isWalk() || isJump() || isFall()) {
            blockInRightSide = blockController.getRightBlock(this);
            blockInLeftSide = blockController.getLeftBlock(this);
            if (!isPossibleToPush()) {
                return;
            }
            pushBlock = choiceBlockForPush();
            setStatePush();
        }
    }


    public boolean load(){
        GameSettings.getInstance().loadHero();
        setLoadParameters();
        setReturnPosition();
        updateSpritePosition(elemStandAnim.getKeyFrame(stateTime),true);
        return true;
    }

    @Override
    public void renderDebug(ShapeRenderer shapeRenderer) {
        super.renderDebug(shapeRenderer);
    }

    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;
        if(body == null) { return; }
        checkContacts();
        updatePositionInCells();
        updateByState(deltaTime);
        if(isBlockUnderHero() && !isHeroIsFall()){ dead();return;}
    }

    private void checkContacts(){
        Set<AbstractGameObject> objToDel = new HashSet<AbstractGameObject>();
        int posMasX = (int) (returnPosition.x / 10) - 5;
        int posMasY = (int) (returnPosition.y / 10) - 15;
        if(posMasY > 0){
            Block block;
            block = (blockController.getBlockFromGridByCoordinate(posMasX,posMasY));
            if(block != null)sensorDown.add(block);
        }else{
            sensorDown.add(gameWorld.getRegionDown());
        }

        for(AbstractGameObject obj : sensorDown){
            float xDist = Math.abs(getBodyPosition().x - (obj.getX() + obj.getWidth()/2));
            float yDist = Math.abs(getBodyPosition().y - (obj.getY() + obj.getHeight() / 2));
            float centerDist = (float)Math.sqrt(xDist * xDist + yDist * yDist);

            float widthDist = (getWidth() + obj.getWidth())/2 ;
            float heightDist = (getHeight() + obj.getHeight())/2;
            float centerWidthHeight = (float)Math.sqrt(widthDist*widthDist + heightDist* heightDist);
            if(xDist * 0.8f > widthDist ||
                    yDist * 0.8f  > heightDist

            ){
                objToDel.add(obj);
            }
        }
        sensorDown.removeAll(objToDel);

        objToDel.clear();
        for(AbstractGameObject obj : sensorUp){
            float xDist = Math.abs(getBodyPosition().x - (obj.getX() + obj.getWidth()/2));
            float yDist = Math.abs(getBodyPosition().y - (obj.getY() + obj.getHeight() / 2));
            float centerDist = (float)Math.sqrt(xDist * xDist + yDist * yDist);

            float widthDist = (getWidth() + obj.getWidth())/2 ;
            float heightDist = (getHeight() + obj.getHeight())/2;
            float centerWidthHeight = (float)Math.sqrt(widthDist*widthDist + heightDist* heightDist);
            if(xDist * 0.8f > widthDist ||
                    yDist * 0.8f  > heightDist

            ){
                objToDel.add(obj);
            }
        }
        sensorUp.removeAll(objToDel);

        objToDel.clear();
        for(AbstractGameObject obj : sensorRight){
            float xDist = Math.abs(getBodyPosition().x - (obj.getX() + obj.getWidth()/2));
            float yDist = Math.abs(getBodyPosition().y - (obj.getY() + obj.getHeight() / 2));
            float centerDist = (float)Math.sqrt(xDist * xDist + yDist * yDist);

            float widthDist = (getWidth() + obj.getWidth())/2 ;
            float heightDist = (getHeight() + obj.getHeight())/2;
            float centerWidthHeight = (float)Math.sqrt(widthDist*widthDist + heightDist* heightDist);
            if(xDist * 0.8f > widthDist ||
                    yDist * 0.8f  > heightDist

            ){
                objToDel.add(obj);
            }
        }
        sensorRight.removeAll(objToDel);

        objToDel.clear();
        for(AbstractGameObject obj : sensorLeft){
            float xDist = Math.abs(getBodyPosition().x - (obj.getX() + obj.getWidth()/2));
            float yDist = Math.abs(getBodyPosition().y - (obj.getY() + obj.getHeight() / 2));
            float centerDist = (float)Math.sqrt(xDist * xDist + yDist * yDist);

            float widthDist = (getWidth() + obj.getWidth())/2 ;
            float heightDist = (getHeight() + obj.getHeight())/2;
            float centerWidthHeight = (float)Math.sqrt(widthDist*widthDist + heightDist* heightDist);
            if(xDist * 0.8f > widthDist ||
                    yDist * 0.8f  > heightDist

            ){
                objToDel.add(obj);
            }
        }
        sensorLeft.removeAll(objToDel);

    }

    private void updateByState(float deltaTime){
        switch (currentState){
            case IDLE:
                stateIdle(deltaTime);
                break;
            case JUMP:
                stateJump(deltaTime);
                break;
            case FALL:
                stateFall(deltaTime);
                break;
            case PUSH:
                statePush(deltaTime);
                break;
            case WALK:
                stateWalk(deltaTime);
                break;
            case DEAD:
                stateDead(deltaTime);
                break;
            case DISPOSE:
                break;
        }
    }
    private void stateIdle(float deltaTime){
            if(isHeroIsFall()){fall();return;}
            setTurnVelocityByMultiplier(SPEED_MULTIPIER_IDLE);
            setReturnPositionY();
            updateSpritePosition(elemStandAnim.getKeyFrame(stateTime,true),moveRight);
        }
    private void stateJump(float deltaTime){
            if(!isHeroIsFall() && stateTime > 1f){ idle(); return; }
            updateSpritePosition(elemJumpAnim.getKeyFrame(stateTime,false),moveRight);
        }
    private void stateFall(float deltaTime){
            if(!isHeroIsFall()){idle();return;}
            updateSpritePosition(elemJumpAnim.getKeyFrame(stateTime,false),moveRight);
        }
    private void statePush(float deltaTime){
            if(isPossibilityToPushBack()){fall();return;}
            if(isPushedBlockNearHero()){idle();return;}
            setReturnPositionY();
            setTurnVelocityByMultiplier(SPEED_MULTIPIER_PUSH);
            pushBlock.push(body.getLinearVelocity().x);
            updateSpritePosition(elemPushAnim.getKeyFrame(stateTime,false),pushRight);
        }
    private void stateWalk(float deltaTime){
            if(isHeroIsFall()){fall();return;}
            setTurnVelocityByMultiplier(SPEED_MULTIPIER_WALK);
            setReturnPositionY();
            updateSpritePosition(elemWalkAnim.getKeyFrame(stateTime,true),moveRight);
        }
    private void stateDead(float deltaTime){
            if(stateTime> elemDeathAnim.getAnimationDuration()) {
                setStateDisposeAndDestroyBody();
            }else {
                updateSpritePosition(elemDeathAnim.getKeyFrame(stateTime,false),moveRight);
            }
    }

    private boolean isBlockUnderHero(){
        if(getSensorUp().size() > 0 &&
                (blockController.getUpBlock(this) != null ||
                blockController.getUpBlock(blockController.getUpBlock(this)) != null)) {
            return true;
        }
        return false;
    }
    private boolean isHeroIsFall(){
        if(sensorDown.size() == 0 &&
                (blockController.getDownBlock(this) == null ||
                        (positionInGameGrid.y == 0)) ){
            return true;
        }
        return false;
    }
    private boolean isPossibilityToPushBack(){
        if(pushRight && !moveRight &&
                blockController.getDownBlock(this) == null
                && blockController.getRightBlock(this) != null){
            return true;
        }
        if(!pushRight && moveRight &&
                blockController.getDownBlock(this) == null
                && blockController.getLeftBlock(this) != null){
            return true;
        }
        return  false;
    }
    private boolean isPushedBlockNearHero(){
        if(sensorLeft.isEmpty() && sensorRight.isEmpty()){
            return true;
        }
        return false;
    }

    public void idle(){
        currentState = State.IDLE;
        stateTime = 0;
    }
    public void setStateWalk(){
        currentState = State.WALK;
        stateTime = 0;
    }
    public void jump(){
        if(isWalk() || isIdle()) {
            stateTime = 0;
            currentState = State.JUMP;
            body.applyLinearImpulse(0, IMPULSE_Y,getWidth()/2,getHeight()/2,true);
        }
    }
    public void fall(){
        if(isWalk() || isIdle() || isPush() || isJump()) {
            stateTime = 0;
            currentState = State.FALL;
            body.applyLinearImpulse(0, IMPULSE_FALL,getWidth()/2,getHeight()/2,true);
        }
    }
    public void dead(){
        if(currentState != State.DEAD) {
            stateTime = 0;
            currentState = State.DEAD;
        }
    }
    private void setStateDisposeAndDestroyBody(){
        gameWorld.destroyBody(body);
        body = null;
        currentState = State.DISPOSE;
    }

    private void setLoadParameters(){
        body.setTransform(GameSettings.getInstance().getHeroX(),GameSettings.getInstance().getHeroY(),0);
        returnPosition.x = GameSettings.getInstance().getHeroX() + getWidth()/2;
        returnPosition.y = GameSettings.getInstance().getHeroY() + getHeight()/2;
    }

    private void setTurnVelocityByMultiplier(float multiplier){
        setTurnVelocityByMultiplier(multiplier, moveRight);
    }
    private void setTurnVelocityByMultiplier(float multiplier, boolean sideMultipier){
        int movedSideMultipier = 1;
        if(!sideMultipier){movedSideMultipier = -1;}
        body.setLinearVelocity(new Vector2(movedSideMultipier* turnImpulse * multiplier,body.getLinearVelocity().y));
    }
    // Update this Sprite to correspond with the position of the Box2D body.
    private void updateSpritePosition(TextureRegion spriteTexture, boolean turnSpriteRight){
        setRegion(spriteTexture);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2,
                spriteTexture.getRegionWidth() * 0.12f,spriteTexture.getRegionHeight() * 0.12f);
        if(turnSpriteRight){setFlip(true,false);}
    }
    private void updatePositionInCells(){
        updateReturnPositionX();
        updateReturnPositionY();
        updatePositionInGrid();
    }
    protected void updatePositionInGrid(){
        positionInGameGrid.set((returnPosition.x / 10) - 5, (returnPosition.y / 10) - 15);
    }
    private void updateReturnPositionX(){
        float x = body.getPosition().x;
        int leftReg = 53, rightReg = 57;
        for(int i = 0; i < 12; i++){
            if(x >= leftReg && x < rightReg){
                returnPosition.x = (rightReg + leftReg) >> 1;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }
    }
    private void updateReturnPositionY(){
        float y = body.getPosition().y - 2;
        int leftReg = 141,rightReg = 149;
        for(int i = 0; i < 20; i++){
            if(y > leftReg && y < rightReg){
                returnPosition.y = leftReg - 1 ;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }
        if(getY() < 0){
            returnPosition.y = 160;
        }
    }
    private void setReturnPosition(){
        setReturnPositionX();
        setReturnPositionY();
    }
    private void setReturnPositionX(){
        body.setTransform(returnPosition.x + getWidth() / 2, body.getPosition().y, 0);
    }
    private void setReturnPositionY(){
        body.setTransform(body.getPosition().x,returnPosition.y + getHeight()/2 + 0.5f,0);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
            draw(spriteBatch);
    }
    public Set<AbstractGameObject> getSensorRight() {
        return sensorRight;
    }
    public Set<AbstractGameObject> getSensorLeft() {
        return sensorLeft;
    }
    public Set<AbstractGameObject> getSensorUp() {
        return sensorUp;
    }
    public Set<AbstractGameObject> getSensorDown() {
        return sensorDown;
    }

    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }
    public Vector2 getVelocity(){
        return body.getLinearVelocity();
    }
    public Vector2 getReturnPosition() {
        return returnPosition;
    }

    public boolean isIdle(){return currentState == State.IDLE;}
    public boolean isWalk(){return currentState == State.WALK;}
    public boolean isJump(){return currentState == State.JUMP;}
    public boolean isPush(){return currentState == State.PUSH;}
    public boolean isFall(){return currentState == State.FALL;}
    @Override
    public boolean isDisposable() {
        return currentState == State.DISPOSE;
    }

    @Override
    public String toString() {
        return "WaterElement{" +
                ", currentState=" + currentState +
                ", moveRight=" + moveRight +
                ", returnCellsPositionY=" + returnPosition.y +
                ", returnCellsPositionX=" + returnPosition.x +
                '}';
    }
}
