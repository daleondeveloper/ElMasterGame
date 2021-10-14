package com.daleondeveloper.Game.tools.Level.Upgrader.Builders;

import com.badlogic.gdx.files.FileHandle;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderBuilder;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderConstats;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.BlockSpawnTime;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.BlockSpeed;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.ChangeTypeBlock;


public class NormalGoodBuilder extends UpgraderBuilder {

    private FileHandle fileHandler;

    public NormalGoodBuilder(GameWorld gameWorld) {
        super(gameWorld);
    }

    protected void addUpgraders(){
        if(UpgraderConstats.getBlockSpeed() > -50){
            upgraderList.add(new BlockSpeed(gameWorld, -10f));
        }
        if(UpgraderConstats.getBlockTimeSpawn() > -4){
            upgraderList.add(new BlockSpawnTime(gameWorld, -0.5f));
        }
        upgraderList.add(new ChangeTypeBlock(gameWorld,3,5));
    }
}
