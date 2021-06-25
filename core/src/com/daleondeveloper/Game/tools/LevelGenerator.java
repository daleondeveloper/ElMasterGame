package com.daleondeveloper.Game.tools;

import com.badlogic.gdx.math.Vector2;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Checkers.ScoreLvlCondition;
import com.daleondeveloper.Game.tools.Checkers.TimeLvlCondition;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerClassicMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerDarkMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerFireMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerLightMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerSnowMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerSpecialMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerWaterMode;
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
            }else{

            }
        }
    }
    private void createStartBlockController(){
        String type = levelParser.getType(levelParser.getDateByPattern(LevelParser.findBlockController));
            if(type.equals("light")){
                blockController = new BlockControllerLightMode(gameWorld);
            }else if(type.equals("snow")){
                blockController = new BlockControllerSnowMode(gameWorld);
            }else if(type.equals("fire")){
                blockController = new BlockControllerFireMode(gameWorld);
            }else if(type.equals("water")){
                blockController = new BlockControllerWaterMode(gameWorld);
            }else if(type.equals("dark")){
                blockController = new BlockControllerDarkMode(gameWorld);
            }else if(type.equals("special")){
                blockController = new BlockControllerSpecialMode(gameWorld);
            }else{
                blockController = new BlockControllerClassicMode(gameWorld);
            }
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
                blockController.addBlock(position.x,position.y,type);
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
