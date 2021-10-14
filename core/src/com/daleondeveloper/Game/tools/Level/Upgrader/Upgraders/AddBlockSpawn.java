package com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Sprites.BlockControllers.BlockSpawner;

import java.util.Random;

public class AddBlockSpawn extends Upgrader {

    private int type;
    private float time;
    private Random rnd;

    public AddBlockSpawn(GameWorld gameWorld,int type, float time ) {
        super(gameWorld);
        this.type = type;
        this.time = time;
        rnd = new Random();
    }

    @Override
    protected void upgrade() {
        new BlockSpawner(gameWorld.getBlockController(),type,time);
    }
}
