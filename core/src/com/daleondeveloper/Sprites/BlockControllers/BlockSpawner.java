package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.tools.Level.ElementSaved;

public class BlockSpawner implements ElementSaved {
    private static final String TAG = BlockSpawner.class.getName();

    private BlockController blockController;
    private int blockType;
    private float timeFromLastSpawn;
    private float timeToSpawn;

    public BlockSpawner(BlockController bC, int blockType, float timeToSpawn){
        this.blockController = bC;
        this.blockType = blockType;
        this.timeToSpawn = timeToSpawn;
        timeFromLastSpawn = 0;
        blockController.addBlockSpawner(this);
    }
    public void update(float deltaTime){
        timeFromLastSpawn += deltaTime;
        if(timeFromLastSpawn > timeToSpawn){
            timeFromLastSpawn = 0;
            blockController.addBlockInRandomPosition(blockType);
        }
    }

    @Override
    public String save() {
        return     "<blockSpawner type = \""+ blockType +"\" value = \"" + (int)timeToSpawn + "\"/>";
    }
}
