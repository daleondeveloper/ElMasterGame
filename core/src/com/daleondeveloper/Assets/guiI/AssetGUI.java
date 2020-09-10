package com.daleondeveloper.Assets.guiI;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;

public class AssetGUI {
    private static final String TAG = AssetGUI.class.getName();

    private TextureRegion buttonExit;
    private TextureRegion buttonHelp;
    private TextureRegion buttonHighScore;
    private TextureRegion buttonLeft;
    private TextureRegion buttonLeftMini;
    private TextureRegion buttonMusicOff;
    private TextureRegion buttonMusicOn;
    private TextureRegion buttonOnline;
    private TextureRegion buttonPause;
    private TextureRegion buttonRightMini;
    private TextureRegion buttonRight;
    private TextureRegion buttonSave;
    private TextureRegion buttonSettings;
    private TextureRegion buttonSinglePlayer;
    private TextureRegion buttonSoundOff;
    private TextureRegion buttonSoundOn;
    private TextureRegion buttonStory;
    private TextureRegion buttonWodden;
    private TextureRegion logo;
    private TextureRegion mainFon;
    private TextureRegion paper;
    private Animation elementOfWaterStand;
    private Animation fog;


    public AssetGUI(TextureAtlas atlasGUI){
        Array<TextureAtlas.AtlasRegion> regions;

        buttonExit = atlasGUI.findRegion("buttonExit");
        buttonHelp = atlasGUI.findRegion("buttonHelp");
        buttonHighScore = atlasGUI.findRegion("buttonHighScore");
        buttonLeft = atlasGUI.findRegion("buttonLeft");
        buttonLeftMini = atlasGUI.findRegion("buttonLeftMini");
        buttonMusicOff = atlasGUI.findRegion("buttonMusicOff");
        buttonMusicOn = atlasGUI.findRegion("buttonMusicOn");
        buttonOnline = atlasGUI.findRegion("buttonOnline");
        buttonPause = atlasGUI.findRegion("buttonPause");
        buttonRight = atlasGUI.findRegion("buttonRight");
        buttonRightMini = atlasGUI.findRegion("buttonRightMini");
        buttonSave = atlasGUI.findRegion("buttonSave");
        buttonSettings = atlasGUI.findRegion("buttonSettings");
        buttonSinglePlayer = atlasGUI.findRegion("buttonSinglePlayer");
        buttonSoundOff = atlasGUI.findRegion("buttonSoundOff");
        buttonSoundOn = atlasGUI.findRegion("buttonSoundOn");
        buttonStory = atlasGUI.findRegion("buttonStory");
        buttonWodden = atlasGUI.findRegion("buttonWodden");
        mainFon = atlasGUI.findRegion("mainFon");
        logo = atlasGUI.findRegion("logo");
        paper = atlasGUI.findRegion("paper");


        //animation
        regions = atlasGUI.findRegions("elementOfWaterStand");
        elementOfWaterStand =new Animation(0.5f/24.0f,regions, Animation.PlayMode.LOOP);
        regions.clear();

        regions = atlasGUI.findRegions("fog");
        fog = new Animation(0.5f/24/0f, Animation.PlayMode.LOOP);
        regions.clear();


    }

    public TextureRegion getButtonExit() {
        return buttonExit;
    }

    public TextureRegion getButtonHelp() {
        return buttonHelp;
    }

    public TextureRegion getButtonHighScore() {
        return buttonHighScore;
    }

    public TextureRegion getButtonLeft() {
        return buttonLeft;
    }

    public TextureRegion getButtonLeftMini() {
        return buttonLeftMini;
    }

    public TextureRegion getButtonMusicOff() {
        return buttonMusicOff;
    }

    public TextureRegion getButtonMusicOn() {
        return buttonMusicOn;
    }

    public TextureRegion getButtonOnline() {
        return buttonOnline;
    }

    public TextureRegion getButtonPause() {
        return buttonPause;
    }

    public TextureRegion getButtonRightMini() {
        return buttonRightMini;
    }

    public TextureRegion getButtonRight() {
        return buttonRight;
    }

    public TextureRegion getButtonSave() {
        return buttonSave;
    }

    public TextureRegion getButtonSettings() {
        return buttonSettings;
    }

    public TextureRegion getButtonSinglePlayer() {
        return buttonSinglePlayer;
    }

    public TextureRegion getButtonSoundOff() {
        return buttonSoundOff;
    }

    public TextureRegion getButtonSoundOn() {
        return buttonSoundOn;
    }

    public TextureRegion getButtonStory() {
        return buttonStory;
    }

    public TextureRegion getButtonWodden() {
        return buttonWodden;
    }

    public TextureRegion getLogo() {
        return logo;
    }

    public TextureRegion getMainFon() {
        return mainFon;
    }

    public TextureRegion getPaper() {
        return paper;
    }

    public Animation getElementOfWaterStand() {
        return elementOfWaterStand;
    }

    public Animation getFog() {
        return fog;
    }
}
