package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.fonts.AssetFonts;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.GameCamera;
import com.daleondeveloper.Game.GameSettings;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;
import com.daleondeveloper.tools.AudioManager;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;

/**
 * Created by AGMCORP on 10/14/2018.
 */

public class InfoScreen extends GUIOverlayAbstractScreen {
    private static final String TAG = InfoScreen.class.getName();

    private static final float BUTTON_WIDTH = 180.0f;
    private static final int MAX_TITLE_KEYS = 2;


    private Image background;
    private PlayScreen playScreen;
    private GameSettings prefs;
    private Assets assets;
    private I18NBundle i18NGameThreeBundle;
    private AssetGUI assetGUI;
    private Array<String> titleKeys;

    private Label.LabelStyle labelStyleBig;
    private Label.LabelStyle labelStyleNormal;
    private Label.LabelStyle labelStyleSmall;

    private Label gameOverLabel;
    private Label scoreLabel;
    private Label highScoreLabel;
    private Label restartLabel;
    private Label mainMenuLabel;

    private Image start;
    private Image pause;
    private Image menuWindow;
    private Image buttonRestart;
    private Image buttonMainMenu;


    public InfoScreen(ElMaster game, PlayScreen playScreen) {
        super(game);

        this.playScreen = playScreen;
        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        assetGUI = assets.getAssetGUI();
        titleKeys = new Array<String>();
        for(int i = 0; i < MAX_TITLE_KEYS; i++) {
            titleKeys.add(i18NGameThreeBundle.format("infoScreen.title" + i));
        }

        // Styles
        AssetFonts assetFonts = assets.getAssetFonts();
        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = assetFonts.getBig();

        labelStyleNormal = new Label.LabelStyle();
        labelStyleNormal.font = assetFonts.getNormal();

        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assetFonts.getSmall();
    }

    @Override
    public void build() {

        // Pause button
        pause = new Image(assetGUI.getButtonPause());
        pause.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                GameSettings.getInstance().save();
                playScreen.setStatePaused();

            }
        }));
        //PreStartImage
        start = new Image(assetGUI.getButtonStart());

        //GameOver Image
        menuWindow = new Image(assetGUI.getPauseWindow());
        buttonMainMenu = new Image(assetGUI.getButtonForPauseWindow());
        buttonRestart = new Image(assetGUI.getButtonForPauseWindow());
        restartLabel = new Label(i18NGameThreeBundle.format("pauseScreen.restart"), labelStyleSmall);
        mainMenuLabel = new Label(i18NGameThreeBundle.format("pauseScreen.mainMenu"), labelStyleSmall);
        gameOverLabel = new Label("GAME OVER", labelStyleNormal);
        scoreLabel = new Label("SCORE", labelStyleSmall);
        highScoreLabel = new Label("HIGH_SCORE", labelStyleSmall);

        defineButtons();

        stage.addActor(pause);
        stage.addActor(start);


        Gdx.input.setInputProcessor(stage);
    }
    private void defineButtons(){
        buttonMainMenu.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK));
        mainMenuLabel.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK));

        buttonRestart.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                GameSettings.getInstance().deleteSave();
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_BLACK);

            }
        }));
        restartLabel.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                GameSettings.getInstance().deleteSave();
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_BLACK);

            }
        }));
        start.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                playScreen.setStateRunning();
                start.setVisible(false);
            }
        }));
    }

    @Override
    public void update(float deltaTime) { stage.act();}

    @Override
    public void render() {
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float w = stage.getWidth() ;
        float h = stage.getHeight();

        pause.setWidth(64);
        pause.setHeight(64);
        pause.setPosition(w - pause.getWidth() , h - pause.getHeight());

        start.setWidth(96);
        start.setHeight(96);
        start.setPosition((w - start.getWidth()) / 2,h / 2);

        menuWindow.setWidth(400);
        menuWindow.setHeight(342);
        menuWindow.setX((w - menuWindow.getWidth()) / 2);
        menuWindow.setY((h - menuWindow.getHeight()) / 2);

        buttonMainMenu.setWidth(menuWindow.getWidth() * 0.6f);
        buttonMainMenu.setHeight(menuWindow.getHeight() / 8);
        buttonMainMenu.setPosition((w - buttonMainMenu.getWidth()) / 2,
                menuWindow.getY() + buttonMainMenu.getHeight() * 2.5f);
        mainMenuLabel.setPosition(buttonMainMenu.getX() + buttonMainMenu.getWidth()/2 - mainMenuLabel.getPrefWidth() / 2 ,
                buttonMainMenu.getY()  + buttonMainMenu.getHeight() * 0.6f - mainMenuLabel.getPrefHeight() / 2);


        buttonRestart.setWidth(menuWindow.getWidth() * 0.6f);
        buttonRestart.setHeight(menuWindow.getHeight() / 8);
        buttonRestart.setPosition((w - buttonRestart.getWidth()) / 2, buttonMainMenu.getY() - buttonRestart.getHeight() * 1.2f);
        restartLabel.setPosition(buttonRestart.getX() + buttonRestart.getWidth()/2 - restartLabel.getPrefWidth() / 2 ,
                buttonRestart.getY()  + buttonRestart.getHeight() * 0.6f - restartLabel.getPrefHeight() / 2);

        gameOverLabel.setPosition(menuWindow.getX() + menuWindow.getWidth() / 2 - gameOverLabel.getPrefWidth() / 2,
                menuWindow.getY() + menuWindow.getHeight() - 5 - gameOverLabel.getPrefHeight());
        scoreLabel.setPosition(menuWindow.getX() + menuWindow.getWidth() / 2  - scoreLabel.getPrefWidth() / 2,
                gameOverLabel.getY() - scoreLabel.getPrefHeight() - 5);
        highScoreLabel.setPosition(menuWindow.getX() + menuWindow.getWidth() / 2  - highScoreLabel.getPrefWidth() / 2,
                scoreLabel.getY() - highScoreLabel.getPrefHeight() - 5);


    }

    public void showGameOver() {
        Hud hud = playScreen.getHud();
        int currentScore = hud.getScore();
        int bestScore = prefs.getHighScore();
        if (currentScore > bestScore) {
            bestScore = currentScore;
            prefs.setHighScore(bestScore);

            // Leaderboards
            //   playServices.submitScore(bestScore);

        }
        prefs.setLastPlayScore(0);
        prefs.save();

        if (hud.isScoreAboveAverage()) {
            gameOverLabel.setText(titleKeys.get(MathUtils.random(0, titleKeys.size - 1)));
        } else {
            gameOverLabel.setText(i18NGameThreeBundle.format("infoScreen.titleDefault", currentScore));
        }
        scoreLabel.setText(i18NGameThreeBundle.format("infoScreen.score", currentScore));
        highScoreLabel.setText(i18NGameThreeBundle.format("infoScreen.highScore", bestScore));

        pause.setVisible(false);
        hud.setVisible(false);
//        startStageAnimation(false, new Runnable() {
//            @Override
//            public void run() {
//                // Only InfoScreen responds to events
//            }
//        });
        Gdx.input.setInputProcessor(stage);


        gameOverLabel.setPosition(menuWindow.getX() + menuWindow.getWidth() / 2 - gameOverLabel.getPrefWidth() / 2,
                menuWindow.getY() + menuWindow.getHeight() - 5 - gameOverLabel.getPrefHeight());
        scoreLabel.setPosition(menuWindow.getX() + menuWindow.getWidth() / 2  - scoreLabel.getPrefWidth() / 2,
                gameOverLabel.getY() - scoreLabel.getPrefHeight() - 5);
        highScoreLabel.setPosition(menuWindow.getX() + menuWindow.getWidth() / 2  - highScoreLabel.getPrefWidth() / 2,
                scoreLabel.getY() - highScoreLabel.getPrefHeight() - 5);

        stage.addActor(menuWindow);
        stage.addActor(buttonMainMenu);
        stage.addActor(buttonRestart);
        stage.addActor(restartLabel);
        stage.addActor(mainMenuLabel);
        stage.addActor(gameOverLabel);
        stage.addActor(scoreLabel);
        stage.addActor(highScoreLabel);
        playScreen.doPause();
        prefs.deleteSave();

    }

}
