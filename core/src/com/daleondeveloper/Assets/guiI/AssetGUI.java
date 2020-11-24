package com.daleondeveloper.Assets.guiI;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import javax.xml.soap.Text;

public class AssetGUI {
    private static final String TAG = AssetGUI.class.getName();

    private TextureRegion buttonStart;
    private TextureRegion buttonHelp;
    private TextureRegion buttonHighScore;
    private TextureRegion buttonSettings;

    private TextureRegion backgroundGates;



    public AssetGUI(TextureAtlas atlasGUI){
        Array<TextureAtlas.AtlasRegion> regions;

        buttonStart = atlasGUI.findRegion("button_start");
        buttonSettings = atlasGUI.findRegion("button_settings");
        buttonHighScore = atlasGUI.findRegion("button_highScore");
        buttonHelp = atlasGUI.findRegion("button_help");
        backgroundGates = atlasGUI.findRegion("backgroundGates");


    }

    public TextureRegion getButtonStart() {
        return buttonStart;
    }

    public TextureRegion getButtonHelp() {
        return buttonHelp;
    }

    public TextureRegion getButtonHighScore() {
        return buttonHighScore;
    }

    public TextureRegion getButtonSettings() {
        return buttonSettings;
    }

    public TextureRegion getBackgroundGates() {
        return backgroundGates;
    }
}
