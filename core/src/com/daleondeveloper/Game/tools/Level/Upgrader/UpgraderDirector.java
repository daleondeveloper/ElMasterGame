package com.daleondeveloper.Game.tools.Level.Upgrader;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Builders.EasyBadBuilder;
import com.daleondeveloper.Game.tools.Level.Upgrader.Builders.EasyGoodBuilder;
import com.daleondeveloper.Game.tools.Level.Upgrader.Builders.HardBadBuilder;
import com.daleondeveloper.Game.tools.Level.Upgrader.Builders.NormalBadBuilder;

public class UpgraderDirector {

    private UpgraderBuilder builder;
    private GameWorld gameWorld;
    private EasyBadBuilder easyBadBuilder;
    private EasyGoodBuilder easyGoodBuilder;
    private NormalBadBuilder normalBadBuilder;
    private HardBadBuilder hardBadBuilder;

    public UpgraderDirector(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        easyBadBuilder = new EasyBadBuilder(gameWorld);
        easyGoodBuilder = new EasyGoodBuilder(gameWorld);
        normalBadBuilder = new NormalBadBuilder(gameWorld);
        hardBadBuilder = new HardBadBuilder(gameWorld);
    }

    public Upgrader buildUpgrader(String type){
        if(type.equals("easyGood")){
            builder = easyGoodBuilder;
        }
        if(type.equals("easyBad")){
            builder = easyBadBuilder;
        }
        if(type.equals("normalGood")){

        }
        if(type.equals("normalBad")){
            builder = normalBadBuilder;
        }
        if(type.equals("hardGood")){

        }
        if(type.equals("hardBad")){
            builder = hardBadBuilder;
        }
        if(type.equals("specialGood")){

        }
        if(type.equals("specialBad")){

        }
        updateBuilders();
        return builder.getUpgrader(gameWorld);
    }
    public void updateBuilders(){
        if(builder != null) {
            builder.updateUpgraders();
        }
    }
}
