package com.daleondeveloper.Game.tools.Level.Upgrader;

import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.GameWorld;

import java.util.ArrayList;
import java.util.Random;

public abstract class UpgraderBuilder {

    protected GameWorld gameWorld;
    protected ArrayList<Upgrader> upgraderList;
    protected Random rnd;
    protected I18NBundle i18NBundle;


    public UpgraderBuilder(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        rnd = new Random();
        i18NBundle = Assets.getInstance().getI18NElementMaster().getI18NElmasterBundle();
        upgraderList = new ArrayList<Upgrader>();
        addUpgraders();
    }
    public Upgrader getUpgrader(GameWorld gameWorld){
        if(upgraderList.isEmpty()){
            updateUpgraders();
        }
        int arrIndex = rnd.nextInt(upgraderList.size());
        Upgrader upgrader = upgraderList.get(arrIndex);
        upgraderList.remove(arrIndex);
        return upgrader;
    }
    protected abstract void addUpgraders();
    public void updateUpgraders(){
        upgraderList.clear();
        addUpgraders();
    }
    
}
