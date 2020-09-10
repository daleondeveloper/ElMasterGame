package com.daleondeveloper.Game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.daleondeveloper.Sprites.Block;
import com.daleondeveloper.Sprites.Hero.HeroSkill;
import com.daleondeveloper.Sprites.Hero.WaterElement;
import com.daleondeveloper.Sprites.Platform;

public class WorldContactListner implements ContactListener {
    private static final String TAG = WorldContactListner.class.getName();

    // Box2D Collision Bits
    public static final short CATEGORY_NOTHING_BIT = 0;
    public static final short CATEGORY_BLOCK_BIT = 1;
    public static final short CATEGORY_WATER_ELEM_BIT = 2;
    public static final short CATEGORY_REGION_BIT = 4;
    public static final short CATEGORY_SKIIL_BIT = 8;

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
            case CATEGORY_BLOCK_BIT | CATEGORY_WATER_ELEM_BIT:
                {WaterElement waterElement;
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
                }
                break;
            case CATEGORY_BLOCK_BIT | CATEGORY_SKIIL_BIT: {
                Block block;
                HeroSkill heroSkill;
                if(fa.getFilterData().categoryBits == CATEGORY_SKIIL_BIT){
                    heroSkill = (HeroSkill) fa.getUserData();
                    block = (Block)fb.getUserData();
                }else{
                    heroSkill = (HeroSkill) fb.getUserData();
                    block = (Block)fa.getUserData();
                }
                heroSkill.addBlockCollision(block);
            }
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
