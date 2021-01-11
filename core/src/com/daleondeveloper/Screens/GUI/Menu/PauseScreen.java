package com.daleondeveloper.Screens.GUI.Menu;

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
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;
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

    private MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;
    private Image menuWindow;
    private Image back;
    private Image mainMenu;
    private Image settings;
    private Image restart;

    private Label pauseLabel;
    private Label restartLabel;
    private Label mainMenuLabel;
    private Label settingsLabel;

    public PauseScreen(ElMaster game, MenuScreen menuScreen) {
        super(game);

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

    }

    @Override
    public void build() {
        menuWindow = menuScreen.getPauseWindow();

        // Title
        pauseLabel = new Label(i18NGameThreeBundle.format("pauseScreen.title"), labelStyleMedium);
        restartLabel = new Label(i18NGameThreeBundle.format("pauseScreen.restart"), labelStyleSmall);
        mainMenuLabel = new Label(i18NGameThreeBundle.format("pauseScreen.mainMenu"), labelStyleSmall);
        settingsLabel = new Label(i18NGameThreeBundle.format("pauseScreen.settings"), labelStyleSmall);
        //stage.addActor(pauseLabel);

        // Buttons
        defineButtons();
        stage.addActor(mainMenu);
        stage.addActor(settings);
        stage.addActor(restart);
        stage.addActor(back);
        stage.addActor(pauseLabel);
        stage.addActor(restartLabel);
        stage.addActor(mainMenuLabel);
        stage.addActor(settingsLabel);

        // Initially hidden
        setVisible(false);
    }

    private void defineButtons() {
        back = new Image(new TextureRegionDrawable(assetGUI.getButtonX()));

        mainMenu = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));

        settings = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));

        restart = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));

        // Events
        back.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.hideMenuScreen();
            }
        }));

        mainMenu.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK));
        mainMenuLabel.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK));

        settings.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
               menuScreen.setSettingsScreen();
            }
        }));
        settingsLabel.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.setSettingsScreen();
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
        pauseLabel.setVisible(visible);
        restartLabel.setVisible(visible);
        mainMenuLabel.setVisible(visible);
        settingsLabel.setVisible(visible);
        back.setVisible(visible);
        mainMenu.setVisible(visible);
        settings.setVisible(visible);
        restart.setVisible(visible);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float w = stage.getWidth(); // Same as stage.getViewport().getWorldWidth()
        float h = stage.getHeight();

        // Place buttons
        placeButtons(w, h);

    }
    private Table getBottomTable() {
        // containerPerfectJump = getContainerPerfectJump();

        Table table = new Table();
        table.setDebug(DebugConstants.DEBUG_LINES);
        table.center();
        table.add(back).row();
        table.add(mainMenu).row();
        table.add(settings).row();
        table.add(restart).row();

        return table;
    }

    private void placeButtons(float width, float height) {

        float x = width / 2;
        float y = height / 2;
        menuWindow.setPosition(x - menuWindow.getWidth() / 2, y - menuWindow.getHeight() / 2);

        back.setWidth(menuWindow.getWidth() * 0.1f);
        back.setHeight(menuWindow.getHeight() * 0.1f);
        back.setPosition(menuWindow.getX() + menuWindow.getWidth() - back.getWidth() * 2f ,
                menuWindow.getY() + menuWindow.getHeight() - back.getHeight() * 1.6f);

        mainMenu.setWidth(MenuScreen.BUTTON_WIDTH);
        mainMenu.setHeight(MenuScreen.BUTTON_HEIGHT);
        mainMenu.setPosition(x - mainMenu.getWidth() / 2 ,
                y * 1.1f - mainMenu.getHeight() / 2);
        mainMenuLabel.setPosition(mainMenu.getX() + mainMenu.getWidth()/2 - mainMenuLabel.getPrefWidth() / 2 ,
                mainMenu.getY()  + mainMenu.getHeight() * 0.6f - mainMenuLabel.getPrefHeight() / 2);

        //audio.setPosition(x, y);
        settings.setWidth(MenuScreen.BUTTON_WIDTH);
        settings.setHeight(MenuScreen.BUTTON_HEIGHT);
        settings.setPosition(x - settings.getWidth() / 2, mainMenu.getY() - settings.getHeight() * 1.2f);
        settingsLabel.setPosition(settings.getX() + settings.getWidth()/2 - settingsLabel.getPrefWidth() / 2 ,
                settings.getY()  + settings.getHeight() * 0.6f - settingsLabel.getPrefHeight() / 2);

        restart.setWidth(MenuScreen.BUTTON_WIDTH);
        restart.setHeight(MenuScreen.BUTTON_HEIGHT);
        restart.setPosition(x - restart.getWidth() / 2, settings.getY() - restart.getHeight() * 1.2f);
        restartLabel.setPosition(restart.getX() + restart.getWidth()/2 - restartLabel.getPrefWidth() / 2 ,
                restart.getY()  + restart.getHeight() * 0.6f - restartLabel.getPrefHeight() / 2);

        pauseLabel.setPosition(mainMenu.getX() + mainMenu.getWidth()/2 - pauseLabel.getPrefWidth() / 2 ,
                menuWindow.getY() + menuWindow.getHeight() - 5 - pauseLabel.getPrefHeight());
    }

    private void setButtonsAnimation() {
        // Disable events
        back.setTouchable(Touchable.disabled);
        mainMenu.setTouchable(Touchable.disabled);
        settings.setTouchable(Touchable.disabled);
        restart.setTouchable(Touchable.disabled);

        // Only available on Android version
    //    rateGame.setVisible(!(playServices instanceof DummyPlayServices));

        // Set actions
        back.clearActions();
        mainMenu.clearActions();
        settings.clearActions();

        back.addAction(
                run(new Runnable() {
                    public void run () {
                        // Enable events
                        back.setTouchable(Touchable.enabled);
                        mainMenu.setTouchable(Touchable.enabled);
                        settings.setTouchable(Touchable.enabled);
                        restart.setTouchable(Touchable.enabled);
                    }
                }));
    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        if(menuScreen.getMenuState() == MenuScreen.MenuState.PAUSE){
            setVisible(true);
        }else{
            setVisible(false);
        }
    }

    @Override
    public void render() {
        stage.draw();
    }

    public void showPauseScreen() {
        if (!isPauseScreenVisible()) {
            setVisible(true);

            // Only PauseScreen responds to events
            Gdx.input.setInputProcessor(stage);
        }
    }

    public void hidePauseScreen() {
//        if (isPauseScreenVisible()) {
//            startStageAnimation(true, new Runnable() {
//                @Override
//                public void run() {
//                    setVisible(false);
//                    placeButtons(stage.getWidth(), stage.getHeight());
////                    playScreen.resume();
//
//                    // Enable input for PlayScreen
////                    Gdx.input.setInputProcessor(playScreen.getInputProcessor());
//                }
//            });
//        }
    }

    public boolean isPauseScreenVisible(){
        return pauseLabel.isVisible();
    }

    private void rateGame() {
        //playServices.rateGame();
    }
}
