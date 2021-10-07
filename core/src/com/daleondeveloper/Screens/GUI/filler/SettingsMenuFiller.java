package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Screens.GUI.Button.BackButton;
import com.daleondeveloper.Screens.GUI.MenuScreen;

public class SettingsMenuFiller extends MenuFiller{
    private static final String TAG = SettingsMenuFiller.class.getName();

    private com.daleondeveloper.Screens.GUI.MenuScreen menuScreen;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleTitle;

    private Label settingsLabel;

    public SettingsMenuFiller(MenuScreen menuScreen){
        this.menuScreen = menuScreen;
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        // Styles
        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assets.getAssetFonts().getGameTitle();
        labelStyleTitle = new Label.LabelStyle();
        labelStyleTitle.font = assets.getAssetFonts().getGameTitle();

    }


    @Override
    public void build() {
        mainTable = menuScreen.getWindowTable();
        super.build();
    }
    @Override
    protected void defineElements() {
        settingsLabel = new Label(i18NGameThreeBundle.format("title.settings"), labelStyleMedium);
    }

    @Override
    protected void addAction() {
    }

    @Override
    protected void addToTable() {
        mainTable.clearChildren();
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }
        mainTable.top();
        mainTable.add(new BackButton(menuScreen)).height(15).width(15).right().padRight(30);
        mainTable.row();
        Table labelTable = new Table();
        mainTable.add(labelTable).growX();
        labelTable.add().growX();
        labelTable.add(settingsLabel);
        labelTable.add().growX();

    }
}
