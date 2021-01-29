package com.daleondeveloper.Sprites.BlockControllers;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.daleondeveloper.Game.Settings.BlockLoad;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.AbstractGameObject;
import com.daleondeveloper.Sprites.Blocks.Block;
import com.daleondeveloper.Sprites.Blocks.DarkBlock;
import com.daleondeveloper.Sprites.Blocks.FireBlock;
import com.daleondeveloper.Sprites.Blocks.LightBlock;
import com.daleondeveloper.Sprites.Blocks.SnowBlock;
import com.daleondeveloper.Sprites.Blocks.WaterBlock;
import com.daleondeveloper.Sprites.Hero.WaterElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockController {
    private static final String TAG = BlockController.class.getName();


    protected List<Block> arrayBlock ;
    protected GameWorld gameWorld;
    protected PlayScreen playScreen;
    protected Random rnd;
    protected Block lastCreateBlock;
    protected Block[][] blocksMas;
    protected float blockFallVelocity;
    protected float blockCreateTime;


    public BlockController (PlayScreen playScreen, GameWorld gameWorld)  {
        this.gameWorld = gameWorld;
        this.playScreen = playScreen;

        arrayBlock = new ArrayList<Block>(10);

        blocksMas = new Block[10][20];
        rnd = new Random ();
        blockFallVelocity = 0;
        blockCreateTime = 0;
        blockFallVelocity = (-50);

    }
    public void update(float deltaTime){

        blockCreateTime += deltaTime;
        int score = playScreen.getHud().getScore();
//       float timeToCreateNewBlockRelativeScore = (4f - score / 125);
//       if(timeToCreateNewBlockRelativeScore < 1.5f){
//           timeToCreateNewBlockRelativeScore = 1.5f;
//       }
//
//        if(blockCreateTime > 4f){
////            float s = (score/10) % 6;
////            if(s > 3){
////                addBlock();
////                addBlock();
////                addBlock();
////            }else if(s > 0){
////                addBlock();
////                addBlock();
////            }else{
//                addBlock();
////            }
//            blockCreateTime = 0;
//        }
//
    }
    public void render(SpriteBatch spriteBatch){
        for(Block block : arrayBlock){
            block.renderEffect(spriteBatch);
        }
        for(Block block : arrayBlock){
            block.render(spriteBatch);
        }
    }
    public Block addBlock (int blockType){
        int countNextRandomNumbers = 0;
        float posCreateX = 0;
        int posMasX = rnd.nextInt(10);
        //if(posMasX >= blocksMas.length-1){posMasX = 0;}
        while (true) {
            if (blocksMas[posMasX][15] == null &&
                    blocksMas[posMasX][14] == null &&
                    blocksMas[posMasX][13] == null &&
                    blocksMas[posMasX][12] == null &&
                    blocksMas[posMasX][11] == null &&
                    blocksMas[posMasX][10] == null &&
                    blocksMas[posMasX][9] == null) {
                break;
            }else{
                posMasX = (int)rnd.nextInt(10);
            }
            countNextRandomNumbers++;
            if(countNextRandomNumbers > 100)break;
            }
        posCreateX = (float)posMasX*10+50;
        blocksMas[posMasX][15] = new Block(gameWorld,this,blockType,posCreateX,300,9.94f,9.94f);
        arrayBlock.add(blocksMas[posMasX][15]);
        blocksMas[posMasX][15].fall();

        return blocksMas[posMasX][15];
    }
    public boolean addBlock(float x, float y){
        Block block = new Block(gameWorld,this,x,y,9.94f,9.94f);
        arrayBlock.add(block);
        block.fall();
        return true;
    }
    public boolean addBlock(float x, float y, int blockTypeNumber){
        Block block = null;
        switch (blockTypeNumber){
            case 0 :
                block = new Block(gameWorld,this,blockTypeNumber,x,y,9.94f,9.94f);
                break;
            case 1 :
                block = new DarkBlock(gameWorld,this,blockTypeNumber,x,y,9.94f,9.94f);
                break;

            case 2 :
                block = new FireBlock(gameWorld,this,blockTypeNumber,x,y,9.94f,9.94f);
                break;

            case 3 :
                block = new LightBlock(gameWorld,this,blockTypeNumber,x,y,9.94f,9.94f);
                break;

            case 4 :
                block = new SnowBlock(gameWorld,this,blockTypeNumber,x,y,9.94f,9.94f);
                break;
            case 5 :
                block = new WaterBlock(gameWorld,this,blockTypeNumber,x,y,9.94f,9.94f);
                break;

            default:
                block = new Block(gameWorld,this,blockTypeNumber,x,y,9.94f,9.94f);
        }
        arrayBlock.add(block);
        block.fall();
        return true;
    }
    public boolean addBlockToBlockMass(Block block){
        int posMasX = (int)(block.getReturnCellsPosition() / 10 ) - 5;
        int posMasY = (int)(block.getReturnCellsPositionY() / 10 ) - 15;
        if(posMasX < blocksMas.length && posMasY < blocksMas[0].length) {
            int i,j;
            if(posMasX == 0 ){i = posMasX;}else{i = posMasX - 1;}
            for (;i < posMasX + 2 && i < blocksMas.length; i++) {
                if(posMasY == 0 ){j = posMasY;}else{j = posMasY - 1;}
                for (;j < posMasY + 2 && j < blocksMas[0].length; j++) {
                    if (blocksMas[i][j] == block) {
                        blocksMas[i][j] = null;
                    }
                }
            }
            blocksMas[posMasX][posMasY] = block;
            return true;
        }
        return false;
    }
    public void deleteBlockFormMass(Block block){
        int posMasX = (int)(block.getReturnCellsPosition() / 10 ) - 5;
        int posMasY = (int)(block.getReturnCellsPositionY() / 10 ) - 15;
        if(posMasX < blocksMas.length && posMasY < blocksMas[0].length) {
            blocksMas[posMasX][posMasY] = null;
        }
    }
    public Block checkDownContact(Block block) {
        int posMasX = (int) (block.getReturnCellsPosition() / 10) - 5;
        int posMasY = (int) (block.getReturnCellsPositionY() / 10) - 15;
        if (posMasX < blocksMas.length && posMasY < blocksMas[0].length) {
            if(posMasY > 0) {
                WaterElement hero = null;
                for(AbstractGameObject downBlock : block.getContactDownList()){
                    if(downBlock instanceof WaterElement){
                        hero = (WaterElement)downBlock;
                    }
                }
                block.getContactDownList().clear();
                if (blocksMas[posMasX][posMasY - 1] != null) {
                    block.getContactDownList().add(blocksMas[posMasX][posMasY]);
                }
                if(hero != null){
                   // block.getContactDownList().add(hero);
                }
            }
        }
        return null;
    }
public Block checkUpContact(Block block) {
        int posMasX = (int) (block.getReturnCellsPosition() / 10) - 5;
        int posMasY = (int) (block.getReturnCellsPositionY() / 10) - 15;
        if (posMasX < blocksMas.length && posMasY < blocksMas[0].length) {
            if(posMasY > 0 && posMasY + 1 < blocksMas[0].length) {
                WaterElement hero = null;
                for(AbstractGameObject downBlock : block.getContactUpList()){
                    if(downBlock instanceof WaterElement){
                        hero = (WaterElement)downBlock;
                    }
                }
                if (blocksMas[posMasX][posMasY + 1] != null) {
                    block.getContactUpList().add(blocksMas[posMasX][posMasY]);
                }
                if(hero != null){
                    block.getContactUpList().add(hero);
                }
            }
        }
        return null;
    }
public Block checkRightContact(Block block) {
        int posMasX = (int) (block.getReturnCellsPosition() / 10) - 5;
        int posMasY = (int) (block.getReturnCellsPositionY() / 10) - 15;
        if (posMasX < blocksMas.length && posMasY < blocksMas[0].length) {
            if(posMasX >= 0 && posMasX + 1 < blocksMas.length) {
                WaterElement hero = null;
                for(AbstractGameObject downBlock : block.getContactRightBlockList()){
                    if(downBlock instanceof WaterElement){
                        hero = (WaterElement)downBlock;
                    }
                }
                if (blocksMas[posMasX][posMasY - 1] != null) {
                    block.getContactRightBlockList().add(blocksMas[posMasX][posMasY]);
                    if(hero != null){
                        block.getContactRightBlockList().add(hero);
                    }
                }
            }
        }
        return null;
    }
public Block checkLeftContact(Block block) {
        int posMasX = (int) (block.getReturnCellsPosition() / 10) - 5;
        int posMasY = (int) (block.getReturnCellsPositionY() / 10) - 15;
        if (posMasX < blocksMas.length && posMasY < blocksMas[0].length) {
            if(posMasY > 0) {
                WaterElement hero = null;
                for(AbstractGameObject downBlock : block.getContactLeftBlockList()){
                    if(downBlock instanceof WaterElement){
                        hero = (WaterElement)downBlock;
                    }
                }
                if (blocksMas[posMasX][posMasY - 1] != null) {
                    block.getContactLeftBlockList().add(blocksMas[posMasX][posMasY]);
                    if(hero != null){
                        block.getContactLeftBlockList().add(hero);
                    }
                }
            }
        }
        return null;
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
        for(BlockLoad loadedBlock : GameSettings.getInstance().getBlockVector()){
            addBlock(loadedBlock.getPosition().x, loadedBlock.getPosition().y,loadedBlock.getBlockType());
        }
    }

    public List<Block> getArrayBlock() {
        return arrayBlock;
    }

    public Block[][] getBlocksMas() {
        return blocksMas;
    }

    public float getBlockFallVelocity() {
        return blockFallVelocity;
    }
}
