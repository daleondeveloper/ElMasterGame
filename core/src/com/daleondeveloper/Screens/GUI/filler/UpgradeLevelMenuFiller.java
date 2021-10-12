package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.tools.Level.ScoreCheker;
import com.daleondeveloper.Screens.GUI.Button.UpgradeTextButton;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.tools.GameConstants;

public class UpgradeLevelMenuFiller extends MenuFiller {
    private static final String TAG = UpgradeLevelMenuFiller.class.getName();

    private MenuScreen menuScreen;
    private ScoreCheker scoreCheker;
    private Assets assets;
    private I18NBundle i18NGameThreeBundle;


    public UpgradeLevelMenuFiller(MenuScreen menuScreen, ScoreCheker scoreCheker) {
        super(menuScreen,"title.pause");
        this.scoreCheker = scoreCheker;
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
        for(int i = 0; i < 3; i++) {
            buttonTable.add(new UpgradeTextButton(scoreCheker.getUpgrader()));
            buttonTable.row();
        }
        mainTable.row();
    }

}
