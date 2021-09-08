package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.Ads.AdsShower;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.tools.Level.Level;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.Play.MainMenuScreen;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;
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

    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;

    private Image backButton;

    private ImageTextButton restartButton;
    private ImageTextButton mainMenuButton;
    private ImageTextButton continueBtn;

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
        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assets.getAssetFonts().getNormal();
        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assets.getAssetFonts().getSmall();

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
        gameOverLabel = new Label("GAME OVER", labelStyleMedium);
            bestScoreLabel = new Label("Best Score : " + bestScore, labelStyleMedium);
        playerScoreLabel = new Label("Score : " + playerScore,labelStyleMedium);

        TextureRegionDrawable textureRegion = new TextureRegionDrawable(assetGUI.getButtonForPauseWindow());

        restartButton = new ImageTextButton("Restart",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));
        continueBtn = new ImageTextButton("Revive (Ads)",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));
        mainMenuButton = new ImageTextButton("MainMenu",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));
        backButton  =new Image(new TextureRegionDrawable(assetGUI.getButtonX()));
    }

    @Override
    protected void addAction() {
        continueBtn.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                AdsShower.getInstance().showAds();
            }
        }));
        mainMenuButton.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                Level.savedLevel.delete();
                ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK);

            }
        }));

        restartButton.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                Level.savedLevel.delete();
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_BLACK);

            }
        }));
        backButton.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                if(menuScreen.getGuiAbstractScreen() instanceof MainMenuScreen){
                    menuScreen.hideMenuScreen();
                }else {
                    menuScreen.setPauseScreen();
                }
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
        Table labelTable = new Table();
        mainTable.add(labelTable).growX();
        labelTable.add().growX();
        labelTable.add(gameOverLabel);
        labelTable.add().growX();
        mainTable.row();

        Table scoreTable = new Table();
        mainTable.add(scoreTable).grow();
        if(bestScore < playerScore){
            bestScoreLabel = new Label("New Best Score : " + playerScore + " !!!!!!",labelStyleMedium);

        } else {
            scoreTable.add(playerScoreLabel);
            scoreTable.row();
        }

        scoreTable.defaults().pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();

        scoreTable.add(mainMenuButton);
        scoreTable.row();
        if(GameSettings.getInstance().getAdsContinueCount() > 0) {
            scoreTable.add(continueBtn);
            scoreTable.row();
        }
        scoreTable.add(restartButton);
        scoreTable.row();

    }
    private void showNewBestScore(){

    }
    private void gameOverScore(){

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
