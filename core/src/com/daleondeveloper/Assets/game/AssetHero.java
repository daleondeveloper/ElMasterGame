package com.daleondeveloper.Assets.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class AssetHero implements IAssetSprite {
    private static final String TAG = AssetHero.class.getName();

    private static final float SCALE = 0.7f;

    private Animation whiteHeroStand;
    private Animation whiteHeroWalk;
    private Animation whiteHeroRun;
    private Animation whiteHeroPush;
    private Animation whiteHeroJump;
    private Animation whiteHeroFall;
    private Animation whiteHeroDeath;

    private Animation blackHeroStand;
    private Animation blackHeroWalk;
    private Animation blackHeroRun;
    private Animation blackHeroPush;
    private Animation blackHeroJump;
    private Animation blackHeroFall;
    private Animation blackHeroDeath;

    public AssetHero(TextureAtlas atlasHero){
        Array<TextureAtlas.AtlasRegion> regions;

        //Animation White Hero
        regions = atlasHero.findRegions("white/stand/stand");
        whiteHeroStand = new Animation(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/walk/walk");
        whiteHeroWalk = new Animation(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/run/run");
        whiteHeroRun = new Animation(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/push/push");
        whiteHeroPush = new Animation(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/jump/jump");
        whiteHeroJump = new Animation(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/fall/fall");
        whiteHeroFall = new Animation(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/death/death");
        whiteHeroDeath = new Animation(0.5f/24.0f, regions, Animation.PlayMode.NORMAL);
        regions.clear();

        //Animation Dark Hero
        regions = atlasHero.findRegions("black/stand/stand");
        blackHeroStand = new Animation(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("black/walk/walk");
        blackHeroWalk = new Animation(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("black/run/run");
        blackHeroRun = new Animation(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("black/push/push");
        blackHeroPush = new Animation(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("black/jump/jump");
        blackHeroJump = new Animation(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("black/fall/fall");
        blackHeroFall = new Animation(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("black/death/death");
        blackHeroDeath = new Animation(0.5f/24.0f, regions, Animation.PlayMode.NORMAL);
        regions.clear();



    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    public Animation getWhiteHeroStand() {
        return whiteHeroStand;
    }

    public Animation getWhiteHeroWalk() {
        return whiteHeroWalk;
    }

    public Animation getWhiteHeroRun() {
        return whiteHeroRun;
    }

    public Animation getWhiteHeroPush() {
        return whiteHeroPush;
    }

    public Animation getWhiteHeroJump() {
        return whiteHeroJump;
    }

    public Animation getWhiteHeroFall() {
        return whiteHeroFall;
    }

    public Animation getWhiteHeroDeath() {
        return whiteHeroDeath;
    }

    public Animation getBlackHeroStand() {
        return blackHeroStand;
    }

    public Animation getBlackHeroWalk() {
        return blackHeroWalk;
    }

    public Animation getBlackHeroRun() {
        return blackHeroRun;
    }

    public Animation getBlackHeroPush() {
        return blackHeroPush;
    }

    public Animation getBlackHeroJump() {
        return blackHeroJump;
    }

    public Animation getBlackHeroFall() {
        return blackHeroFall;
    }

    public Animation getBlackHeroDeath() {
        return blackHeroDeath;
    }
}
