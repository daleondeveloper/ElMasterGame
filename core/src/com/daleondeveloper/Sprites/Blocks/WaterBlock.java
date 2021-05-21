package com.daleondeveloper.Sprites.Blocks;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;

public class WaterBlock extends Block {
    private static final String TAG = WaterBlock.class.getName();

    private static final float FREEZING_TIME = 10f;

    private float freezingTime;


    public WaterBlock(GameWorld gameWorld, BlockController blockController, int blockTypeNumber, float x, float y, float width, float height) {
        super(gameWorld, blockController, blockTypeNumber, x, y, width, height);

        textureRegionBlock = Assets.getInstance().getAssetBlock().getBlockWater();
        blockType = BlockType.SNOW;
        freezingTime = 0;
        this.setBlockTypeNumber(5);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(!isIdle()){
            freezingTime = 0;
        }
    }

    @Override
    protected void stateIdle(float deltaTime) {
        super.stateIdle(deltaTime);
        freezingTime += deltaTime;
        if(freezingTime < FREEZING_TIME) {
//            int posMasY = (int) (getReturnCellsPositionY() / 10) - 15;
//            int posMasX = (int) (getReturnCellsPosition() / 10) - 5;

            if ( gameGrid.getLeftBlockRelativeToObject(this) != null) {
                Block block = gameGrid.getLeftBlockRelativeToObject(this);
                if (block.isPush()) {
                    block.idle();
                    block.stateIdle(0);
                }
            }
            if (gameGrid.getRightBlockRelativeToObject(this) != null) {
                Block block = gameGrid.getRightBlockRelativeToObject(this);
                if (block.isPush()) {
                    block.idle();
                    block.stateIdle(0);

                }
            }
            if (gameGrid.getLowerBlockRelativeToObject(this) != null) {
                Block block = gameGrid.getLowerBlockRelativeToObject(this);
                if (block.isPush()) {
                    block.idle();
                }
            }
            if (gameGrid.getTopBlockRelativeToObject(this) != null) {
                Block block = gameGrid.getTopBlockRelativeToObject(this);
                if (block.isPush()) {
                    block.idle();
                }
            }
        }
    }
}
