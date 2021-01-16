package com.daleondeveloper.Sprites.Blocks;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;

import java.util.ArrayList;

public class FireBlock extends Block {

    public FireBlock(GameWorld gameWorld, BlockController blockController, int blockTypeNumber, float x, float y, float width, float height) {
        super(gameWorld, blockController, blockTypeNumber, x, y, width, height);

        textureRegionBlock = Assets.getInstance().getAssetBlock().getBlockFire();
        blockType = BlockType.FIRE;
        this.setBlockTypeNumber(2);
    }

    @Override
    protected void stateIdle(float deltaTime) {
        Block[][] blocksMas = blockController.getBlocksMas();
        int posMasX = (int)(getReturnCellsPosition() / 10 ) - 5;
        int posMasY = (int)(getReturnCellsPositionY() / 10 ) - 15;

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
    }
    @Override
    protected void statePush(float deltaTime) {
        stateIdle(deltaTime);
    }
}
