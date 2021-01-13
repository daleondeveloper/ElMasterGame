package com.daleondeveloper.Sprites.Blocks;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;

public class LightBlock extends Block {

    public LightBlock(GameWorld gameWorld, BlockController blockController, int blockTypeNumber, float x, float y, float width, float height) {
        super(gameWorld, blockController, blockTypeNumber, x, y, width, height);

        textureRegionBlock = Assets.getInstance().getAssetBlock().getBlockLight();
        blockType = BlockType.LIGHT;
        this.setBlockTypeNumber(3);
    }

    @Override
    protected void stateIdle(float deltaTime) {
        Block[][] blocksMas = blockController.getBlocksMas();
        int posMasX = (int)(getReturnCellsPosition() / 10 ) - 5;

        for(int i = 0; i < blocksMas[posMasX].length; i++){
            if(blocksMas[posMasX][i] != null){
                blocksMas[posMasX][i].delete();
            }
        }
    }
}