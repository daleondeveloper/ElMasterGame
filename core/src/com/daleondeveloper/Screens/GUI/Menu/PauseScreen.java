package com.daleondeveloper.Screens.GUI.Menu;

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

/**
 * Created by AGM on 11/1/2018.
 */

public class PauseScreen extends MenuFiller{
    private static final String TAG = PauseScreen.class.getName();

    private MenuScreen menuScreen;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;
    private ImageTextButton restartButton;
    private ImageTextButton mainMenuButton;
    private ImageTextButton settingsButton;
    private ImageButton backButton;

    private Label pauseLabel;


    public PauseScreen(MenuScreen menuScreen) {

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
        restartButton = new ImageTextButton("Restart",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));
        mainMenuButton = new ImageTextButton("MainMenu",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));
//        textureRegion.setRegionHeight(50);
        settingsButton = new ImageTextButton("Settings",new ImageTextButton.ImageTextButtonStyle(
                textureRegion,textureRegion, textureRegion, assets.getAssetFonts().getSmall()
        ));
        backButton = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonX()));

        // Events




    }
    @Override
    protected void addAction(){
        mainMenuButton.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK));

        settingsButton.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.setSettingsScreen();
            }
        }));

        restartButton.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                GameSettings.getInstance().deleteSave();
                ScreenManager.getInstance().showScreen(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_BLACK);

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
        mainTable.add(buttonTable).grow();
        buttonTable.defaults().pad(10).width(MenuScreen.BUTTON_WIDTH).height(MenuScreen.BUTTON_HEIGHT).center();
        buttonTable.add(mainMenuButton);
        buttonTable.row();
        buttonTable.add(restartButton);
        buttonTable.row();
        buttonTable.add(settingsButton);
        mainTable.row();
    }

}
