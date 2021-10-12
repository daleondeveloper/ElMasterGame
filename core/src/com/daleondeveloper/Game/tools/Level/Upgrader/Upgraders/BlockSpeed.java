package com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderConstats;

public class BlockSpeed extends Upgrader {

    private float speed;

    public BlockSpeed(GameWorld gameWorld, float speed) {
        super(gameWorld);
        this.speed = speed;
    }

    @Override
    protected void upgrade() {
        UpgraderConstats.setBlockSpeed(speed);

    }

    @Override
    public String toString() {
        info = "";
        info += super.toString();
        return info + "Збільшити швидкість \nблоків";
    }

}
