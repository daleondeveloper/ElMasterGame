package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;

import java.util.Random;

public class BlockControllerSpecialMode extends BlockController {
    private static final String TAG = BlockControllerSpecialMode.class.getName();




    public BlockControllerSpecialMode(PlayScreen playScreen, GameWorld gameWorld) {
        super(playScreen, gameWorld);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(blockCreateTime > 4f){
            addBlock(new Random().nextInt(4));
            blockCreateTime = 0;
        }
    }
}
