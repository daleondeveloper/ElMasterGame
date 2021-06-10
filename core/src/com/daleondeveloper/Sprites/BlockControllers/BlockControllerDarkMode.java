package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.tools.GameConstants;

public class BlockControllerDarkMode extends BlockController {
    private static final String TAG = BlockControllerDarkMode.class.getName();
    private static final float TIME_TO_CRATE_SPECIAL_BLOCK = 10f;


    private float timeToCreateSpecialBlock;


    public BlockControllerDarkMode(GameWorld gameWorld) {
        super(gameWorld);
        type = ControllerType.DARK;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeToCreateSpecialBlock += deltaTime;
        if(timeToCreateSpecialBlock > TIME_TO_CRATE_SPECIAL_BLOCK){
            addBlockInRandomPosition(GameConstants.BLOCK_DARK);
            timeToCreateSpecialBlock = 0;
        }
    }
}
