package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.Block;

import java.util.Random;

public class BlockControllerFireMode extends BlockController {
    private static final String TAG = BlockControllerFireMode.class.getName();
    private static final float TIME_TO_CRATE_SPECIAL_BLOCK = 10f;

    private float timeToCreateSpecialBlock;
    private Block specialBlock;


    public BlockControllerFireMode(PlayScreen playScreen, GameWorld gameWorld) {
        super(playScreen, gameWorld);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeToCreateSpecialBlock += deltaTime;
        if(blockCreateTime > 4f){
            int blockTypeNumber =new Random().nextInt(4);
            if(blockTypeNumber == 4){
                blockTypeNumber = 5;
            }
            addBlock(blockTypeNumber);
            blockCreateTime = 0;
        }

        if(timeToCreateSpecialBlock > TIME_TO_CRATE_SPECIAL_BLOCK && specialBlock == null){
            specialBlock = addBlock(4);
            timeToCreateSpecialBlock = 0;
        }

        if(specialBlock != null && specialBlock.isIdle()){
            int posMasX = (int)(specialBlock.getReturnCellsPosition() / 10 ) - 5;
            int posMasY = (int)(specialBlock.getReturnCellsPositionY() / 10 ) - 15;

            int xStartPosInCycle = posMasX - 1;
            int yStartPosInCycle = posMasY - 1;
            if(xStartPosInCycle < 0){xStartPosInCycle = 0;}
            if(yStartPosInCycle < 0){yStartPosInCycle = 0;}

            int xEndPosInCycle = posMasX + 1;
            int yEndPosInCycle = posMasY + 1;
            if(xEndPosInCycle >= blocksMas.length){xEndPosInCycle = blocksMas.length - 1;}
            if(yEndPosInCycle >= blocksMas[0].length){yEndPosInCycle = blocksMas[0].length - 1;}

            for(int i = xStartPosInCycle; i <= xEndPosInCycle; i++){
                for(int j = yStartPosInCycle; j <= yEndPosInCycle; j++) {
                    if (blocksMas[i][j] != null) {
                        blocksMas[i][j].delete();
                    }
                }
            }
            specialBlock = null;
        }
    }
}
