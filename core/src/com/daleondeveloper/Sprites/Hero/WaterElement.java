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
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.tools.WorldContactListner;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.AbstractDynamicObject;
import com.daleondeveloper.Sprites.AbstractGameObject;
import com.daleondeveloper.Sprites.Blocks.Block;
import com.daleondeveloper.Sprites.Platform;

import java.util.HashSet;
import java.util.Set;

public class WaterElement extends AbstractDynamicObject {
    private static final String TAG = WaterElement.class.getName();

    private static float IMPULSE_Y = 50f;
    private static float IMPULSE_FALL = -30f;
    private static final float MIN_SPEAK_TIME = 7.0f;
    private static final float MAX_SPEAK_TIME = 10.0f;

    private enum State{
        IDLE,WALK,JUMP,FALL,PUSH,DEAD,DISPOSE
    }

    private PlayScreen playScreen;
    private GameWorld gameWorld;
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
    private boolean stopElem;
    private boolean activateElem;
    private boolean moveRight;
    private boolean pushRight;
    private Platform currentPlatform;
    private float speakTime;
    private float timeToSpeak;
    private boolean turn;
    private float turnImpulse;
    private float pushImpulse;
    private float returnCellsPositionY;
    private float returnCellsPositionX;
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
        stopElem = false;
        activateElem = true;
        currentPlatform = null;
        pushBlock = null;

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


    public void jump(){
        if(isWalk() || isIdle()) {
            stateTime = 0;
            currentState = State.JUMP;
            body.applyLinearImpulse(0, IMPULSE_Y,getWidth()/2,getHeight()/2,true);
        }
    }
    public void fall(){
        if(isWalk() || isIdle() || isPush() || isJump()) {
                body.applyLinearImpulse(0, IMPULSE_FALL,getWidth()/2,getHeight()/2,true);

            stateTime = 0;
            currentState = State.FALL;
        }
    }
    public void turn(float impulse){

            if (impulse > 0) {
                moveRight = true;
            } else {
                moveRight = false;
            }
            if (isPush()) {
                pushImpulse = impulse;
                turnImpulse = impulse;
                if ((moveRight && sensorRight.size() > 0) || (!moveRight && sensorLeft.size() > 0)) {
                    return;
                }
            }
            if (isIdle()) {
                currentState = State.WALK;
                stateTime = 0;
            }
            if (isJump() || isFall()) {
                if ((impulse > 0 && sensorRight.size() > 0) ||
                        (impulse < 0 && sensorLeft.size() > 0)) {
                    impulse = 0;
                }
                impulse *= 0.66f;
                body.setLinearVelocity(impulse , body.getLinearVelocity().y);
            }
            turnImpulse = impulse ;


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
//        if(pushBlock != null){
//            pushBlock.idle();
//        }
    }
    public void push(float impulse){

        if(isIdle() || isWalk() || isJump() || isFall() ||
                (sensorDown.size() == 0 && sensorLeft.size() == 0 && sensorRight.size() == 0)){
            int posMasX = (int) (getReturnCellsPositionX() / 10) - 5;
            int posMasY = (int) (getReturnCellsPositionY() / 10) - 15;
            boolean blockForPushRight = true;

            if(pushRight && !moveRight && posMasY > 0 && gameWorld.getBlockController().getBlocksMas()[posMasX][posMasY - 1] == null
                    && posMasX < gameWorld.getBlockController().getBlocksMas().length - 1 && gameWorld.getBlockController().getBlocksMas()[posMasX + 1][posMasY] != null){
                fall();
                return;
            }
            if(!pushRight && moveRight && posMasY > 0 && gameWorld.getBlockController().getBlocksMas()[posMasX][posMasY - 1] == null
                    && posMasX > 0 && gameWorld.getBlockController().getBlocksMas()[posMasX - 1][posMasY] != null){
                fall();
                return;
            }

            Block[][] blocksMas = gameWorld.getBlockController().getBlocksMas();
            if(sensorRight.size() > 0 && sensorLeft.size() > 0 &&
            posMasX > 0 && posMasX < blocksMas.length - 1){
                float lendthToLeftblock = 0;
                if(blocksMas[posMasX - 1][posMasY] != null){
                    Math.abs(blocksMas[posMasX - 1][posMasY].getBodyPosition().x - body.getPosition().x);
                }else{
                    blockForPushRight = true;
                }
                float lendthToRightblock = 0;
                if(blocksMas[posMasX + 1][posMasY] != null){
                    Math.abs(blocksMas[posMasX + 1][posMasY].getBodyPosition().x - body.getPosition().x);
                }else {
                    blockForPushRight = false;
                }
                if(lendthToLeftblock != 0 && lendthToRightblock != 0 && lendthToLeftblock - lendthToRightblock < 0){
                    blockForPushRight = false;
                }
            }
                if( posMasX < blocksMas.length - 1 && sensorRight.size() > 0 && blockForPushRight){
                    Block block = blocksMas[posMasX + 1][posMasY];
                    Block secondRightBlock = null;
                    if(posMasX <= 7){
                        secondRightBlock = (blocksMas[posMasX +2][posMasY]);
                    }
                    if(block == null || !block.isIdle() ||
                    blocksMas[posMasX+1][posMasY+1] != null){
                        return;
                    }else{
                        pushBlock = block;
                        currentState = State.PUSH;
                        pushRight = true;
                        pushImpulse = turnImpulse;
                        //turnImpulse = impulse + playScreen.getHud().getScore() / 5;
                        stateTime = 0;
                    }
                    return;
                }
                if(posMasX > 0 ){
                    Block block = blocksMas[posMasX - 1][posMasY];
                    Block secondLeftBlock = null;
                    if(posMasX >= 2){
                        secondLeftBlock = (blocksMas[posMasX - 2][posMasY]);
                    }
                    if(block == null || !block.isIdle() ||
                    blocksMas[posMasX -1][posMasY + 1] != null){
                        return;
                    }else{
                        pushBlock = block;
                        currentState = State.PUSH;
                        pushRight = false;
                        pushImpulse = turnImpulse;
                       // turnImpulse = -(impulse + playScreen.getHud().getScore() / 5);
                        stateTime = 0;
                    }
                }
            }

    }
    public void onDead(){
        if(currentState != State.DEAD) {
            stateTime = 0;
            currentState = State.DEAD;
        }
    }

    public boolean load(){
        GameSettings.getInstance().loadHero();
        body.setTransform(GameSettings.getInstance().getHeroX(),GameSettings.getInstance().getHeroY(),0);
        returnCellsPositionX = GameSettings.getInstance().getHeroX() + getWidth()/2;
        returnCellsPositionY = GameSettings.getInstance().getHeroY() + getHeight()/2;
        body.setTransform(returnCellsPositionX,returnCellsPositionY,0);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion((TextureRegion) elemStandAnim.getKeyFrame(stateTime, true));
        return true;
    }

    @Override
    public void renderDebug(ShapeRenderer shapeRenderer) {
        super.renderDebug(shapeRenderer);
    }

    @Override
    public void update(float deltaTime) {
      //  body.setTransform(0,0,0);
        if(body != null) {
            body.setGravityScale(10);
        } else {
            return;
        }
        checkContacts();

          // checkContacts();
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
            if(body != null && sensorDown.size() == 0 ){
                body.setGravityScale(10);
            }
        if(getSensorUp().size() > 0) {
            onDead();
            return;
        }

    }

    private void checkContacts(){
        Block[][] blocksMass = gameWorld.getBlockController().getBlocksMas();
        Set<AbstractGameObject> objToDel = new HashSet<AbstractGameObject>();
        int posMasX = (int) (getReturnCellsPositionX() / 10) - 5;
        int posMasY = (int) (getReturnCellsPositionY() / 10) - 15;
        if(posMasY > 0){
            Block block;
            block = (blocksMass[posMasX][posMasY]);
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

    private void stateIdle(float deltaTime){
        //Logic
            if(stopElem){stopElem = false; }
            if(sensorDown.size() == 0 ){ fall(); return;}

           // body.setGravityScale(1);
            body.setLinearVelocity(0,0);
            body.setTransform(body.getPosition().x,returnCellsPositionY + getHeight()/2 + 0.5f,0);
            //render
            // Update this Sprite to correspond with the position of the Box2D body.
            TextureRegion textureRegion = (TextureRegion) elemStandAnim.getKeyFrame(stateTime, true);
        setRegion(textureRegion);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2,
                textureRegion.getRegionWidth() * 0.12f,textureRegion.getRegionHeight() * 0.12f);
//            setRegion(newHero);
            if(moveRight){setFlip(true,false);}
            stateTime += deltaTime;
        }
    private void stateJump(float deltaTime){
        float y = body.getPosition().y - 2;
        int leftReg = 141,rightReg = 149;
        for(int i = 0; i < 20; i++){
            if(y > leftReg && y < rightReg){
                returnCellsPositionY = leftReg - 1 ;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }
        float x = body.getPosition().x;
        leftReg = 53;
        rightReg = 57;
        for(int i = 0; i < 12; i++){
            if(x >= leftReg && x < rightReg){
                returnCellsPositionX = (rightReg + leftReg) >> 1;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }
            if(body.getLinearVelocity().y < 1){fall();return;}
            if(sensorDown.size() > 0 && stateTime > 1f){ idle(); return; }
            if(sensorUp.size() > 0 || stateTime > 1){ fall(); return;}
            if(sensorLeft.size() > 0 || sensorRight.size() > 0){ body.getLinearVelocity().x = 0; }

            // Update this Sprite to correspond with the position of the Box2D body.
        TextureRegion textureRegion = (TextureRegion) elemJumpAnim.getKeyFrame(stateTime, false);
            setRegion(textureRegion);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2,
                textureRegion.getRegionWidth() * 0.12f,textureRegion.getRegionHeight() * 0.12f);
        if(moveRight){setFlip(true,false);}
            stateTime += deltaTime;
        }
    private void stateFall(float deltaTime){
        float y = body.getPosition().y - 2;
        int leftReg = 141,rightReg = 149;
        for(int i = 0; i < 20; i++){
            if(y > leftReg && y < rightReg){
                returnCellsPositionY = leftReg -1 ;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }
        float x = body.getPosition().x;
        leftReg = 53;
        rightReg = 57;
        for(int i = 0; i < 12; i++){
            if(x >= leftReg && x < rightReg){
                returnCellsPositionX = (rightReg + leftReg) >> 1;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }
        if(getY() < 0){
            returnCellsPositionY = 160;
        }

        if(stateTime > 2) {
            body.setTransform(returnCellsPositionX + getWidth() / 2, body.getPosition().y, 0);
        }
            if(sensorDown.size() > 0){ idle(); }
            if(sensorLeft.size() > 0 && body.getLinearVelocity().x < 0){
                body.getLinearVelocity().x = 0;
            }
            if(sensorRight.size() > 0 && body.getLinearVelocity().x > 0){
                body.getLinearVelocity().x = 0;
            }
            body.setLinearVelocity(body.getLinearVelocity().x,IMPULSE_FALL);


            TextureRegion textureRegion = (TextureRegion) elemJumpAnim.getKeyFrame(stateTime, false);
            setRegion(textureRegion);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2,
                textureRegion.getRegionWidth() * 0.12f,textureRegion.getRegionHeight() * 0.12f);
        if(moveRight){setFlip(true,false);}
            stateTime += deltaTime;
        }
    private void statePush(float deltaTime){
        float x = body.getPosition().x;
        int leftReg = 53,rightReg = 57;
        for(int i = 0; i < 12; i++){
            if(x >= leftReg && x < rightReg){
                returnCellsPositionX = (rightReg + leftReg) >> 1;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }
        int posMasX = (int) (getReturnCellsPositionX() / 10) - 5;
        int posMasY = (int) (getReturnCellsPositionY() / 10) - 15;
        if(pushRight && !moveRight && posMasY > 0 && gameWorld.getBlockController().getBlocksMas()[posMasX][posMasY - 1] == null
         && posMasX < gameWorld.getBlockController().getBlocksMas().length - 1 && gameWorld.getBlockController().getBlocksMas()[posMasX + 1][posMasY] != null){
            fall();
            return;
        }
        if(!pushRight && moveRight && posMasY > 0 && gameWorld.getBlockController().getBlocksMas()[posMasX][posMasY - 1] == null
                && posMasX > 0 && gameWorld.getBlockController().getBlocksMas()[posMasX - 1][posMasY] != null){
            fall();
            return;
        }
            if(sensorLeft.isEmpty() && sensorRight.isEmpty()){
                idle();
                return;
            }

            body.setTransform(body.getPosition().x,returnCellsPositionY + getHeight()/2 + 0.5f,0);
            pushBlock.push(turnImpulse);
            body.setLinearVelocity(turnImpulse,body.getLinearVelocity().y);

            // Update this Sprite to correspond with the position of the Box2D body.
        TextureRegion textureRegion = (TextureRegion) elemPushAnim.getKeyFrame(stateTime, false);
        setRegion(textureRegion);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2,
                textureRegion.getRegionWidth() * 0.12f,textureRegion.getRegionHeight() * 0.12f);
        if(pushRight){setFlip(true,false);}
            stateTime += deltaTime;
        }
    private void stateWalk(float deltaTime){

            if(activateElem){
                activateElem = false;
            }
         float x = body.getPosition().x;
         int leftReg = 53,rightReg = 57;
            for(int i = 0; i < 12; i++){
            if(x >= leftReg && x < rightReg){
                returnCellsPositionX = (rightReg + leftReg) >> 1;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }
        int posMasX = (int) (getReturnCellsPositionX() / 10) - 5;
        int posMasY = (int) (getReturnCellsPositionY() / 10) - 15;
        if(posMasY > 0 && gameWorld.getBlockController().getBlocksMas()[posMasX][posMasY - 1] == null){
            fall();
            return;
        }
            body.setGravityScale(0);
            body.setLinearVelocity(new Vector2(turnImpulse*2,0));
        body.setTransform(body.getPosition().x,returnCellsPositionY + getHeight()/2 + 0.5f,0);

        if(sensorDown.size() < 1){
                fall();
            }
            if(!gameWorld.isRightButtonPressed() && !gameWorld.isLeftButtonPressed()){
                fall();
            }
            // Update this Sprite to correspond with the position of the Box2D body.
        TextureRegion textureRegion = (TextureRegion) elemWalkAnim.getKeyFrame(stateTime, true);
            setRegion(textureRegion);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2,
                textureRegion.getRegionWidth() * 0.12f,textureRegion.getRegionHeight() * 0.12f);
        if(moveRight){setFlip(true,false);}
            stateTime += deltaTime;
        }
    private void stateDead(float deltaTime){
            if(stateTime> elemDeathAnim.getAnimationDuration()) {
                gameWorld.destroyBody(body);
                body = null;
                currentState = State.DISPOSE;
            }else {
                TextureRegion textureRegion = (TextureRegion) elemDeathAnim.getKeyFrame(stateTime, false);
                //body.
                setRegion(textureRegion);
//                setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
                setBounds(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2,
                        textureRegion.getRegionWidth() * 0.12f,textureRegion.getRegionHeight() * 0.12f);
                if (moveRight) {
                    setFlip(true, false);
                }
                stateTime += deltaTime;
            }
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

    public float getReturnCellsPositionY() {
        return returnCellsPositionY;
    }

    public float getReturnCellsPositionX() {
        return returnCellsPositionX;
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
                ", returnCellsPositionY=" + returnCellsPositionY +
                ", returnCellsPositionX=" + returnCellsPositionX +
                '}';
    }
}
