package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Screens.GUI.Button.BackButton;
import com.daleondeveloper.Screens.GUI.Button.MainMenuTextButton;
import com.daleondeveloper.Screens.GUI.Button.RestartTextButton;
import com.daleondeveloper.Screens.GUI.Button.SettingsTextButton;
import com.daleondeveloper.tools.GameConstants;

/**
 * Created by AGM on 11/1/2018.
 */

public class PauseMenuFiller extends MenuFiller {
    private static final String TAG = PauseMenuFiller.class.getName();

    private com.daleondeveloper.Screens.GUI.MenuScreen menuScreen;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;

    private Label pauseLabel;


    public PauseMenuFiller(com.daleondeveloper.Screens.GUI.MenuScreen menuScreen) {

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
    }
    @Override
    protected void addAction(){
    }
    @Override
    protected void addToTable(){
        mainTable.clearChildren();
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }
        mainTable.columnDefaults(0);
        Table labelTable = new Table();
        mainTable.add(new BackButton(menuScreen)).height(15).width(15).right().padRight(30);
        mainTable.row();
        mainTable.add(labelTable);
        labelTable.add().grow();
        labelTable.add(pauseLabel).colspan(5).fill();
        labelTable.add().grow();
        mainTable.row();
        Table buttonTable = new Table();
        mainTable.add(buttonTable).grow().padBottom(30);
        buttonTable.defaults().pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();
        buttonTable.add(new MainMenuTextButton());
        buttonTable.row();
        buttonTable.add(new RestartTextButton());
        buttonTable.row();
        buttonTable.add(new SettingsTextButton(menuScreen));
        mainTable.row();
    }

}
