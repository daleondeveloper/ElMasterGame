package com.daleondeveloper.Assets.help;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetHelp {
    private static final String TAG = AssetHelp.class.getName();

    private TextureRegion gameMode1_1;
    private TextureRegion gameMode1_2;
    private TextureRegion gameMode1_3;
    private TextureRegion gameMode2_1;
    private TextureRegion gameMode2_2;
    private TextureRegion gameMode2_3;
    private TextureRegion gameMode3_1;
    private TextureRegion gameMode3_2;
    private TextureRegion gameMode3_3;
    private TextureRegion help_1;
    private TextureRegion help_2;

    public AssetHelp(TextureAtlas atlas){

        gameMode1_1 = atlas.findRegion("gameMode1_1");
        gameMode1_2 = atlas.findRegion("gameMode1_2");
        gameMode1_3 = atlas.findRegion("gameMode1_3");
        gameMode2_1 = atlas.findRegion("gameMode2_1");
        gameMode2_2 = atlas.findRegion("gameMode2_2");
        gameMode2_3 = atlas.findRegion("gameMode2_3");
        gameMode3_1 = atlas.findRegion("gameMode3_1");
        gameMode3_2 = atlas.findRegion("gameMode3_2");
        gameMode3_3 = atlas.findRegion("gameMode3_3");
        help_1 = atlas.findRegion("help_1");
        help_2 = atlas.findRegion("help_2");
    }

    public TextureRegion getGameMode1_1() {
        return gameMode1_1;
    }

    public TextureRegion getGameMode1_2() {
        return gameMode1_2;
    }

    public TextureRegion getGameMode1_3() {
        return gameMode1_3;
    }

    public TextureRegion getGameMode2_1() {
        return gameMode2_1;
    }

    public TextureRegion getGameMode2_2() {
        return gameMode2_2;
    }

    public TextureRegion getGameMode2_3() {
        return gameMode2_3;
    }

    public TextureRegion getGameMode3_1() {
        return gameMode3_1;
    }

    public TextureRegion getGameMode3_2() {
        return gameMode3_2;
    }

    public TextureRegion getGameMode3_3() {
        return gameMode3_3;
    }

    public TextureRegion getHelp_1() {
        return help_1;
    }

    public TextureRegion getHelp_2() {
        return help_2;
    }
}
