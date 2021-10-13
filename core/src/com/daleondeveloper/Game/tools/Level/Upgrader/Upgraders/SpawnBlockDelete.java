package com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Sprites.BlockControllers.BlockSpawner;

import java.util.List;
import java.util.Random;

public class SpawnBlockDelete extends Upgrader {

    private int count;
    private int type;
    private Random rnd;

    public SpawnBlockDelete(GameWorld gameWorld, int count, int type ) {
        super(gameWorld);
        this.count = count;
        this.type = type;
        rnd = new Random();
    }

    @Override
    protected void upgrade() {
        List<BlockSpawner> blocks = gameWorld.getBlockController().getBlockSpawners();
        if(count > blocks.size()){
            gameWorld.getBlockController().cleatBlockSpawner();

        }else {
            for (BlockSpawner spawner : blocks) {
                if (spawner.getBlockType() == type) {
                    gameWorld.getBlockController().deleteBlockSpawner(spawner);
                    count--;
                } else if (type == -1) {
                    gameWorld.getBlockController().deleteBlockSpawner(spawner);
                    count--;
                }
                if (count < 0) break;
            }
        }

        if(gameWorld.getBlockController().getBlockSpawners().isEmpty()){
            gameWorld.getBlockController().addBlockSpawner(
                    new BlockSpawner(gameWorld.getBlockController(),0,4)
            );
        }
    }

    @Override
    public String toString() {
        info = "";
        info += super.toString();
        String s = "Видалити " + count + " спавнерів блоків";
        return info + s;
    }

}
