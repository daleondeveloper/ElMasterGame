package com.daleondeveloper.Sprites;


import com.badlogic.gdx.Game;
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
    public boolean addBlock(float x, float y){
        Random rnd = new Random ();
        Block block = new Block(gameWorld,(float)rnd.nextInt(9)*10+5,gameWorld.getGameCamera().getWorldHeight(),9.95f,9.95f);
        arrayBlock.add(block);
        block.fall();
        return true;
    }
    public boolean deleteBlock(Block block){


        return true;
    }

    public Array<Block> getArrayBlock() {
        return arrayBlock;
    }
}
