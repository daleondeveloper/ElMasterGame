package com.daleondeveloper.Sprites.Blocks;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;

public class DarkBlock extends Block {
    public DarkBlock(GameWorld gameWorld, BlockController blockController, int blockTypeNumber, float x, float y, float width, float height) {
        super(gameWorld, blockController, blockTypeNumber, x, y, width, height);
        textureRegionBlock = Assets.getInstance().getAssetBlock().getBlockDark();
        blockType = BlockType.DARK;
        this.setBlockTypeNumber(1);
    }

    @Override
    public void push(Float turnImpulse) {
        return;
    }

    @Override
    protected void statePush(float deltaTime) {
        fall();
    }
}
