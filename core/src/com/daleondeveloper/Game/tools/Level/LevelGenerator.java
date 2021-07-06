package com.daleondeveloper.Game.tools.Level;

import com.badlogic.gdx.math.Vector2;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.tools.Checkers.ScoreLvlCondition;
import com.daleondeveloper.Game.tools.Checkers.StarLvlCondition;
import com.daleondeveloper.Game.tools.Checkers.TimeLvlCondition;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;
import com.daleondeveloper.Sprites.BlockControllers.BlockSpawner;
import com.daleondeveloper.Sprites.Blocks.Block;
import com.daleondeveloper.Sprites.Blocks.SnowBlock;
import com.daleondeveloper.Sprites.Hero.WaterElement;
import com.daleondeveloper.tools.GameConstants;

import java.util.ArrayList;

public class LevelGenerator {
    private static final String TAG = LevelGenerator.class.getName();

    private GameWorld gameWorld;
    private Levels levels;
    private LevelParser levelParser;
    private LvlEndConditionController lvlEndConditionController;
    private BlockController blockController;
    private WaterElement waterElement;

    private int score ;

    public LevelGenerator(GameWorld gameWorld,int lvlNumber){
        this.gameWorld = gameWorld;
        levels = new Levels();
        levelParser = new LevelParser(levels.getLevel(lvlNumber));
        lvlEndConditionController = new LvlEndConditionController();
        score = levelParser.getScore();
        createStartBlockController();
        addStartBlocks();
        createHero();
        loadNotSavedElement();
    }

    public void loadNotSavedElement(){
        levelParser = new LevelParser(levels.getLevel(getLevelNumber()));
        GameSettings.getInstance().setLevel(getLevelNumber());
        createBlockSpawners();
        createLevelConditions();
    }
    private void createLevelConditions(){
        ArrayList<String> allConditions = levelParser.getObjectsByPattern(LevelParser.findLevelChecker);
        for(String condition : allConditions){
            String type = levelParser.getType(condition);
            if(type.equals("time")){
                int time = levelParser.getValue(condition);
                lvlEndConditionController.addCondition(new TimeLvlCondition(time));
            }else if(type.equals("score")){
                int score = levelParser.getValue(condition);
                lvlEndConditionController.addCondition(new ScoreLvlCondition(score));
            }else if(type.equals("star")){
                lvlEndConditionController.addCondition(new StarLvlCondition(blockController));
            }else{

            }
        }
    }
    private void createBlockSpawners(){
        ArrayList<String> allSpawner = levelParser.getObjectsByPattern(LevelParser.findBlockSpawner);
        for(String spawner : allSpawner){
            String type = levelParser.getType(spawner);
                int time = levelParser.getValue(spawner);
                blockController.addBlockSpawner(new BlockSpawner(blockController,GameConstants.getBlockTypeByName(type),time));
        }
    }

    private void createStartBlockController(){
        blockController = new BlockController(gameWorld);
    }
    private void createHero(){
        String heroStr = levelParser.getDateByPattern(LevelParser.findHero);
        Vector2 startPos = levelParser.getPosition(heroStr);
        waterElement = new WaterElement(gameWorld,startPos.x,startPos.y);
    }
    private void addStartBlocks(){
        ArrayList<String> startBlocks = levelParser.getObjectsByPattern(LevelParser.findBlock);
        for(String block: startBlocks){
            Vector2 position = levelParser.getPosition(block);
            int type = GameConstants.getBlockTypeByName(levelParser.getType(block));
            if(type >= 0 && position != null){
                Block createdBlock = blockController.addBlock(position.x,position.y,type);
                    if(createdBlock instanceof SnowBlock) {
                        SnowBlock snowBlock = (SnowBlock) createdBlock;
                        snowBlock.setFreezingTime(levelParser.getValue(block));
                    }
                    if(levelParser.getBodyType(block).equals("static")){
                        createdBlock.setStaticBody();
                    }
            }
        }
    }

    public int getLevelNumber(){
        return levelParser.getLevelNumber();
    }
    public int getScore(){
        return score;
    }
    public BlockController getBlockController() {
        return blockController;
    }
    public WaterElement getHero(){
        if(waterElement == null){
            createHero();
        }
        return waterElement;
    }

    public LvlEndConditionController getLvlEndConditionController() {
        return lvlEndConditionController;
    }

}
