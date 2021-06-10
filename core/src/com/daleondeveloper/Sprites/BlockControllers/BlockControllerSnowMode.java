package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.tools.GameConstants;

public class BlockControllerSnowMode extends BlockController {
    private static final String TAG = BlockControllerSnowMode.class.getName();
    private static final float TIME_TO_CRATE_SPECIAL_BLOCK = 10f;


    private float timeToCreateSpecialBlock;

    public BlockControllerSnowMode(GameWorld gameWorld) {
        super(gameWorld);
        type = ControllerType.SNOW;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeToCreateSpecialBlock += deltaTime;
        if(timeToCreateSpecialBlock > TIME_TO_CRATE_SPECIAL_BLOCK){
            addBlockInRandomPosition(GameConstants.BLOCK_SNOW);
            timeToCreateSpecialBlock = 0;
        }

    }
}
