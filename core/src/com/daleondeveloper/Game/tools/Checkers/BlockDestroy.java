package com.daleondeveloper.Game.tools.Checkers;

import com.daleondeveloper.Sprites.BlockControllers.BlockController;
import com.daleondeveloper.Sprites.Blocks.Block;

import java.util.List;

public class BlockDestroy extends LvlCondition {

    private BlockController blockController;
    private Block.BlockType blockType;
    private int count;

    public BlockDestroy(BlockController blockController, Block.BlockType blockType, int count) {
        this.blockController = blockController;
        this.blockType = blockType;
        this.count = count;
    }

    @Override
    public void checkCondition() {
        conditionFulfilled = true;
        List<Block> blocks = blockController.getArrayBlock();
        for(Block block : blocks){
            if(block.getBlockType() == blockType){
                break;
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        if(!conditionFulfilled)checkCondition();
    }

    @Override
    public String save() {
        String s = "<levelTasks " +
                "type = \"blockDestroy\" value = \"" + count + "\"" +
                "/>\n";
        return s;
    }
}
