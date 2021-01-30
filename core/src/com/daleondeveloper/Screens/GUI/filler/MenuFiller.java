package com.daleondeveloper.Screens.GUI.filler;

//Абстрактний клас наслідники якого заповнюватимуть
//таблицю меню
public abstract  class MenuFiller {

    public void build(){
        defineElements();
        addAction();
        addToTable();
    }
    protected abstract void defineElements();
    protected abstract void addAction();
    protected abstract void addToTable();
    public void update(float deltaTime){};
}
