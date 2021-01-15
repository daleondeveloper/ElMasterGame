package com.daleondeveloper.Assets.help;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetHelp {
    private static final String TAG = AssetHelp.class.getName();

    private TextureRegion help_block_push;
    private TextureRegion help_block_fall;
    private TextureRegion help_create_block_line;
    private TextureRegion help_fire_mode;
    private TextureRegion help_light_mode;
    private TextureRegion help_snow_mode;
    private TextureRegion help_dark_mode;
    private TextureRegion help_special_mode;

    public AssetHelp(TextureAtlas atlas){

        help_block_fall = atlas.findRegion("help_block_fall");
        help_block_push = atlas.findRegion("help_block_push");
        help_create_block_line = atlas.findRegion("help_full_block_line");
        help_fire_mode = atlas.findRegion("fire_mode_help_screen");
        help_light_mode = atlas.findRegion("light_mode_help_screen");
        help_snow_mode = atlas.findRegion("snow_mode_help_screen");
        help_dark_mode = atlas.findRegion("dark_mode_help_screen");
        help_special_mode = atlas.findRegion("special_mode_help_screen");

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

    public TextureRegion getHelp_fire_mode() {
        return help_fire_mode;
    }

    public TextureRegion getHelp_light_mode() {
        return help_light_mode;
    }

    public TextureRegion getHelp_snow_mode() {
        return help_snow_mode;
    }

    public TextureRegion getHelp_dark_mode() {
        return help_dark_mode;
    }

    public TextureRegion getHelp_special_mode() {
        return help_special_mode;
    }
}
