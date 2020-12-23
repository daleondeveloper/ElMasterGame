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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
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
    private Label.LabelStyle labelStyleBig;
    private Image screenPauseBg;
    private Image pauseWindow;
    private Label pauseLabel;
    private ImageButton resume;
    private ImageButton mainMenu;
    private ImageButton audio;
    private ImageButton settings;
    private ImageButton restart;

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
    }

    @Override
    public void build() {
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
        //stage.addActor(pauseLabel);

        // Buttons
        defineButtons();
        stage.addActor(mainMenu);
        stage.addActor(audio);
        stage.addActor(settings);
        stage.addActor(restart);
        stage.addActor(resume);

        // Initially hidden
        setVisible(false);
    }

    private void defineButtons() {
        resume = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()),
                new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));

        mainMenu = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()),
                new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));

        audio = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonStart()),
                new TextureRegionDrawable(assetGUI.getButtonHelp()),
                new TextureRegionDrawable(assetGUI.getButtonHelp()));
        audio.setChecked(!prefs.isAudio());

        settings = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()),
                new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));

        restart = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()),
                new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));

        // Events
        resume.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                playScreen.setGameStateRunning();
            }
        }));
        mainMenu.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.SLIDE_DOWN));
        audio.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                prefs.setAudio(!audio.isChecked());
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
        restart.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                GameSettings.getInstance().deleteSave();
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_WHITE);

            }
        }));
    }

    private void setVisible(boolean visible) {
        screenPauseBg.setVisible(visible);
        pauseWindow.setVisible(visible);
        pauseLabel.setVisible(visible);
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
        pauseLabel.setX((w - pauseLabel.getWidth()) / 2);
        pauseLabel.setY((h - pauseLabel.getHeight()) / 2 + TITLE_OFFSET_Y);

        // Place buttons
        placeButtons(w, h);

        if (isPauseScreenVisible()) {
            // Buttons Animations
            setButtonsAnimation();
        }
    }

    private void placeButtons(float width, float height) {
        resume.setX((width - resume.getWidth()) / 2);
        resume.setY((height - resume.getHeight()) / 2 - BUTTONS_OFFSET_Y);
        float x = width / 2;
        float y = height / 2;
        pauseWindow.setPosition(x - pauseWindow.getWidth() / 2, y - pauseWindow.getHeight() / 2);
        resume.setPosition(x - resume.getWidth() / 2, y + 75);
        mainMenu.setPosition(x - resume.getWidth() / 2, y + 25);
        //audio.setPosition(x, y);
        settings.setPosition(x - settings.getWidth() / 2, y - 25);
        restart.setPosition(x - restart.getWidth() / 2, y - 75);
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
