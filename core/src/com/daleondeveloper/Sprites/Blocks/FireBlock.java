package com.daleondeveloper.Sprites.Blocks;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;

public class FireBlock extends Block {

    public FireBlock(GameWorld gameWorld, BlockController blockController, float x, float y, float width, float height) {
        super(gameWorld, blockController, x, y, width, height);

        textureRegionBlock = Assets.getInstance().getAssetBlock().getBlockFire();
        blockType = BlockType.FIRE;
        this.setBlockTypeNumber(2);
    }

    @Override
    protected void stateIdle(float deltaTime) {
        super.stateIdle(deltaTime);
        int posMasX = (int)(getReturnCellsPosition() / 10 ) - 5;
        int posMasY = (int)(getReturnCellsPositionY() / 10 ) - 15;

        int xStartPosInCycle = posMasX - 1;
        int yStartPosInCycle = posMasY - 1;
        if(xStartPosInCycle < 0){xStartPosInCycle = 0;}
        if(yStartPosInCycle < 0){yStartPosInCycle = 0;}

        int xEndPosInCycle = posMasX + 1;
        int yEndPosInCycle = posMasY + 1;
        if(xEndPosInCycle >= gameGrid.getGridWidthLength()){xEndPosInCycle = gameGrid.getGridWidthLength() - 1;}
        if(yEndPosInCycle >= gameGrid.getGridHeightLength()){yEndPosInCycle = gameGrid.getGridHeightLength() - 1;}

        if(gameGrid.getLowerBlockRelativeToObject(this) != null ||
        posMasY == 0)
        for(int i = xStartPosInCycle; i <= xEndPosInCycle; i++){
            for(int j = yStartPosInCycle; j <= yEndPosInCycle; j++) {
                if (gameGrid.getBlockByCordinate(i,j) != null) {
                    gameGrid.getBlockByCordinate(i,j).delete();
                }
            }
        }
    }
    @Override
    protected void statePush(float deltaTime) {
        stateIdle(deltaTime);
    }
}
