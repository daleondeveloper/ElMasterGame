package com.daleondeveloper.Assets.guiI;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AssetGUI {
    private static final String TAG = AssetGUI.class.getName();

    //private TextureRegion buttonStart;

    private TextureRegion buttonHelp;
    private TextureRegion buttonHighScore;
    private TextureRegion buttonSettings;
    private TextureRegion buttonPause;
    private TextureRegion buttonStart;
    private TextureRegion buttonLeft;
    private TextureRegion buttonRight;
    private TextureRegion buttonJump;
    private TextureRegion buttonPush;
    private TextureRegion buttonX;
    private TextureRegion gameWindow;
    private TextureRegion pauseWindow;
    private TextureRegion buttonForPauseWindow;

    public AssetGUI(TextureAtlas atlasGUI){
        Array<TextureAtlas.AtlasRegion> regions;

        buttonHelp = atlasGUI.findRegion("button/button_help");
        buttonHighScore = atlasGUI.findRegion("button/button_highscore");
        buttonSettings = atlasGUI.findRegion("button/button_settings");
        buttonPause = atlasGUI.findRegion("button/button_pause");
        buttonStart = atlasGUI.findRegion("button/button_start");
        buttonLeft = atlasGUI.findRegion("button/button_left");
        buttonRight = atlasGUI.findRegion("button/button_right");
        buttonJump = atlasGUI.findRegion("button/button_jump");
        buttonPush = atlasGUI.findRegion("button/button_push");
        buttonX = atlasGUI.findRegion("button/button_X");
        gameWindow = atlasGUI.findRegion("panel/game_panel");
        pauseWindow = atlasGUI.findRegion("panel/menu_panel");
        buttonForPauseWindow = atlasGUI.findRegion("panel/button_panel");

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

    public TextureRegion getButtonPause() {
        return buttonPause;
    }

    public TextureRegion getButtonStart() {
        return buttonStart;
    }

    public TextureRegion getButtonLeft() {
        return buttonLeft;
    }

    public TextureRegion getButtonRight() {
        return buttonRight;
    }

    public TextureRegion getButtonJump() {
        return buttonJump;
    }

    public TextureRegion getButtonPush() {
        return buttonPush;
    }

    public TextureRegion getButtonX() {
        return buttonX;
    }

    public TextureRegion getGameWindow() {
        return gameWindow;
    }

    public TextureRegion getPauseWindow() {
        return pauseWindow;
    }

    public TextureRegion getButtonForPauseWindow() {
        return buttonForPauseWindow;
    }
}
