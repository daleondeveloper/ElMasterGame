package com.daleondeveloper.Sprites;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Game.GameSettings;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.Hero.WaterElement;

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
    private Block[][] blocksMas;
    private float blockFallVelocity;


    public BlockController (PlayScreen playScreen, GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.playScreen = playScreen;

        arrayBlock = new ArrayList<Block>(10);

        blocksMas = new Block[10][20];
        rnd = new Random ();
        blockFallVelocity = 0;

    }
    public void update(float deltaTime){
        if(lastCreateBlock != null && lastCreateBlock.isIdle()){
            if(lastCreateBlock.getY() > 300){
                lastCreateBlock.delete();
            }
            gameWorld.setTimeCreateBlock(101);
        }

        blockFallVelocity = (-50 - ((int) playScreen.getHud().getScore() >> 1));
    }

    public boolean addBlock (){
        float posCreateX = (int)(rnd.nextInt(100)/10)*10+50;
        int posMasX = (int)(posCreateX / 10 ) - 5;
        if(posMasX >= blocksMas.length-1){posMasX = 0;}
        while (true) {
            if (blocksMas[posMasX][9] == null) {
                posCreateX = (float)posMasX*10+50;
                break;
            }else{
                posMasX = (int)rnd.nextInt(100) / 10;
            }

            }
        lastCreateBlock = new Block(gameWorld,this,posCreateX,gameWorld.getGameCamera().getWorldHeight()-30,9.94f,9.94f);
        arrayBlock.add(lastCreateBlock);
        lastCreateBlock.fall();

        return true;
    }
    public boolean addBlock(float x, float y){
        Block block = new Block(gameWorld,this,x,y,9.94f,9.94f);
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
        for(Vector2 vec : GameSettings.getInstance().getBlockVector()){
            addBlock(vec.x,vec.y);
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
