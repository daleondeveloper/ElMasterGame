package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Sprites.Blocks.Block;
import com.daleondeveloper.tools.GameConstants;

public class BlockControllerLightMode extends BlockController {
    private static final String TAG = BlockControllerLightMode.class.getName();
    private static final float TIME_TO_CRATE_SPECIAL_BLOCK = 10f;

    private float timeToCreateSpecialBlock;
    private Block specialBlock;

    public BlockControllerLightMode(GameWorld gameWorld) {
        super(gameWorld);
        timeToCreateSpecialBlock = 0;
        type = ControllerType.LIGHT;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeToCreateSpecialBlock += deltaTime;
        if(timeToCreateSpecialBlock > TIME_TO_CRATE_SPECIAL_BLOCK){
            addBlockInRandomPosition(GameConstants.BLOCK_LIGHT);
            timeToCreateSpecialBlock = 0;
        }
    }
}
