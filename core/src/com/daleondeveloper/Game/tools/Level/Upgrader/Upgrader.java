package com.daleondeveloper.Game.tools.Level.Upgrader;

import com.daleondeveloper.Game.GameWorld;

public abstract class Upgrader {
    private static final String TAG = Upgrader.class.getName();

    protected GameWorld gameWorld;
    private Upgrader nextUpgrader;
    protected String info;


    public Upgrader(GameWorld gameWorld){
        this.gameWorld = gameWorld;
    }

    public void upgradeLevel(){
        if(nextUpgrader != null) {
            nextUpgrader.upgradeLevel();
        }
        upgrade();
        if(nextUpgrader == null){
            gameWorld.nextLvl();
            gameWorld.resumeGame();
        }
    }
    protected abstract void upgrade();

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public  String toString(){
        return null;
    }

    public Upgrader getNextUpgrader() {
        return nextUpgrader;
    }

    public Upgrader setNextUpgrader(Upgrader nextUpgrader) {
        this.nextUpgrader = nextUpgrader;
        return nextUpgrader;
    }
}
