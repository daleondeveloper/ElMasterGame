package com.daleondeveloper.Assets.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Game.GameCamera;

public class AssetBlock implements IAssetSprite {
    private static final String TAG = AssetBlock.class.getName();

    private static final float SCALE = 0.7f;

    private TextureRegion blockBlue;
    private TextureRegion blockPurr;
    private TextureRegion blockGreen;
    private TextureRegion blockRed;
    private TextureRegion blockYellow;

    private Animation destroyWind;

    public  AssetBlock(TextureAtlas atlas){
        Array<TextureAtlas.AtlasRegion> regions;

        blockBlue = atlas.findRegion("Block_Fire");
        blockPurr = atlas.findRegion("Block_Lightning");
        blockRed = atlas.findRegion("Block_Snow");
        blockYellow = atlas.findRegion("Block_Water");
        blockGreen = atlas.findRegion("Block_Water");

        //animation
        regions = atlas.findRegions("Block_Water");
        destroyWind =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();
    }

    @Override
    public float getWidth() {
        return (blockBlue.getRegionWidth() / GameCamera.PPM) * SCALE;
    }

    @Override
    public float getHeight() {
        return (blockBlue.getRegionWidth() / GameCamera.PPM) * SCALE;
    }

    public TextureRegion getBlockBlue() {
        return blockBlue;
    }

    public TextureRegion getBlockPurr() {
        return blockPurr;
    }

    public TextureRegion getBlockGreen() {
        return blockGreen;
    }

    public TextureRegion getBlockRed() {
        return blockRed;
    }

    public TextureRegion getBlockYellow() {
        return blockYellow;
    }

    public Animation getDestroyWind() {
        return destroyWind;
    }
}
