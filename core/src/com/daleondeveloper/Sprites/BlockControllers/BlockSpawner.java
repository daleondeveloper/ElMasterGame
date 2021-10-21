package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.tools.Level.ElementSaved;
import com.daleondeveloper.Game.tools.Level.Upgrader.UpgraderConstats;

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
        float time = (timeToSpawn + UpgraderConstats.getBlockTimeSpawn());
        if(time < 1) {time = 1;};
        if(timeFromLastSpawn > time){
            timeFromLastSpawn = 0;
            blockController.addBlockInRandomPosition(blockType);
        }
    }

    public float getTimeToSpawn() {
        return timeToSpawn;
    }

    public void setTimeToSpawn(float timeToSpawn) {
        this.timeToSpawn = timeToSpawn;
    }

    public int getBlockType() {
        return blockType;
    }

    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }

    @Override
    public String save() {
        return     "<blockSpawner type = \""+ blockType +"\" value = \"" + (int)timeToSpawn + "\"/>";
    }

}
