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
            Block[][] blocksMas = blockController.getBlocksMas();
            int posMasY = (int) (getReturnCellsPositionY() / 10) - 15;
            int posMasX = (int) (getReturnCellsPosition() / 10) - 5;

            if (posMasX > 0 && blocksMas[posMasX - 1][posMasY] != null) {
                Block block = blocksMas[posMasX - 1][posMasY];
                if (!block.isIFall()) {
                    block.idle();
                }
            }
            if (posMasX < blocksMas.length - 1 && blocksMas[posMasX + 1][posMasY] != null) {
                Block block = blocksMas[posMasX + 1][posMasY];
                if (!block.isIFall()) {
                    block.idle();
                }
            }
            if (posMasY > 0 && blocksMas[posMasX][posMasY - 1] != null) {
                Block block = blocksMas[posMasX][posMasY - 1];
                if (!block.isIFall()) {
                    block.idle();
                }
            }
            if (posMasY < blocksMas[0].length - 1 && blocksMas[posMasX][posMasY + 1] != null) {
                Block block = blocksMas[posMasX][posMasY + 1];
                if (!block.isIFall()) {
                    block.idle();
                }
            }
        }
    }
}