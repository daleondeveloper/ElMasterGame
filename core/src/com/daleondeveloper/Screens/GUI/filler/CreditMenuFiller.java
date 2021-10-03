package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Screens.GUI.Button.BackButton;
import com.daleondeveloper.Screens.GUI.MenuScreen;

public class    CreditMenuFiller extends MenuFiller {
    private static final String TAG = CreditMenuFiller.class.getName();

    private com.daleondeveloper.Screens.GUI.MenuScreen menuScreen;
    private Assets assets;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private Label.LabelStyle labelStyleSmall;
    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleLarge;

    private Label creditLabel;
    private Label textCreditLabel;

    public CreditMenuFiller(MenuScreen menuScreen){
        this.menuScreen = menuScreen;

        assets = Assets.getInstance();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();


        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assets.getAssetFonts().getSmall();
        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assets.getAssetFonts().getNormal();
        labelStyleLarge = new Label.LabelStyle();
        labelStyleLarge.font = assets.getAssetFonts().getBig();

    }

    @Override
    public void build() {
        mainTable = menuScreen.getWindowTable();
        super.build();
    }


    @Override
    protected void defineElements() {
        creditLabel = new Label(i18NGameThreeBundle.format("creditsScreen.title"),labelStyleMedium);
        textCreditLabel = new Label(i18NGameThreeBundle.format("creditsScreen.text"),labelStyleSmall);
        textCreditLabel.setWrap(true);
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
        labelTable.add(creditLabel);
        labelTable.add().growX();
        mainTable.row();
        mainTable.add(textCreditLabel).growX().padRight(50).padLeft(50);
    }
}
