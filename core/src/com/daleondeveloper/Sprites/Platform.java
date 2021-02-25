package com.daleondeveloper.Sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetBlock;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.WorldContactListner;
import com.daleondeveloper.Sprites.Blocks.Block;

import java.util.HashSet;
import java.util.Set;

public class Platform extends AbstractGameObject {
    private static final String TAG = Platform.class.getName();

    private static final float SCALE = 0.4f;

    private enum State{
        STATIC,DISPOSABLE,DELETE;
    }

    private GameWorld gameWorld;
    private TextureRegion assetPlatform;
    private float stateTime;
    private State currentState;
    private Body body;
    private float velocity;

    public Platform(GameWorld gameWorld, float x, float y,float width, float height){
        this.gameWorld = gameWorld;


        AssetBlock assetBlock = Assets.getInstance().getAssetBlock();
        assetPlatform = assetBlock.getBlockFire();

        setBounds(x,y,width,height);
        setRegion(assetPlatform);
        stateTime = 0;


        definePlatform();

        currentState = State.STATIC;

        velocity = 0.0f;
    }

    private void definePlatform(){
        // Creates main body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX() + getWidth() / 2, getY() + getHeight() / 2); // In box2D the origin is at the center of the body
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = gameWorld.createBody(bodyDef);
        body.setFixedRotation(true);
        body.setGravityScale(0); // No gravity

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getWidth() / 2, getHeight() / 2);
        fixtureDef.filter.categoryBits = WorldContactListner.CATEGORY_REGION_BIT; // Depicts what this fixture is
        fixtureDef.filter.maskBits = WorldContactListner.MASK_ALL; // Depicts what this Fixture can collide with (see WorldContactListener)
        fixtureDef.shape = polygonShape;
        fixtureDef.restitution = 0f;
        body.createFixture(fixtureDef).setUserData(this);
    }



    public void delete(){
        currentState = State.DELETE;
    }



    @Override
    public void update(float deltaTime) {
        switch (currentState){
            case DELETE:
                stateDelete(deltaTime);
                break;
        }
    }
    private void stateDelete (float deltaTime){
        Set<AbstractGameObject> objToDelete = new HashSet<AbstractGameObject>();
        objToDelete.addAll(fixOnContact);
        for(AbstractGameObject fixture : objToDelete){
            removeFixOnContact(this,fixture);
        }
        gameWorld.destroyBody(body);
        currentState = State.DISPOSABLE;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        draw(spriteBatch);
    }

    public boolean isDestroy(){
        if(currentState == State.DELETE){
            return true;
        }else return false;
    }
    @Override
    public boolean isDisposable() {
        if(currentState == State.DISPOSABLE){
            return true;
        }else return false;
    }

    @Override
    public Set<AbstractGameObject> removeFixOnContact(AbstractGameObject mainFix, AbstractGameObject contactFix) {
        if(contactFix instanceof Block){
            Block contactBlock = (Block)contactFix;
            contactBlock.getContactDownList().remove(mainFix);
        }
        return super.removeFixOnContact(mainFix,contactFix);
    }
}
