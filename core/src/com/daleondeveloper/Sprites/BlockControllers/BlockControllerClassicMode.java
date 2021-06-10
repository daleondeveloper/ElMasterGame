package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.GameWorld;

import java.util.Random;

public class BlockControllerClassicMode extends BlockController {
    private static final String TAG = BlockControllerClassicMode.class.getName();




    public BlockControllerClassicMode(GameWorld gameWorld) {
        super( gameWorld);
        type = ControllerType.CLASSIC;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(blockCreateTime > 4f){
            addBlockInRandomPosition(new Random().nextInt(5));
            blockCreateTime = 0;
        }
    }
}
