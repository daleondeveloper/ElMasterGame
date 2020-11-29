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
    private TextureRegion buttonPause;
    private TextureRegion pauseButtonSettings;
    private TextureRegion pauseButtonResume;
    private TextureRegion pauseButtonMainMenu;
    private TextureRegion pauseButtonRestart;
    private TextureRegion pauseWindow;
    private TextureRegion gameButtonLeft;
    private TextureRegion gameButtonRight;
    private TextureRegion gameButtonJump;
    private TextureRegion gameButtonPush;
    private TextureRegion gameWindow;
    private TextureRegion background;

    private TextureRegion backgroundGates;



    public AssetGUI(TextureAtlas atlasGUI){
        Array<TextureAtlas.AtlasRegion> regions;

        background = atlasGUI.findRegion("background");
        buttonStart = atlasGUI.findRegion("button_start");
        buttonSettings = atlasGUI.findRegion("button_settings");
        buttonHighScore = atlasGUI.findRegion("button_highScore");
        buttonHelp = atlasGUI.findRegion("button_help");
        backgroundGates = atlasGUI.findRegion("backgroundGates");
        buttonPause = atlasGUI.findRegion("pauseButton");
        pauseButtonSettings = atlasGUI.findRegion("settingsButton");
        pauseButtonMainMenu = atlasGUI.findRegion("mainMenuButton");
        pauseButtonResume = atlasGUI.findRegion("resumeButton");
        pauseButtonRestart = atlasGUI.findRegion("restartButton");
        pauseWindow = atlasGUI.findRegion("pauseWindow");
        gameButtonJump = atlasGUI.findRegion("jumpButton");
        gameButtonPush = atlasGUI.findRegion("pushButton");
        gameButtonLeft = atlasGUI.findRegion("leftButton");
        gameButtonRight = atlasGUI.findRegion("rughtButton");
        gameWindow = atlasGUI.findRegion("buttonPanel");


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

    public TextureRegion getButtonPause() {
        return buttonPause;
    }

    public TextureRegion getPauseButtonSettings() {
        return pauseButtonSettings;
    }

    public TextureRegion getPauseButtonResume() {
        return pauseButtonResume;
    }

    public TextureRegion getPauseButtonMainMenu() {
        return pauseButtonMainMenu;
    }

    public TextureRegion getPauseButtonRestart() {
        return pauseButtonRestart;
    }

    public TextureRegion getPauseWindow() {
        return pauseWindow;
    }

    public TextureRegion getGameButtonLeft() {
        return gameButtonLeft;
    }

    public TextureRegion getGameButtonRight() {
        return gameButtonRight;
    }

    public TextureRegion getGameButtonJump() {
        return gameButtonJump;
    }

    public TextureRegion getGameButtonPush() {
        return gameButtonPush;
    }

    public TextureRegion getGameWindow() {
        return gameWindow;
    }

    public TextureRegion getBackgroundGates() {
        return backgroundGates;
    }

    public TextureRegion getBackground() {
        return background;
    }
}
