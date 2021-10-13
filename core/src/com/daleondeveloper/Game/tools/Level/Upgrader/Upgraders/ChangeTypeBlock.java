package com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.GameGrid;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Sprites.Blocks.Block;

import java.util.List;
import java.util.Random;

public class ChangeTypeBlock extends Upgrader {

    private int count;
    private Random rnd;
    private GameGrid gameGrid;
    private int type;

    public ChangeTypeBlock(GameWorld gameWorld, int count, int type) {
        super(gameWorld);
        this.count = count;
        this.type = type;
        gameGrid = gameWorld.getGameGrid();
        rnd = new Random();
    }

    @Override
    protected void upgrade() {
        List<Block> blocks = gameWorld.getBlockController().getArrayBlock();
        for(int i = 0; i < count; i++){
            Block block = blocks.get(rnd.nextInt(blocks.size()));
            gameWorld.getBlockController().addBlock(block.getReturnCellsPosition(),block.getReturnCellsPositionY(),type);
            block.delete();
        }

    }

    @Override
    public String toString() {
        info = "";
        info += super.toString();
        return info + "Змінити тип блоків";
    }

}
