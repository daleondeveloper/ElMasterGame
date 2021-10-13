package com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Sprites.Blocks.Block;
import com.daleondeveloper.Sprites.Blocks.DarkBlock;
import com.daleondeveloper.Sprites.Blocks.FireBlock;
import com.daleondeveloper.Sprites.Blocks.LightBlock;
import com.daleondeveloper.Sprites.Blocks.SnowBlock;
import com.daleondeveloper.Sprites.Blocks.WaterBlock;
import com.daleondeveloper.tools.GameConstants;

import java.util.List;
import java.util.Random;

public class DeleteBlock extends Upgrader {

    private int count;
    private int type;
    private Random rnd;

    public DeleteBlock(GameWorld gameWorld, int count,int type ) {
        super(gameWorld);
        this.count = count;
        this.type = type;
        rnd = new Random();
    }

    @Override
    protected void upgrade() {
        List<Block> blocks = gameWorld.getBlockController().getArrayBlock();
//        if(count > blocks.size()){
//            for(Block block : blocks){
//                block.delete();
//            }
//        }
        for(int i = 0; i < count; i ++){
            Block block = blocks.get(rnd.nextInt(blocks.size()));
            switch (type){
                case GameConstants
                        .BLOCK_LIGHT : {
                    if(block instanceof LightBlock){
                        block.delete();
                        continue;
                    }
                    break;
                }
                case GameConstants
                        .BLOCK_SNOW : {
                    if(block instanceof SnowBlock){
                        block.delete();
                        continue;
                    }
                    break;
                }
                case GameConstants
                        .BLOCK_FIRE : {
                    if(block instanceof FireBlock){
                        block.delete();
                        continue;
                    }
                    break;
                }
                case GameConstants
                        .BLOCK_WATER : {
                    if(block instanceof WaterBlock){
                        block.delete();
                        continue;
                    }
                    break;
                }
                case GameConstants
                        .BLOCK_DARK : {
                    if(block instanceof DarkBlock){
                        block.delete();
                        continue;
                    }
                    break;
                }
                default:{
                    block.delete();
                }

            }
        }
        //gameWorld.getBlockController().setArrayBlock(blocks);
    }

    @Override
    public String toString() {
        info = "";
        info += super.toString();
        String s = "Видалити " + count + " блоків";
        return info + s;
    }

}
