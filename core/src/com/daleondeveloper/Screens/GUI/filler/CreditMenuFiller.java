package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Screens.GUI.MenuScreen;

public class    CreditMenuFiller extends MenuFiller {
    private static final String TAG = CreditMenuFiller.class.getName();

    private Assets assets;
    private I18NBundle i18NGameThreeBundle;

    private Label textCreditLabel;

    public CreditMenuFiller(MenuScreen menuScreen){
        super(menuScreen,"title.credits");

        assets = Assets.getInstance();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();

    }

    @Override
    public void build() {
        super.build();
    }


    @Override
    protected void defineElements() {
        textCreditLabel = new Label(i18NGameThreeBundle.format("creditsScreen.text"),labelStyleSmall);
        textCreditLabel.setWrap(true);
    }

    @Override
    protected void addAction() {
    }

    @Override
    protected void addToTable() {
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }
        mainTable.add(textCreditLabel).growX().padRight(50).padLeft(50);
    }
}
