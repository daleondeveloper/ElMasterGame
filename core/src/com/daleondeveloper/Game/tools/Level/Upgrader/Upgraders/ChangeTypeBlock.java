package com.daleondeveloper.Game.tools.Level.Upgrader.Upgraders;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.GameGrid;
import com.daleondeveloper.Game.tools.Level.Upgrader.Upgrader;
import com.daleondeveloper.Sprites.Blocks.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChangeTypeBlock extends Upgrader {

    private int count;
    private Random rnd;
    private GameGrid gameGrid;
    private int oldType;
    private int newType;

    public ChangeTypeBlock(GameWorld gameWorld, int count, int oldType, int newType) {
        super(gameWorld);
        this.count = count;
        this.oldType = oldType;
        this.newType = newType;
        gameGrid = gameWorld.getGameGrid();
        rnd = new Random();
    }

    @Override
    protected void upgrade() {
        List<Block> blocks = new ArrayList<Block>();
                blocks.addAll(gameWorld.getBlockController().getArrayBlock());

        for(int i = 0; i < count; i++){
            int index = rnd.nextInt(blocks.size());
            Block block = blocks.get(index);
            gameWorld.getBlockController().addBlock(block.getReturnCellsPosition() - 5,block.getReturnCellsPositionY(),newType);
            block.delete();
            blocks.remove(index);
            if(blocks.isEmpty())break;
        }

    }

    @Override
    public String toString() {
        info = "";
        info += super.toString();
        return info + "Змінити тип блоків";
    }

}
