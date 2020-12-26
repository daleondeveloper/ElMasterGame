package com.daleondeveloper.Assets.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AssetHero implements IAssetSprite {
    private static final String TAG = AssetHero.class.getName();

    private static final float SCALE = 0.7f;

    private enum HeroType{
        WHITE,BLACK;
    }
    private HeroType heroType;

    private TextureRegion heroStandStatic;
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

    private Animation heroStand;
    private Animation heroWalk;
    private Animation heroRun;
    private Animation heroPush;
    private Animation heroJump;
    private Animation heroFall;
    private Animation heroDeath;

    public AssetHero(TextureAtlas atlasHero){
        whiteHeroGetTextureFromAtlas(atlasHero);
        blackHeroGetTextureFromAtlas(atlasHero);
        heroStandStatic = atlasHero.findRegion("white/stand/stand",1);
        changeWhiteHero();

    }
    public void changeWhiteHero(){
        heroType = HeroType.WHITE;
          heroStand = whiteHeroStand;
          heroWalk = whiteHeroWalk;
          heroRun = whiteHeroRun;
          heroPush = whiteHeroPush;
          heroJump = whiteHeroJump;
          heroFall = whiteHeroFall;
          heroDeath = whiteHeroDeath;
    }
    public void changeBlackHero(){
        heroType = HeroType.BLACK;
          heroStand = blackHeroStand;
          heroWalk = blackHeroWalk;
          heroRun = blackHeroRun;
          heroPush = blackHeroPush;
          heroJump = blackHeroJump;
          heroFall = blackHeroFall;
          heroDeath = blackHeroDeath;
    }


    private void whiteHeroGetTextureFromAtlas(TextureAtlas atlasHero){
        Array<TextureAtlas.AtlasRegion> regions;

        //Animation White Hero
        regions = atlasHero.findRegions("white/stand/stand");
        whiteHeroStand = new Animation(3f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/walk/walk");
        whiteHeroWalk = new Animation(2f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/run/run");
        whiteHeroRun = new Animation(1f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/push/push");
        whiteHeroPush = new Animation(2f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/jump/jump");
        whiteHeroJump = new Animation(2f/24.0f, regions, Animation.PlayMode.NORMAL);
        regions.clear();

        regions = atlasHero.findRegions("white/fall/fall");
        whiteHeroFall = new Animation(2f/24.0f, regions, Animation.PlayMode.NORMAL);
        regions.clear();

        regions = atlasHero.findRegions("white/death/death");
        whiteHeroDeath = new Animation(2f/24.0f, regions, Animation.PlayMode.NORMAL);
        regions.clear();
    }
    private void blackHeroGetTextureFromAtlas(TextureAtlas atlasHero){
        Array<TextureAtlas.AtlasRegion> regions;

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

    public HeroType getHeroType() {
        return heroType;
    }

    public TextureRegion getHeroStandStatic() {
        return heroStandStatic;
    }

    public Animation getHeroStand() {
        return heroStand;
    }

    public Animation getHeroWalk() {
        return heroWalk;
    }

    public Animation getHeroRun() {
        return heroRun;
    }

    public Animation getHeroPush() {
        return heroPush;
    }

    public Animation getHeroJump() {
        return heroJump;
    }

    public Animation getHeroFall() {
        return heroFall;
    }

    public Animation getHeroDeath() {
        return heroDeath;
    }
}
