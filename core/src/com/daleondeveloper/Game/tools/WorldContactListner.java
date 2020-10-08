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

        int collisionDef = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;

        switch (collisionDef) {
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
                Block block = null;
                if (fa.getUserData() instanceof WaterElement) {
                    waterElement = (WaterElement) fa.getUserData();
                    if (!waterElement.getSensorDown().contains(fb)) {
                        waterElement.getSensorDown().add(fb);
                    }
                } else if (fb.getUserData() instanceof WaterElement) {
                    waterElement = (WaterElement) fb.getUserData();
                    if (!waterElement.getSensorDown().contains(fa)) {
                        waterElement.getSensorDown().add(fb);
                    }

                }
            }break;
            case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_BLOCK_SENSOR_UP_BIT:
            case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_REGION_BIT:
            case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_WATER_ELEM_SENSOR_UP_BIT:{
                Block block = null;
                WaterElement waterElement = null;
                System.out.println("In Start Contact");
                if(fa.getBody().getPosition().y > fb.getBody().getPosition().y){
                    block = (Block)fa.getUserData();
                }else{
                    block = (Block)fb.getUserData();
                }
                if(fa.getUserData() instanceof WaterElement){
                    waterElement = (WaterElement)fa.getUserData();
                    if(!waterElement.getSensorUp().contains(fb)) {
                        waterElement.getSensorUp().add(fb);
                    }
                }else if(fb.getUserData() instanceof WaterElement){
                    waterElement = (WaterElement)fb.getUserData();
                    if(!waterElement.getSensorUp().contains(fa)) {
                        waterElement.getSensorUp().add(fb);
                    }                }
                block.setSensorDown(true);
            }break;

            case CATEGORY_BLOCK_SENSOR_LEFT_BIT | CATEGORY_WATER_ELEM_SENSOR_RIGHT_BIT : {
                WaterElement waterElement = null;
                Block block = null;
                if (fa.getUserData() instanceof WaterElement) {
                    waterElement = (WaterElement) fa.getUserData();
                    if (!waterElement.getSensorRight().contains(fb)) {
                        waterElement.getSensorRight().add(fb);
                    }
                } else if (fb.getUserData() instanceof WaterElement) {
                    waterElement = (WaterElement) fb.getUserData();
                    if (!waterElement.getSensorRight().contains(fa)) {
                        waterElement.getSensorRight().add(fb);
                    }
                }
            }break;
            case CATEGORY_BLOCK_SENSOR_RIGHT_BIT | CATEGORY_WATER_ELEM_SENSOR_LEFT_BIT : {
                WaterElement waterElement = null;
                Block block = null;
                if (fa.getUserData() instanceof WaterElement) {
                    waterElement = (WaterElement) fa.getUserData();
                    if (!waterElement.getSensorLeft().contains(fb)) {
                        waterElement.getSensorLeft().add(fb);
                    }
                } else if (fb.getUserData() instanceof WaterElement) {
                    waterElement = (WaterElement) fb.getUserData();
                    if (!waterElement.getSensorLeft().contains(fa)) {
                        waterElement.getSensorLeft().add(fb);
                    }
                }
            }break;
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
            case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_REGION_BIT :
            case CATEGORY_BLOCK_SENSOR_DOWN_BIT | CATEGORY_WATER_ELEM_SENSOR_UP_BIT : {
                Block contactBlock = null;
                WaterElement waterElement = null;
                System.out.println("In End Contact");
                if(fa.getBody().getPosition().y > fb.getBody().getPosition().y){
                    contactBlock = (Block)fa.getUserData();
                }else{
                    contactBlock = (Block)fb.getUserData();
                }
                if(fa.getUserData() instanceof WaterElement){
                    waterElement = (WaterElement)fa.getUserData();
                    waterElement.getSensorUp().remove(fb);
                }else if(fb.getUserData() instanceof WaterElement){
                    waterElement = (WaterElement)fb.getUserData();
                    waterElement.getSensorUp().remove(fa);
                }
                contactBlock.setSensorDown(false);

            }break;
            case CATEGORY_BLOCK_SENSOR_LEFT_BIT | CATEGORY_WATER_ELEM_SENSOR_RIGHT_BIT : {
                WaterElement waterElement = null;
                Block block = null;
                if (fa.getUserData() instanceof WaterElement) {
                    waterElement = (WaterElement) fa.getUserData();
                    waterElement.getSensorRight().remove(fb);
                } else if (fb.getUserData() instanceof WaterElement) {
                    waterElement = (WaterElement) fb.getUserData();
                    waterElement.getSensorRight().remove(fb);
                }
            }break;

            case CATEGORY_BLOCK_SENSOR_RIGHT_BIT | CATEGORY_WATER_ELEM_SENSOR_LEFT_BIT : {
                WaterElement waterElement = null;
                Block block = null;
                if (fa.getUserData() instanceof WaterElement) {
                    waterElement = (WaterElement) fa.getUserData();
                    waterElement.getSensorLeft().remove(fb);
                } else if (fb.getUserData() instanceof WaterElement) {
                    waterElement = (WaterElement) fb.getUserData();
                    waterElement.getSensorLeft().remove(fb);
                }
            }break;
            case CATEGORY_BLOCK_SENSOR_UP_BIT | CATEGORY_WATER_ELEM_SENSOR_DOWN_BIT :
            case CATEGORY_REGION_BIT | CATEGORY_WATER_ELEM_SENSOR_DOWN_BIT :{
                WaterElement waterElement = null;
                Block block = null;
                if (fa.getUserData() instanceof WaterElement) {
                    waterElement = (WaterElement) fa.getUserData();
                    waterElement.getSensorDown().remove(fb);
                } else if (fb.getUserData() instanceof WaterElement) {
                    waterElement = (WaterElement) fb.getUserData();
                    waterElement.getSensorDown().remove(fb);
                }
            }break;

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
