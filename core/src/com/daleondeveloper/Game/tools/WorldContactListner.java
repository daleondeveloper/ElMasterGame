package com.daleondeveloper.Game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.daleondeveloper.Sprites.Block;
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



    public static final short MASK_ALL = -1;
    public static final short MASK_BLOCK = 0;

    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        Short faCatBit = fa.getFilterData().categoryBits;
//        System.out.println(fa.getFilterData().categoryBits+ "\n" +
//                fb.getFilterData().categoryBits);
//        System.out.println(collisionDef);

        int collisionDef = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;

        switch (collisionDef) {
            case CATEGORY_BLOCK_BIT | CATEGORY_REGION_BIT: {
                Block block;
                Platform platform;
                if(fa.getFilterData().categoryBits == CATEGORY_REGION_BIT){
                    platform = (Platform) fa.getUserData();
                    block = (Block)fb.getUserData();
                }else{
                    platform = (Platform) fb.getUserData();
                    block = (Block)fa.getUserData();
                }
                //block.stopFall();
                //heroSkill.addBlockCollision(block);
            }break;
            case CATEGORY_REGION_BIT | CATEGORY_WATER_ELEM_BIT: {
                Platform platform;
                WaterElement hero;
                if (fa.getFilterData().categoryBits == CATEGORY_REGION_BIT) {
                    platform = (Platform) fa.getUserData();
                    hero = (WaterElement) fb.getUserData();
                } else {
                    platform = (Platform) fb.getUserData();
                    hero = (WaterElement) fa.getUserData();

                }
                if(hero.getBodyPosition().y > platform.getY() + platform.getHeight()){
                    hero.endJump();
                }
            }break;
            case CATEGORY_WATER_ELEM_SENSOR_DOWN_BIT | CATEGORY_BLOCK_SENSOR_UP_BIT:
            case CATEGORY_WATER_ELEM_SENSOR_DOWN_BIT | CATEGORY_REGION_BIT: {
                WaterElement waterElement = null;
                if(fa.getUserData() instanceof WaterElement ){
                    waterElement = (WaterElement)fa.getUserData();
                }else{
                    waterElement = (WaterElement)fb.getUserData();
                }

            }break;
            case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_BLOCK_SENSOR_UP_BIT:
            case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_REGION_BIT:
            case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_WATER_ELEM_SENSOR_UP_BIT:{
                Block contactBlock = null;
                if(fa.getBody().getPosition().y > fb.getBody().getPosition().y){
                    contactBlock = (Block)fa.getUserData();
                }else{
                    contactBlock = (Block)fb.getUserData();
                }
                contactBlock.setSensorDown(true);

            }break;
//            case CATEGORY_BLOCK_SENSOR_LEFT_BIT | CATEGORY_WATER_ELEM_BIT : {
//                WaterElement waterElement;
//                Block block;
//                if (fa.getFilterData().categoryBits == CATEGORY_WATER_ELEM_BIT) {
//                    waterElement = (WaterElement)fa.getUserData();
//                    block = (Block) fb.getUserData();
//
//                } else {
//                    waterElement = (WaterElement)fb.getUserData();
//                    block = (Block) fa.getUserData();
//
//                }
//                waterElement.push(0.2f);
//                block.push(waterElement);
//
//
//            }break;
//            case CATEGORY_BLOCK_SENSOR_RIGHT_BIT | CATEGORY_WATER_ELEM_BIT : {
//                WaterElement waterElement;
//                Block block;
//                if (fa.getFilterData().categoryBits == CATEGORY_WATER_ELEM_BIT) {
//                    waterElement = (WaterElement)fa.getUserData();
//                    block = (Block) fb.getUserData();
//
//                } else {
//                    waterElement = (WaterElement)fb.getUserData();
//                    block = (Block) fa.getUserData();
//
//                }
//                waterElement.push(1);
//                block.push(waterElement);
//
//
//            }break;
    }

//            switch (fa.getFilterData().categoryBits){
//                case CATEGORY_BLOCK_SENSOR_DOWN_BIT : {
//                    Block block = (Block) fa.getUserData();
//                    //block.setSensorDown(true);
//                }break;
//                case CATEGORY_BLOCK_SENSOR_UP_BIT : {
//                    Block block = (Block) fa.getUserData();
//                    block.setSensorUp(true);
//                }break;
//                case CATEGORY_BLOCK_SENSOR_RIGHT_BIT : {
//                    Block block = (Block) fa.getUserData();
//                    block.setSensorRight(true);
//                }break;
//                case CATEGORY_BLOCK_SENSOR_LEFT_BIT : {
//                    Block block = (Block) fa.getUserData();
//                    block.setSensorLeft(true);
//                }break;
//            }

            if(fa.getUserData() instanceof WaterElement){
                switch (faCatBit){
                    case CATEGORY_WATER_ELEM_SENSOR_LEFT_BIT : {
                        System.out.println("sensor left active");
                        WaterElement hero = (WaterElement) fa.getUserData();
                        hero.getSensorLeft().add(fb);
                    }break;
                    case CATEGORY_WATER_ELEM_SENSOR_RIGHT_BIT : {
                        System.out.println("sensor right active");
                        WaterElement hero = (WaterElement) fa.getUserData();
                        hero.getSensorRight().add(fb);
                    }break;
                    case CATEGORY_WATER_ELEM_SENSOR_UP_BIT : {
                        System.out.println("sensor up active");
                        WaterElement hero = (WaterElement) fa.getUserData();
                        hero.getSensorUp().add(fb);
                    }break;
                    case CATEGORY_WATER_ELEM_SENSOR_DOWN_BIT : {
                        System.out.println("sensor down active");
                        WaterElement hero = (WaterElement) fa.getUserData();
                        hero.getSensorDown().add(fb);

                    }break;
                }
            }


    }

    @Override
    public void endContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        Short faCatBit = fa.getFilterData().categoryBits;

        int collisionDef = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;

        switch (collisionDef) {
           case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_BLOCK_SENSOR_UP_BIT :
      //      case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_REGION_BIT :
            case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_WATER_ELEM_SENSOR_UP_BIT : {
                Block contactBlock = null;
                if(fa.getBody().getPosition().y > fb.getBody().getPosition().y){
                    contactBlock = (Block)fa.getUserData();
                }else{
                    contactBlock = (Block)fb.getUserData();
                }
                contactBlock.setSensorDown(false);

            }break;

        }
//            switch (faCatBit){
//                case CATEGORY_BLOCK_SENSOR_DOWN_BIT : {
//                    Block block = (Block) fa.getUserData();
//                   // block.setSensorDown(false);
//                }break;
//                case CATEGORY_BLOCK_SENSOR_UP_BIT : {
//                    Block block = (Block) fa.getUserData();
//                    block.setSensorUp(false);
//                }break;
//                case CATEGORY_BLOCK_SENSOR_RIGHT_BIT : {
//                    Block block = (Block) fa.getUserData();
//                    block.setSensorRight(false);
//                }break;
//                case CATEGORY_BLOCK_SENSOR_LEFT_BIT : {
//                    Block block = (Block) fa.getUserData();
//                    block.setSensorLeft(false);
//                }break;
//
//
//            }
            if(fa.getUserData() instanceof WaterElement){
                switch (faCatBit){
                    case CATEGORY_WATER_ELEM_SENSOR_LEFT_BIT : {
                        System.out.println("sensor left deactivate");
                        WaterElement hero = (WaterElement) fa.getUserData();
                        hero.getSensorLeft().remove(fb);
                    }break;
                    case CATEGORY_WATER_ELEM_SENSOR_RIGHT_BIT : {
                        System.out.println("sensor right deactive");
                        WaterElement hero = (WaterElement) fa.getUserData();
                        hero.getSensorRight().remove(fb);
                    }break;
                    case CATEGORY_WATER_ELEM_SENSOR_UP_BIT : {
                        System.out.println("sensor up deactive");
                        WaterElement hero = (WaterElement) fa.getUserData();
                        hero.getSensorUp().remove(fb);
                    }break;
                    case CATEGORY_WATER_ELEM_SENSOR_DOWN_BIT : {
                        WaterElement hero = (WaterElement) fa.getUserData();
                        hero.getSensorDown().remove(fb);

                    }break;
                }
            }


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
