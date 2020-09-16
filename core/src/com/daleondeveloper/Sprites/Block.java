package com.daleondeveloper.Sprites;

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
import com.daleondeveloper.Game.GameCamera;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.WorldContactListner;
import com.daleondeveloper.Sprites.Hero.WaterElement;

public class Block extends AbstractDynamicObject {
    private static final String TAG = Block.class.getName();


    private static final float FALL_VELOCITY =  5;

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
    private State currentState;
    private Body body;
    private float velocity;

    public Block(GameWorld gameWorld, float x, float y, float width,float heght){
        this.gameWorld = gameWorld;

        assetBlocks = new Array<TextureRegion>();
        AssetBlock assets =  Assets.getInstance().getAssetBlock();
        assetBlocks.add(assets.getBlockBlue());
        assetBlocks.add(assets.getBlockGreen());
        assetBlocks.add(assets.getBlockPurr());
        assetBlocks.add(assets.getBlockRed());
        assetBlocks.add(assets.getBlockYellow());

        textureRegionBlock = assetBlocks.get(0);

        // Sets initial values for position, width and height and initial frame as jumperStand.
        setBounds(x, y, 1, 1);
        setRegion(textureRegionBlock);
        stateTime = 0;
        velocity = 0;

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
        body.setLinearVelocity(0,-10);


        FixtureDef fixture = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getWidth()/2.05f,getHeight()/2.05f);
   //     fixture.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_BIT;
   //     fixture.filter.maskBits = WorldContactListner.MASK_ALL;
        fixture.shape = polygonShape;
        fixture.density = 1f;
        fixture.friction = 1f;
        fixture.restitution = 0f;


        body.createFixture(fixture).setUserData(this);


    }
    private void defineSensors(){
        //Sensor Left
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.1f,0.01f, new Vector2(-0.5f,0),0);
        FixtureDef sensorLeft = new FixtureDef();
        sensorLeft.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_BIT;
        sensorLeft.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorLeft.shape = polygonShape;
        sensorLeft.isSensor = true;
        body.createFixture(sensorLeft).setUserData(this);

        //Sensor Right
        polygonShape.setAsBox(0.1f,0.01f, new Vector2(0.5f,0),0);
        FixtureDef sensorRight = new FixtureDef();
        sensorRight.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_BIT;
        sensorRight.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorRight.shape = polygonShape;
        sensorRight.isSensor = true;
        body.createFixture(sensorRight).setUserData(this);

        //Sensor Down
        polygonShape.setAsBox(0.1f,0.01f, new Vector2(0,-0.5f),0);
        FixtureDef sensorDown = new FixtureDef();
        sensorDown.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_BIT;
        sensorDown.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorDown.shape = polygonShape;
        sensorDown.isSensor = true;
        body.createFixture(sensorDown).setUserData(this);

        //Sensor Up
        polygonShape.setAsBox(0.1f,0.01f, new Vector2(0,0.5f),0);
        FixtureDef sensorUp = new FixtureDef();
        sensorUp.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_BIT;
        sensorUp.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorUp.shape = polygonShape;
        sensorUp.isSensor = true;
        body.createFixture(sensorUp).setUserData(this);
    }

    public void stopFall(){
        currentState = State.IDLE;
        body.setLinearVelocity(0,0);
    }
    public void fall(){
        currentState = State.FALL;

    }
    public void push(WaterElement waterElement){
        currentState = State.PUSH;
        body.setLinearVelocity(waterElement.getVelocity().x/2,body.getLinearVelocity().y);
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
       // body.setLinearVelocity(0,body.getLinearVelocity().y);
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
        stateTime += deltaTime;
        // Update this Sprite to correspond with the position of the Box2D body.
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(textureRegionBlock);

    }
    private void statePush(float deltaTime){
        stateTime += deltaTime;
        // Update this Sprite to correspond with the position of the Box2D body.
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(textureRegionBlock);
    }
    private void stateFall(float deltaTime){
        stateTime += deltaTime;
        body.setLinearVelocity(0,-10);

        // Update this Sprite to correspond with the position of the Box2D body.
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(textureRegionBlock);
        body.setLinearVelocity(0,-10);
    }
    private void stateDestroy(float deltaTime){
        gameWorld.destroyBody(body);
        currentState = State.DISPOSE;
    }
    private void stateDead(float deltaTime){

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        draw(spriteBatch);
    }

    @Override
    public boolean isDisposable() {
        return false;
    }


}
