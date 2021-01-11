package com.daleondeveloper.Sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetBlock;
import com.daleondeveloper.Game.GameSettings;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.WorldContactListner;
import com.daleondeveloper.Screens.Play.PlayScreen;

import java.util.HashSet;
import java.util.Set;

public class GameSensor extends AbstractDynamicObject {
    private static final String TAG = Platform.class.getName();

    private GameWorld gameWorld;
    private PlayScreen playScreen;
    private TextureRegion assetPlatform;
    private Body body;

    private Set<Block> firstLineBlocks;

    public GameSensor(PlayScreen playScreen, GameWorld gameWorld, float x, float y, float hx, float hy){
        this.gameWorld = gameWorld;
        this.playScreen = playScreen;

        AssetBlock assetBlock = Assets.getInstance().getAssetBlock();
        assetPlatform = assetBlock.getBlockLight();

        firstLineBlocks = new HashSet<Block>();
        setRegion(assetPlatform);
        setBounds(x,y,hx,hy);


        defineGameSensor();
    }
    private void defineGameSensor(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX() + getWidth() / 2, getY() + getHeight() / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = gameWorld.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(115,2);
        FixtureDef sensorLeft = new FixtureDef();
        sensorLeft.filter.categoryBits = WorldContactListner.CATEGORY_GAME_WORLD_SENSOR;
        sensorLeft.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorLeft.shape = polygonShape;
        sensorLeft.isSensor = true;
        body.createFixture(sensorLeft).setUserData(this);
    }

    @Override
    public void renderDebug(ShapeRenderer shapeRenderer) {
        super.renderDebug(shapeRenderer);
    }

    @Override
    public void update(float deltaTime) {

        if(firstLineBlocks.size() > 9){
            int blockCountInFirstLine = 0;
            for(Block block : firstLineBlocks){
                if(block.isIdle()){
                    blockCountInFirstLine++;
                }
            }
            if(blockCountInFirstLine > 9) {
                for (Block block : firstLineBlocks) {
                    block.delete();
                }
                playScreen.getHud().addScore(10);
            }
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        draw(spriteBatch);
    }

    @Override
    public boolean isDisposable() {
        return false;
    }

    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

    public Set<Block> getFirstLineBlocks() {
        return firstLineBlocks;
    }
}
