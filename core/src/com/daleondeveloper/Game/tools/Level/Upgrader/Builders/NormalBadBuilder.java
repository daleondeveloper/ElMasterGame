package com.daleondeveloper.Game.tools.Level.Upgrader.Builders;

import com.badlogic.gdx.files.FileHandle;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderBuilder;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderConstats;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.AddBlockSpawn;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.BlockCountToDeleteLine;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.BlockSpawnTime;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.BlockSpeed;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.ReviveCountUpdate;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.SpawnBlockDelete;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.SpawnBlockSwitchBlockType;
import com.daleondeveloper.tools.GameConstants;


public class NormalBadBuilder extends UpgraderBuilder {

    private FileHandle fileHandler;
    private Upgrader upgrader ;

    public NormalBadBuilder(GameWorld gameWorld) {
        super(gameWorld);
    }

    protected void addUpgraders(){
        addDarkBlockSpawner();
        addStandarTwoBlockSpawner();
        addSnowBlockSpawner();
        reduceReviveCount();
        reduceBlockTimeSpawnAndUpBlockSpeed();
        upBlockCountToDeleteLine();
        changeTwoBlockSpawnToDark();
        deleteAllSpawnerAndAddFire();
        switchDarkBlockToIce();
        switchIceBlockToDark();
    }
    private void addDarkBlockSpawner(){
        if(gameWorld.getBlockController().getBlockSpawners().size() < 4) {
            upgrader = new AddBlockSpawn(gameWorld, GameConstants.BLOCK_DARK, 6);
            upgrader.setInfo(i18NBundle.format("upgradeNormal.addDarkBlockSpawner"));
            upgraderList.add(upgrader);
        }
    }
    private void addStandarTwoBlockSpawner(){
        if(gameWorld.getBlockController().getBlockSpawners().size() < 4) {
            upgrader = new AddBlockSpawn(gameWorld, GameConstants.BLOCK_DARK, 4);
            upgrader.setNextUpgrader(new AddBlockSpawn(gameWorld,GameConstants.BLOCK_CLASSIC,7));
            upgrader.setInfo(i18NBundle.format("upgradeNormal.addDarkBlockSpawner"));
            upgraderList.add(upgrader);
        }
    }
    private void addSnowBlockSpawner(){
        if(gameWorld.getBlockController().getBlockSpawners().size() < 4) {
            upgrader = new AddBlockSpawn(gameWorld, GameConstants.BLOCK_SNOW, 6);
            upgrader.setInfo(i18NBundle.format("upgradeNormal.addSnowBlockSpawner"));
            upgraderList.add(upgrader);
        }
    }
    private void reduceReviveCount(){
        if(UpgraderConstats.getReviveCount() > -1){
            upgrader = new ReviveCountUpdate(gameWorld,-1);
            upgrader.setInfo(i18NBundle.format("upgradeNormal.reduceReviveCount"));
            upgraderList.add(upgrader);
        }
    }
    private void reduceBlockTimeSpawnAndUpBlockSpeed(){
        if(UpgraderConstats.getBlockTimeSpawn() > -4 && UpgraderConstats.getBlockSpeed() > -50){
            upgrader = new BlockSpawnTime(gameWorld, -0.5f);
            upgrader.setNextUpgrader(new BlockSpeed(gameWorld, -10f));
            upgrader.setInfo(i18NBundle.format("upgradeNormal.reduceBlockTimeSpawnAndUpBlockSpeed"));
            upgraderList.add(upgrader);
        }
    }
    private void upBlockCountToDeleteLine(){
        if(UpgraderConstats.getBlockCountToDelete() < 10){
            upgrader = new BlockCountToDeleteLine(gameWorld,1);
            upgrader.setInfo(i18NBundle.format("upgradeNormal.upBlockCountToDeleteLine"));
            upgraderList.add(upgrader);
        }
    }
    private void changeTwoBlockSpawnToDark(){
        if(gameWorld.getBlockController().getBlockSpawners().size() > 2){
            upgrader = new SpawnBlockSwitchBlockType(gameWorld,2,-1,GameConstants.BLOCK_DARK);
            upgrader.setInfo(i18NBundle.format("upgradeNormal.changeTwoBlockSpawnToDark"));
            upgraderList.add(upgrader);
        }
    }
    private void deleteAllSpawnerAndAddFire(){
        upgrader = new SpawnBlockDelete(gameWorld,gameWorld.getBlockController().getBlockSpawners().size(),-1);
        upgrader.setNextUpgrader(new AddBlockSpawn(gameWorld,GameConstants.BLOCK_FIRE,1));
        upgrader.setInfo(i18NBundle.format("upgradeNormal.deleteAllSpawnerAndAddFire"));
        upgraderList.add(upgrader);
    }
    private void switchDarkBlockToIce(){
        upgrader = new SpawnBlockSwitchBlockType(gameWorld,100,GameConstants.BLOCK_DARK,GameConstants.BLOCK_SNOW);
        upgrader.setInfo(i18NBundle.format("upgradeNormal.switchDarkBlockToIce"));
        upgraderList.add(upgrader);
    }
    private void switchIceBlockToDark(){
        upgrader = new SpawnBlockSwitchBlockType(gameWorld,100,GameConstants.BLOCK_SNOW,GameConstants.BLOCK_DARK);
        upgrader.setInfo(i18NBundle.format("upgradeNormal.switchIceBlockToDark"));
        upgraderList.add(upgrader);
    }
}
