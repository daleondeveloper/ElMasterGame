package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.Button.BackButton;
import com.daleondeveloper.Screens.GUI.MenuScreen;

public class HighScoreMenuFiller extends MenuFiller  {
    private static final String TAG = SettingsMenuFiller.class.getName();

    private static final int LAST_SCORE_PAGE = 6;


    private com.daleondeveloper.Screens.GUI.MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private Label.LabelStyle labelStyleBig;
    private Label.LabelStyle labelStyleMedium;

    private int pageShow;

    private Label highScoreLabel;
    private Label bestHighScoreLabel;
    private Label modeNameLabel;


    public HighScoreMenuFiller( MenuScreen menuScreen){
        this.menuScreen = menuScreen;
        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        // Styles
        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = assets.getAssetFonts().getBig();
        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assets.getAssetFonts().getNormal();


        pageShow = 0;
    }


    @Override
    public void build() {
        mainTable = menuScreen.getWindowTable();
        super.build();

    }

    @Override
    protected void defineElements() {
        highScoreLabel = new Label(i18NGameThreeBundle.format("highScoreScreen.title"), labelStyleMedium);
        modeNameLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.classicMode"), labelStyleMedium);
        bestHighScoreLabel = new Label(String.valueOf(prefs.getHighScoreClassic()), labelStyleBig);
    }

    @Override
    protected void addAction() {
    }

    @Override
    protected void addToTable() {
        mainTable.clearChildren();
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }
        mainTable.top();
        mainTable.add(new BackButton(menuScreen)).height(15).width(15).right().padRight(30);
        mainTable.row();
        // Додавання назви меню до таблиці
        Table labelTable = new Table();
        mainTable.add(labelTable).growX();
        labelTable.add().growX();
        labelTable.add(highScoreLabel);
        labelTable.add().growX();
        mainTable.row();
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
