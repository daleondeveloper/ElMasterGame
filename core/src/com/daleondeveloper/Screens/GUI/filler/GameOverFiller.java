package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.Button.MainMenuTextButton;
import com.daleondeveloper.Screens.GUI.Button.RestartTextButton;
import com.daleondeveloper.Screens.GUI.Button.ReviveTextButton;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.tools.GameConstants;

public class GameOverFiller extends MenuFiller{
    private static final String TAG = GameOverFiller.class.getName();

    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;

    private int bestScore;
    private int playerScore;

    private Label gameOverLabel;
    private Label playerScoreLabel;
    private Label bestScoreLabel;

    public GameOverFiller(MenuScreen menuScreen){
        super(menuScreen,"title.gameOver");
        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();

    }


    @Override
    public void build() {
        super.build();
    }
    @Override
    protected void defineElements() {
        updateScore();
        bestScoreLabel = new Label("Best Score : " + bestScore, labelStyleNormal);
        playerScoreLabel = new Label("Score : " + playerScore, labelStyleNormal);
    }

    @Override
    protected void addAction() {
    }

    @Override
    protected void addToTable() {
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }

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
