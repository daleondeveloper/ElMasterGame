package com.daleondeveloper.Sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetBlock;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.WorldContactListner;

public class Platform extends AbstractDynamicObject {
    private static final String TAG = Platform.class.getName();

    private static final float SCALE = 0.4f;

    private enum State{
        STATIC;
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
        assetPlatform = assetBlock.getBlockBlue();

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
        body.createFixture(fixtureDef).setUserData(this);
    }



    @Override
    public Vector2 getBodyPosition() {
        return null;
    }

    @Override
    public void update(float deltaTime) {

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
