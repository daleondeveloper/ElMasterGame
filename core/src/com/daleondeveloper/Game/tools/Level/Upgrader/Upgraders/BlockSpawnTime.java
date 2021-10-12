package com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderConstats;

public class BlockSpawnTime extends Upgrader {

    private float time;

    public BlockSpawnTime(GameWorld gameWorld, float time) {
        super(gameWorld);
        this.time = time;
    }

    @Override
    protected void upgrade() {
        UpgraderConstats.setBlockTimeSpawn(time);

    }

    @Override
    public String toString() {
        info = super.toString();
        return info + "Зменшити час \nстворення блоків";
    }

}
