package com.daleondeveloper.Assets.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetBackground implements IAssetSprite{
    private static final String TAG = AssetBlock.class.getName();

    private static final float SCALE = 0.7f;

    private TextureRegion background;
    private TextureRegion fogDark;
    private TextureRegion fogLeft;
    private TextureRegion fogCenter;
    private TextureRegion fogRight;

    public AssetBackground(TextureAtlas atlasBackground){
        background = atlasBackground.findRegion("background_fon");
        fogDark = atlasBackground.findRegion("background_fog_down");
        fogLeft = atlasBackground.findRegion("background_fog",1);
        fogCenter = atlasBackground.findRegion("background_fog",2);
        fogRight = atlasBackground.findRegion("background_fog",3);
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

    public TextureRegion getFogLeft() {
        return fogLeft;
    }

    public TextureRegion getFogCenter() {
        return fogCenter;
    }

    public TextureRegion getFogRight() {
        return fogRight;
    }
}
