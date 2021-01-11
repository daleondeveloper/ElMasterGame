package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;

import java.util.Random;

public class BlockControllerDarkMode extends BlockController {
    private static final String TAG = BlockControllerDarkMode.class.getName();




    public BlockControllerDarkMode(PlayScreen playScreen, GameWorld gameWorld) {
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
