package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.GameSettings;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;
import com.daleondeveloper.tools.AudioManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by AGM on 11/1/2018.
 */

public class PauseScreen extends GUIOverlayAbstractScreen {
    private static final String TAG = PauseScreen.class.getName();

    private static final float TITLE_OFFSET_Y = 200.0f;
    private static final float BUTTONS_OFFSET_Y = 120.0f;
    private static final float BUTTONS_ANIM_DURATION = 1.0f;
    private static final float BUTTONS_MOVE_BY_AMOUNT = 110.0f;
    private static final float DIM_ALPHA = 0.8f;

    private PlayScreen playScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private Label.LabelStyle labelStyleBig;
    private Label.LabelStyle labelStyleSmall;
    private Image screenPauseBg;
    private Image pauseWindow;
    private Image resume;
    private Image mainMenu;
    private Image audio;
    private Image settings;
    private Image restart;

    private Label pauseLabel;
    private Label resumeLabel;
    private Label restartLabel;
    private Label mainMenuLabel;
    private Label settingsLabel;

    public PauseScreen(ElMaster game, PlayScreen playScreen) {
        super(game);

        this.playScreen = playScreen;
        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        // Styles
        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = assets.getAssetFonts().getBig();
        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assets.getAssetFonts().getSmall();
    }

    @Override
    public void build() {
//        mainTable = new Table();
//        mainTable.setDebug(DebugConstants.DEBUG_LINES);
//        mainTable.center();
//        mainTable.setFillParent(true);
//        mainTable.add(getBottomTable()).height(stage.getHeight() * 0.1f);
//        stage.addActor(mainTable);
        // Background
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(255, 0, 0, DIM_ALPHA);
        pixmap.fill();
        TextureRegion dim = new TextureRegion(new Texture(pixmap));
        pixmap.dispose();
        screenPauseBg = new Image(dim);
        stage.addActor(screenPauseBg);

        pauseWindow = new Image(new TextureRegionDrawable(assetGUI.getPauseWindow()));
        stage.addActor(pauseWindow);

        // Title
        pauseLabel = new Label(i18NGameThreeBundle.format("pauseScreen.title"), labelStyleBig);
        resumeLabel = new Label(i18NGameThreeBundle.format("pauseScreen.resume"), labelStyleSmall);
        restartLabel = new Label(i18NGameThreeBundle.format("pauseScreen.restart"), labelStyleSmall);
        mainMenuLabel = new Label(i18NGameThreeBundle.format("pauseScreen.mainMenu"), labelStyleSmall);
        settingsLabel = new Label(i18NGameThreeBundle.format("pauseScreen.settings"), labelStyleSmall);
        //stage.addActor(pauseLabel);

        // Buttons
        defineButtons();
        stage.addActor(mainMenu);
//        stage.addActor(audio);
        stage.addActor(settings);
        stage.addActor(restart);
        stage.addActor(resume);
        stage.addActor(pauseLabel);
        stage.addActor(resumeLabel);
        stage.addActor(restartLabel);
        stage.addActor(mainMenuLabel);
        stage.addActor(settingsLabel);

        // Initially hidden
        setVisible(false);
    }

    private void defineButtons() {
        resume = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));

        mainMenu = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));

        audio = new Image(new TextureRegionDrawable(assetGUI.getButtonStart()));
        //audio.setChecked(!prefs.isAudio());

        settings = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));

        restart = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));

        // Events
        resume.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                playScreen.setGameStateRunning();
            }
        }));
        resumeLabel.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                playScreen.setGameStateRunning();
            }
        }));

        mainMenu.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK));
        mainMenuLabel.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK));
        audio.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
              //  prefs.setAudio(!audio.isChecked());
                prefs.save();
                AudioManager.getInstance().onSettingsUpdated();
            }
        }));
        settings.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                rateGame();
            }
        }));
        settingsLabel.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                rateGame();
            }
        }));

        restart.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
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

    }

    private void setVisible(boolean visible) {
        screenPauseBg.setVisible(visible);
        pauseWindow.setVisible(visible);
        pauseLabel.setVisible(visible);
        resumeLabel.setVisible(visible);
        restartLabel.setVisible(visible);
        mainMenuLabel.setVisible(visible);
        settingsLabel.setVisible(visible);
        resume.setVisible(visible);
        mainMenu.setVisible(visible);
        audio.setVisible(false);
        settings.setVisible(visible);
        restart.setVisible(visible);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float w = stage.getWidth(); // Same as stage.getViewport().getWorldWidth()
        float h = stage.getHeight();

        // Resize background
        screenPauseBg.setSize(w, h);

        // Place the title
        pauseWindow.setWidth(w);
        pauseWindow.setHeight(h * 0.425f);
        pauseWindow.setX((w - pauseLabel.getWidth()) / 2);
        pauseWindow.setY((h - pauseLabel.getHeight()) / 2 + TITLE_OFFSET_Y);


        // Place buttons
        placeButtons(w, h);

        if (isPauseScreenVisible()) {
            // Buttons Animations
            setButtonsAnimation();
        }
    }
    private Table getBottomTable() {
        // containerPerfectJump = getContainerPerfectJump();

        Table table = new Table();
        table.setDebug(DebugConstants.DEBUG_LINES);
        table.center();
        table.add(resume).row();
        table.add(mainMenu).row();
        table.add(settings).row();
        table.add(restart).row();

        return table;
    }

    private void placeButtons(float width, float height) {
//        resume.setX((width - resume.getWidth()) / 2);
//        resume.setY((height - resume.getHeight()) / 2 - BUTTONS_OFFSET_Y);
        float x = width / 2;
        float y = height / 2;
        pauseWindow.setPosition(x - pauseWindow.getWidth() / 2, y - pauseWindow.getHeight() / 2);
        resume.setWidth(pauseWindow.getWidth() * 0.6f);
        resume.setHeight(pauseWindow.getHeight() / 8);
        resume.setPosition(x - resume.getWidth() / 2, height * 0.55f);
        resumeLabel.setFontScale(width / 750, height / 1500);
        resumeLabel.setPosition(resume.getX() + resume.getWidth()/2 - resumeLabel.getPrefWidth() / 2 ,
                resume.getY()  + resume.getHeight() * 0.8f - resumeLabel.getPrefHeight() / 2);

        mainMenu.setWidth(pauseWindow.getWidth() * 0.6f);
        mainMenu.setHeight(pauseWindow.getHeight() / 8);
        mainMenu.setPosition(x - resume.getWidth() / 2, resume.getY() - mainMenu.getHeight() * 1.2f);
        mainMenuLabel.setFontScale(width / 750, height / 1500);
        mainMenuLabel.setPosition(mainMenu.getX() + mainMenu.getWidth()/2 - mainMenuLabel.getPrefWidth() / 2 ,
                mainMenu.getY()  + mainMenu.getHeight() * 0.8f - mainMenuLabel.getPrefHeight() / 2);

        //audio.setPosition(x, y);
        settings.setWidth(pauseWindow.getWidth() * 0.6f);
        settings.setHeight(pauseWindow.getHeight() / 8);
        settings.setPosition(x - settings.getWidth() / 2, mainMenu.getY() - settings.getHeight() * 1.2f);
        settingsLabel.setFontScale(width / 750, height / 1500);
        settingsLabel.setPosition(settings.getX() + settings.getWidth()/2 - settingsLabel.getPrefWidth() / 2 ,
                settings.getY()  + settings.getHeight() * 0.8f - settingsLabel.getPrefHeight() / 2);

        restart.setWidth(pauseWindow.getWidth() * 0.6f);
        restart.setHeight(pauseWindow.getHeight() / 8);
        restart.setPosition(x - restart.getWidth() / 2, settings.getY() - restart.getHeight() * 1.2f);
        restartLabel.setFontScale(width / 750, height / 1500);
        restartLabel.setPosition(restart.getX() + restart.getWidth()/2 - restartLabel.getPrefWidth() / 2 ,
                restart.getY()  + restart.getHeight() * 0.8f - restartLabel.getPrefHeight() / 2);

        pauseLabel.setFontScale(width / 500, height / 1000);
        pauseLabel.setPosition(resume.getX() + resume.getWidth()/2 - pauseLabel.getPrefWidth() / 2 ,
                resume.getY()  + resume.getHeight() * 1.9f  - pauseLabel.getPrefHeight() / 2);
    }

    public boolean isPauseScreenVisible() {
        return screenPauseBg.isVisible();
    }

    private void setButtonsAnimation() {
        // Disable events
        resume.setTouchable(Touchable.disabled);
        mainMenu.setTouchable(Touchable.disabled);
        audio.setTouchable(Touchable.disabled);
        settings.setTouchable(Touchable.disabled);
        restart.setTouchable(Touchable.disabled);

        // Only available on Android version
    //    rateGame.setVisible(!(playServices instanceof DummyPlayServices));

        // Set actions
        resume.clearActions();
        mainMenu.clearActions();
        audio.clearActions();
        settings.clearActions();

        resume.addAction(
                run(new Runnable() {
                    public void run () {
                        // Enable events
                        resume.setTouchable(Touchable.enabled);
                        mainMenu.setTouchable(Touchable.enabled);
                        audio.setTouchable(Touchable.enabled);
                        settings.setTouchable(Touchable.enabled);
                        restart.setTouchable(Touchable.enabled);
                    }
                }));
//        mainMenu.addAction(moveBy(BUTTONS_MOVE_BY_AMOUNT, BUTTONS_MOVE_BY_AMOUNT, BUTTONS_ANIM_DURATION, Interpolation.bounceOut));
//        audio.addAction(moveBy(-BUTTONS_MOVE_BY_AMOUNT, BUTTONS_MOVE_BY_AMOUNT, BUTTONS_ANIM_DURATION, Interpolation.bounceOut));
//        settings.addAction(moveBy(0, BUTTONS_MOVE_BY_AMOUNT * 2, BUTTONS_ANIM_DURATION, Interpolation.bounceOut));
    }

    @Override
    public void update(float deltaTime) {
        stage.act();
    }

    @Override
    public void render() {
        stage.draw();
    }

    public void showPauseScreen() {
        if (!isPauseScreenVisible()) {
            playScreen.doPause();
            setVisible(true);
            startStageAnimation(false, new Runnable() {
                @Override
                public void run() {
                    setButtonsAnimation();
                }
            });

            // Only PauseScreen responds to events
            Gdx.input.setInputProcessor(stage);
        }
    }

    public void hidePauseScreen() {
        if (isPauseScreenVisible()) {
            startStageAnimation(true, new Runnable() {
                @Override
                public void run() {
                    setVisible(false);
                    placeButtons(stage.getWidth(), stage.getHeight());
                    playScreen.resume();

                    // Enable input for PlayScreen
                    Gdx.input.setInputProcessor(playScreen.getInputProcessor());
                }
            });
        }
    }

    private void rateGame() {
        //playServices.rateGame();
    }
}
