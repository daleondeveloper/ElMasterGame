package com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Sprites.BlockControllers.BlockSpawner;
import com.daleondeveloper.tools.GameConstants;

import java.util.List;
import java.util.Random;

public class SpawnBlockSwitchBlockType extends Upgrader {

    private int count;
    private int oldType;
    private int newType;
    private Random rnd;

    public SpawnBlockSwitchBlockType(GameWorld gameWorld, int count, int oldType, int newType ) {
        super(gameWorld);
        this.count = count;
        this.oldType = oldType;
        this.newType =newType;
        rnd = new Random();
    }

    @Override
    protected void upgrade() {
        int count = 1;
        List<BlockSpawner> blocks = gameWorld.getBlockController().getBlockSpawners();
        for (BlockSpawner spawner : blocks) {
            if(spawner.getBlockType() == GameConstants.BLOCK_CLASSIC &&
            count > 0){
                count--;
                continue;
            }
                if (spawner.getBlockType() == oldType) {
                    spawner.setBlockType(newType);
                    count--;
                }
                if (count < 0) break;
            }
    }

    @Override
    public String toString() {
        info = "";
        info += super.toString();
        String s = "Змінити " + count + " спавнерів блоків";
        return info + s;
    }

}
