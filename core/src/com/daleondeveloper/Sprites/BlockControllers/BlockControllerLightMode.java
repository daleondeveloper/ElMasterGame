package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.Block;

import java.util.Random;

public class BlockControllerLightMode extends BlockController {
    private static final String TAG = BlockControllerLightMode.class.getName();
    private static final float TIME_TO_CRATE_SPECIAL_BLOCK = 10f;

    private float timeToCreateSpecialBlock;
    private Block specialBlock;

    public BlockControllerLightMode(PlayScreen playScreen, GameWorld gameWorld) {
        super(playScreen, gameWorld);
        timeToCreateSpecialBlock = 0;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeToCreateSpecialBlock += deltaTime;
        if(blockCreateTime > 4f){
            int blockTypeNumber =new Random().nextInt(4);
            if(blockTypeNumber == 2){
                blockTypeNumber = 5;
            }
            addBlock(blockTypeNumber);
            blockCreateTime = 0;
        }

        if(timeToCreateSpecialBlock > TIME_TO_CRATE_SPECIAL_BLOCK && specialBlock == null){
            specialBlock = addBlock(2);
            timeToCreateSpecialBlock = 0;
        }

        if(specialBlock != null && specialBlock.isIdle()){
            int posMasX = (int)(specialBlock.getReturnCellsPosition() / 10 ) - 5;

            for(int i = 0; i < blocksMas[posMasX].length; i++){
                if(blocksMas[posMasX][i] != null){
                    blocksMas[posMasX][i].delete();
                }
            }
            specialBlock = null;
        }
    }
}
