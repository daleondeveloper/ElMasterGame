package com.daleondeveloper.Sprites.Blocks;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;

public class LightBlock extends Block {

    public LightBlock(GameWorld gameWorld, BlockController blockController, float x, float y, float width, float height) {
        super(gameWorld, blockController, x, y, width, height);

        textureRegionBlock = Assets.getInstance().getAssetBlock().getBlockLight();
        blockType = BlockType.LIGHT;
    }

    @Override
    protected void stateIdle(float deltaTime) {
        int posMasX = (int)(getReturnCellsPosition() / 10 ) - 5;

        for(int i = 0; i < gameGrid.getGridHeightLength(); i++){
            if(gameGrid.getBlockByCordinate(posMasX,i) != null){
                gameGrid.getBlockByCordinate(posMasX,i).delete();
            }
        }
    }

    @Override
    protected void statePush(float deltaTime) {
        stateIdle(deltaTime);
    }
}
