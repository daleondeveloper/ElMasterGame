package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.tools.GameGrid;
import com.daleondeveloper.Sprites.AbstractGameObject;
import com.daleondeveloper.Sprites.Blocks.Block;

import java.util.ArrayList;

public class BlockTouchLineCheсker {
    private static final String TAG = BlockTouchLineCheсker.class.getName();

    private  BlockController blockController;
    private GameGrid gameGrid;
    private AbstractGameObject[] oneLineObjects;
    private ArrayList<AbstractGameObject> blockToDestroy;

    public BlockTouchLineCheсker(BlockController blockController, GameGrid gameGrid){
        this.blockController = blockController;
        this.gameGrid = gameGrid;
        blockToDestroy = new ArrayList<AbstractGameObject>();
    }
    public boolean check(){
        blockToDestroy.clear();
        ArrayList<AbstractGameObject> objectsWichCheked = new ArrayList<AbstractGameObject>();
        int counter = 0;
        for(int i = 0 ; i < gameGrid.getGridWidthLength() + 1; i++){
            if(gameGrid.getGridWidthLength() != i &&
                    gameGrid.getGameGridImpl().getElementByCordinate(i,0) != null &&
                    gameGrid.getGameGridImpl().getElementByCordinate(i,0) instanceof Block &&
                    ((Block) gameGrid.getGameGridImpl().getElementByCordinate(i,0)).isIdle()){
                counter++;
                objectsWichCheked.add(gameGrid.getGameGridImpl().getElementByCordinate(i,0) );
            }else if(counter > 3){
                blockToDestroy.addAll(objectsWichCheked);
                counter = 0;
            }else {
                counter = 0;
                objectsWichCheked.clear();
            }
        }

        if(blockToDestroy.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<AbstractGameObject> getBlockToDestroy() {
        return blockToDestroy;
    }

}
