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
    public  String toString(){
        info = "";
        if(nextUpgrader != null) {
            info += nextUpgrader.toString() + "\n";
        }
        return info;
    }

    public Upgrader getNextUpgrader() {
        return nextUpgrader;
    }

    public void setNextUpgrader(Upgrader nextUpgrader) {
        this.nextUpgrader = nextUpgrader;
    }
}
