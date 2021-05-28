package com.daleondeveloper.Game.tools;

import com.badlogic.gdx.math.Vector2;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerDarkMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerFireMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerLightMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerSnowMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerSpecialMode;
import com.daleondeveloper.Sprites.BlockControllers.BlockControllerWaterMode;
import com.daleondeveloper.tools.GameConstants;

import java.util.ArrayList;

public class LevelGenerator {

    private PlayScreen playScreen;
    private GameWorld gameWorld;
    private Levels levels;
    private LevelParser levelParser;
    private BlockController blockController;

    public LevelGenerator(PlayScreen playScreen, GameWorld gameWorld,int lvlNumber){
        levels = new Levels();
        levelParser = new LevelParser(levels.getLevel(lvlNumber));
        createStartBlockController();
    }

    private void createStartBlockController(){
        String type = levelParser.getType(levelParser.getBlockController());
            if(type.equals("light")){
                blockController = new BlockControllerLightMode(playScreen,gameWorld);
            }else if(type.equals("snow")){
                blockController = new BlockControllerSnowMode(playScreen,gameWorld);
            }else if(type.equals("fire")){
                blockController = new BlockControllerFireMode(playScreen,gameWorld);
            }else if(type.equals("water")){
                blockController = new BlockControllerWaterMode(playScreen,gameWorld);
            }else if(type.equals("dark")){
                blockController = new BlockControllerDarkMode(playScreen,gameWorld);
            }else if(type.equals("special")){
                blockController = new BlockControllerSpecialMode(playScreen,gameWorld);
            }else{
                blockController = new BlockController(playScreen,gameWorld);
            }
    }
    public void addStartBlocks(){
        ArrayList<String> startBlocks = levelParser.getStartBlock();
        for(String block: startBlocks){
            Vector2 position = levelParser.getPosition(block);
            int type = GameConstants.getBlockTypeByName(levelParser.getType(block));
            if(type >= 0 && position != null){
                blockController.addBlock(position.x,position.y,type);
            }
        }
    }


}
