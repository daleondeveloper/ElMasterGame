package com.daleondeveloper.Game.tools.Level.Upgrader.Builders;

import com.badlogic.gdx.files.FileHandle;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderBuilder;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderConstats;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.AddBlockSpawn;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.BlockCountToDeleteLine;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.BlockSpeed;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.CreateBlock;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.DeleteBlock;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.ReviveCountUpdate;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders.SpawnBlockSwitchBlockType;
import com.daleondeveloper.tools.GameConstants;


public class HardBadBuilder extends UpgraderBuilder {

    private FileHandle fileHandler;
    private Upgrader upgrader;

    public HardBadBuilder(GameWorld gameWorld) {
        super(gameWorld);
    }

    protected void addUpgraders(){
        increaseBlockCountToDeleteLine();
        switchAllBlockToDark();
        switchAllBlockToIce();
        addFourLightBlock();
        addFireBlockSpawn();
        addLightBlockSpawn();
        increaseReviveCountAndIncreaseBlockSpeed();
        deleteAllBlockAndAddToDarkSpawner();
    }
    private void increaseBlockCountToDeleteLine(){
        if(UpgraderConstats.getBlockCountToDelete() < 6){
            upgrader = new BlockCountToDeleteLine(gameWorld,2);
            upgrader.setInfo("Збільшити кількість блоків для стирання на 2");
            upgraderList.add(upgrader);
        }
    }
    private void switchAllBlockToDark(){
        upgrader = new SpawnBlockSwitchBlockType(gameWorld,200,-1, GameConstants.BLOCK_DARK);
        upgrader.setInfo("Замінити всі блоки на блоки тьми");
        upgraderList.add(upgrader);
    }
    private void switchAllBlockToIce(){
        upgrader = new SpawnBlockSwitchBlockType(gameWorld,200,-1,GameConstants.BLOCK_SNOW);
        upgrader.setInfo("Замінити всі блоки на блоки льоду");
        upgraderList.add(upgrader);
    }
    private void addFourLightBlock(){
        upgrader = new CreateBlock(gameWorld,4,GameConstants.BLOCK_LIGHT);
        upgrader.setInfo("Створити 4 молнії");
        upgraderList.add(upgrader);
    }
    private void addFireBlockSpawn(){
        upgrader = new AddBlockSpawn(gameWorld,GameConstants.BLOCK_FIRE, 10);
        upgrader.setInfo("Додани спавн блоків вогню");
        upgraderList.add(upgrader);
    }
    private void addLightBlockSpawn(){
        upgrader = new AddBlockSpawn(gameWorld,GameConstants.BLOCK_LIGHT,12);
        upgrader.setInfo("Додати спавн блоків молнії");
        upgraderList.add(upgrader);
    }
    private void increaseReviveCountAndIncreaseBlockSpeed(){
        upgrader = new ReviveCountUpdate(gameWorld,2);
        upgrader.setNextUpgrader(new BlockSpeed(gameWorld, -30));
        upgrader.setInfo("Додати два воскресіння і сильно збільшити швидкість блоків");
        upgraderList.add(upgrader);
    }
    private void deleteAllBlockAndAddToDarkSpawner(){
        upgrader = new DeleteBlock(gameWorld,gameWorld.getBlockController().getArrayBlock().size(),-1);
        upgrader.setNextUpgrader(new AddBlockSpawn(gameWorld,GameConstants.BLOCK_DARK,6)).
        setNextUpgrader(new AddBlockSpawn(gameWorld,GameConstants.BLOCK_DARK, 7));
        upgrader.setInfo("Видалити всі блоки і додати два спавни темних блоків");
    }
}
