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
import com.daleondeveloper.Sprites.Platform;

public class WaterElement extends AbstractDynamicObject {
    private static final String TAG = WaterElement.class.getName();

    private static final float CIRCLE_SHAPE_RADIUS_METERS = 30.0f / GameCamera.PPM;
    private static final float SENSOR_HX = 0.1f;
    private static final float SENSOR_HY = 0.01f;
    private static final float IMPULSE_Y = 7.5f;
    private static final float SCALE_IMPULSE_X = 30.0f;
    private static final float POWER_JUMP_OFFSET_Y = 1.0f;
    private static final int SUCCESSFUL_JUMP_SCORE = 2;
    private static final int PERFECT_JUMP_SCORE = 3;
    private static final float PERFECT_JUMP_TOLERANCE = 0.1f;
    private static final float MIN_SPEAK_TIME = 7.0f;
    private static final float MAX_SPEAK_TIME = 10.0f;
    private static final int MAX_BULLETS = 1;

    private enum State{
        IDLE,WALK,JUMP, USE_SKILL1,DEAD,DISPOSE
    }

    private PlayScreen playScreen;
    private GameWorld gameWorld;
    private TextureRegion elemTexReg;
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
    private FixtureDef fixtureSkill;
    private CircleShape circleShapeSkillFixture;

    private State debugState;

    public WaterElement (PlayScreen playScreen, GameWorld gameWorld, float x, float y){
        this.playScreen = playScreen;
        this.gameWorld = gameWorld;

        AssetWaterElement assetWaterElem = Assets.getInstance().getAssetWaterElement();
        elemStandAnim = assetWaterElem.getElementOfWaterStand();
        elemWalkAnim = assetWaterElem.getElementOfWaterWalk();
        elemPushAnim = assetWaterElem.getElementOfWaterPush();
        elemJumpAnim = assetWaterElem.getElementOfWaterWalk();
        elemDeathAnim = assetWaterElem.getElementOfWaterStand();
        elemTexReg = assetWaterElem.getElementOfWater();

        setBounds(x,y, 1,1.5f);
        setRegion(elemTexReg);
        stateTime = 0;

        turnImpulse = 0f;
        defineWaterElem();

        currentState = State.IDLE;
        debugState = currentState;
        moveRight = true;
        turn = false;
        stopElem = false;
        activateElem = true;
        currentPlatform = null;

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

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(getHeight()/2);
        fixtureDef.filter.categoryBits = WorldContactListner.CATEGORY_WATER_ELEM_BIT;
        fixtureDef.filter.maskBits = WorldContactListner.MASK_ALL;
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.0f;
        body.createFixture(fixtureDef).setUserData(this);

        setFilters();

    }

    private void setFilters(){
//        Filter filterWaterElem = new Filter();
//        filterWaterElem.categoryBits = WorldContactListner.CATEGORY_WATER_ELEM_BIT;
//        filterWaterElem.maskBits = WorldContactListner.MASK_ALL;
//
//        Filter filterSensor = new Filter();
//        filterSensor.categoryBits = WorldContactListner.CATEGORY_WATER_ELEM_BIT;
//        filterSensor.maskBits = WorldContactListner.MASK_ALL;
//
//        // We set the previous filters in every fixture
//        for (Fixture fixture : body.getFixtureList()) {
//            if (fixture.isSensor()) {
//                fixture.setFilterData(filterSensor);
//            } else {
//                fixture.setFilterData(filterWaterElem);
//            }
//        }
    }

    private void removeFilters(){
        //hero cant collide with anything
        Filter filter = new Filter();
        filter.categoryBits = WorldContactListner.CATEGORY_WATER_ELEM_BIT;
        filter.maskBits = WorldContactListner.CATEGORY_BLOCK_BIT;

        for(Fixture fixture : body.getFixtureList()){
            fixture.setFilterData(filter);
        }
    }

    public void jump(){
        if(currentState == State.WALK || currentState == State.IDLE) {
            stateTime = 0;
            currentState = State.JUMP;
            body.applyLinearImpulse(new Vector2(0, IMPULSE_Y), body.getWorldCenter(), true);
        }
    }
    public void endJump(){
        if(currentState == State.JUMP && getVelocity().y < -0.001f){
            currentState = State.IDLE;
            stateTime = 0;
        }
    }
    public void turn(float impulse){
        if(impulse > 0){
            moveRight = true;
        }else {
            moveRight = false;
        }
        impulse*= 2;
        if(currentState == State.IDLE) {
            currentState = State.WALK;
            stateTime = 0;

        }
        if(currentState == State.JUMP){
            impulse /= 2;
        }
        turnImpulse = impulse;

    }
    public void idle(){
        currentState = State.IDLE;
        stateTime = 0;
        if(body.getLinearVelocity().x != 0 ){
            currentState = State.WALK;
        }
        initVoice();
    }
    public void push(float impulse){
        currentState = State.USE_SKILL1;
        body.applyForceToCenter(impulse/2,body.getLinearVelocity().y,true);

    }
    public void onDead(){
        currentState = State.DEAD;
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
    public boolean isPush(){return currentState == State.USE_SKILL1;}

    @Override
    public void renderDebug(ShapeRenderer shapeRenderer) {
        super.renderDebug(shapeRenderer);
    }

    @Override
    public void update(float deltaTime) {

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
            case USE_SKILL1:
                statePush(deltaTime);
            case WALK:
                stateWalk(deltaTime);
                body.setLinearVelocity(new Vector2(turnImpulse*2,getVelocity().y));
                break;
            case DEAD:
                stateDead(deltaTime);
                break;
            case DISPOSE:
                break;
        }

    }

        private void stateIdle(float deltaTime){
            if(stopElem){
                stopElem = false;
            }
            stateTime += deltaTime;

            speakTime += deltaTime;
            // Update this Sprite to correspond with the position of the Box2D body.
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) elemStandAnim.getKeyFrame(stateTime, true));

            if(speakTime > timeToSpeak){
                initVoice();
            }
        }
        private void stateJump(float deltaTime){
            if(activateElem){
                setFilters();
                activateElem = false;
            }

            // Update this Sprite to correspond with the position of the Box2D body.
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) elemJumpAnim.getKeyFrame(stateTime, true));
            stateTime += deltaTime;
        }
        private void statePush(float deltaTime){
            if(activateElem){
                setFilters();
                activateElem = false;
            }

            // Update this Sprite to correspond with the position of the Box2D body.
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) elemPushAnim.getKeyFrame(stateTime, true));
            stateTime += deltaTime;
        }
        private void stateWalk(float deltaTime){
            if(activateElem){
                setFilters();
                activateElem = false;
            }
            stateTime += deltaTime;
            // Update this Sprite to correspond with the position of the Box2D body.
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) elemWalkAnim.getKeyFrame(stateTime, true));
            if(!moveRight)
            setFlip(true,false);
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
        return currentState == State.DISPOSE;
    }
}
