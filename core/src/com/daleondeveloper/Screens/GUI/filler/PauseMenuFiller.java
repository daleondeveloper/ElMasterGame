package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Screens.GUI.Button.MainMenuTextButton;
import com.daleondeveloper.Screens.GUI.Button.RestartTextButton;
import com.daleondeveloper.Screens.GUI.Button.SettingsTextButton;
import com.daleondeveloper.tools.GameConstants;

public class PauseMenuFiller extends MenuFiller {
    private static final String TAG = PauseMenuFiller.class.getName();

    private com.daleondeveloper.Screens.GUI.MenuScreen menuScreen;
    private Assets assets;
    private I18NBundle i18NGameThreeBundle;

    public PauseMenuFiller(com.daleondeveloper.Screens.GUI.MenuScreen menuScreen) {
        super(menuScreen,"title.pause");

        assets = Assets.getInstance();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
    }

    public void build() {
        super.build();
    }
    @Override
    protected void defineElements() {
        }
    @Override
    protected void addAction(){
    }
    @Override
    protected void addToTable(){
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }
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
