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
import com.daleondeveloper.Game.tools.Level.Levels;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;
import com.daleondeveloper.tools.GameConstants;

/**
 * Created by AGM on 11/1/2018.
 */

public class LevelChangeMenuFiller extends MenuFiller {
    private static final String TAG = LevelChangeMenuFiller.class.getName();

    private static final int LAST_CHANGE_MODE_PAGE = 2;

    private MenuScreen menuScreen;
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

    private ImageTextButton[] levelsBtn;
    private Label[] levelsTxt;
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

    public LevelChangeMenuFiller(MenuScreen menuScreen) {

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
        levelsBtn = new ImageTextButton[Levels.maxLevel];
    }

    @Override
    public void build() {
        mainTable = menuScreen.getWindowTable();
        super.build();
    }

    @Override
    protected void defineElements() {
        // Title
        gameModeChangeTitleLabel = new Label(i18NGameThreeBundle.format("levelsScreen.title"), labelStyleMedium);

        continueGameLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.continueGame"), labelStyleMedium);
        newGameLabel = new Label(i18NGameThreeBundle.format("gameModeChangeScreen.newGame"), labelStyleMedium);

        nextModesImage = new Image(assetGUI.getButtonRight());
        previsionModeImage = new Image(assetGUI.getButtonLeft());

        backButton = new Image(new TextureRegionDrawable(assetGUI.getButtonX()));
        TextureRegionDrawable textureRegion = new TextureRegionDrawable(assetGUI.getButtonForPauseWindow());
        TextureRegionDrawable textureRegionOff = new TextureRegionDrawable(assetGUI.getGameWindow());

        int openedLevel = prefs.getHighCompletedLvl();
        for(int i = 0; i < levelsBtn.length; i++){
            if(openedLevel > i){
                levelsBtn[i] = new ImageTextButton(String.valueOf(i), new ImageTextButton.ImageTextButtonStyle(
                        textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
                ));
            }else{
                levelsBtn[i] = new ImageTextButton(String.valueOf(i), new ImageTextButton.ImageTextButtonStyle(
                        textureRegionOff, textureRegionOff, textureRegionOff, assets.getAssetFonts().getSmall()
                ));
            }
        }
        continueGameImage = new ImageTextButton("Continue",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));;
        newGameImage = new ImageTextButton("NewGame",new ImageTextButton.ImageTextButtonStyle(
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
                prefs.setLevel(-1);
                prefs.loadIsInfinityLevel();
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME,ScreenTransitionEnum.COLOR_FADE_WHITE);
            }
        }));

        newGameImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                prefs.clearSaveLevel();
                prefs.setInfinityLvl(false);
                menuScreen.setGameModeChangeScreen();
            }
        }));
        for(int i = 0; i < levelsBtn.length; i++){
            if(i < prefs.getHighCompletedLvl()) {
                final int j = i;
                levelsBtn[i].addListener(ListenerHelper.runnableListener(new Runnable() {
                    @Override
                    public void run() {
                        prefs.setLevel(j);
                        ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_WHITE);
                    }
                }));
            }
        }
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
        if(!prefs.loadSavedLevel().isEmpty()){
            continueGamePanel();
        }else{
            chooseModePanel();
        }


    }
    private void chooseModePanel(){
//        prefs.clearSaveLevel();
        // Таблиця вибору режиму гри
        Table gameModeTable = new Table();
        scrollPane = new ScrollPane(gameModeTable);
        scrollPane.setScrollingDisabled(true,false);
        mainTable.add(scrollPane);
        gameModeTable.defaults().pad(10).padLeft(10).padRight(10).width(GameConstants.BUTTON_WIDTH/8).height(GameConstants.BUTTON_HEIGHT).center();
        for(int i = 0, j = 0 ; i < levelsBtn.length; i++, j++){
            gameModeTable.add(levelsBtn[i]);
            if(j == 5) {
                j = -1;
                gameModeTable.row();
            }
        }

        mainTable.row();
        //Таблиця з кнопками навішації по режимам гри
        Table moveArrowTable = new Table();
        mainTable.add(moveArrowTable).padBottom(20).padRight(50).padLeft(50).growX();
        moveArrowTable.add(previsionModeImage).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).left()
        .padRight(50);
        moveArrowTable.add().growX();
        moveArrowTable.add(nextModesImage).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).right()
        .padLeft(50);
    }
    private void continueGamePanel(){
        Table continueGameTable = new Table();
        mainTable.add(continueGameTable).grow().padBottom(40);
        continueGameTable.add(continueGameImage).pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();;
        continueGameTable.row();
        continueGameTable.add(newGameImage).pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();;

    }
}
