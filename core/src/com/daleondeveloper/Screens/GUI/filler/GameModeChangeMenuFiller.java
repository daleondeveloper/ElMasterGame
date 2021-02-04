package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;
import com.daleondeveloper.tools.GameConstants;

/**
 * Created by AGM on 11/1/2018.
 */

public class GameModeChangeMenuFiller extends MenuFiller {
    private static final String TAG = GameModeChangeMenuFiller.class.getName();

    private static final int LAST_CHANGE_MODE_PAGE = 2;

    private com.daleondeveloper.Screens.GUI.MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private int pageShow;

    private ScrollPane scrollPane;
    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;
    private Image backButton;
    private Image nextModesImage;
    private Image previsionModeImage;
    private ImageTextButton classicModeImage;

    private ImageTextButton continueGameImage;
    private ImageTextButton newGameImage;

    private ImageTextButton fireModeImage;
    private ImageTextButton lightModeImage;
    private ImageTextButton snowModeImage;
    private ImageTextButton waterModeImage;
    private ImageTextButton darkModeImage;
    private ImageTextButton specialModeImage;

    private Label continueGameLabel;
    private Label newGameLabel;

    private Label gameModeChangeTitleLabel;
    private Label classicModeLabel;
    private Label fireModeLabel;
    private Label lightModeLabel;
    private Label snowModeLabel;
    private Label waterModeLabel;
    private Label darkModeLabel;
    private Label specialModeLabel;

    public GameModeChangeMenuFiller(MenuScreen menuScreen) {

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
        mainTable = menuScreen.getWindowTable();
        super.build();
    }

    @Override
    protected void defineElements() {
        // Title
        gameModeChangeTitleLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.title"), labelStyleMedium);

        continueGameLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.continueGame"), labelStyleMedium);
        newGameLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.newGame"), labelStyleMedium);

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

        backButton = new Image(new TextureRegionDrawable(assetGUI.getButtonX()));
        TextureRegionDrawable textureRegion = new TextureRegionDrawable(assetGUI.getButtonForPauseWindow());

        continueGameImage = new ImageTextButton("Continue",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));;
        newGameImage = new ImageTextButton("NewGame",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));;

        classicModeImage = new ImageTextButton("Classic",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));;
        lightModeImage = new ImageTextButton("Light",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));;
        snowModeImage = new ImageTextButton("Snow",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));;
        fireModeImage = new ImageTextButton("Fire",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));;
        waterModeImage = new ImageTextButton("Water",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));;
        darkModeImage = new ImageTextButton("Dark",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));;
        specialModeImage = new ImageTextButton("Special",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));;
    }

    @Override
    protected void addAction() {


        // Events
        backButton.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.hideMenuScreen();
            }
        }));
        nextModesImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                scrollPane.setScrollY(scrollPane.getVisualScrollY() + 189);
                scrollPane.updateVisualScroll();
            }
        }));

        previsionModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                scrollPane.setScrollY(scrollPane.getVisualScrollY() - 189);
                scrollPane.updateVisualScroll();

            }
        }));

        continueGameImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,ScreenTransitionEnum.COLOR_FADE_WHITE);
            }
        }));

        newGameImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                GameSettings.getInstance().deleteSave();
                menuScreen.setGameModeChangeScreen();
            }
        }));

        classicModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                prefs.setGameModeDragon(GameConstants.GAME_MODE_CLASSIC);
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,ScreenTransitionEnum.COLOR_FADE_WHITE);
            }
        }));
        lightModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                prefs.setGameModeDragon(GameConstants.GAME_MODE_LIGHT);
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,ScreenTransitionEnum.COLOR_FADE_WHITE);
            }
        }));
        snowModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                prefs.setGameModeDragon(GameConstants.GAME_MODE_SNOW);
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,ScreenTransitionEnum.COLOR_FADE_WHITE);
            }
        }));
        fireModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                prefs.setGameModeDragon(GameConstants.GAME_MODE_FIRE);
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,ScreenTransitionEnum.COLOR_FADE_WHITE);
            }
        }));
        waterModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                prefs.setGameModeDragon(GameConstants.GAME_MODE_WATER);
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,ScreenTransitionEnum.COLOR_FADE_WHITE);
            }
        }));
        darkModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                prefs.setGameModeDragon(GameConstants.GAME_MODE_DARK);
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,ScreenTransitionEnum.COLOR_FADE_WHITE);
            }
        }));
        specialModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                prefs.setGameModeDragon(GameConstants.GAME_MODE_SPECIAL);
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,ScreenTransitionEnum.COLOR_FADE_WHITE);
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
        labelTable.add(gameModeChangeTitleLabel);
        labelTable.add().growX();
        mainTable.row();
        if(prefs.isGameSave()){
            continueGamePanel();
        }else{
            chooseModePanel();
        }


    }
    private void chooseModePanel(){
        GameSettings.getInstance().deleteSave();
        // Таблиця вибору режиму гри
        Table gameModeTable = new Table();
        scrollPane = new ScrollPane(gameModeTable);
        scrollPane.setScrollingDisabled(true,false);
        mainTable.add(scrollPane);
        gameModeTable.defaults().pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();
        gameModeTable.add(classicModeImage);
        gameModeTable.row();

        gameModeTable.add(fireModeImage);
        gameModeTable.row();

        gameModeTable.add(waterModeImage);
        gameModeTable.row();

        gameModeTable.add(snowModeImage);
        gameModeTable.row();

        gameModeTable.add(lightModeImage);
        gameModeTable.row();

        gameModeTable.add(darkModeImage);
        gameModeTable.row();

        gameModeTable.add(specialModeImage);
        gameModeTable.row();

        mainTable.row();
        //Таблиця з кнопками навішації по режимам гри
        Table moveArrowTable = new Table();
        mainTable.add(moveArrowTable).padBottom(20).padRight(50).padLeft(50).growX();
        moveArrowTable.add(previsionModeImage).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).left();
        moveArrowTable.add().growX();
        moveArrowTable.add(nextModesImage).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).right();
    }
    private void continueGamePanel(){
        Table continueGameTable = new Table();
        mainTable.add(continueGameTable).grow().padBottom(40);
        continueGameTable.add(continueGameImage).pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();;
        continueGameTable.row();
        continueGameTable.add(newGameImage).pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();;

    }
}
