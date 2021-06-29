package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

//Абстрактний клас наслідники якого заповнюватимуть
//таблицю меню
public abstract  class MenuFiller {

    public void build(){
        defineElements();
        addAction();
        addToTable();
    }
    protected void addBackButtonToTable(Table mainTable, Image backButton){
        mainTable.top();
        mainTable.add(backButton).height(15).width(15).right().padRight(30);
        mainTable.row();
    }
    protected abstract void defineElements();
    protected abstract void addAction();
    protected abstract void addToTable();
    public void update(float deltaTime){};
}
