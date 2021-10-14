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
            upgrader.setInfo("Збільшити швидкість блоків");
            upgraderList.add(upgrader);
        }
    }
    private void decreaseBlockTimeSpawn(){
        if(UpgraderConstats.getBlockTimeSpawn() > -4){
            upgrader = new BlockSpawnTime(gameWorld, -0.5f);
            upgrader.setInfo("Зменшити час створення блоків");
            upgraderList.add(upgrader);
        }
    }
    private void addClassicBlockSpawn(){
        if(gameWorld.getBlockController().getBlockSpawners().size() < 5){
            upgrader = new AddBlockSpawn(gameWorld, GameConstants.BLOCK_CLASSIC,5);
            upgrader.setInfo("Додати спавн блоку");
            upgraderList.add(upgrader);
        }
    }
    private void increaseBlockTimeSpawnAndIncreaseBlockSpeed() {
        if (UpgraderConstats.getBlockSpeed() > -50 && UpgraderConstats.getBlockTimeSpawn() > -4) {
            upgrader = new BlockSpawnTime(gameWorld, 1.5f);
            upgrader.setNextUpgrader(new BlockSpeed(gameWorld, -20));
            upgrader.setInfo("Збільшити час створення блоків і збільшити швидкість блоків");
            upgraderList.add(upgrader);
        }
    }
     private void decreaseBlockTimeSpawnAndDeacreaseBlockSpeed() {
        if (UpgraderConstats.getBlockSpeed() > -50 && UpgraderConstats.getBlockTimeSpawn() > -4) {
            upgrader = new BlockSpawnTime(gameWorld, -1f);
            upgrader.setNextUpgrader(new BlockSpeed(gameWorld, 20));
            upgrader.setInfo("Зменшити час створення блоків і зменшити швидкість блоків");
            upgraderList.add(upgrader);
        }
    }
    private void clearAllBlocksAndAddDarkBlockSpawn(){
        upgrader = new DeleteBlock(gameWorld,gameWorld.getBlockController().getArrayBlock().size(),-1);
        upgrader.setNextUpgrader(new AddBlockSpawn(gameWorld,GameConstants.BLOCK_DARK,8));
        upgrader.setInfo("Видалити всі блоки і додати спавн темних блоків");
        upgraderList.add(upgrader);
    }


}
