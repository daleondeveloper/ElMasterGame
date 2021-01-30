package com.daleondeveloper.Screens.GUI.Menu;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.ListenerHelper;

public class HighScoreMenuFiller extends MenuFiller  {
    private static final String TAG = SettingsMenuFiller.class.getName();

    private static final int LAST_SCORE_PAGE = 6;


    private MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private Label.LabelStyle labelStyleBig;
    private Label.LabelStyle labelStyleMedium;

    private int pageShow;

    private Image menuWindow;
    private Image backButton;
    private Image nextScoreImage;
    private Image previsionScoreImage;

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
        backButton  =new Image(new TextureRegionDrawable(assetGUI.getButtonX()));
        nextScoreImage = new Image(assetGUI.getButtonLeft());
        previsionScoreImage = new Image(assetGUI.getButtonRight());
    }

    @Override
    protected void addAction() {
        backButton.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.hideMenuScreen();
            }
        }));


        nextScoreImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                if(pageShow < LAST_SCORE_PAGE){
                    pageShow++;
                }else{
                    pageShow = 0;
                }
                changeScore();
            }
        }));

        previsionScoreImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                if(pageShow > 0){
                    pageShow--;
                }else{
                    pageShow = LAST_SCORE_PAGE;
                }
                changeScore();
            }
        }));
    }

    @Override
    protected void addToTable() {
        mainTable.clearChildren();
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }
        mainTable.top();
        mainTable.add(backButton).height(15).width(15).right().padRight(30);
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
        //Додавання кнопок переміщення знизу
        Table moveArrowTable = new Table();
        mainTable.add(moveArrowTable).padBottom(30).padRight(50).padLeft(50).growX();
        moveArrowTable.add(previsionScoreImage).width(50).height(58).left();
        moveArrowTable.add().growX();
        moveArrowTable.add(nextScoreImage).width(50).height(58).right();
    }

    private void changeScore() {
        switch (pageShow){
            case 0:
                modeNameLabel.setText("Classic");
                bestHighScoreLabel.setText(prefs.getHighScoreClassic());
                break;
            case 1:
                modeNameLabel.setText("Light");
                bestHighScoreLabel.setText(prefs.getHighScoreLight());
                break;
            case 2:
                modeNameLabel.setText("Snow");
                bestHighScoreLabel.setText(prefs.getHighScoreSnow());
                break;
            case 3:
                modeNameLabel.setText("Fire");
                bestHighScoreLabel.setText(prefs.getHighScoreFire());
                break;
            case 4:
                modeNameLabel.setText("Water");
                bestHighScoreLabel.setText(prefs.getHighScoreWater());
                break;
            case 5:
                modeNameLabel.setText("Dark");
                bestHighScoreLabel.setText(prefs.getHighScoreDark());
                break;
            case 6:
                modeNameLabel.setText("Special");
                bestHighScoreLabel.setText(prefs.getHighScoreSpecial());
                break;
            default:
                modeNameLabel.setText("Mode");
                bestHighScoreLabel.setText(prefs.getHighScoreClassic());
        }

    }
}
