package com.daleondeveloper.Assets.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetBackground implements IAssetSprite{
    private static final String TAG = AssetBlock.class.getName();

    private static final float SCALE = 0.7f;

    private TextureRegion background;
    private TextureRegion fogDark;
    private TextureRegion fog;

    public AssetBackground(TextureAtlas atlasBackground){
        background = atlasBackground.findRegion("background_fon");
        fogDark = atlasBackground.findRegion("background_fog_down");
        fog = atlasBackground.findRegion("background_fog",1);
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    public TextureRegion getBackground() {
        return background;
    }

    public TextureRegion getFogDark() {
        return fogDark;
    }

    public TextureRegion getFog() {
        return fog;
    }
}
