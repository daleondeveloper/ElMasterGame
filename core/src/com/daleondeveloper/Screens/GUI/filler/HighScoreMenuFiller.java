package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.MenuScreen;

public class HighScoreMenuFiller extends MenuFiller  {
    private static final String TAG = SettingsMenuFiller.class.getName();

    private static final int LAST_SCORE_PAGE = 6;

    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;

    private int pageShow;

    private Label bestHighScoreLabel;
    private Label modeNameLabel;


    public HighScoreMenuFiller( MenuScreen menuScreen){
        super(menuScreen,"title.highScore");
        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        pageShow = 0;
    }


    @Override
    public void build() {
        super.build();

    }

    @Override
    protected void defineElements() {
        modeNameLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.classicMode"), labelStyleBig);
        bestHighScoreLabel = new Label(String.valueOf(prefs.getHighScoreClassic()), labelStyleBig);
    }

    @Override
    protected void addAction() {
    }

    @Override
    protected void addToTable() {
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }
        //Додавання очків до таблиці
        Table scoreTable = new Table();
        mainTable.add(scoreTable).grow();
        scoreTable.add(modeNameLabel);
        scoreTable.row();
        scoreTable.add(bestHighScoreLabel);
        mainTable.row();
    }

    private void changeScore() {
       modeNameLabel.setText("Classic");
       bestHighScoreLabel.setText(prefs.getHighScoreClassic());
       }
}
