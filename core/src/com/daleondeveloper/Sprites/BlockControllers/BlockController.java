package com.daleondeveloper.Sprites.BlockControllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.GameGrid;
import com.daleondeveloper.Game.tools.Level.ElementSaved;
import com.daleondeveloper.Sprites.Blocks.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockController implements ElementSaved {
    private static final String TAG = BlockController.class.getName();

    protected  enum ControllerType{
        CLASSIC,DARK,FIRE,LIGHT,SNOW,WATER,SPECIAL;
    }
    private static BlockController instance;

    protected List<Block> arrayBlock;
    protected List<BlockSpawner> blockSpawners;
    protected GameWorld gameWorld;
    protected GameGrid gameGrid;
    protected Random rnd;
    protected Block lastCreateBlock;
    protected float blockFallVelocity;
    protected float blockCreateTime;
    protected ControllerType type;


    public BlockController (GameWorld gameWorld)  {
        this.gameWorld = gameWorld;
        gameGrid = gameWorld.getGameGrid();
        arrayBlock = new ArrayList<Block>(10);
        blockSpawners = new ArrayList<BlockSpawner>();

        rnd = new Random ();
        blockFallVelocity = 0;
        blockCreateTime = 0;
        blockFallVelocity = (-50);

        type = ControllerType.CLASSIC;
    }

    public void update(float deltaTime){
        for(BlockSpawner spawner: blockSpawners){
            spawner.update(deltaTime);
        }
    }
    public void render(SpriteBatch spriteBatch){
        for(Block block : arrayBlock){
            block.renderEffect(spriteBatch);
        }
        for(Block block : arrayBlock){
            block.render(spriteBatch);
        }
    }
    public Block addBlockInRandomPosition (int blockType){
        return addBlock(getRandomXBlockCordinateToCreate(),300,blockType);
    }
    public Block addBlock(float x, float y){
        return addBlock(x,y,0);
    }
    public Block addBlock(float x, float y, int blockTypeNumber){
        Block block;
        switch (blockTypeNumber){
            case 1 :
                block = new LightBlock(gameWorld,this,x,y,9.94f,9.94f);
                break;

            case 2 :
                block = new SnowBlock(gameWorld,this,x,y,9.94f,9.94f,20);
                break;

            case 3 :
                block = new FireBlock(gameWorld,this,x,y,9.94f,9.94f);
                block.setCoefficientFrostbite(0);
                break;

            case 4 :
                block = new WaterBlock(gameWorld,this,x,y,9.94f,9.94f);
                break;
            case 5 :
                block = new DarkBlock(gameWorld,this,x,y,9.94f,9.94f);
                block.setCoefficientFrostbite(0.5f);
                break;
            case 6 :
                block = new StarBlock(gameWorld,this,x,y,9.94f,9.94f);
                break;

            default:
                block = new Block(gameWorld,this,x,y,9.94f,9.94f);
        }
        addBlockToArrayAndChangeStateFall(block);
        return block;
    }

    private void addBlockToArrayAndChangeStateFall(Block block){
        block.fall();
        arrayBlock.add(block);
    }
    private float getRandomXBlockCordinateToCreate(){
        int countNextRandomNumbers = 0;
        float posCreateX;
        int posMasX = rnd.nextInt(10);
        while (true) {
            if (gameGrid.getBlockByCordinate(posMasX,15) == null &&
                    gameGrid.getBlockByCordinate(posMasX,14) == null &&
                    gameGrid.getBlockByCordinate(posMasX,13) == null &&
                    gameGrid.getBlockByCordinate(posMasX,12) == null &&
                    gameGrid.getBlockByCordinate(posMasX,11) == null &&
                    gameGrid.getBlockByCordinate(posMasX,10) == null) {
                break;
            }else{
                posMasX = rnd.nextInt(10);
            }
            countNextRandomNumbers++;
            if(countNextRandomNumbers > 100)break;
        }
        posCreateX = (float)posMasX*10+50;
        return posCreateX;
    }
    public void addBlockSpawner(BlockSpawner blockSpawner){
        if(blockSpawner != null){
            blockSpawners.add(blockSpawner);
        }
    }
    public List<Block> getArrayBlock() {
        return arrayBlock;
    }
    public float getBlockFallVelocity() {
        return blockFallVelocity;
    }

    @Override
    public String save() {
        String s = "";
        for(Block block : arrayBlock){
            s += block.save();
        }
        for(BlockSpawner blockSpawner : blockSpawners){
            s += blockSpawner.save();
        }
        return s;
    }

    @Override
    public String toString() {
        return "<blockController>" +
                "<type : " + type.toString() + ">" +
                "</blockController>";
    }
}
