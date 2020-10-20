package com.daleondeveloper.Sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetBlock;
import com.daleondeveloper.Game.GameCamera;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.WorldContactListner;
import com.daleondeveloper.Sprites.Hero.WaterElement;

import java.util.ArrayList;
import java.util.List;

public class Block extends AbstractDynamicObject {
    private static final String TAG = Block.class.getName();


    private static final float FALL_VELOCITY =  -100;

    public void skill1End() {
    }

    public void skill1Start() {
    }

    private enum State{
        FALL, IDLE, PUSH, DESTROY, DISPOSE;
    }

    private GameWorld gameWorld;
    private Array<TextureRegion> assetBlocks;
    private TextureRegion textureRegionBlock;
    private float stateTime;
    private boolean statePosition;
    private State currentState;
    private Body body;
    private List<Block> contactLeftBlockList ;
    private List<Block> contactRightBlockList ;
    private Platform upPlatform;
    //marks
    private boolean sensorRight;
    private boolean sensorLeft;
    private boolean sensorUp;
    private boolean sensorDown;

    public Block(GameWorld gameWorld, float x, float y, float width,float height){
        this.gameWorld = gameWorld;

        assetBlocks = new Array<TextureRegion>();
        AssetBlock assets =  Assets.getInstance().getAssetBlock();
        assetBlocks.add(assets.getBlockBlue());
        assetBlocks.add(assets.getBlockGreen());
        assetBlocks.add(assets.getBlockPurr());
        assetBlocks.add(assets.getBlockRed());
        assetBlocks.add(assets.getBlockYellow());

        textureRegionBlock = assetBlocks.get(1);

        // Sets initial values for position, width and height and initial frame as jumperStand.
        setBounds(x, y, width, height);
        setRegion(textureRegionBlock);
        stateTime = 0;
        statePosition = false;

        contactLeftBlockList = new ArrayList<Block>();
        contactRightBlockList = new ArrayList<Block>();

        sensorDown = false;
        sensorLeft = false;
        sensorRight = false;
        sensorUp = false;

        defineBlock();

        currentState = State.IDLE;

    }

    private void defineBlock(){
        BodyDef blockDef = new BodyDef();
        blockDef.position.set((getX() + getWidth()/2), getY() + getHeight()/2);
        blockDef.type = BodyDef.BodyType.DynamicBody;
        body =gameWorld.createBody(blockDef);
        body.setFixedRotation(true);
        body.setGravityScale(0);
        body.setLinearVelocity(0,FALL_VELOCITY);

        FixtureDef fixture = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getWidth()/2f,getHeight()/2f);
        fixture.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_BIT;
//        fixture.filter.maskBits = WorldContactListner.MASK_ALL;
        fixture.shape = polygonShape;
        fixture.density = 0f;
        fixture.friction = 0f;
        fixture.restitution = 0f;

        body.createFixture(fixture).setUserData(this);

        defineSensors();


    }
    private void defineSensors(){
        //Sensor Left
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.1f,(getHeight()/2)*0.95f, new Vector2((-getWidth()/2)+0.05f,0),0);
        FixtureDef sensorLeft = new FixtureDef();
        sensorLeft.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_SENSOR_LEFT_BIT;
        sensorLeft.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorLeft.shape = polygonShape;
        sensorLeft.isSensor = true;
        body.createFixture(sensorLeft).setUserData(this);

        //Sensor Right
        polygonShape.setAsBox(0.1f,(getHeight()/2)*0.95f, new Vector2((getWidth()/2)-0.05f,0),0);
        FixtureDef sensorRight = new FixtureDef();
        sensorRight.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_SENSOR_RIGHT_BIT;
        sensorRight.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorRight.shape = polygonShape;
        sensorRight.isSensor = true;
        body.createFixture(sensorRight).setUserData(this);

        //Sensor Down
        polygonShape.setAsBox((getWidth()/2)*0.95f,0.11f, new Vector2(0,(-getHeight()/2)+0.05f),0);
        FixtureDef sensorDown = new FixtureDef();
        sensorDown.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_SENSOR_DOWN_BIT;
        sensorDown.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorDown.shape = polygonShape;
        sensorDown.isSensor = true;
        body.createFixture(sensorDown).setUserData(this);

        //Sensor Up
        polygonShape.setAsBox((getWidth()/2)*0.95f,0.1f, new Vector2(0,(getHeight()/2)-0.05f),0);
        FixtureDef sensorUp = new FixtureDef();
        sensorUp.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_SENSOR_UP_BIT;
        sensorUp.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorUp.shape = polygonShape;
        sensorUp.isSensor = true;
        body.createFixture(sensorUp).setUserData(this);
    }

    public void setStaticPosition(float x, float y){
        body.setTransform(x,y,0);
    }
    public void stopFall(){
        currentState = State.IDLE;
        stateTime = 0;
        statePosition = false;
    }
    public void fall(){
        currentState = State.FALL;
        stateTime = 0;
    }
    public void push(Float turnImpulse){
        currentState = State.PUSH;
        stateTime = 0;
        body.setLinearVelocity(turnImpulse,body.getLinearVelocity().y);
    }
    public void delete(){
        currentState = State.DESTROY;
    }
    @Override
    public Vector2 getBodyPosition() {
        return null;
    }



    @Override
    public void update(float deltaTime) {
        switch (currentState){
            case IDLE:
                stateIdle(deltaTime);
                break;
            case PUSH:
                statePush(deltaTime);
            case FALL:
                stateFall(deltaTime);
                break;
            case DESTROY:
                stateDestroy(deltaTime);
                break;
            case DISPOSE:
                break;
        }

    }

    private void stateIdle(float deltaTime){
        body.setType(BodyDef.BodyType.StaticBody);
        if(!sensorDown){fall();}
        body.setLinearVelocity(0,0);
        textureRegionBlock = assetBlocks.get(1);
        float centerBodyPositionY = (body.getPosition().y - (int)body.getPosition().y);
        if(stateTime > 0.2) {
            statePosition = true;
            centerBodyPositionY = (int) (body.getPosition().y + 0.5f);
            body.setTransform(body.getPosition().x, centerBodyPositionY, 0);
        }
        if(getUpPlatform() == null) {
            createPlatformUnderBlock();
        }

        // Update this Sprite to correspond with the position of the Box2D body.
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(textureRegionBlock);

        stateTime += deltaTime;


    }
    private void statePush(float deltaTime){
        stateTime += deltaTime;
        if(upPlatform != null) {
            deletePlatformUnderBlock();
        }
        if(!sensorDown){currentState = State.FALL;}
        if(stateTime > 0.2) {
            float centerBodyPositionY = (int) (body.getPosition().y + 0.5f);
            body.setTransform(body.getPosition().x, centerBodyPositionY, 0);
        }
        // Update this Sprite to correspond with the position of the Box2D body.
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(textureRegionBlock);
    }
    private void stateFall(float deltaTime){
        if(upPlatform != null) {
            deletePlatformUnderBlock();
        }
        body.setType(BodyDef.BodyType.DynamicBody);
        if(sensorDown){stopFall();}
        body.setLinearVelocity(0,FALL_VELOCITY);
        textureRegionBlock = assetBlocks.get(3);
        // Update this Sprite to correspond with the position of the Box2D body.
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(textureRegionBlock);
        stateTime += deltaTime;


    }
    private void stateDestroy(float deltaTime){
        deletePlatformUnderBlock();
        gameWorld.destroyBody(body);
        currentState = State.DISPOSE;
    }
    private void stateDead(float deltaTime){

    }

    private void createPlatformUnderBlock(){
        float platformX = getX(),platformHX = 10f, platformY = getY() + 10, platformHY = 1f;
        Platform left = null;
        Platform right = null;
        if(contactLeftBlockList.size() > 0) {
            left = contactLeftBlockList.get(0).getUpPlatform();
            platformX = left.getX();
            platformHX += left.getWidth();
        }
        if(contactRightBlockList.size() > 0) {
            right = contactRightBlockList.get(0).getUpPlatform();
            platformHX += right.getWidth();
        }
        Platform platformBlockUp = new Platform(gameWorld,platformX+0.1f,platformY,platformHX-0.2f,platformHY);
        if(getUpPlatform() != null){
            getUpPlatform().delete();
        }
        setUpPlatform(platformBlockUp);
        Block tmpBlock = this;
        if(left != null) {
            left.delete();
            while (true) {
                if (tmpBlock.getContactLeftBlockList().size() > 0) {
                    tmpBlock.getContactLeftBlockList().get(0).setUpPlatform(platformBlockUp);
                    tmpBlock = tmpBlock.getContactLeftBlockList().get(0);
                } else {
                    break;
                }
            }
        }
        tmpBlock = this;
        if(right != null) {
            right.delete();
            while (true) {
                if (tmpBlock.getContactRightBlockList().size() > 0) {
                    //tmpBlock.getContactRightBlockList().get(0).getUpPlatform().delete();
                    tmpBlock.getContactRightBlockList().get(0).setUpPlatform(platformBlockUp);
                    tmpBlock = tmpBlock.getContactRightBlockList().get(0);
                } else {
                    break;
                }
            }
        }
    }
    private void deletePlatformUnderBlock(){
        float platformLeftX = getX(),platformLeftHX = 10f, platformLeftY = getY() + 10, platformLeftHY = 1f;
        float platformRightX = getX(),platformRightHX = 10f, platformRightY = getY() + 10, platformRightHY = 1f;
        Platform left = null;
        Platform right = null;
        if(contactLeftBlockList.size() > 0) {
            //left = contactLeftBlockList.get(0).getUpPlatform();
            for(Block block : contactLeftBlockList){
                if(block.getUpPlatform() != null){
                    block.setUpPlatform(null);
                    if(currentState == State.DISPOSE || currentState ==State.DESTROY) {
                        block.getContactRightBlockList().remove(this);
                    }
                }
            }
        }
        if(contactRightBlockList.size() > 0) {
            //right = contactRightBlockList.get(0).getUpPlatform();
            for(Block block : contactRightBlockList){
                if(block.getUpPlatform() != null){
                    block.setUpPlatform(null);
                    if(currentState == State.DISPOSE || currentState ==State.DESTROY) {
                        block.getContactLeftBlockList().remove(this);
                    }
                }
            }
        }
        if(upPlatform != null) {
            getUpPlatform().delete();
            setUpPlatform(null);
        }
    }
    @Override
    public void render(SpriteBatch spriteBatch) {
        draw(spriteBatch);
    }

    public boolean isIdle(){
        if(currentState == State.IDLE){
            return true;
        }else return false;
    }
    public boolean isDestroy(){
        if(currentState == State.DESTROY){
            return true;
        }else return false;
    }
    @Override
    public boolean isDisposable() {
        if(currentState == State.DISPOSE){
            return true;
        }else return false;
    }



    public List<Block> getContactLeftBlockList() {
        return contactLeftBlockList;
    }

    public void setContactLeftBlockList(List<Block> contactLeftBlockList) {
        this.contactLeftBlockList = contactLeftBlockList;
    }

    public List<Block> getContactRightBlockList() {
        return contactRightBlockList;
    }

    public void setContactRightBlockList(List<Block> contactRightBlockList) {
        this.contactRightBlockList = contactRightBlockList;
    }


    public Platform getUpPlatform() {
        return upPlatform;
    }

    public void setUpPlatform(Platform upPlatform) {
        this.upPlatform = upPlatform;
    }

    public boolean isSensorRight() {
        return sensorRight;
    }

    public void setSensorRight(boolean sensorRight) {
        this.sensorRight = sensorRight;
    }

    public boolean isSensorLeft() {
        return sensorLeft;
    }

    public void setSensorLeft(boolean sensorLeft) {
        this.sensorLeft = sensorLeft;
    }

    public boolean isSensorUp() {
        return sensorUp;
    }

    public void setSensorUp(boolean sensorUp) {
        this.sensorUp = sensorUp;
    }

    public boolean isSensorDown() {
        return sensorDown;
    }

    public void setSensorDown(boolean sensorDown) {
        this.sensorDown = sensorDown;
    }
}
