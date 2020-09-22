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
        System.out.println("In begin Contact");

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        System.out.println(fa.getFilterData().categoryBits+ "\n" +
                fb.getFilterData().categoryBits);

        int collisionDef = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;
        System.out.println(collisionDef);

        switch (collisionDef) {

            case CATEGORY_BLOCK_BIT | CATEGORY_WATER_ELEM_BIT: {
                WaterElement waterElement;
                Block block;
                if (fa.getFilterData().categoryBits == CATEGORY_WATER_ELEM_BIT) {
                  //      resolveContact(((WaterElement) fa.getUserData()), ((Block) fb.getUserData()));
                         waterElement = (WaterElement)fa.getUserData();
                         block = (Block) fb.getUserData();

                } else {
                        //resolveContact(((Jumper) fixB.getUserData()), ((Platform) fixA.getUserData()));
                        waterElement = (WaterElement)fb.getUserData();
                        block = (Block) fa.getUserData();

                }
                if(waterElement.getY() + 0.1f  > block.getY() + block.getHeight()){
                    waterElement.endJump();
                }
                }break;
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
                block.stopFall();
                System.out.println("contact = " + contact);
                System.out.println("block = " + block );
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
            case CATEGORY_BLOCK_SENSOR_LEFT_BIT | CATEGORY_WATER_ELEM_BIT : {
                WaterElement waterElement;
                Block block;
                if (fa.getFilterData().categoryBits == CATEGORY_WATER_ELEM_BIT) {
                    waterElement = (WaterElement)fa.getUserData();
                    block = (Block) fb.getUserData();

                } else {
                    waterElement = (WaterElement)fb.getUserData();
                    block = (Block) fa.getUserData();

                }
                waterElement.push(0);
                block.push(waterElement);


            }break;
            case CATEGORY_BLOCK_SENSOR_RIGHT_BIT | CATEGORY_WATER_ELEM_SENSOR_LEFT_BIT : {
                WaterElement waterElement;
                Block block;
                if (fa.getFilterData().categoryBits == CATEGORY_WATER_ELEM_SENSOR_RIGHT_BIT) {
                    waterElement = (WaterElement)fa.getUserData();
                    block = (Block) fb.getUserData();

                } else {
                    waterElement = (WaterElement)fb.getUserData();
                    block = (Block) fa.getUserData();

                }
                waterElement.push(1);
                block.push(waterElement);


            }break;
    }
        for(int i = 0; i < 2; i++){
            if(i == 1){
                Fixture fc = fa;
                fa = fb;
                fb = fc;
            }
            switch (fa.getFilterData().categoryBits){
                case CATEGORY_BLOCK_SENSOR_DOWN_BIT : {
                    Block block = (Block) fa.getUserData();
                    block.setSensorDown(true);
                }break;
                case CATEGORY_BLOCK_SENSOR_UP_BIT : {
                    Block block = (Block) fb.getUserData();
                    block.setSensorUp(true);
                }
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
