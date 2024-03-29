package com.daleondeveloper.Game.tools.Level.Upgrader.Builders;

import com.badlogic.gdx.files.FileHandle;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderBuilder;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderConstats;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.AddBlockSpawn;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.BlockSpawnTime;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.BlockSpeed;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.DeleteBlock;
import com.daleondeveloper.tools.GameConstants;


public class EasyBadBuilder extends UpgraderBuilder {

    private FileHandle fileHandler;
    private Upgrader upgrader;

    public EasyBadBuilder(GameWorld gameWorld) {
        super(gameWorld);
    }

    protected void addUpgraders(){
        increaseBlockSpeed();
        decreaseBlockTimeSpawn();
        addClassicBlockSpawn();
        decreaseBlockTimeSpawnAndDeacreaseBlockSpeed();
        increaseBlockTimeSpawnAndIncreaseBlockSpeed();
        clearAllBlocksAndAddDarkBlockSpawn();
    }
    private void increaseBlockSpeed(){
        if(UpgraderConstats.getBlockSpeed() > -50){
            upgrader = new BlockSpeed(gameWorld, -10f);
            upgrader.setInfo(i18NBundle.format("upgradeEasy.increaseBlockSpeed"));
            upgraderList.add(upgrader);
        }
    }
    private void decreaseBlockTimeSpawn(){
        if(UpgraderConstats.getBlockTimeSpawn() > -4){
            upgrader = new BlockSpawnTime(gameWorld, -0.5f);
            upgrader.setInfo(i18NBundle.format("upgradeEasy.decreaseTimeSpawn"));
            upgraderList.add(upgrader);
        }
    }
    private void addClassicBlockSpawn(){
        if(gameWorld.getBlockController().getBlockSpawners().size() < 5){
            upgrader = new AddBlockSpawn(gameWorld, GameConstants.BLOCK_CLASSIC,5);
            upgrader.setInfo(i18NBundle.format("upgradeEasy.addClassicBlockSpawn"));
            upgraderList.add(upgrader);
        }
    }
    private void increaseBlockTimeSpawnAndIncreaseBlockSpeed() {
        if (UpgraderConstats.getBlockSpeed() > -50 && UpgraderConstats.getBlockTimeSpawn() > -4) {
            upgrader = new BlockSpawnTime(gameWorld, 1.5f);
            upgrader.setNextUpgrader(new BlockSpeed(gameWorld, -20));
            upgrader.setInfo(i18NBundle.format("upgradeEasy.increaseBlockTimeSpawnAndIncreaseBlockSpeed"));
            upgraderList.add(upgrader);
        }
    }
     private void decreaseBlockTimeSpawnAndDeacreaseBlockSpeed() {
        if (UpgraderConstats.getBlockSpeed() > -50 && UpgraderConstats.getBlockTimeSpawn() > -4) {
            upgrader = new BlockSpawnTime(gameWorld, -1f);
            upgrader.setNextUpgrader(new BlockSpeed(gameWorld, 20));
            upgrader.setInfo(i18NBundle.format("upgradeEasy.decreaseBlockTimeSpawnAndDeacreaseBlockSpeed"));
            upgraderList.add(upgrader);
        }
    }
    private void clearAllBlocksAndAddDarkBlockSpawn(){
        upgrader = new AddBlockSpawn(gameWorld,GameConstants.BLOCK_DARK,8);
        upgrader.setNextUpgrader(new DeleteBlock(gameWorld,gameWorld.getBlockController().getArrayBlock().size(),-1));
        upgrader.setInfo(i18NBundle.format("upgradeEasy.clearAllBlocksAndAddDarkBlockSpawn"));
        upgraderList.add(upgrader);
    }


}
