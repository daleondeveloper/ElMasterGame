package com.daleondeveloper.Sprites.Hero;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetWaterElement;
import com.daleondeveloper.Game.tools.WorldContactListner;
import com.daleondeveloper.Game.GameCamera;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.AbstractDynamicObject;
import com.daleondeveloper.Sprites.AbstractGameObject;
import com.daleondeveloper.Sprites.Block;
import com.daleondeveloper.Sprites.Platform;

import java.util.ArrayList;
import java.util.List;

public class WaterElement extends AbstractDynamicObject {
    private static final String TAG = WaterElement.class.getName();

    private static final float IMPULSE_Y = 30f;
    private static final float IMPULSE_FALL = -30f;
    private static final float MIN_SPEAK_TIME = 7.0f;
    private static final float MAX_SPEAK_TIME = 10.0f;

    private enum State{
        IDLE,WALK,JUMP,FALL,PUSH,DEAD,DISPOSE
    }

    private PlayScreen playScreen;
    private GameWorld gameWorld;
    private TextureRegion elemTexReg;
    private TextureRegion newHero;
    private Animation elemStandAnim;
    private Animation elemWalkAnim;
    private Animation elemPushAnim;
    private Animation elemJumpAnim;
    private Animation elemDeathAnim;
    private float stateTime;
    private Body body;
    private State currentState;
    private boolean stopElem;
    private boolean activateElem;
    private boolean moveRight;
    private Platform currentPlatform;
    private float speakTime;
    private float timeToSpeak;
    private boolean turn;
    private float turnImpulse;
    private float pushImpulse;
    private FixtureDef fixtureSkill;
    private CircleShape circleShapeSkillFixture;

    private Block pushBlock;

    //marks
    private List<AbstractGameObject> sensorRight;
    private List<AbstractGameObject> sensorLeft;
    private List<AbstractGameObject> sensorUp;
    private List<AbstractGameObject> sensorDown;

    private State debugState;

    public WaterElement (PlayScreen playScreen, GameWorld gameWorld, float x, float y){
        this.playScreen = playScreen;
        this.gameWorld = gameWorld;

        AssetWaterElement assetWaterElem = Assets.getInstance().getAssetWaterElement();
        newHero = assetWaterElem.getNewHero();
        elemStandAnim = assetWaterElem.getElementOfWaterStand();
        elemWalkAnim = assetWaterElem.getElementOfWaterWalk();
        elemPushAnim = assetWaterElem.getElementOfWaterPush();
        elemJumpAnim = assetWaterElem.getElementOfWaterWalk();
        elemDeathAnim = assetWaterElem.getElementOfWaterStand();
        elemTexReg = assetWaterElem.getElementOfWater();

        setBounds(x,y, 8f,14f);
        setRegion(elemTexReg);
        stateTime = 0;

        turnImpulse = 0f;
        pushImpulse = 0f;
        defineWaterElem();

        currentState = State.IDLE;
        debugState = currentState;
        moveRight = true;
        turn = false;
        stopElem = false;
        activateElem = true;
        currentPlatform = null;
        pushBlock = null;

        sensorDown = new ArrayList<AbstractGameObject>();
        sensorLeft = new ArrayList<AbstractGameObject>();
        sensorRight = new ArrayList<AbstractGameObject>();
        sensorUp = new ArrayList<AbstractGameObject>();

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
        polygonShape.setAsBox(getWidth()/2f,getHeight()/2f);
        fixtureDef.filter.categoryBits = WorldContactListner.CATEGORY_WATER_ELEM_BIT;
       // fixtureDef.filter.maskBits = WorldContactListner.MASK_ALL;
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
        polygonShape.setAsBox((getWidth()/2)*0.95f,0.1f, new Vector2(0,(-getHeight()/2)+0.05f),0);
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


    public void jump(){
        if(isWalk() || isIdle()) {
            stateTime = 0;
            currentState = State.JUMP;
            body.applyLinearImpulse(0, 50,getWidth()/2,getHeight()/2,true);
        }
    }
    public void fall(){
        if(isWalk() || isIdle() || isPush() || isJump()) {
            if(isJump()){
                body.setLinearVelocity(body.getLinearVelocity().x, IMPULSE_FALL);
            }  else {
                body.setLinearVelocity(body.getLinearVelocity().x / 2, IMPULSE_FALL);
            }
            stateTime = 0;
            currentState = State.FALL;
        }
    }
    public void turn(float impulse){
        if(currentState != State.PUSH) {
            if (impulse > 0) {
                moveRight = true;
            } else {
                moveRight = false;
            }
            if (isPush()) {
                if ((moveRight && sensorRight.size() > 0) || (!moveRight && sensorLeft.size() > 0)) {
                    return;
                }
            }
            if (isIdle()) {
                currentState = State.WALK;
                stateTime = 0;
            }
            if (isJump()) {
                if ((impulse > 0 && sensorRight.size() > 0) ||
                        (impulse < 0 && sensorLeft.size() > 0)) {
                    impulse = 0;
                }
                body.setLinearVelocity(impulse, body.getLinearVelocity().y);
            }
            turnImpulse = impulse;
        }

    }

    public void stopWalk(){
        if(currentState == State.WALK){
            currentState = State.IDLE;
            stateTime = 0;
            body.setLinearVelocity(0,body.getLinearVelocity().y);
        }

    }
    public void idle(){
        currentState = State.IDLE;
        stateTime = 0;
        if(pushBlock != null){
            pushBlock.idle();
        }
    }
    public void push(float impulse){
        if(isIdle() || isWalk() || isJump()){
            currentState = State.PUSH;
            turnImpulse = impulse;
            stateTime = 0;
        }
    }
    public void onDead(){
        currentState = State.DEAD;
    }

    @Override
    public void renderDebug(ShapeRenderer shapeRenderer) {
        super.renderDebug(shapeRenderer);
    }

    @Override
    public void update(float deltaTime) {
        body.setGravityScale(10);
            if(currentState != debugState){
                debugState = currentState;
                System.out.println(debugState);
            }
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
            if(sensorDown.size() == 0){
                body.setGravityScale(10);
            }

    }

    private void stateIdle(float deltaTime){
        //Logic
            if(stopElem){stopElem = false; }
            if(sensorDown.size() == 0 || stateTime > 5){ fall();}

            body.setGravityScale(1);
            body.setLinearVelocity(0,0);

            //render
            // Update this Sprite to correspond with the position of the Box2D body.
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
           // setRegion((TextureRegion) elemStandAnim.getKeyFrame(stateTime, true));
            setRegion(newHero);
            if(!moveRight){setFlip(true,false);}
            stateTime += deltaTime;
        }
    private void stateJump(float deltaTime){
            if(sensorDown.size() > 0 && stateTime > 1f){ idle(); }
            if(sensorUp.size() > 0){ fall(); }
            if(sensorLeft.size() > 0 || sensorRight.size() > 0){ body.getLinearVelocity().x = 0; }

            // Update this Sprite to correspond with the position of the Box2D body.
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) elemJumpAnim.getKeyFrame(stateTime, true));
            if(!moveRight){setFlip(true,false);}
            stateTime += deltaTime;
        }
    private void stateFall(float deltaTime){
            if(sensorDown.size() > 0){ idle(); }
            if(sensorLeft.size() > 0 && body.getLinearVelocity().x < 0){
                body.getLinearVelocity().x = 0;
            }
            if(sensorRight.size() > 0 && body.getLinearVelocity().x > 0){
                body.getLinearVelocity().x = 0;
            }
            body.setLinearVelocity(body.getLinearVelocity().x,IMPULSE_FALL);

            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) elemJumpAnim.getKeyFrame(stateTime, true));
            if(!moveRight){setFlip(true,false);}
            stateTime += deltaTime;
        }
    private void statePush(float deltaTime){
        System.out.println("deltaTime = " + stateTime);
            if(turnImpulse == 0){
                idle();
                return;
            }
            if(moveRight && sensorRight.size() > 0){
                for(AbstractGameObject f : sensorRight){
                    if(f instanceof Block){
                        Block block = (Block)f;
                        block.push(turnImpulse);
                        body.setLinearVelocity(turnImpulse,body.getLinearVelocity().y);
                        pushBlock = block;
                    }
                }
            }
            if(!moveRight && sensorLeft.size() > 0){
                for(AbstractGameObject f : sensorLeft){
                    if(f instanceof Block){
                        Block block = (Block)f;
                        block.push(-turnImpulse);
                        body.setLinearVelocity(-turnImpulse,body.getLinearVelocity().y);
                        pushBlock = block;
                    }
                }
            }

            // Update this Sprite to correspond with the position of the Box2D body.
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) elemJumpAnim.getKeyFrame(stateTime, true));
            if(!moveRight){setFlip(true,false);}
            stateTime += deltaTime;
        }
    private void stateWalk(float deltaTime){

            if(activateElem){
                activateElem = false;
            }
            body.setGravityScale(1);
            body.setLinearVelocity(new Vector2(turnImpulse*2,getVelocity().y));

            if(sensorDown.size() < 1){
                fall();
            }
//            if((sensorLeft.size() > 0 && body.getLinearVelocity().x < 0 ) ||
//                    (sensorRight.size() > 0 && body.getLinearVelocity().x > 0)){
//                fall();
//            }
            if(!gameWorld.isRightButtonPressed() && !gameWorld.isLeftButtonPressed()){
                fall();
            }
            // Update this Sprite to correspond with the position of the Box2D body.
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) elemWalkAnim.getKeyFrame(stateTime, true));
            if(!moveRight){setFlip(true,false);}
            stateTime += deltaTime;
        }
    private void stateDead(float deltaTime){
            gameWorld.destroyBody(body);
            currentState = State.DISPOSE;
        }
    @Override
    public void render(SpriteBatch spriteBatch) {
            draw(spriteBatch);
    }

    @Override
    public boolean isDisposable() {
        return false;
    }

    public List<AbstractGameObject> getSensorRight() {
        return sensorRight;
    }

    public List<AbstractGameObject> getSensorLeft() {
        return sensorLeft;
    }

    public List<AbstractGameObject> getSensorUp() {
        return sensorUp;
    }

    public List<AbstractGameObject> getSensorDown() {
        return sensorDown;
    }

    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }
    public Vector2 getVelocity(){
        return body.getLinearVelocity();
    }
    public boolean isIdle(){return currentState == State.IDLE;}
    public boolean isWalk(){return currentState == State.WALK;}
    public boolean isJump(){return currentState == State.JUMP;}
    public boolean isPush(){return currentState == State.PUSH;}
}
