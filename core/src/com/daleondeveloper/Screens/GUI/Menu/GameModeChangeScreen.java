package com.daleondeveloper.Screens.GUI.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

/**
 * Created by AGM on 11/1/2018.
 */

public class GameModeChangeScreen extends GUIOverlayAbstractScreen {
    private static final String TAG = GameModeChangeScreen.class.getName();

    private static final float TITLE_OFFSET_Y = 200.0f;
    private static final float BUTTONS_OFFSET_Y = 120.0f;
    private static final float BUTTONS_ANIM_DURATION = 1.0f;
    private static final float BUTTONS_MOVE_BY_AMOUNT = 110.0f;
    private static final float DIM_ALPHA = 0.8f;
    private static final int LAST_CHANGE_MODE_PAGE = 2;

    private MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private int pageShow;

    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;
    private Image menuWindow;
    private Image back;
    private Image nextModesImage;
    private Image previsionModeImage;
    private Image classicModeImage;
    private Image fireModeImage;
    private Image lightModeImage;
    private Image snowModeImage;
    private Image waterModeImage;
    private Image darkModeImage;
    private Image specialModeImage;

    private Label gameModeChangeTitleLabel;
    private Label classicModeLabel;
    private Label fireModeLabel;
    private Label lightModeLabel;
    private Label snowModeLabel;
    private Label waterModeLabel;
    private Label darkModeLabel;
    private Label specialModeLabel;

    public GameModeChangeScreen(ElMaster game, MenuScreen menuScreen) {
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

        pageShow = 0;
    }

    @Override
    public void build() {
        menuWindow = menuScreen.getPauseWindow();

        // Title
        gameModeChangeTitleLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.title"), labelStyleMedium);
        classicModeLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.classicMode"), labelStyleSmall);
        lightModeLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.lightMode"), labelStyleSmall);
        snowModeLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.snowMode"), labelStyleSmall);
        fireModeLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.fireMode"), labelStyleSmall);
        waterModeLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.waterMode"), labelStyleSmall);
        darkModeLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.darkMode"), labelStyleSmall);
        specialModeLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.specialMode"), labelStyleSmall);
        //stage.addActor(pauseLabel);
        nextModesImage = new Image(assetGUI.getButtonLeft());
        previsionModeImage = new Image(assetGUI.getButtonRight());

        // Buttons
        defineButtons();
        stage.addActor(back);
        stage.addActor(previsionModeImage);
        stage.addActor(nextModesImage);
        stage.addActor(lightModeImage);
        stage.addActor(snowModeImage);
        stage.addActor(classicModeImage);
        stage.addActor(fireModeImage);
        stage.addActor(darkModeImage);
        stage.addActor(waterModeImage);
        stage.addActor(specialModeImage);

        stage.addActor(gameModeChangeTitleLabel);
        stage.addActor(classicModeLabel);
        stage.addActor(lightModeLabel);
        stage.addActor(snowModeLabel);
        stage.addActor(waterModeLabel);
        stage.addActor(fireModeLabel);
        stage.addActor(darkModeLabel);
        stage.addActor(specialModeLabel);

        // Initially hidden
        setVisible(false);
    }

    private void defineButtons() {
        back = new Image(new TextureRegionDrawable(assetGUI.getButtonX()));

        classicModeImage = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));
        lightModeImage = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));
        snowModeImage = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));
        fireModeImage = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));
        waterModeImage = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));
        darkModeImage = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));
        specialModeImage = new Image(new TextureRegionDrawable(assetGUI.getButtonForPauseWindow()));

        // Events
        back.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.hideMenuScreen();
            }
        }));
        nextModesImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                    if(pageShow < LAST_CHANGE_MODE_PAGE){
                        pageShow++;
                    }else{
                        pageShow = 0;
                    }
            }
        }));

        previsionModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                    if(pageShow > 0){
                        pageShow--;
                    }else{
                        pageShow = LAST_CHANGE_MODE_PAGE;
                    }
            }
        }));



        classicModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(0);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));
        classicModeLabel.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(0);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));

        lightModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(1);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));
        lightModeLabel.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(1);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));

        snowModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(2);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));
        snowModeLabel.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(2);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));

        fireModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(3);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));
        fireModeLabel.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(3);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));

        waterModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(4);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));
        waterModeLabel.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(4);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));

        darkModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(5);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));
        darkModeLabel.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(5);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));

        specialModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(6);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));
        specialModeLabel.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
            prefs.setGameModeDragon(6);
            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,null);
            }
        }));


    }

    private void setVisible(boolean visible) {
        gameModeChangeTitleLabel.setVisible(false);
        back.setVisible(false);
        nextModesImage.setVisible(false);
        previsionModeImage.setVisible(false);

        classicModeLabel.setVisible(false);
        lightModeLabel.setVisible(false);
        snowModeLabel.setVisible(false);
        fireModeLabel.setVisible(false);
        waterModeLabel.setVisible(false);
        darkModeLabel.setVisible(false);
        specialModeLabel.setVisible(false);


        classicModeImage.setVisible(false);
        lightModeImage.setVisible(false);
        snowModeImage.setVisible(false);
        fireModeImage.setVisible(false);
        waterModeImage.setVisible(false);
        darkModeImage.setVisible(false);
        specialModeImage.setVisible(false);
        if(visible) {
            showPage(pageShow);
        }
    }
    private  void showPage(int page){
        gameModeChangeTitleLabel.setVisible(true);
        back.setVisible(true);
        switch (page){
            case 0:
                classicModeLabel.setVisible(true);
                lightModeLabel.setVisible(true);
                snowModeLabel.setVisible(true);

                classicModeImage.setVisible(true);
                lightModeImage.setVisible(true);
                snowModeImage.setVisible(true);

                nextModesImage.setVisible(true);
                break;
            case 1:
                fireModeLabel.setVisible(true);
                waterModeLabel.setVisible(true);
                darkModeLabel.setVisible(true);

                fireModeImage.setVisible(true);
                waterModeImage.setVisible(true);
                darkModeImage.setVisible(true);

                previsionModeImage.setVisible(true);
                nextModesImage.setVisible(true);
                break;
            case 2:
                specialModeImage.setVisible(true);
                specialModeLabel.setVisible(true);

                previsionModeImage.setVisible(true);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float w = stage.getWidth(); // Same as stage.getViewport().getWorldWidth()
        float h = stage.getHeight();

        nextModesImage.setWidth(50);
        nextModesImage.setHeight(58);
        nextModesImage.setPosition(menuWindow.getX() + 300, menuWindow.getY() + 35);
        previsionModeImage.setWidth(50);
        previsionModeImage.setHeight(58);
        previsionModeImage.setPosition(menuWindow.getX() + 55 , menuWindow.getY() + 35);
        // Place buttons
        placeButtons(w, h);

    }

    private void placeButtons(float width, float height) {

        float x = width / 2;
        float y = height / 2;
        menuWindow.setPosition(x - menuWindow.getWidth() / 2, y - menuWindow.getHeight() / 2);

        back.setHeight(24);
        back.setWidth(24);
        back.setPosition(menuWindow.getX() + menuWindow.getWidth() - 60 ,
                menuWindow.getY() + menuWindow.getHeight() - 40);
        //
        //
        //перша сторінка кнопок
        classicModeImage.setWidth(MenuScreen.BUTTON_WIDTH);
        classicModeImage.setHeight(MenuScreen.BUTTON_HEIGHT);
        classicModeImage.setPosition(x - classicModeImage.getWidth() / 2 ,
                y * 1.15f - classicModeImage.getHeight() / 2);
        classicModeLabel.setPosition(classicModeImage.getX() + classicModeImage.getWidth()/2 - classicModeLabel.getPrefWidth() / 2 ,
                classicModeImage.getY()  + classicModeImage.getHeight() * 0.6f - classicModeLabel.getPrefHeight() / 2);

        //audio.setPosition(x, y);
        lightModeImage.setWidth(MenuScreen.BUTTON_WIDTH);
        lightModeImage.setHeight(MenuScreen.BUTTON_HEIGHT);
        lightModeImage.setPosition(x - lightModeImage.getWidth() / 2, classicModeImage.getY() - lightModeImage.getHeight() * 1.2f);
        lightModeLabel.setPosition(lightModeImage.getX() + lightModeImage.getWidth()/2 - lightModeLabel.getPrefWidth() / 2 ,
                lightModeImage.getY()  + lightModeImage.getHeight() * 0.6f - lightModeLabel.getPrefHeight() / 2);

        snowModeImage.setWidth(MenuScreen.BUTTON_WIDTH);
        snowModeImage.setHeight(MenuScreen.BUTTON_HEIGHT);
        snowModeImage.setPosition(x - snowModeImage.getWidth() / 2, lightModeImage.getY() - snowModeImage.getHeight() * 1.2f);
        snowModeLabel.setPosition(snowModeImage.getX() + snowModeImage.getWidth()/2 - snowModeLabel.getPrefWidth() / 2 ,
                snowModeImage.getY()  + snowModeImage.getHeight() * 0.6f - snowModeLabel.getPrefHeight() / 2);

        //
        //
        //друга сторінка кнопок
        fireModeImage.setWidth(MenuScreen.BUTTON_WIDTH);
        fireModeImage.setHeight(MenuScreen.BUTTON_HEIGHT);
        fireModeImage.setPosition(x - fireModeImage.getWidth() / 2 ,
                y * 1.15f - fireModeImage.getHeight() / 2);
        fireModeLabel.setPosition(fireModeImage.getX() + fireModeImage.getWidth()/2 - fireModeLabel.getPrefWidth() / 2 ,
                fireModeImage.getY()  + fireModeImage.getHeight() * 0.6f - fireModeLabel.getPrefHeight() / 2);

        //audio.setPosition(x, y);
        waterModeImage.setWidth(MenuScreen.BUTTON_WIDTH);
        waterModeImage.setHeight(MenuScreen.BUTTON_HEIGHT);
        waterModeImage.setPosition(x - waterModeImage.getWidth() / 2, fireModeImage.getY() - waterModeImage.getHeight() * 1.2f);
        waterModeLabel.setPosition(waterModeImage.getX() + waterModeImage.getWidth()/2 - waterModeLabel.getPrefWidth() / 2 ,
                waterModeImage.getY()  + waterModeImage.getHeight() * 0.6f - waterModeLabel.getPrefHeight() / 2);

        darkModeImage.setWidth(MenuScreen.BUTTON_WIDTH);
        darkModeImage.setHeight(MenuScreen.BUTTON_HEIGHT);
        darkModeImage.setPosition(x - darkModeImage.getWidth() / 2, waterModeImage.getY() - darkModeImage.getHeight() * 1.2f);
        darkModeLabel.setPosition(darkModeImage.getX() + darkModeImage.getWidth()/2 - darkModeLabel.getPrefWidth() / 2 ,
                darkModeImage.getY()  + darkModeImage.getHeight() * 0.6f - darkModeLabel.getPrefHeight() / 2);

        //
        //
        //Третя сторінка кнопок
        specialModeImage.setWidth(MenuScreen.BUTTON_WIDTH);
        specialModeImage.setHeight(MenuScreen.BUTTON_HEIGHT);
        specialModeImage.setPosition(x - specialModeImage.getWidth() / 2 ,
                y  - specialModeImage.getHeight() / 2);
        specialModeLabel.setPosition(specialModeImage.getX() + specialModeImage.getWidth()/2 - specialModeLabel.getPrefWidth() / 2 ,
                specialModeImage.getY()  + specialModeImage.getHeight() * 0.6f - specialModeLabel.getPrefHeight() / 2);
        gameModeChangeTitleLabel.setPosition(menuWindow.getX() + (menuWindow.getWidth()/2 - gameModeChangeTitleLabel.getPrefWidth()/2),
                menuWindow.getY() + 270);
    }

    private void setButtonsAnimation() {
        // Disable events
        classicModeImage.setTouchable(Touchable.disabled);
        back.setTouchable(Touchable.disabled);
        lightModeImage.setTouchable(Touchable.disabled);
        snowModeImage.setTouchable(Touchable.disabled);

        // Only available on Android version
    //    rateGame.setVisible(!(playServices instanceof DummyPlayServices));

        // Set actions
        classicModeImage.clearActions();
        back.clearActions();
        lightModeImage.clearActions();

        classicModeImage.addAction(
                run(new Runnable() {
                    public void run () {
                        // Enable events
                        classicModeImage.setTouchable(Touchable.enabled);
                        back.setTouchable(Touchable.enabled);
                        lightModeImage.setTouchable(Touchable.enabled);
                        snowModeImage.setTouchable(Touchable.enabled);
                    }
                }));
    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        if(menuScreen.getMenuState() == MenuScreen.MenuState.GAMEMODECHOOSE){
            setVisible(true);
        }else{
            setVisible(false);
        }
    }

    @Override
    public void render() {
        stage.draw();
    }

    public void showGameModeChangeScreen() {
        if (!isGameModeGameChangeScreenShow()) {
            setVisible(true);
            pageShow = 0;

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

    public boolean isGameModeGameChangeScreenShow(){
        return gameModeChangeTitleLabel.isVisible();
    }

    private void rateGame() {
        //playServices.rateGame();
    }
}
