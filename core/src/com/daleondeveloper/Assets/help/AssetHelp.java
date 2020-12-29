package com.daleondeveloper.Assets.help;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetHelp {
    private static final String TAG = AssetHelp.class.getName();

    private TextureRegion help_block_push;
    private TextureRegion help_block_fall;
    private TextureRegion help_create_block_line;

    public AssetHelp(TextureAtlas atlas){

        help_block_fall = atlas.findRegion("help_block_fall");
        help_block_push = atlas.findRegion("help_block_push");
        help_create_block_line = atlas.findRegion("help_full_block_line");

    }

    public TextureRegion getHelp_block_push() {
        return help_block_push;
    }

    public TextureRegion getHelp_block_fall() {
        return help_block_fall;
    }

    public TextureRegion getHelp_create_block_line() {
        return help_create_block_line;
    }
}
