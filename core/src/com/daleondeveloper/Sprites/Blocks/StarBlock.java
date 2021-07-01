package com.daleondeveloper.Sprites.Blocks;

import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;

public class StarBlock extends Block {
    public StarBlock(GameWorld gameWorld, BlockController blockController, float x, float y, float width, float height) {
        super(gameWorld, blockController, x, y, width, height);
        textureRegionBlock = Assets.getInstance().getAssetBlock().getBlockStar();
        blockType = BlockType.STAR;
    }
}
