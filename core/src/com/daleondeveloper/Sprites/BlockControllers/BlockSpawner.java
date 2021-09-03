package com.daleondeveloper.Sprites.BlockControllers;

public class BlockSpawner {
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
}
