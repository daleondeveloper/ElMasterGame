package com.daleondeveloper.Assets.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AssetGame {
    private final static String TAG = AssetGame.class.getName();


    private TextureRegion button;
    private TextureRegion buttonExit;
    private TextureRegion buttonLeftMini;
    private TextureRegion buttonPause;
    private TextureRegion buttonRigthMini;
    private TextureRegion buttonSave;
    private TextureRegion buttonWodden;
    private TextureRegion gameFon;
    private TextureRegion paper;
    private TextureRegion gates;


    public AssetGame(TextureAtlas atlas, TextureAtlas newAtlas) {


        button = atlas.findRegion("button");
        buttonExit = atlas.findRegion("buttonExit");
        buttonLeftMini = atlas.findRegion("buttonLeftMini");
        buttonPause = atlas.findRegion("buttonPause");
        buttonRigthMini = atlas.findRegion("buttonRigthMini");
        buttonSave = atlas.findRegion("buttonSave");
        buttonWodden = atlas.findRegion("buttonWodden");
        gameFon = newAtlas.findRegion("gamefon");
        paper = atlas.findRegion("paper");
        gates = newAtlas.findRegion("open_gates");

    }


    public TextureRegion getButton() {
        return button;
    }

    public TextureRegion getButtonExit() {
        return buttonExit;
    }

    public TextureRegion getButtonLeftMini() {
        return buttonLeftMini;
    }

    public TextureRegion getButtonPause() {
        return buttonPause;
    }

    public TextureRegion getButtonRigthMini() {
        return buttonRigthMini;
    }

    public TextureRegion getButtonSave() {
        return buttonSave;
    }

    public TextureRegion getButtonWodden() {
        return buttonWodden;
    }

    public TextureRegion getGameFon() {
        return gameFon;
    }

    public TextureRegion getPaper() {
        return paper;
    }

    public TextureRegion getGates() {
        return gates;
    }
}