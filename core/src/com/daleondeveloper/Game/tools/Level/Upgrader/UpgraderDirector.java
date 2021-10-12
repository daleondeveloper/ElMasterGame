package com.daleondeveloper.Game.tools.Level.Upgrader;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Builders.EasyBadBuilder;
import com.daleondeveloper.Game.tools.Level.Upgrader.Builders.EasyGoodBuilder;

public class UpgraderDirector {

    private UpgraderBuilder builder;
    private GameWorld gameWorld;
    private EasyBadBuilder easyBadBuilder;
    private EasyGoodBuilder easyGoodBuilder;

    public UpgraderDirector(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        easyBadBuilder = new EasyBadBuilder(gameWorld);
        easyGoodBuilder = new EasyGoodBuilder(gameWorld);
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

        }
        if(type.equals("hardGood")){

        }
        if(type.equals("hardBad")){

        }
        if(type.equals("specialGood")){

        }
        if(type.equals("specialBad")){

        }
        return builder.getUpgrader(gameWorld);
    }
    public void updateBuilders(){
        easyGoodBuilder.updateUpgraders();
        easyBadBuilder.updateUpgraders();
    }
}
