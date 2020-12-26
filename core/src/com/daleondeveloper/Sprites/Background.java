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
import com.daleondeveloper.Assets.game.AssetGates;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.WorldContactListner;

public class Background extends AbstractDynamicObject {
    private static final String TAG = Background.class.getName();

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

    public Background(GameWorld gameWorld, float x, float y, float width, float height){
        this.gameWorld = gameWorld;


        AssetGates assetBlock = Assets.getInstance().getAssetGates();
        assetPlatform = assetBlock.getStaticMain();

        setBounds(x,y,width,height);
        setRegion(assetPlatform);
        stateTime = 0;


        definePlatform();

        currentState = State.STATIC;

        velocity = 0.0f;
    }

    private void definePlatform(){
    }

    public void setRegionGates(){
        setRegion(Assets.getInstance().getAssetGates().getStaticMain());
    }
    public void setRegionGameFon(){
        setRegion(Assets.getInstance().getAssetGates().getPlay_background());
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
