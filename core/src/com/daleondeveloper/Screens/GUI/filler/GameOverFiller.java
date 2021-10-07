package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.Button.BackButton;
import com.daleondeveloper.Screens.GUI.Button.MainMenuTextButton;
import com.daleondeveloper.Screens.GUI.Button.RestartTextButton;
import com.daleondeveloper.Screens.GUI.Button.ReviveTextButton;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.tools.GameConstants;

public class GameOverFiller extends MenuFiller{
    private static final String TAG = GameOverFiller.class.getName();

    private MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private int bestScore;
    private int playerScore;

    private Label.LabelStyle labelStyleTitle;
    private Label.LabelStyle labelStyleNormal;

    private Label gameOverLabel;
    private Label playerScoreLabel;
    private Label bestScoreLabel;

    public GameOverFiller(MenuScreen menuScreen){
        this.menuScreen = menuScreen;
        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        // Styles
        labelStyleTitle = new Label.LabelStyle();
        labelStyleTitle.font = assets.getAssetFonts().getGameTitle();
        labelStyleNormal = new Label.LabelStyle();
        labelStyleNormal.font = assets.getAssetFonts().getNormal();

       // menuType = MenuScreen.MenuState.GAME_OVER;
    }


    @Override
    public void build() {
        mainTable = menuScreen.getWindowTable();
        super.build();
    }
    @Override
    protected void defineElements() {
        updateScore();
        gameOverLabel = new Label(i18NGameThreeBundle.format("title.gameOver"), labelStyleTitle);
            bestScoreLabel = new Label("Best Score : " + bestScore, labelStyleNormal);
        playerScoreLabel = new Label("Score : " + playerScore, labelStyleNormal);
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
        Table labelTable = new Table();
        mainTable.add(labelTable).growX();
        labelTable.add().growX();
        labelTable.add(gameOverLabel);
        labelTable.add().growX();
        mainTable.row();

        Table scoreTable = new Table();
        mainTable.add(scoreTable).grow();
        if(bestScore < playerScore){
            bestScoreLabel = new Label("New Best Score : " + playerScore + " !!!!!!", labelStyleTitle);

        } else {
            scoreTable.add(playerScoreLabel);
            scoreTable.row();
        }

        scoreTable.defaults().pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();

        scoreTable.add(new MainMenuTextButton());
        scoreTable.row();
        if(GameSettings.getInstance().getAdsContinueCount() > 0) {
            scoreTable.add(new ReviveTextButton());
            scoreTable.row();
        }
        scoreTable.add(new RestartTextButton());
        scoreTable.row();

    }
    private void updateScore(){
        playerScore = prefs.getLastPlayScore();
                bestScore = prefs.getHighScoreClassic();
        if(bestScore < playerScore){
            bestScore = playerScore;
            prefs.saveNewBestScore(bestScore);
        }
        prefs.setLastPlayScore(0);
    }
}
