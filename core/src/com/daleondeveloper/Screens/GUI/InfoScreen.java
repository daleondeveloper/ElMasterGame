package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.fonts.AssetFonts;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.GameSettings;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenTransitionEnum;
import com.daleondeveloper.tools.AudioManager;

/**
 * Created by AGMCORP on 10/14/2018.
 */

public class InfoScreen extends GUIOverlayAbstractScreen {
    private static final String TAG = InfoScreen.class.getName();

    private static final float BUTTON_WIDTH = 180.0f;
    private static final int MAX_TITLE_KEYS = 15;


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
    private Table gameOverTable;
    private Table helpTable;
    private ImageButton pause;
    private Label gameOverLabel;
    private Label scoreLabel;
    private Label highScoreLabel;

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
        gameOverTable = getGameOverTable();
        //helpTable = getHelpTable();

        Stack stack = new Stack();
        stack.add(gameOverTable);
        //stack.add(helpTable);

        Table mainTable = new Table();
        mainTable.setDebug(DebugConstants.DEBUG_LINES);
        mainTable.center();
        mainTable.setFillParent(true);
        mainTable.add(stack);
        stage.addActor(mainTable);

        // Pause button
        pause = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonHelp()),
                new TextureRegionDrawable(assetGUI.getButtonHelp()));
        pause.setPosition(0, stage.getHeight() - pause.getHeight());
        pause.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                playScreen.setGameStatePaused();

            }
        }));
        stage.addActor(pause);


    }

    private Table getGameOverTable() {
        gameOverLabel = new Label("GAME OVER", labelStyleBig);
        scoreLabel = new Label("SCORE", labelStyleNormal);
        highScoreLabel = new Label("HIGH_SCORE", labelStyleSmall);

        Table table = new Table();
        table.setDebug(DebugConstants.DEBUG_LINES);
        table.center();
        table.add(gameOverLabel).row();
        table.add(getGameOverButtonsTable()).row();
        table.add(scoreLabel).row();
        table.add(highScoreLabel);
        table.setVisible(false);
        return table;
    }

    private Table getGameOverButtonsTable() {
        ImageButton reload = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonStart()),
                new TextureRegionDrawable(assetGUI.getButtonHelp()));

        ImageButton home = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonStart()),
                new TextureRegionDrawable(assetGUI.getButtonHelp()));

        reload.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_WHITE));
        home.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.SLIDE_DOWN));

        Table table = new Table();
        table.setDebug(DebugConstants.DEBUG_LINES);
        table.center();
        table.add(reload).width(BUTTON_WIDTH);
        table.add(home).width(BUTTON_WIDTH);
        return table;
    }

    private Table getHelpTable() {
        Table table = new Table();
        table.setDebug(DebugConstants.DEBUG_LINES);
        table.center();
        table.add(new Image(assetGUI.getButtonHelp())).row();
        table.add(getHelpButtonsTable());
        table.setVisible(false);
        return table;
    }

    private Table getHelpButtonsTable() {
        ImageButton gotIt = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonHelp()),
                new TextureRegionDrawable(assetGUI.getButtonHelp()));
        gotIt.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                // Enable input for PlayScreen
//                Gdx.input.setInputProcessor(playScreen.getInputProcessor());
                startStageAnimation(true, new Runnable() {
                    @Override
                    public void run() {
                        pause.setVisible(true);
                        helpTable.setVisible(false);
                        stage.getRoot().setY(0);
                        playScreen.getHud().setVisible(true);
                    }
                });
            }
        }));

        Table table = new Table();
        table.setDebug(DebugConstants.DEBUG_LINES);
        table.center();
        table.add(gotIt).width(BUTTON_WIDTH);
        return table;
    }

    @Override
    public void update(float deltaTime) {
        stage.act();
    }

    @Override
    public void render() {
        stage.draw();
    }

    public void showGameOver() {
        Hud hud = playScreen.getHud();
        int currentScore = hud.getScore();
        int bestScore = prefs.getHighScore();
        if (currentScore > bestScore) {
            bestScore = currentScore;
            prefs.setHighScore(bestScore);
            prefs.save();

            // Leaderboards
         //   playServices.submitScore(bestScore);

            // Audio effect
            AudioManager.getInstance().playSound(assets.getAssetSounds().getNewAchievement());
        }

        if (hud.isScoreAboveAverage()) {
            gameOverLabel.setText(titleKeys.get(MathUtils.random(0, titleKeys.size - 1)));
        } else {
            gameOverLabel.setText(i18NGameThreeBundle.format("infoScreen.titleDefault", currentScore));
        }
        scoreLabel.setText(i18NGameThreeBundle.format("infoScreen.score", currentScore));
        highScoreLabel.setText(i18NGameThreeBundle.format("infoScreen.highScore", bestScore));

        gameOverTable.setVisible(true);
        helpTable.setVisible(false);
        pause.setVisible(false);
        hud.setVisible(false);
        startStageAnimation(false, new Runnable() {
            @Override
            public void run() {
                // Only InfoScreen responds to events
                Gdx.input.setInputProcessor(stage);
            }
        });
    }

    public void showHelp() {
        gameOverTable.setVisible(false);
      //  helpTable.setVisible(true);
        pause.setVisible(false);
        playScreen.getHud().setVisible(false);
        startStageAnimation(false, new Runnable() {
            @Override
            public void run() {
                // Only InfoScreen responds to events
                Gdx.input.setInputProcessor(stage);
            }
        });
    }
}
