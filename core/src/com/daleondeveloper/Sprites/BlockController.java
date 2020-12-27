package com.daleondeveloper.Sprites;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Game.GameSettings;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockController {
    private static final String TAG = BlockController.class.getName();


    private List<Block> arrayBlock ;
    private GameWorld gameWorld;
    private PlayScreen playScreen;
    private Random rnd;
    private Block lastCreateBlock;


    public BlockController (PlayScreen playScreen, GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.playScreen = playScreen;

        arrayBlock = new ArrayList<Block>(10);

        rnd = new Random ();

    }
    public void update(float deltaTime){
        if(lastCreateBlock != null && lastCreateBlock.isIdle()){
            if(lastCreateBlock.getY() > 300){
                lastCreateBlock.delete();
            }
            gameWorld.setTimeCreateBlock(101);
        }
    }

    public boolean addBlock (){
        lastCreateBlock = new Block(gameWorld,(float)rnd.nextInt(10)*10+50,gameWorld.getGameCamera().getWorldHeight()-30,9.94f,9.94f);
        arrayBlock.add(lastCreateBlock);
        lastCreateBlock.fall();

        return true;
    }
    public boolean addBlock(float x, float y){
        Block block = new Block(gameWorld,x,y,9.94f,9.94f);
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

    public void load(){
        GameSettings.getInstance().loadBlock();
        for(Vector2 vec : GameSettings.getInstance().getBlockVector()){
            addBlock(vec.x,vec.y);
        }
    }

    public List<Block> getArrayBlock() {
        return arrayBlock;
    }
}
