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
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.WorldContactListner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Platform> contactPlatformList;

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
        contactPlatformList = new HashSet<Platform>();

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
        body.setGravityScale(1);
        body.setLinearVelocity(0,FALL_VELOCITY);

        FixtureDef fixture = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getWidth()/2f,getHeight()/2f);
        fixture.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_BIT;
//        fixture.filter.maskBits = WorldContactListner.MASK_ALL;
        fixture.shape = polygonShape;
        fixture.density = 1f;
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
        if(currentState == State.FALL) {
            currentState = State.IDLE;
            stateTime = 0;
            statePosition = false;
        }
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
        if(contactPlatformList.size() == 0){fall();}
        body.setLinearVelocity(0,0);
        float centerBodyPositionY = (body.getPosition().y - (int)body.getPosition().y);
        if(stateTime > 0.2) {
       //     statePosition = true;
            //       centerBodyPositionY = (int) (body.getPosition().y + 0.6f);
            //       body.setTransform(body.getPosition().x, centerBodyPositionY, 0);
        }
        if(getUpPlatform() == null) {
            createPlatformUnderBlock();
        }

        // Update this Sprite to correspond with the position of the Box2D body.
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        textureRegionBlock = assetBlocks.get(1);
        setRegion(textureRegionBlock);

        stateTime += deltaTime;


    }
    private void statePush(float deltaTime){
        System.out.println("deltaTimePush = " + stateTime);
        body.setType(BodyDef.BodyType.DynamicBody);
        stateTime += deltaTime;
        if(upPlatform != null) {
            deletePlatformUnderBlock();
        }
        if(contactPlatformList.size() == 0){currentState = State.FALL;}
        //body.setLinearVelocity(4f,0);

        // Update this Sprite to correspond with the position of the Box2D body.
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        textureRegionBlock =assetBlocks.get(3);
        setRegion(textureRegionBlock);
    }
    private void stateFall(float deltaTime){
        if(upPlatform != null) {
            deletePlatformUnderBlock();
        }
        body.setType(BodyDef.BodyType.DynamicBody);
        if(contactPlatformList.size() > 0){stopFall();}
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

    // Створення платформи над блоком,
    // до уваги беруться сусідні платформи і створюється спільна платформа,
    //Спільна платформа покриває всі блоки які стоять рядом з нашим
    //Силка у всіх сусідніх блоків буде зсилатися на одну силку платформи
    private void createPlatformUnderBlock() {
        //Задаються початкові дані платформи
        float platformX = getX(), platformHX = 10f, platformY = getY() + 10f, platformHY = 0.01f;
        platformY = (float)Math.ceil((getY() + 5)*0.1)*10;
       // platformY = (float)(Math.ceil((getY()) * 0.1f)*10)-platformHY*0.75f;
        Platform left = null;
        Platform right = null;

        //Оновлення даних платформи блоку відносно сусідніх платформ
        //Просвоєння сусідніх платформ відповідним зміним left і Right
        if (contactLeftBlockList.size() > 0) {
            left = contactLeftBlockList.get(0).getUpPlatform();
            if (left != null) {
                platformX = left.getX();
                platformHX += left.getWidth();
            }
        }
        if (contactRightBlockList.size() > 0) {
            right = contactRightBlockList.get(0).getUpPlatform();
            if (right != null) {
                platformHX += right.getWidth();
            }
        }

        //Створення платформи
        Platform platformBlockUp = gameWorld.getPlatformController().addPlatform(platformX + 0.1f, platformY, platformHX - 0.2f, platformHY);
        //Попереднє очищення платформи блока,
        //якщо за якоїст ошибки вона у нього присутння
        //подія у задумці відбутися не має
        if (getUpPlatform() != null) {
            getUpPlatform().delete();
        }
        //Присвоєння створеної платформи блоку
        setUpPlatform(platformBlockUp);

        //Якщо є сусідня платформа, призначення їй статусу dispose
        //і присвоєння сусідньому блоку з ліва створену платформу
        // силка на платформу у всіх лівих блоків одна тому одне відбувається тільки присвоєння
        if (left != null) {
        Block tmpBlock = this.getContactLeftBlockList().get(0);
            left.delete();
            if (tmpBlock.getContactLeftBlockList().size() > 0) {
                tmpBlock.setUpPlatform(platformBlockUp);
            }
        }
        // Аналогічно верхній функції, тільки використовуються блоки з права
        if(right != null) {
            Block tmpBlock = this.getContactRightBlockList().get(0);
            right.delete();

                if (tmpBlock.getContactRightBlockList().size() > 0) {
                    tmpBlock.setUpPlatform(platformBlockUp);
                }
            }
    }

    //Присвоєння стану dispose платформі над блоком, і присвоєння цій зміні null значення
    //відповідна дія буде використана і до сусідніх блоків цього блоку
    //також блок буде видалений з списків контактів правих лівих блоків
    //при статусі цього  блоку DISPOSE або DELETE
    private void deletePlatformUnderBlock(){
        //Перевірка чи є бллоки з ліва
        if(contactLeftBlockList.size() > 0) {
            Block leftBlock = this;
            //Видалення цього блоку зі списку лівого блоку при відповідних станах
            if(currentState == State.DISPOSE || currentState ==State.DESTROY) {
                leftBlock.getContactLeftBlockList().get(0).getContactRightBlockList().remove(this);
            }
            //Проходження по всіх лівих блоках і присвоєння їх платформам стану DISPOSE
            //І присвоєння значенню null
            while(true){
                    leftBlock = leftBlock.getContactLeftBlockList().get(0);
                    if(leftBlock.getUpPlatform() != null){
                        leftBlock.getUpPlatform().delete();
                    }
                    leftBlock.setUpPlatform(null);
                    if(leftBlock.getContactLeftBlockList().size() == 0){ break;}
            }
        }
        //Аналогічно верхній функції, тільки використовуються праві блоки
        if(contactRightBlockList.size() > 0) {
            Block rightBlock = this;
            if(currentState == State.DISPOSE || currentState ==State.DESTROY) {
                rightBlock.getContactRightBlockList().get(0).getContactLeftBlockList().remove(this);
            }
            while(true){
                rightBlock = rightBlock.getContactRightBlockList().get(0);
                if(rightBlock.getUpPlatform() != null){
                    rightBlock.getUpPlatform().delete();
                }
                rightBlock.setUpPlatform(null);
                if(rightBlock.getContactRightBlockList().size() == 0){ break; }
            }
        }
        //Якщо платформа цього блока не дорівнює null
        //проводимо операцію відповідну як і до інших платформ у цьому методі
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

    public Set<Platform> getContactPlatformList() {
        return contactPlatformList;
    }
}
