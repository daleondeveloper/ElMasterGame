package com.daleondeveloper.Sprites;


import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;

import java.util.Random;

public class BlockController {
    private static final String TAG = BlockController.class.getName();


    private Array<Block> arrayBlock ;
    private GameWorld gameWorld;
    private PlayScreen playScreen;


    public BlockController (PlayScreen playScreen, GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.playScreen = playScreen;

        arrayBlock = new Array<Block>(10);

    }
    public boolean addBlock (){
        Random rnd = new Random ();
        Block block = new Block(gameWorld,(float)rnd.nextInt(12)*10+40,gameWorld.getGameCamera().getWorldHeight()-30,10f,10f);
        arrayBlock.add(block);
        block.fall();
        return true;
    }
    public boolean addBlock(float x, float y){
        Block block = new Block(gameWorld,x,y,9.95f,9.95f);
        arrayBlock.add(block);
        block.fall();
        block.setSensorDown(false);
        return true;
    }
    public boolean deleteBlock(Block block){


        return true;
    }
    public void blockChecker(){
        for(Block block : arrayBlock){
            if(block.isIdle()){
            }
        }
    }

    public Array<Block> getArrayBlock() {
        return arrayBlock;
    }
}
