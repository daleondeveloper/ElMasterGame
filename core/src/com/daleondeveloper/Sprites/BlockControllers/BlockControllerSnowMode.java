package com.daleondeveloper.Sprites.BlockControllers;

import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Sprites.Blocks.Block;
import com.daleondeveloper.Sprites.Blocks.SnowBlock;

import java.util.Random;

public class BlockControllerSnowMode extends BlockController {
    private static final String TAG = BlockControllerSnowMode.class.getName();
    private static final float TIME_TO_CRATE_SPECIAL_BLOCK = 10f;


    private float timeToCreateSpecialBlock;

    public BlockControllerSnowMode(GameWorld gameWorld) {
        super(gameWorld);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeToCreateSpecialBlock += deltaTime;
        if(blockCreateTime > 4f){
            int blockTypeNumber =new Random().nextInt(4);
            if(blockTypeNumber == 4){
                blockTypeNumber = 5;
            }
            addBlock(blockTypeNumber);
            blockCreateTime = 0;
        }

        if(timeToCreateSpecialBlock > TIME_TO_CRATE_SPECIAL_BLOCK){
            addSpecialBlock(4);
            timeToCreateSpecialBlock = 0;
        }

    }
    public Block addSpecialBlock(int blockType) {
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
        blocksMas[posMasX][15] = new SnowBlock(gameWorld,this,blockType,posCreateX,300,9.94f,9.94f);
        arrayBlock.add(blocksMas[posMasX][15]);
        blocksMas[posMasX][15].fall();

        return blocksMas[posMasX][15];
    }
}
