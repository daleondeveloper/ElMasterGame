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
    private Animation<TextureRegion> whiteHeroStand;
    private Animation<TextureRegion> whiteHeroWalk;
    private Animation<TextureRegion> whiteHeroRun;
    private Animation<TextureRegion> whiteHeroPush;
    private Animation<TextureRegion> whiteHeroJump;
    private Animation<TextureRegion> whiteHeroFall;
    private Animation<TextureRegion> whiteHeroDeath;

    private Animation<TextureRegion> blackHeroStand;
    private Animation<TextureRegion> blackHeroWalk;
    private Animation<TextureRegion> blackHeroRun;
    private Animation<TextureRegion> blackHeroPush;
    private Animation<TextureRegion> blackHeroJump;
    private Animation<TextureRegion> blackHeroFall;
    private Animation<TextureRegion> blackHeroDeath;

    private Animation<TextureRegion> heroStand;
    private Animation<TextureRegion> heroWalk;
    private Animation<TextureRegion> heroRun;
    private Animation<TextureRegion> heroPush;
    private Animation<TextureRegion> heroJump;
    private Animation<TextureRegion> heroFall;
    private Animation<TextureRegion> heroDeath;

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
        whiteHeroStand = new Animation<TextureRegion>(3f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/walk/walk");
        whiteHeroWalk = new Animation<TextureRegion>(2f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/run/run");
        whiteHeroRun = new Animation<TextureRegion>(1f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/push/push");
        whiteHeroPush = new Animation<TextureRegion>(2f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("white/jump/jump");
        whiteHeroJump = new Animation<TextureRegion>(2f/24.0f, regions, Animation.PlayMode.NORMAL);
        regions.clear();

        regions = atlasHero.findRegions("white/fall/fall");
        whiteHeroFall = new Animation<TextureRegion>(2f/24.0f, regions, Animation.PlayMode.NORMAL);
        regions.clear();

        regions = atlasHero.findRegions("white/death/death" );
        whiteHeroDeath = new Animation<TextureRegion>(2f/24.0f, regions, Animation.PlayMode.NORMAL);
        regions.clear();
    }
    private void blackHeroGetTextureFromAtlas(TextureAtlas atlasHero){
        Array<TextureAtlas.AtlasRegion> regions;

        //Animation Dark Hero
        regions = atlasHero.findRegions("black/stand/stand");
        blackHeroStand = new Animation<TextureRegion>(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("black/walk/walk");
        blackHeroWalk = new Animation<TextureRegion>(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("black/run/run");
        blackHeroRun = new Animation<TextureRegion>(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("black/push/push");
        blackHeroPush = new Animation<TextureRegion>(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("black/jump/jump");
        blackHeroJump = new Animation<TextureRegion>(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("black/fall/fall");
        blackHeroFall = new Animation<TextureRegion>(0.5f/24.0f, regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasHero.findRegions("black/death/death");
        blackHeroDeath = new Animation<TextureRegion>(0.5f/24.0f, regions, Animation.PlayMode.NORMAL);
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

    public Animation<TextureRegion> getHeroStand() {
        return heroStand;
    }

    public Animation<TextureRegion> getHeroWalk() {
        return heroWalk;
    }

    public Animation<TextureRegion> getHeroRun() {
        return heroRun;
    }

    public Animation<TextureRegion> getHeroPush() {
        return heroPush;
    }

    public Animation<TextureRegion> getHeroJump() {
        return heroJump;
    }

    public Animation<TextureRegion> getHeroFall() {
        return heroFall;
    }

    public Animation<TextureRegion> getHeroDeath() {
        return heroDeath;
    }
}
