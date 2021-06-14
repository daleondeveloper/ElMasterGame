package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.fonts.AssetFonts;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.Play.PlayScreen;

//Екран відповідає за відображення панелі упарвління персонажем
//Покажчиками набраних очків і фпс
public class Hud extends GUIOverlayAbstractScreen {
    private static final String TAG = Hud.class.getName();

    private static final float PAD_BOTTOM = 800.0f;
    private static final int AVERAGE_SCORE = 8;

    private PlayScreen playScreen;
    private I18NBundle i18NGameThreeBundle;
    private AssetGUI assetGUI;
    private int score;
    private Label scoreLabel;
    private int fps;
    private Label.LabelStyle labelStyleBig;
    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;
    private Label fpsLabel;
    private Container containerPerfectJump;
    private float stateTime;

    private Table mainTable;
    private Table topTable;
    private Table centerTable;
    private Table bottomTable;

    private ImageButton pauseButton;

    private ImageButton startButton;

    private TextureRegionDrawable playerWindowDrawable;
    private Image gameButtonLeft;
    private Image gameButtonRight;
    private Image gameButtonPush;
    private Image gameButtonJump;


    public Hud(ElMaster game, PlayScreen playScreen) {
        super(game);

        this.playScreen = playScreen;
        i18NGameThreeBundle = Assets.getInstance().getI18NElementMaster().getI18NElmasterBundle();
        score = GameSettings.getInstance().getLastPlayScore();
        fps = 0;
        stateTime = 0;

        assetGUI = Assets.getInstance().getAssetGUI();

        // Styles
        AssetFonts assetFonts = Assets.getInstance().getAssetFonts();
        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = assetFonts.getBig();

        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assetFonts.getSmall();

        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assetFonts.getNormal();
    }

    @Override
    public void build() {
        mainTable = new Table();
        topTable = new Table();
        centerTable = new Table();
        bottomTable = new Table();
        scoreLabel = new Label(String.valueOf(score), labelStyleMedium);
        defineButtons();


        mainTable.setDebug(DebugConstants.DEBUG_LINES);
        topTable.setDebug(DebugConstants.DEBUG_LINES);
        centerTable.setDebug(DebugConstants.DEBUG_LINES);
        bottomTable.setDebug(DebugConstants.DEBUG_LINES);
        mainTable.center();
        mainTable.setFillParent(true);

        mainTable.add(topTable).growX().row();
        mainTable.add(centerTable).grow().row();
        mainTable.add(bottomTable).growX().row();


        updateTopTable();
        updateCenterTable();
        updateBottomTable();
        stage.addActor(mainTable);

    }

    private void defineButtons() {
        pauseButton = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonPause()));
        startButton = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonStart()));

        gameButtonLeft = new Image(new TextureRegionDrawable(assetGUI.getButtonLeft()));
        gameButtonRight = new Image(new TextureRegionDrawable(assetGUI.getButtonRight()));
        gameButtonJump = new Image(new TextureRegionDrawable(assetGUI.getButtonJump()));
        gameButtonPush = new Image(new TextureRegionDrawable(assetGUI.getButtonPush()));

        pauseButton.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                GameSettings.getInstance().saveSetting();
                playScreen.setStatePaused();
                startButton.setVisible(true);
            }
        }));
        startButton.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                playScreen.setStateRunning();
                startButton.setVisible(false);
            }
        }));

        gameButtonLeft.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                playScreen.getInputProcessor().keyDown(Input.Keys.LEFT);
            }

        }));
        gameButtonLeft.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                playScreen.getInputProcessor().keyUp(Input.Keys.LEFT);
            }
        }));
        gameButtonRight.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                playScreen.getInputProcessor().keyDown(Input.Keys.RIGHT);
            }

        }));
        gameButtonRight.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                playScreen.getInputProcessor().keyUp(Input.Keys.RIGHT);
            }
        }));
        gameButtonPush.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                playScreen.getInputProcessor().keyDown(31);
            }

        }));
        gameButtonPush.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                playScreen.getInputProcessor().keyUp(31);
            }
        }));
        gameButtonJump.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                playScreen.getInputProcessor().keyDown(62);
            }

        }));
        gameButtonJump.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                playScreen.getInputProcessor().keyUp(62);
            }
        }));
    }

    private void updateTopTable() {
        topTable.clearChildren();
        topTable.add().growX();
        topTable.add(getFPSTable()).padLeft(64);
        topTable.add().growX();
        topTable.add(pauseButton).width(64).height(64);
    }

    private void updateCenterTable() {
        centerTable.clearChildren();
        centerTable.add(startButton).width(96).height(96);

    }

    private void updateBottomTable() {
        playerWindowDrawable = new TextureRegionDrawable(assetGUI.getGameWindow());
        bottomTable.clearChildren();
        bottomTable.setBackground(playerWindowDrawable);
        Table scoreTable = new Table();
        Table insidePlayerTable = new Table();
        bottomTable.add(scoreTable).growX().row();
        scoreTable.add().growX();
        scoreTable.add(scoreLabel).top();
        scoreTable.add().growX();
        scoreTable.row();
        bottomTable.add(insidePlayerTable).grow();
        insidePlayerTable.add().growX();

        insidePlayerTable.add(gameButtonPush).width(96).height(96).padTop(96).padBottom(10);
        insidePlayerTable.add(gameButtonJump).width(96).height(96).padBottom(96);
        insidePlayerTable.add().growX();
        insidePlayerTable.add(gameButtonLeft).width(83).height(96).padTop(48).padBottom(48).padRight(10).center();
        insidePlayerTable.add(gameButtonRight).width(83).height(96).padTop(48).padBottom(48).center();
        insidePlayerTable.add().growX();


    }

    private Table getFPSTable() {
        Label fpsTitle = new Label(i18NGameThreeBundle.format("hud.FPS"), labelStyleSmall);
        fpsLabel = new Label(String.valueOf(fps), labelStyleSmall);

        Table table = new Table();
        table.setDebug(DebugConstants.DEBUG_LINES);
        table.add(fpsTitle);
        table.add(fpsLabel);
        return table;
    }

    private void updateFPS() {
        fps = Gdx.graphics.getFramesPerSecond();
        fpsLabel.setText(String.valueOf(fps));
    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        updateFPS();
        stateTime += deltaTime;
        if(playScreen.isPlayScreenStateRunning() && startButton.isVisible() && stateTime > 1f){
            playScreen.pause();
            Gdx.input.setInputProcessor(stage);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float x = stage.getWidth() / 2;
        float y = stage.getHeight() / 2;

    }

    public void addScore(int value) {
        score += value;
        scoreLabel.setText(String.valueOf(score));
        GameSettings.getInstance().setLastPlayScore(score);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isScoreAboveAverage() {
        return score > AVERAGE_SCORE;
    }

    @Override
    public void render() {
        stage.draw();
    }

    public void setVisible(boolean visible) {
        mainTable.setVisible(visible);
    }
}
