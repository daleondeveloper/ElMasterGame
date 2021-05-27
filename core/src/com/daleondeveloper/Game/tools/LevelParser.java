package com.daleondeveloper.Game.tools;

import com.badlogic.gdx.math.Vector2;
import com.daleondeveloper.Sprites.AbstractDynamicObject;
import com.daleondeveloper.Sprites.Blocks.Block;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevelParser {

    private static final Pattern findBlock = Pattern.compile("<block>.+?</block>");
    private static final Pattern findHero = Pattern.compile("<hero>.+?</hero>");
    private static final Pattern findPosition = Pattern.compile("<position.+?>");
    private static final Pattern findType = Pattern.compile("<type.+?>");


    public LevelParser (){
        Pattern pattern = Pattern.compile("<block>+</block>");
    }
    public static ArrayList<Block> parseBlockForLevel(String level){
        ArrayList<Block> blocks= new ArrayList<Block>();
        Matcher matcher = findBlock.matcher(level);

        while (matcher.find()){
            Matcher typeMatcher = findType.matcher(matcher.group());
            Matcher positionMatcher = findPosition.matcher(matcher.group());
            String type = "";
            int[] position = new int[2];
            if(typeMatcher.find()){
                type = typeMatcher.group().split(":")[1].split(">")[0].trim();
            }
            if(positionMatcher.find()){
                String[] posStr = positionMatcher.group().split(":")[1].split(">")[0].trim().split(",");
                position[0] = Integer.getInteger(posStr[0]);
                position[1] = Integer.getInteger(posStr[1]);
            }
            if(type.equals("default")){

            }
        }

        return blocks;
    }

    public static Vector2 parseHeroStartCoordinate(String level){
        return null;
    }

    public static ArrayList<AbstractDynamicObject> parseStars(String level){
        return null;
    }
}
