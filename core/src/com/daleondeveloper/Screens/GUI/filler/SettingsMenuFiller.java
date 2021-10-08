package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Screens.GUI.MenuScreen;

public class SettingsMenuFiller extends MenuFiller{
    private static final String TAG = SettingsMenuFiller.class.getName();

    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;

    public SettingsMenuFiller(MenuScreen menuScreen){
        super(menuScreen,"title.settings");
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
    }


    @Override
    public void build() {
        super.build();
    }
    @Override
    protected void defineElements() {
    }

    @Override
    protected void addAction() {
    }

    @Override
    protected void addToTable() {
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }
    }
}
