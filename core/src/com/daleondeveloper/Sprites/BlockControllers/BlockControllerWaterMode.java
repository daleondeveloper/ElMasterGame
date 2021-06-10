package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.tools.GameConstants;

public class BlockControllerWaterMode extends BlockController {
    private static final String TAG = BlockControllerWaterMode.class.getName();
    private static final float TIME_TO_CRATE_SPECIAL_BLOCK = 10f;


    private float timeToCreateSpecialBlock;



    public BlockControllerWaterMode(GameWorld gameWorld) {
        super(gameWorld);
        type = ControllerType.WATER;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeToCreateSpecialBlock += deltaTime;
        if(timeToCreateSpecialBlock > TIME_TO_CRATE_SPECIAL_BLOCK){
            addBlockInRandomPosition(GameConstants.BLOCK_WATER);
            timeToCreateSpecialBlock = 0;
        }

    }
}
