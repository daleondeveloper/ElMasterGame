package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Screens.GUI.Button.BackButton;
import com.daleondeveloper.Screens.GUI.MenuScreen;

//Абстрактний клас наслідники якого заповнюватимуть
//таблицю меню
public abstract  class MenuFiller {

    protected MenuScreen.MenuState menuType;
    protected MenuScreen menuScreen;
    protected Table mainTable;
    protected Label.LabelStyle labelStyleSmall;
    protected Label.LabelStyle labelStyleNormal;
    protected Label.LabelStyle labelStyleBig;
    protected Label.LabelStyle labelStyleCredit;
    protected Label.LabelStyle labelStyleTitle;
    private String title;

    protected MenuFiller(MenuScreen menuScreen, String title){
        this.menuScreen = menuScreen;
        this.title = title;
        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = Assets.getInstance().getAssetFonts().getSmall();
        labelStyleNormal = new Label.LabelStyle();
        labelStyleNormal.font = Assets.getInstance().getAssetFonts().getNormal();
        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = Assets.getInstance().getAssetFonts().getBig();
        labelStyleCredit = new Label.LabelStyle();
        labelStyleCredit.font = Assets.getInstance().getAssetFonts().getCredits();
        labelStyleTitle = new Label.LabelStyle();
        labelStyleTitle.font = Assets.getInstance().getAssetFonts().getGameTitle();
    }
    public void build(){
        mainTable = menuScreen.getWindowTable();
        defineElements();
        addAction();
        mainTable.clearChildren();
        addTitleToTable();
        addToTable();
    }
    protected abstract void defineElements();
    protected abstract void addAction();
    protected abstract void addToTable();
    public void update(float deltaTime){};

    protected void addTitleToTable(){
        Table labelTable = new Table();
        mainTable.top();
        mainTable.add(new BackButton(menuScreen)).height(15).width(15).right().padRight(30);
        mainTable.row();
        mainTable.add(labelTable).growX();
        labelTable.add().growX();
        labelTable.add(new Label(Assets.getInstance().getI18NElementMaster().getI18NElmasterBundle().format(title),
                labelStyleTitle));
        labelTable.add().growX();
        labelTable.row();
        mainTable.row();

    }

    public void setLabelStyleSmall(Label.LabelStyle labelStyleSmall) {
        this.labelStyleSmall = labelStyleSmall;
    }

    public void setLabelStyleNormal(Label.LabelStyle labelStyleNormal) {
        this.labelStyleNormal = labelStyleNormal;
    }

    public void setLabelStyleBig(Label.LabelStyle labelStyleBig) {
        this.labelStyleBig = labelStyleBig;
    }

    public void setLabelStyleCredit(Label.LabelStyle labelStyleCredit) {
        this.labelStyleCredit = labelStyleCredit;
    }

    public void setLabelStyleTitle(Label.LabelStyle labelStyleTitle) {
        this.labelStyleTitle = labelStyleTitle;
    }
}
