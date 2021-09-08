package com.daleondeveloper.Game.tools.Checkers;

import com.daleondeveloper.Sprites.BlockControllers.BlockController;
import com.daleondeveloper.Sprites.Blocks.Block;
import com.daleondeveloper.Sprites.Blocks.StarBlock;

import java.util.List;

public class StarLvlCondition extends LvlCondition {

    private BlockController blockController;

    public StarLvlCondition(BlockController blockController) {
        this.blockController = blockController;
    }

    @Override
    public void checkCondition() {
        conditionFulfilled = true;
        List<Block> blocks = blockController.getArrayBlock();
        for(Block block : blocks){
            if(block instanceof StarBlock){
                conditionFulfilled = false;
                break;
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        checkCondition();
    }

    @Override
    public String save() {
        String s = "<levelTasks " +
                "type = \"star\" " +
                "/>\n";
        return s;
    }
}
