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
            upgrader.setInfo("Додати блок спавнер темних блоків");
            upgraderList.add(upgrader);
        }
    }
    private void addStandarTwoBlockSpawner(){
        if(gameWorld.getBlockController().getBlockSpawners().size() < 4) {
            upgrader = new AddBlockSpawn(gameWorld, GameConstants.BLOCK_DARK, 4);
            upgrader.setNextUpgrader(new AddBlockSpawn(gameWorld,GameConstants.BLOCK_CLASSIC,7));
            upgrader.setInfo("Додати 2 блок спавнери класичних блоків");
            upgraderList.add(upgrader);
        }
    }
    private void addSnowBlockSpawner(){
        if(gameWorld.getBlockController().getBlockSpawners().size() < 4) {
            upgrader = new AddBlockSpawn(gameWorld, GameConstants.BLOCK_SNOW, 6);
            upgrader.setInfo("Додати блок спавнер зимових блоків");
            upgraderList.add(upgrader);
        }
    }
    private void reduceReviveCount(){
        if(UpgraderConstats.getReviveCount() > -1){
            upgrader = new ReviveCountUpdate(gameWorld,-1);
            upgrader.setInfo("Зменшити кількість воскресінь");
            upgraderList.add(upgrader);
        }
    }
    private void reduceBlockTimeSpawnAndUpBlockSpeed(){
        if(UpgraderConstats.getBlockTimeSpawn() > -4 && UpgraderConstats.getBlockSpeed() > -50){
            upgrader = new BlockSpawnTime(gameWorld, -0.5f);
            upgrader.setNextUpgrader(new BlockSpeed(gameWorld, -10f));
            upgrader.setInfo("Зменшити час створення блоків і збільшити їх швидкість");
            upgraderList.add(upgrader);
        }
    }
    private void upBlockCountToDeleteLine(){
        if(UpgraderConstats.getBlockCountToDelete() < 10){
            upgrader = new BlockCountToDeleteLine(gameWorld,1);
            upgrader.setInfo("Збільшити кількість блоків для стирання на 1");
            upgraderList.add(upgrader);
        }
    }
    private void changeTwoBlockSpawnToDark(){
        if(gameWorld.getBlockController().getBlockSpawners().size() > 2){
            upgrader = new SpawnBlockSwitchBlockType(gameWorld,2,-1,GameConstants.BLOCK_DARK);
            upgrader.setInfo("Замінити два блок спавни на темні");
            upgraderList.add(upgrader);
        }
    }
    private void deleteAllSpawnerAndAddFire(){
        upgrader = new SpawnBlockDelete(gameWorld,gameWorld.getBlockController().getBlockSpawners().size(),-1);
        upgrader.setNextUpgrader(new AddBlockSpawn(gameWorld,GameConstants.BLOCK_FIRE,1));
        upgrader.setInfo("Замінити стихійні спавнери блоків на спавнер вогняних блоків");
    }
    private void switchDarkBlockToIce(){
        upgrader = new SpawnBlockSwitchBlockType(gameWorld,100,GameConstants.BLOCK_DARK,GameConstants.BLOCK_SNOW);
        upgrader.setInfo("Замінити блоки тьми на блоки льоду");
    }
    private void switchIceBlockToDark(){
        upgrader = new SpawnBlockSwitchBlockType(gameWorld,100,GameConstants.BLOCK_SNOW,GameConstants.BLOCK_DARK);
        upgrader.setInfo("Замінити блоки льоду на блоки тьми");
    }
}
