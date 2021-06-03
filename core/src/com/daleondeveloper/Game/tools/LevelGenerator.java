package com.daleondeveloper.Game.tools;

import com.badlogic.gdx.math.Vector2;
import com.daleondeveloper.Game.GameWorld;
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

    private GameWorld gameWorld;
    private Levels levels;
    private LevelParser levelParser;
    private BlockController blockController;
    private WaterElement waterElement;

    public LevelGenerator(GameWorld gameWorld,int lvlNumber){
        this.gameWorld = gameWorld;
        levels = new Levels();
        levelParser = new LevelParser(levels.getLevel(lvlNumber));
        createStartBlockController();
        addStartBlocks();
        createHero();
    }

    private void createStartBlockController(){
        String type = levelParser.getType(levelParser.getBlockController());
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
        String heroStr = levelParser.getHeroStartParameter();
        Vector2 startPos = levelParser.getPosition(heroStr);
        waterElement = new WaterElement(gameWorld,startPos.x,startPos.y);
    }
    private void addStartBlocks(){
        ArrayList<String> startBlocks = levelParser.getStartBlock();
        for(String block: startBlocks){
            Vector2 position = levelParser.getPosition(block);
            int type = GameConstants.getBlockTypeByName(levelParser.getType(block));
            if(type >= 0 && position != null){
                blockController.addBlock(position.x,position.y,type);
            }
        }
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
}
