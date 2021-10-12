package com.daleondeveloper.Game.tools.Level.Upgrader;

import com.daleondeveloper.Game.GameWorld;

import java.util.ArrayList;
import java.util.Random;

public abstract class UpgraderBuilder {

    protected GameWorld gameWorld;
    protected ArrayList<Upgrader> upgraderList;
    protected Random rnd;

    public UpgraderBuilder(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        rnd = new Random();
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
