package com.daleondeveloper.Sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetBlock;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.WorldContactListner;
import com.daleondeveloper.Sprites.Hero.WaterElement;

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
    private float checkTime;
    private Body body;
    private Set<AbstractGameObject> contactLeftBlockList ;
    private Set<AbstractGameObject> contactRightBlockList ;
    private Set<AbstractGameObject> contactUpList ;
    private Set<AbstractGameObject> contactDownList ;
    private WaterElement contactHero;
    private Platform upPlatform;
    private Platform downPlatform;

    private float pushImpulse;
    private float returnCellsPosition;
    private float returnCellsPositionY;

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
        checkTime = 0;
        statePosition = false;

        pushImpulse = 10;
        returnCellsPosition = x;
        returnCellsPosition = y;

        contactLeftBlockList = new HashSet<AbstractGameObject>();
        contactRightBlockList = new HashSet<AbstractGameObject>();
        contactDownList = new HashSet<AbstractGameObject>();
        contactUpList = new HashSet<AbstractGameObject>();
        contactPlatformList = new HashSet<Platform>();
        contactHero = null;

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
        body.setLinearVelocity(body.getLinearVelocity().x,FALL_VELOCITY);

        FixtureDef fixture = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getWidth()/2f,getHeight()/2f);
        fixture.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_BIT;
        fixture.filter.maskBits = WorldContactListner.MASK_ALL;
        fixture.shape = polygonShape;
        fixture.density = 0f;
        fixture.friction = 0f;
        fixture.restitution = 0f;

        body.createFixture(fixture).setUserData(this);

       // defineSensors();
        float x = body.getPosition().x;
        int leftReg = 44,rightReg = 46;
        for(int i = 0; i < 12; i++){
            if(x > leftReg && x < rightReg){
                returnCellsPosition = (leftReg + rightReg)/2;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }


    }
    private void defineSensors(){
        //Sensor Left
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.1f,(getHeight()/2)*0.95f, new Vector2((-getWidth()/2)+0.03f,0),0);
        FixtureDef sensorLeft = new FixtureDef();
        sensorLeft.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_SENSOR_LEFT_BIT;
        sensorLeft.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorLeft.shape = polygonShape;
        sensorLeft.isSensor = true;
        body.createFixture(sensorLeft).setUserData(this);

        //Sensor Right
        polygonShape.setAsBox(0.1f,(getHeight()/2)*0.95f, new Vector2((getWidth()/2)-0.03f,0),0);
        FixtureDef sensorRight = new FixtureDef();
        sensorRight.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_SENSOR_RIGHT_BIT;
        sensorRight.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorRight.shape = polygonShape;
        sensorRight.isSensor = true;
        body.createFixture(sensorRight).setUserData(this);

        //Sensor Down
        polygonShape.setAsBox((getWidth()/2)*0.9f,0.5f, new Vector2(0,(-getHeight()/2)+0.05f),0);
//        polygonShape.setAsBox(10,10, new Vector2(getWidth()/2,getHeight()/2),0);
        FixtureDef sensorDown = new FixtureDef();
        sensorDown.filter.categoryBits = WorldContactListner.CATEGORY_BLOCK_SENSOR_DOWN_BIT;
        sensorDown.filter.maskBits = WorldContactListner.MASK_ALL;
        sensorDown.shape = polygonShape;
        sensorDown.isSensor = true;
        body.createFixture(sensorDown).setUserData(this);

        //Sensor Up
        polygonShape.setAsBox((getWidth()/2)*0.95f,0.5f, new Vector2(0,(getHeight()/2)-0.05f),0);
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

    public void idle(){
        currentState = State.IDLE;
        stateTime = 0;
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
        if(currentState == State.IDLE && contactUpList.isEmpty()) {
            currentState = State.PUSH;
            stateTime = 0;
            pushImpulse = turnImpulse;
        }
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
        checkTime += deltaTime;

//        Set<Platform> tmpSet = new HashSet<Platform>();
//        for(Platform p : contactPlatformList){
//           if(p.isDisposable()){
//                tmpSet.add(p);
//            }
//        }
//        contactPlatformList.removeAll(tmpSet);

        if(contactDownList.size() == 0){
            sensorDown = false;
        }else{
            sensorDown = true;
        }
        if(contactUpList.size() == 0){
            sensorUp = false;
        }else{
            sensorUp = true;
        }
        if(contactRightBlockList.size() == 0){
            sensorRight = false;
        }else{
            sensorRight = true;
        }
        if(contactLeftBlockList.size() == 0){
            sensorLeft = false;
        }else{
            sensorLeft = true;
        }


        switch (currentState){
            case IDLE:
                stateIdle(deltaTime);
                break;
            case PUSH:
                statePush(deltaTime);
                break;
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
        if(!sensorDown){fall();return;}
       // if(stateTime > 1){fall();return;}
        body.setLinearVelocity(0,0);
            if(this.getX() + 5 - returnCellsPosition > 0.01f ||
            this.getX() + 5 - returnCellsPosition < -0.01f){
                body.setType(BodyDef.BodyType.DynamicBody);
//                body.applyForceToCenter((returnCellsPosition-body.getPosition().x)*1000,0,true);
                if(getUpPlatform() != null){
                    deletePlatformUnderBlock();
                }
            }
        if(this.getY() + 5 - returnCellsPositionY > 0.01f ||
                this.getY() + 5 - returnCellsPositionY < -0.01f){
            body.setType(BodyDef.BodyType.DynamicBody);
//            body.applyForceToCenter(0,(returnCellsPosition-body.getPosition().y)*1000,true);
            if(getUpPlatform() != null){
                deletePlatformUnderBlock();
            }
        }
        body.setTransform(returnCellsPosition,(returnCellsPositionY),0);
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
        float x = body.getPosition().x;
        int leftReg = 44,rightReg = 46;
        for(int i = 0; i < 12; i++){
            if(x > leftReg && x < rightReg){
                returnCellsPosition = (leftReg + rightReg)/2;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }
        body.setType(BodyDef.BodyType.DynamicBody);
        stateTime += deltaTime;
        if(upPlatform != null) {
            deletePlatformUnderBlock();
        }
        if(contactPlatformList.size() == 0){fall(); return;}

        if(contactHero != null && Math.abs(contactHero.getY() - getY()) < 3 && Math.abs(contactHero.getX() - getX()) < 13){
            if(contactHero.getX() - getX() > 1){
                body.setLinearVelocity(pushImpulse, body.getLinearVelocity().y);
            }else if(contactHero.getX() - getX() < -1){
                body.setLinearVelocity(-pushImpulse, body.getLinearVelocity().y);
            }

        }else{idle();return;}

        body.setLinearVelocity(10, body.getLinearVelocity().y);


        // Update this Sprite to correspond with the position of the Box2D body.
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        textureRegionBlock =assetBlocks.get(2);
        setRegion(textureRegionBlock);
    }
    private void stateFall(float deltaTime){
        float y = body.getPosition().y;
        int leftReg = 144,rightReg = 146;
        for(int i = 0; i < 20; i++){
            if(y > leftReg && y < rightReg){
                returnCellsPositionY = (leftReg + rightReg)/2;
                break;
            }
            leftReg += 10;
            rightReg += 10;
        }
        if(upPlatform != null) {
            deletePlatformUnderBlock();
        }
        body.setType(BodyDef.BodyType.DynamicBody);
        if(contactPlatformList.size() > 0){stopFall();}
        if(contactDownList.size() > 0){stopFall();}
        body.setLinearVelocity(0,FALL_VELOCITY);
        textureRegionBlock = assetBlocks.get(3);

        if(this.getX() + 5 - returnCellsPosition > 0.01f ||
                this.getX() + 5 - returnCellsPosition < -0.01f){
            body.setType(BodyDef.BodyType.DynamicBody);
            body.applyForceToCenter((returnCellsPosition-body.getPosition().x)*1000,0,true);
            if(getUpPlatform() != null){
                deletePlatformUnderBlock();
            }
        }
//        if(body.getPosition().x - returnCellsPosition > 0.025f ||
//                body.getPosition().x - returnCellsPosition < -0.025f){
//            body.setType(BodyDef.BodyType.DynamicBody);
//            body.applyForceToCenter((returnCellsPosition-body.getPosition().x)*1000,0,true);
//            if(getUpPlatform() != null){
//                deletePlatformUnderBlock();
//            }
//        }
        // Update this Sprite to correspond with the position of the Box2D body.
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(textureRegionBlock);
        stateTime += deltaTime;


    }
    private void stateDestroy(float deltaTime){
        body.setType(BodyDef.BodyType.KinematicBody);
        deletePlatformUnderBlock();
        GameSensor gameSensor = gameWorld.getFirstLineBlockChecker();
        if(gameSensor.getFirstLineBlocks().contains(this)){
            gameSensor.getFirstLineBlocks().remove(this);
        }
        Set<AbstractGameObject> objToDelete = new HashSet<AbstractGameObject>();
        objToDelete.addAll(fixOnContact);
        for(AbstractGameObject abstractGO : objToDelete){
            removeFixOnContact(this,abstractGO);
        }

        gameWorld.destroyBody(body);
        currentState = State.DISPOSE;
    }
    private void stateDead(float deltaTime){

    }

    private void checkContacts(){
        Set<AbstractGameObject> tmpSet = new HashSet<AbstractGameObject>();
        tmpSet.addAll(contactDownList);
        for(AbstractGameObject contactObj : tmpSet){
            if((Math.abs((contactObj.getY() + contactObj.getHeight()/2) - (getY() + getHeight()/2))
                    > (this.getHeight() + contactObj.getHeight())  / 2 ) ||
                    (Math.abs((contactObj.getX() + contactObj.getWidth()/2) - (getX() + getWidth()/2))
                            > (this.getWidth() + contactObj.getWidth()) / 2)){
              //  contactDownList.remove(contactObj);
            }
        }
    }

    // Створення платформи над блоком,
    // до уваги беруться сусідні платформи і створюється спільна платформа,
    //Спільна платформа покриває всі блоки які стоять рядом з нашим
    //Силка у всіх сусідніх блоків буде зсилатися на одну силку платформи
    private void createPlatformUnderBlock() {
//        //Задаються початкові дані платформи
//        float platformX = getX(), platformHX = 10f, platformY = getY() + 10f, platformHY = 0.025f;
//        platformY = (float)Math.ceil((getY() + 5)*0.1)*10;
//       // platformY = (float)(Math.ceil((getY()) * 0.1f)*10)-platformHY*0.75f;
//        Platform left = null;
//        Platform right = null;
//
//        //Оновлення даних платформи блоку відносно сусідніх платформ
//        //Просвоєння сусідніх платформ відповідним зміним left і Right
//        if (contactLeftBlockList.size() > 0) {
//            Block leftBlock = this;
//
//            while (true){
//                Block prevLeftBlock = leftBlock;
//                if(leftBlock.getContactLeftBlockList().size() > 0){
//                    for(Block block : leftBlock.getContactLeftBlockList()){
//                        float posYDifference = Math.abs(getY() - block.getY());
//                        if(posYDifference < 1){
//                            leftBlock = block;
//                        }
//                    }
//                }else{
//                    break;
//                }
//                if(prevLeftBlock == leftBlock){
//                    break;
//                }
//            }
//                platformX = leftBlock.getX() ;
//                platformHX += getX() - leftBlock.getX();
//        }
//        if (contactRightBlockList.size() > 0) {
//            Block rightBlock= this;
//
//            while (true){
//                Block prevRightBlock = rightBlock;
//                if(rightBlock.getContactRightBlockList().size() > 0){
//                    for(Block block : rightBlock.getContactRightBlockList()){
//                        float posYDifference = Math.abs(getY() - block.getY());
//                        if(posYDifference < 1){
//                            rightBlock = block;
//                        }
//                    }
//                }else{
//                    break;
//                }
//                if(prevRightBlock == rightBlock){
//                    break;
//                }
//            }
//                platformHX += rightBlock.getX() - getX();
//        }
//
//        //Створення платформи
//        Platform platformBlockUp = gameWorld.getPlatformController().addPlatform(platformX + 0.5f, platformY, platformHX - 1f, platformHY);
//        //Попереднє очищення платформи блока,
//        //якщо за якоїст ошибки вона у нього присутння
//        //подія у задумці відбутися не має
//        if (getUpPlatform() != null) {
//            getUpPlatform().delete();
//        }
//        //Присвоєння створеної платформи блоку
//        setUpPlatform(platformBlockUp);
//
//        //Якщо є сусідня платформа, призначення їй статусу dispose
//        //і присвоєння сусідньому блоку з ліва створену платформу
//        // силка на платформу у всіх лівих блоків одна тому одне відбувається тільки присвоєння
//            Block tmpBlock = this;
//            while(tmpBlock.getContactLeftBlockList().size() > 0){
//                Block prevLeftBlock = tmpBlock;
//                for(Block block : tmpBlock.getContactLeftBlockList()){
//                    float posYDifference = Math.abs(getY() - block.getY());
//                    if(posYDifference < 1){
//                        tmpBlock = block;
//                    }
//                }
//                if(prevLeftBlock == tmpBlock){
//                    break;
//                }
//                if(tmpBlock.getUpPlatform() != null) {
//                    tmpBlock.getUpPlatform().delete();
//                }
//                tmpBlock.setUpPlatform(platformBlockUp);
////            if (this.getContactLeftBlockList().size() > 0) {
////              //  Block tmpBlock = this.getContactLeftBlockList().get(0);
////                tmpBlock.setUpPlatform(platformBlockUp);
////            }
//        }
//        // Аналогічно верхній функції, тільки використовуються блоки з права
////        if(right != null) {
//            tmpBlock = this;
//            while (tmpBlock.getContactRightBlockList().size() > 0) {
//                Block prevRightBlock = tmpBlock;
//                for(Block block : tmpBlock.getContactRightBlockList()){
//                    float posYDifference = Math.abs(getY() - block.getY());
//                    if(posYDifference < 1){
//                        tmpBlock = block;
//                    }
//                }
//                if(prevRightBlock == tmpBlock){
//                    break;
//                }
//                if (tmpBlock.getUpPlatform() != null) {
//                    tmpBlock.getUpPlatform().delete();
//                }
//                tmpBlock.setUpPlatform(platformBlockUp);
//
//            }
//

    }

    //Присвоєння стану dispose платформі над блоком, і присвоєння цій зміні null значення
    //відповідна дія буде використана і до сусідніх блоків цього блоку
    //також блок буде видалений з списків контактів правих лівих блоків
    //при статусі цього  блоку DISPOSE або DELETE
    private void deletePlatformUnderBlock(){
//        //Перевірка чи є бллоки з ліва
//        if(contactLeftBlockList.size() > 0) {
//            Block leftBlock = this;
//            //Видалення цього блоку зі списку лівого блоку при відповідних станах
////            if(currentState == State.DISPOSE || currentState ==State.DESTROY) {
////                leftBlock.getContactLeftBlockList().get(0).getContactRightBlockList().remove(this);
////            }
//            //Проходження по всіх лівих блоках і присвоєння їх платформам стану DISPOSE
//            //І присвоєння значенню null
//            while(true){
//                Block prevLeftBlock = leftBlock;
//                for(Block block : leftBlock.getContactLeftBlockList()){
//                    float posYDifference = Math.abs(getY() - block.getY());
//                    if(posYDifference < 1){
//                        leftBlock = block;
//                    }
//
//                }
//
//                    if(leftBlock.getUpPlatform() != null){
//                        leftBlock.getUpPlatform().delete();
//                    }
//                    leftBlock.setUpPlatform(null);
//                    if(leftBlock.getContactLeftBlockList().size() == 0){ break;}
//                    if(prevLeftBlock == leftBlock){
//                        break;
//                    }
//            }
//        }
//        //Аналогічно верхній функції, тільки використовуються праві блоки
//        if(contactRightBlockList.size() > 0) {
//            Block rightBlock = this;
////            if(currentState == State.DISPOSE || currentState ==State.DESTROY) {
////                rightBlock.getContactRightBlockList().get(0).getContactLeftBlockList().remove(this);
////            }
//            while(true){
//                Block prevRightBlock = rightBlock;
//                for(Block block : rightBlock.getContactRightBlockList()){
//                    float posYDifference = Math.abs(getY() - block.getY());
//                    if(posYDifference < 1){
//                        rightBlock = block;
//                    }
//                }
//                if(rightBlock.getUpPlatform() != null){
//                    rightBlock.getUpPlatform().delete();
//                }
//                rightBlock.setUpPlatform(null);
//                if(rightBlock.getContactRightBlockList().size() == 0){ break; }
//                if(prevRightBlock == rightBlock){
//                    break;
//                }
//            }
//        }
//        //Якщо платформа цього блока не дорівнює null
//        //проводимо операцію відповідну як і до інших платформ у цьому методі
//        if(upPlatform != null) {
//           getUpPlatform().delete();
//            setUpPlatform(null);
//        }
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

    @Override
    public Set<AbstractGameObject> removeFixOnContact(AbstractGameObject mainFix, AbstractGameObject contactFix) {
        if(contactFix instanceof Block){
            Block block = (Block) contactFix;
          //  block.getContactDownList().remove(this);
            block.getContactUpList().remove(this);
            block.getContactLeftBlockList().remove(this);
            block.getContactRightBlockList().remove(this);
        }
        if(contactFix instanceof WaterElement){
            WaterElement hero = (WaterElement) contactFix;
            hero.getSensorDown().remove(this);
            hero.getSensorUp().remove(this);
            hero.getSensorLeft().remove(this);
            hero.getSensorRight().remove(this);
        }

        return super.removeFixOnContact(mainFix, contactFix);
    }

    public Set<AbstractGameObject> getContactLeftBlockList() {
        return contactLeftBlockList;
    }

    public Set<AbstractGameObject> getContactRightBlockList() {
        return contactRightBlockList;
    }

    public Set<AbstractGameObject> getContactUpList() {
        return contactUpList;
    }

    public Set<AbstractGameObject> getContactDownList() {
        return contactDownList;
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

    public WaterElement getContactHero() {
        return contactHero;
    }

    public void setContactHero(WaterElement contactHero) {
        this.contactHero = contactHero;
    }
}
