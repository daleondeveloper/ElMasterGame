package com.daleondeveloper.Sprites.Hero;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.AbstractDynamicObject;
import com.daleondeveloper.Sprites.Blocks.Block;

import java.util.ArrayList;

public class HeroSkill extends AbstractDynamicObject {
    private final static String TAG = HeroSkill.class.getName();

    private enum State{
        WAITINGUSES, SKILL1, SKILL2;
    }

    private GameWorld world;
    private PlayScreen playScreen;
    private WaterElement hero;
    private float stateTime;
    private Enum currentState;
    private Body body;

    private Vector2 heroVector2;
    private float heroHeight;
    private float heroWidth;

    private ArrayList<Block> blockCollisions;
    private Block blockInUsesSkill;

    public HeroSkill(PlayScreen playScreen,GameWorld world,WaterElement hero,float x, float y){
        this.playScreen = playScreen;
        this.world = world;
        this.hero = hero;
        heroVector2 = new Vector2(hero.getOriginX(),hero.getOriginY());
        heroWidth = hero.getWidth();
        heroHeight = hero.getHeight();
        blockCollisions = new ArrayList<Block>();

        currentState = State.WAITINGUSES;
        stateTime = 0;

        setBounds(heroVector2.x + 1, heroVector2.y,heroWidth/4,heroHeight/4);

        defineHeroSkill();
    }

    private void defineHeroSkill(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(heroVector2.x + 1,heroVector2.y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(heroHeight/4);
        circleShape.setPosition(new Vector2(heroVector2.x + 2.5f, heroVector2.y));
   //     fixtureDef.filter.categoryBits = WorldContactListner.CATEGORY_SKIIL_BIT;
   //     fixtureDef.filter.maskBits = WorldContactListner.MASK_ALL;
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);

        circleShape.setPosition(new Vector2(heroVector2.x - 2.5f, heroVector2.y));
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

    @Override
    public void update(float deltaTime) {
        blockCollisions.clear();
        heroVector2.x = hero.getOriginX();
        heroVector2.y = hero.getOriginY();
        body.setTransform(heroVector2,0);

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public boolean isDisposable() {
        return false;
    }
    //Uses from GameController class
    public void addBlockCollision(Block block){
        if(block!=null){
            blockCollisions.add(block);
        }
    }
    //Uses from hero class
    public void useSkill(boolean heroSeeRigth){
        for(Block block : blockCollisions){
            if(block.getBodyPosition().x > heroVector2.x && heroSeeRigth){
                blockInUsesSkill = block;
//                block.skill1Start();
            }else if(block.getBodyPosition().x < heroVector2.x && !heroSeeRigth){
                blockInUsesSkill = block;
//                block.skill1Start();
            }
        }
    }
    //Uses from hero class
    public void endSkill(){
        if(blockInUsesSkill != null) {
//            blockInUsesSkill.skill1End();
        }
    }

    @Override
    protected void updatePositionInGrid() {

    }
}
