package com.daleondeveloper.Game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.daleondeveloper.Sprites.AbstractGameObject;
import com.daleondeveloper.Sprites.Block;
import com.daleondeveloper.Sprites.GameSensor;
import com.daleondeveloper.Sprites.Hero.WaterElement;
import com.daleondeveloper.Sprites.Platform;

public class WorldContactListner implements ContactListener {
    private static final String TAG = WorldContactListner.class.getName();

    // Box2D Collision Bits
    public static final short CATEGORY_NOTHING_BIT = 0;
    public static final short CATEGORY_BLOCK_BIT = 1;
    public static final short CATEGORY_WATER_ELEM_BIT = 2;
    public static final short CATEGORY_REGION_BIT = 4;
    public static final short CATEGORY_BLOCK_SENSOR_RIGHT_BIT = 8;
    public static final short CATEGORY_BLOCK_SENSOR_LEFT_BIT = 16;
    public static final short CATEGORY_BLOCK_SENSOR_DOWN_BIT = 32;
    public static final short CATEGORY_BLOCK_SENSOR_UP_BIT = 64;
    public static final short CATEGORY_WATER_ELEM_SENSOR_RIGHT_BIT = 128;
    public static final short CATEGORY_WATER_ELEM_SENSOR_LEFT_BIT = 256;
    public static final short CATEGORY_WATER_ELEM_SENSOR_DOWN_BIT = 512;
    public static final short CATEGORY_WATER_ELEM_SENSOR_UP_BIT = 1024;
    public static final short CATEGORY_GAME_WORLD_SENSOR = 2048;



    public static final short MASK_ALL = -1;
    public static final short MASK_BLOCK = 0;

    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        int collisionDef = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;
        System.out.println("Contact Begin");
        System.out.println(fa.getFilterData().categoryBits + " ::::: " + fb.getFilterData().categoryBits);

        AbstractGameObject abstractGameObject = (AbstractGameObject)fa.getUserData();
        abstractGameObject.addFixToFixOnContact((AbstractGameObject)fb.getUserData());
        abstractGameObject = (AbstractGameObject)fb.getUserData();
        abstractGameObject.addFixToFixOnContact((AbstractGameObject)fa.getUserData());

        switch (collisionDef) {
//        //    case CATEGORY_BLOCK_BIT :{
//            case CATEGORY_BLOCK_SENSOR_UP_BIT : {
//                Block blockA = (Block)fa.getUserData();
//                Block blockB = (Block)fb.getUserData();
//                float blocksWidth = (blockA.getWidth() + blockB.getWidth()) / 2 ;
//                float blocksHeight = (blockA.getHeight() + blockB.getHeight()) / 2 ;
//
//
//                    if(Math.abs(blockA.getY() - blockB.getY()) > blocksHeight * 0.9f &&
//                            Math.abs(blockA.getY() - blockB.getY()) < blocksHeight * 1.5f &&
//                    Math.abs(blockA.getX() - blockB.getX()) < blocksWidth * 0.8f){
//                        if(blockA.getY() - blockB.getY() > 0){
//                            blockA.getContactDownList().add((AbstractGameObject)fb.getUserData());
//                            blockB.getContactUpList().add((AbstractGameObject)fa.getUserData());
//                        }else if(blockA.getY() - blockB.getY() < 0){
//                            blockB.getContactDownList().add((AbstractGameObject)fa.getUserData());
//                            blockA.getContactUpList().add((AbstractGameObject)fb.getUserData());
//                        }
//                    }
//                    if(Math.abs(blockA.getX() - blockB.getX()) > blocksWidth * 0.9f &&
//                            Math.abs(blockA.getX() - blockB.getX()) > blocksWidth * 1.5 &&
//                    Math.abs(blockA.getY() - blockB.getY()) < blocksHeight * 0.8f){
//                        if(blockA.getX() - blockB.getX() > 0){
//                            blockA.getContactLeftBlockList().add(blockB);
//                            blockB.getContactRightBlockList().add(blockA);
//                        }if(blockA.getX() - blockB.getX() < 0){
//                            blockA.getContactRightBlockList().add(blockB);
//                            blockB.getContactLeftBlockList().add(blockA);
//                        }
//                    }
//
//            }break;
//       //     case CATEGORY_BLOCK_BIT | CATEGORY_WATER_ELEM_BIT :
//            case CATEGORY_WATER_ELEM_BIT | CATEGORY_BLOCK_SENSOR_UP_BIT :{
//                WaterElement hero = null;
//                Block block = null;
//                if(fa.getUserData() instanceof WaterElement){
//                    hero = (WaterElement)fa.getUserData();
//                    block = (Block)fb.getUserData();
//                }else if(fa.getUserData() instanceof Block){
//                    hero = (WaterElement)fb.getUserData();
//                    block = (Block)fa.getUserData();
//                }else{break;}
//
//                float widthDifference = (hero.getWidth() + block.getWidth())/2;
//                float heightDifference = (hero.getHeight() + block.getHeight())/2;
//
//                if(Math.abs(hero.getBodyPosition().y - block.getBodyPosition().y) > heightDifference * 0.95f &&
//                Math.abs(hero.getBodyPosition().x - block.getBodyPosition().x) < widthDifference * 0.95f){
//                    if(hero.getBodyPosition().y - block.getBodyPosition().y > 0){
//                        hero.getSensorDown().add((AbstractGameObject)fb.getUserData());
//                        block.getContactUpList().add((AbstractGameObject)fa.getUserData());
//                    }else if(hero.getBodyPosition().y - block.getBodyPosition().y < 0){
//                        block.getContactDownList().add((AbstractGameObject)fa.getUserData());
//                        hero.getSensorUp().add((AbstractGameObject)fb.getUserData());
//                    }
//                }
//                if(Math.abs(hero.getBodyPosition().x - block.getBodyPosition().x) > widthDifference * 0.95f &&
//                Math.abs(hero.getBodyPosition().y - block.getBodyPosition().y) < heightDifference * 0.95f){
//                    if(hero.getBodyPosition().x - block.getBodyPosition().x > 0){
//                        hero.getSensorLeft().add(block);
//                        block.setContactHero(hero);
//                        block.getContactRightBlockList().add(hero);
//                    }if(hero.getBodyPosition().x - block.getBodyPosition().x < 0){
//                        hero.getSensorRight().add(block);
//                        block.setContactHero(hero);
//                        block.getContactLeftBlockList().add(hero);
//                    }
//                }
//
//            }break;
//            case CATEGORY_WATER_ELEM_BIT | CATEGORY_REGION_BIT:
//            {
////                WaterElement waterElement = heroStartContactDown(fa,fb);
//                WaterElement hero = null;
//                Platform platform = null;
//                if(fa.getUserData() instanceof WaterElement){
//                    hero = (WaterElement)fa.getUserData();
//                    platform = (Platform) fb.getUserData();
//                }else if(fa.getUserData() instanceof Platform){
//                    hero = (WaterElement)fb.getUserData();
//                    platform = (Platform) fa.getUserData();
//                }else{break;}
//
//                float heightDifference = (hero.getHeight() + platform.getHeight())/2;
//
//                if(Math.abs(hero.getY() - platform.getY()) > heightDifference * 0.8f){
//                    if(hero.getY() - platform.getY() > 0){
//                        hero.getSensorDown().add((AbstractGameObject)fb.getUserData());
//                    }
//                }
//
//
//            }break;
////            case CATEGORY_BLOCK_SENSOR_UP_BIT | CATEGORY_REGION_BIT : {
////                Block block = null;//blockStartContactDown(fa,fb);
////                Platform platform = null;
////                if(fa.getUserData() instanceof Platform){
////                    platform = (Platform)fa.getUserData();
////                    block = (Block)fb.getUserData();
////                }else{
////                    platform = (Platform)fb.getUserData();
////                    block = (Block)fa.getUserData();
////                }
////                block.getContactPlatformList().add(platform);
////
////                float heightDifference = (platform.getHeight() + block.getHeight())/2;
////
////                if(Math.abs(platform.getY() - block.getY()) > heightDifference * 0.9f){
////                    if(platform.getY() - block.getY() < 0){
////                        block.getContactDownList().add((AbstractGameObject)fa.getUserData());
////                        //platform.getSensorUp().add((AbstractGameObject)fb.getUserData());
////                    }
////                }
////            }break;

            //Кнотакт з героєм
            //Нижній сенсор

            case CATEGORY_WATER_ELEM_SENSOR_DOWN_BIT | CATEGORY_BLOCK_SENSOR_UP_BIT:{
                WaterElement waterElement = heroStartContactDown(fa,fb);
                Block block = blockStartContactUp(fa,fb);
                block.setContactHero(waterElement);

            }break;
            case CATEGORY_WATER_ELEM_SENSOR_DOWN_BIT | CATEGORY_REGION_BIT: {
                WaterElement waterElement = heroStartContactDown(fa,fb);
            }break;
            //Верхній сенсор
            case CATEGORY_WATER_ELEM_SENSOR_UP_BIT | CATEGORY_BLOCK_SENSOR_DOWN_BIT:{
                WaterElement waterElement = heroStartContactUp(fa,fb);
                Block block = blockStartContactDown(fa,fb);
                block.setContactHero(waterElement);

            }break;
            case CATEGORY_WATER_ELEM_SENSOR_UP_BIT | CATEGORY_REGION_BIT: {
                WaterElement waterElement = heroStartContactUp(fa,fb);
            }break;
            //Лівий сенсор
            case CATEGORY_WATER_ELEM_SENSOR_LEFT_BIT | CATEGORY_BLOCK_SENSOR_RIGHT_BIT:{
                WaterElement waterElement = heroStartContactLeft(fa,fb);
                Block block = blockStartContactRight(fa,fb);
                block.setContactHero(waterElement);

            }break;
            case CATEGORY_WATER_ELEM_SENSOR_LEFT_BIT | CATEGORY_REGION_BIT: {
                WaterElement waterElement = heroStartContactLeft(fa,fb);
            }break;
            //Правий сенсор
            case CATEGORY_WATER_ELEM_SENSOR_RIGHT_BIT | CATEGORY_BLOCK_SENSOR_LEFT_BIT:{
                WaterElement waterElement = heroStartContactRight(fa,fb);
                Block block = blockStartContactLeft(fa,fb);
                block.setContactHero(waterElement);

            }break;
            case CATEGORY_WATER_ELEM_SENSOR_RIGHT_BIT | CATEGORY_REGION_BIT: {
                WaterElement waterElement = heroStartContactRight(fa,fb);
            }break;

            //Контакт блоків між собою
            case CATEGORY_BLOCK_SENSOR_UP_BIT | CATEGORY_BLOCK_SENSOR_DOWN_BIT : {
                Block blockDown = blockStartContactDown(fa,fb);
                Block blockUp = blockStartContactUp(fa,fb);

            }break;
            case CATEGORY_BLOCK_SENSOR_LEFT_BIT | CATEGORY_BLOCK_SENSOR_RIGHT_BIT : {
                Block blockRight = blockStartContactRight(fa,fb);
                Block blockLeft = blockStartContactLeft(fa,fb);
            }break;

            //Контакт блоків з регіонами\
            case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_REGION_BIT : {
                Block block = blockStartContactDown(fa,fb);
            }break;
            case CATEGORY_BLOCK_SENSOR_UP_BIT | CATEGORY_REGION_BIT : {
                Block block = blockStartContactUp(fa,fb);
            }break;
            case CATEGORY_BLOCK_SENSOR_LEFT_BIT | CATEGORY_REGION_BIT : {
                Block block = blockStartContactLeft(fa,fb);
            }break;
            case CATEGORY_BLOCK_SENSOR_RIGHT_BIT | CATEGORY_REGION_BIT : {
                Block block = blockStartContactRight(fa,fb);
            }break;

            //Контакт з сенсорами ігрового світу
            case CATEGORY_BLOCK_SENSOR_UP_BIT | CATEGORY_GAME_WORLD_SENSOR : {
                GameSensor gameSensor = null;
                Block block = null;
                if(fa.getUserData() instanceof GameSensor){
                    gameSensor = (GameSensor)fa.getUserData();
                    block = (Block)fb.getUserData();
                }else if(fb.getUserData() instanceof GameSensor){
                    gameSensor = (GameSensor)fb.getUserData();
                    block = (Block)fa.getUserData();
                }else{break;}
                gameSensor.getFirstLineBlocks().add(block);
            }break;

        }
    }

    @Override
    public void endContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        AbstractGameObject abstractGameObject = (AbstractGameObject)fa.getUserData();
        abstractGameObject.removeFixOnContact((AbstractGameObject)fb.getUserData(),(AbstractGameObject)fa.getUserData());
//        abstractGameObject = (AbstractGameObject)fb.getUserData();
//        abstractGameObject.removeFixOnContact(fa);

        int collisionDef = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;
        System.out.println(fa.getFilterData().categoryBits + " ::::: " + fb.getFilterData().categoryBits);

        switch (collisionDef) {
//            case CATEGORY_BLOCK_BIT :
//            case CATEGORY_BLOCK_BIT | CATEGORY_BLOCK_SENSOR_UP_BIT : {
//                Block blockA = (Block)fa.getUserData();
//                Block blockB = (Block)fb.getUserData();
//                //Видалення з списків блоку А всіх контактів блоку Б
//                blockA.getContactRightBlockList().remove(blockB);
//                blockA.getContactLeftBlockList().remove(blockB);
//                blockA.getContactDownList().remove(blockB);
//                blockA.getContactUpList().remove(blockB);
//
//                //Видалення з списків блоку Б всіх контактів блоку А
//                blockB.getContactRightBlockList().remove(blockA);
//                blockB.getContactLeftBlockList().remove(blockA);
//                blockB.getContactDownList().remove(blockA);
//                blockB.getContactUpList().remove(blockA);
//
//            }break;
//            case CATEGORY_BLOCK_BIT | CATEGORY_WATER_ELEM_BIT :
//            case CATEGORY_WATER_ELEM_BIT | CATEGORY_BLOCK_SENSOR_UP_BIT : {
//                WaterElement hero = null;
//                Block block = null;
//                if(fa.getUserData() instanceof WaterElement){
//                    hero = (WaterElement)fa.getUserData();
//                    block = (Block)fb.getUserData();
//                }else if(fa.getUserData() instanceof Block){
//                    hero = (WaterElement)fb.getUserData();
//                    block = (Block)fa.getUserData();
//                }else{break;}
//
//                hero.getSensorLeft().remove(block);
//                hero.getSensorRight().remove(block);
//                hero.getSensorDown().remove(block);
//                hero.getSensorUp().remove(block);
//
//                block.getContactRightBlockList().remove(hero);
//                block.getContactLeftBlockList().remove(hero);
//                block.getContactUpList().remove(hero);
//                block.getContactDownList().remove(hero);
//            //    block.setContactHero(null);
//
//            }break;
//            case CATEGORY_WATER_ELEM_BIT | CATEGORY_REGION_BIT: {
//                WaterElement hero = null;
//                Platform platform = null;
//                if(fa.getUserData() instanceof WaterElement){
//                    hero = (WaterElement)fa.getUserData();
//                    platform = (Platform) fb.getUserData();
//                }else if(fa.getUserData() instanceof Platform){
//                    hero = (WaterElement)fb.getUserData();
//                    platform = (Platform) fa.getUserData();
//                }else{break;}
//
//                hero.getSensorDown().remove(platform);
//
//            }break;
//            case CATEGORY_BLOCK_BIT | CATEGORY_REGION_BIT : {
//                Block block = null;//blockStartContactDown(fa,fb);
//                Platform platform = null;
//                if(fa.getUserData() instanceof Platform){
//                    platform = (Platform)fa.getUserData();
//                    block = (Block)fb.getUserData();
//                }else{
//                    platform = (Platform)fb.getUserData();
//                    block = (Block)fa.getUserData();
//                }
//
//                block.getContactDownList().remove(platform);
//            }break;
//            case CATEGORY_BLOCK_BIT | CATEGORY_GAME_WORLD_SENSOR : {
//                System.out.println("contact = " + contact);
//            }

            //Кінець Кнотакта з героєм
            //Нижній сенсор
            case CATEGORY_WATER_ELEM_SENSOR_DOWN_BIT | CATEGORY_BLOCK_SENSOR_UP_BIT:{
                WaterElement waterElement = heroEndContactDown(fa,fb);
                Block block = blockEndContactUp(fa,fb);
                block.setContactHero(null);
            }break;
            case CATEGORY_WATER_ELEM_SENSOR_DOWN_BIT | CATEGORY_REGION_BIT: {
                WaterElement waterElement = heroEndContactDown(fa,fb);
            }break;
            //Верхній сенсор
            case CATEGORY_WATER_ELEM_SENSOR_UP_BIT | CATEGORY_BLOCK_SENSOR_DOWN_BIT:{
                WaterElement waterElement = heroEndContactUp(fa,fb);
                Block block = blockEndContactDown(fa,fb);
                block.setContactHero(null);

            }break;
            case CATEGORY_WATER_ELEM_SENSOR_UP_BIT | CATEGORY_REGION_BIT: {
                WaterElement waterElement = heroEndContactUp(fa,fb);
            }break;
            //Лівий сенсор
            case CATEGORY_WATER_ELEM_SENSOR_LEFT_BIT | CATEGORY_BLOCK_SENSOR_RIGHT_BIT:{
                WaterElement waterElement = heroEndContactLeft(fa,fb);
                Block block = blockEndContactRight(fa,fb);
                block.setContactHero(null);

            }break;
            case CATEGORY_WATER_ELEM_SENSOR_LEFT_BIT | CATEGORY_REGION_BIT: {
                WaterElement waterElement = heroEndContactLeft(fa,fb);
            }break;
            //Правий сенсор
            case CATEGORY_WATER_ELEM_SENSOR_RIGHT_BIT | CATEGORY_BLOCK_SENSOR_LEFT_BIT:{
                WaterElement waterElement = heroEndContactRight(fa,fb);
                Block block = blockEndContactLeft(fa,fb);
                block.setContactHero(null);

            }break;
            case CATEGORY_WATER_ELEM_SENSOR_RIGHT_BIT | CATEGORY_REGION_BIT: {
                WaterElement waterElement = heroEndContactRight(fa,fb);
            }break;

            //Кінець контактів блоків між собою
            case CATEGORY_BLOCK_SENSOR_UP_BIT | CATEGORY_BLOCK_SENSOR_DOWN_BIT : {
                Block blockDown = blockEndContactDown(fa,fb);
                Block blockUp = blockEndContactUp(fa,fb);

            }break;
            case CATEGORY_BLOCK_SENSOR_LEFT_BIT | CATEGORY_BLOCK_SENSOR_RIGHT_BIT : {
                Block blockRight = blockEndContactRight(fa,fb);
                Block blockLeft = blockEndContactLeft(fa,fb);
            }break;

            //Кінець контактів блоків з регіонами
            case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_REGION_BIT : {
                Block block = blockEndContactDown(fa,fb);
                Platform platform = null;
                if(fa.getUserData() instanceof Platform){
                    platform = (Platform)fa.getUserData();
                }else{
                    platform = (Platform)fb.getUserData();
                }
                block.getContactPlatformList().remove(platform);
            }break;
            case CATEGORY_BLOCK_SENSOR_UP_BIT | CATEGORY_REGION_BIT : {
                Block block = blockEndContactUp(fa,fb);
            }break;
            case CATEGORY_BLOCK_SENSOR_LEFT_BIT | CATEGORY_REGION_BIT : {
                Block block = blockEndContactLeft(fa,fb);
            }break;
            case CATEGORY_BLOCK_SENSOR_RIGHT_BIT | CATEGORY_REGION_BIT : {
                Block block = blockEndContactRight(fa,fb);
            }break;
            //Контакт з сенсорами ігрового світу


        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    //Перевірка чи один з Fixture являється героєм , якщо так повернення його і задіяння відповідного сенсору
    public WaterElement heroStartContactDown(Fixture fa, Fixture fb){
        WaterElement waterElement = null;
        if(fa.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fa.getUserData();
            if(!waterElement.getSensorDown().contains((AbstractGameObject)fb.getUserData())) {
                waterElement.getSensorDown().add((AbstractGameObject)fb.getUserData());
            }
        }else if(fb.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fb.getUserData();
            if(!waterElement.getSensorDown().contains((AbstractGameObject)fa.getUserData())) {
                waterElement.getSensorDown().add((AbstractGameObject)fa.getUserData());
            }                }
        return waterElement;
    }
    public WaterElement heroStartContactUp(Fixture fa, Fixture fb){
        WaterElement waterElement = null;
        if(fa.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fa.getUserData();
            if(!waterElement.getSensorUp().contains((AbstractGameObject)fb.getUserData())) {
                waterElement.getSensorUp().add((AbstractGameObject)fb.getUserData());
            }
        }else if(fb.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fb.getUserData();
            if(!waterElement.getSensorUp().contains((AbstractGameObject)fa.getUserData())) {
                waterElement.getSensorUp().add((AbstractGameObject)fa.getUserData());
            }                }
        return waterElement;
    }
    public WaterElement heroStartContactRight(Fixture fa, Fixture fb){
        WaterElement waterElement = null;
        if(fa.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fa.getUserData();
            if(!waterElement.getSensorRight().contains((AbstractGameObject)fb.getUserData())) {
                waterElement.getSensorRight().add((AbstractGameObject)fb.getUserData());
            }
        }else if(fb.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fb.getUserData();
            if(!waterElement.getSensorRight().contains((AbstractGameObject)fa.getUserData())) {
                waterElement.getSensorRight().add((AbstractGameObject)fa.getUserData());
            }                }
        return waterElement;
    }
    public WaterElement heroStartContactLeft(Fixture fa, Fixture fb){
        WaterElement waterElement = null;
        if(fa.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fa.getUserData();
            if(!waterElement.getSensorLeft().contains((AbstractGameObject)fb.getUserData())) {
                waterElement.getSensorLeft().add((AbstractGameObject)fb.getUserData());
            }
        }else if(fb.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fb.getUserData();
            if(!waterElement.getSensorLeft().contains((AbstractGameObject)fa.getUserData())) {
                waterElement.getSensorLeft().add((AbstractGameObject)fa.getUserData());
            }                }
        return waterElement;
    }

    //Перевірка дві з Fixture являються блоком, якщо так порівння їх кординат і повернення правильного блоку і зідіяння сенсору
    //Перевірка чи один з Fixture являється блоком , якщо так повернення його і задіяння відповідного сенсору
    public Block blockStartContactDown(Fixture fa,Fixture  fb){
        Block block = null;
        Fixture fixture = null;
        Object objFa = fa.getUserData();
        Object objFb = fb.getUserData();
        if((objFa instanceof Block) && (objFb instanceof  Block)){
            if(fa.getBody().getPosition().y > fb.getBody().getPosition().y){
                block = (Block)objFa;
                fixture = fb;
            }else{
                block = (Block)objFb;
                fixture = fa;
            }
        }else if(objFa instanceof Block){
            block = (Block)objFa;
            fixture = fb;
        }else if(objFb instanceof Block){
            block = (Block)objFb;
            fixture = fa;
        }
        if(block != null) {
            block.setSensorDown(true);
            block.getContactDownList().add((AbstractGameObject)fixture.getUserData());
        }
        return block;
    }
    public Block blockStartContactUp(Fixture fa,Fixture  fb){
        Block block = null;
        Object objFa = fa.getUserData();
        Object objFb = fb.getUserData();
        if((objFa instanceof Block) && (objFb instanceof  Block)){
            if(fa.getBody().getPosition().y < fb.getBody().getPosition().y){
                block = (Block)objFa;
            }else{
                block = (Block)objFb;
            }
        }else if(objFa instanceof Block){
            block = (Block)objFa;
        }else if(objFb instanceof Block){
            block = (Block)objFb;
        }
        if(block != null) {
            block.setSensorUp(true);
        }
        return block;
    }
    public Block blockStartContactLeft(Fixture fa,Fixture  fb) {
        Block block = null;
        Object objFa = fa.getUserData();
        Object objFb = fb.getUserData();
        if ((objFa instanceof Block) && (objFb instanceof Block)) {
            if (fa.getBody().getPosition().x > fb.getBody().getPosition().x) {
                block = (Block) objFa;
                if(!block.getContactLeftBlockList().contains((Block)objFb)) {
                    block.getContactLeftBlockList().add((Block) objFb);
                }
            } else {
                block = (Block) objFb;
                if(!block.getContactLeftBlockList().contains((Block) objFa)) {
                    block.getContactLeftBlockList().add((Block) objFa);
                }
            }
        } else if (objFa instanceof Block) {
            block = (Block) objFa;
        } else if (objFb instanceof Block) {
            block = (Block) objFb;
        }
        if (block != null) {
            block.setSensorLeft(true);
        }
        return block;
    }
    public Block blockStartContactRight(Fixture fa,Fixture  fb){
        Block block = null;
        Object objFa = fa.getUserData();
        Object objFb = fb.getUserData();
        if((objFa instanceof Block) && (objFb instanceof  Block)){
            if(fa.getBody().getPosition().x < fb.getBody().getPosition().x){
                block = (Block)objFa;
                if(!block.getContactRightBlockList().contains((Block) objFb)) {
                    block.getContactRightBlockList().add((Block) objFb);
                }
            }else{
                block = (Block)objFb;
                if(!block.getContactRightBlockList().contains((Block) objFa)) {
                    block.getContactRightBlockList().add((Block) objFa);
                }
            }
        }else if(objFa instanceof Block){
            block = (Block)objFa;
        }else if(objFb instanceof Block){
            block = (Block)objFb;
        }
        if(block != null) {
            block.setSensorRight(true);
        }
        return block;
    }

    //Перевірка чи один з Fixture являється героєм, якщо так повернення його і деактивація відповідного сенсора
    public WaterElement heroEndContactDown(Fixture fa, Fixture fb){
        WaterElement waterElement = null;
        if(fa.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fa.getUserData();
            waterElement.getSensorDown().remove((AbstractGameObject)fb.getUserData());

        }else if(fb.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fb.getUserData();
            waterElement.getSensorDown().remove((AbstractGameObject)fa.getUserData());
            }
        return waterElement;
    }
    public WaterElement heroEndContactUp(Fixture fa, Fixture fb) {
        WaterElement waterElement = null;
        if(fa.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fa.getUserData();
            waterElement.getSensorUp().remove((AbstractGameObject)fb.getUserData());
        }else if(fb.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fb.getUserData();
            waterElement.getSensorUp().remove((AbstractGameObject)fa.getUserData());
            }
        return waterElement;
    }
    public WaterElement heroEndContactRight(Fixture fa, Fixture fb){
        WaterElement waterElement = null;
        if(fa.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fa.getUserData();
            waterElement.getSensorRight().remove((AbstractGameObject)fb.getUserData());
        }else if(fb.getUserData() instanceof WaterElement) {
            waterElement = (WaterElement) fb.getUserData();
            waterElement.getSensorRight().remove((AbstractGameObject)fa.getUserData());
        }
        return waterElement;
    }
    public WaterElement heroEndContactLeft(Fixture fa, Fixture fb){
        WaterElement waterElement = null;
        if(fa.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fa.getUserData();
            waterElement.getSensorLeft().remove((AbstractGameObject)fb.getUserData());
        }else if(fb.getUserData() instanceof WaterElement){
            waterElement = (WaterElement)fb.getUserData();
            waterElement.getSensorLeft().remove((AbstractGameObject)fa.getUserData());
            }
        return waterElement;
    }

    //Перевірка дві з Fixture являються блоком, якщо так порівння їх кординат і повернення правильного блоку і деактивація сенсору
    //Перевірка чи один з Fixture являється блоком , якщо так повернення його і деактивація відповідного сенсору
    public Block blockEndContactDown(Fixture fa,Fixture  fb){
        Block block = null;
        Fixture fixture = null;
        Object objFa = fa.getUserData();
        Object objFb = fb.getUserData();
        if((objFa instanceof Block) && (objFb instanceof  Block)){
            if(fa.getBody().getPosition().y > fb.getBody().getPosition().y){
                block = (Block)objFa;
                fixture = fb;
            }else{
                block = (Block)objFb;
                fixture = fa;
            }
        }else if(objFa instanceof Block){
            block = (Block)objFa;
            fixture = fb;
        }else if(objFb instanceof Block){
            block = (Block)objFb;
            fixture = fa;
        }
        if(block !=null) {
            block.setSensorDown(false);
            block.getContactDownList().remove((AbstractGameObject)fixture.getUserData());
        }
        return block;
    }
    public Block blockEndContactUp(Fixture fa,Fixture  fb){
        Block block = null;
        Object objFa = fa.getUserData();
        Object objFb = fb.getUserData();
        if((objFa instanceof Block) && (objFb instanceof  Block)){
            if(fa.getBody().getPosition().y < fb.getBody().getPosition().y){
                block = (Block)objFa;
            }else{
                block = (Block)objFb;
            }
        }else if(objFa instanceof Block){
            block = (Block)objFa;
        }else if(objFb instanceof Block){
            block = (Block)objFb;
        }
        if(block != null) {
            block.setSensorUp(false);
        }
        return block;
    }
    public Block blockEndContactLeft(Fixture fa,Fixture  fb){
        Block block = null;
        Object objFa = fa.getUserData();
        Object objFb = fb.getUserData();
        if((objFa instanceof Block) && (objFb instanceof  Block)){
            if(fa.getBody().getPosition().x > fb.getBody().getPosition().x){
                block = (Block)objFa;
                block.getContactLeftBlockList().remove((Block)objFb);
            }else{
                block = (Block)objFb;
                block.getContactLeftBlockList().remove((Block)objFa);
            }
        }else if(objFa instanceof Block){
            block = (Block)objFa;
        }else if(objFb instanceof Block){
            block = (Block)objFb;
        }
        if(block != null) {
            block.setSensorLeft(false);
        }
        return block;
    }
    public Block blockEndContactRight(Fixture fa,Fixture  fb){
        Block block = null;
        Object objFa = fa.getUserData();
        Object objFb = fb.getUserData();
        if((objFa instanceof Block) && (objFb instanceof  Block)){
            if(fa.getBody().getPosition().x < fb.getBody().getPosition().x){
                block = (Block)objFa;
                block.getContactRightBlockList().remove((Block)objFb);
            }else{
                block = (Block)objFb;
                block.getContactRightBlockList().remove((Block)objFa);
            }
        }else if(objFa instanceof Block){
            block = (Block)objFa;
        }else if(objFb instanceof Block){
            block = (Block)objFb;
        }
        if(block != null) {
            block.setSensorRight(false);
        }
        return block;
    }

}
