package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;
import com.daleondeveloper.tools.GameConstants;

/**
 * Created by AGM on 11/1/2018.
 */

public class LevelCompleteMenuFiller extends MenuFiller {
    private static final String TAG = LevelCompleteMenuFiller.class.getName();

    private com.daleondeveloper.Screens.GUI.MenuScreen menuScreen;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;
    private ImageTextButton infinityLvl;
    private ImageTextButton mainMenuButton;
    private ImageTextButton nextLvl;
    private ImageButton backButton;

    private Label pauseLabel;


    public LevelCompleteMenuFiller(com.daleondeveloper.Screens.GUI.MenuScreen menuScreen) {

        this.menuScreen = menuScreen;
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        // Styles
        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assets.getAssetFonts().getNormal();
        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assets.getAssetFonts().getSmall();

    }

    public void build() {
        mainTable = menuScreen.getWindowTable();
        super.build();
    }
    @Override
    protected void defineElements() {
        // Title
        pauseLabel = new Label(i18NGameThreeBundle.format("pauseScreen.title"), labelStyleMedium);
        TextureRegionDrawable textureRegion = new TextureRegionDrawable(assetGUI.getButtonForPauseWindow());
        infinityLvl = new ImageTextButton("InfinityLvl",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));
        mainMenuButton = new ImageTextButton("MainMenu",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));
//        textureRegion.setRegionHeight(50);
        nextLvl = new ImageTextButton("NextLvl",new ImageTextButton.ImageTextButtonStyle(
                textureRegion,textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));
        backButton = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonX()));

        // Events




    }
    @Override
    protected void addAction(){
        mainMenuButton.addListener(
                ListenerHelper.screenNavigationListener(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK));

        nextLvl.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                GameSettings.getInstance().setSavedLevel("");
                int nextLvl = GameSettings.getInstance().getLevel() + 1;
                if(nextLvl < GameConstants.MAX_LEVEL){
                    GameSettings.getInstance().setLevel(nextLvl);
                    ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_WHITE);
                }else {
                    ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK);
                }
            }
        }));

        infinityLvl.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                GameSettings.getInstance().setInfinityLvl(true);
                menuScreen.hideMenuScreen();
            }
        }));
        backButton.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.hideMenuScreen();
            }
        }));
    }
    @Override
    protected void addToTable(){
        mainTable.clearChildren();
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }
        mainTable.columnDefaults(0);
        Table labelTable = new Table();
        mainTable.add(backButton).height(15).width(15).right().padRight(30);
        mainTable.row();
        mainTable.add(labelTable);
        labelTable.add().grow();
        labelTable.add(pauseLabel).colspan(5).fill();
        labelTable.add().grow();
        mainTable.row();
        Table buttonTable = new Table();
        mainTable.add(buttonTable).grow().padBottom(30);
        buttonTable.defaults().pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();
        buttonTable.add(mainMenuButton);
        buttonTable.row();
        buttonTable.add(infinityLvl);
        buttonTable.row();
        buttonTable.add(nextLvl);
        mainTable.row();
    }

}
