package com.daleondeveloper.Game.tools;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevelParser {

    private static final Pattern findBlockController = Pattern.compile("blockController>.+?<blockController>");
    private static final Pattern findBlock = Pattern.compile("<block>.+?</block>");
    private static final Pattern findHero = Pattern.compile("<hero>.+?</hero>");
    private static final Pattern findPosition = Pattern.compile("<position.+?>");
    private static final Pattern findType = Pattern.compile("<type.+?>");

    private String level;


    public LevelParser (String level){
        this.level = level;
    }
    public String getBlockController(){
        Matcher matcher = findBlockController.matcher(level);
        if(matcher.find()){
            return matcher.group();
        }
        return null;
    }
    public String getHeroStartParameter(){
        Matcher matcher = findHero.matcher(level);
        if(matcher.find()){
            return matcher.group();
        }
        return null;
    }
    public ArrayList<String> getStartBlock(){
        ArrayList<String> blocks = new ArrayList<String>();
        Matcher matcher = findBlock.matcher(level);
        while (matcher.find()){
            blocks.add(matcher.group());
        }
        return blocks;
    }
    public String getType(String row){
        Matcher matcher = findType.matcher(row);
        if(matcher.find()){
            return matcher.group().split(":")[1].split(">")[0].trim();
            }
        return "";
    }
    public Vector2 getPosition(String row){
        Matcher matcher = findPosition.matcher(row);
        if(matcher.find()){
            String[] posStr = matcher.group().split(":")[1].split(">")[0].trim().split(",");
            Vector2 position = new Vector2();
            position.x = Integer.getInteger(posStr[0]);
            position.y = Integer.getInteger(posStr[1]);
            return position;
        }
        return null;
    }

    public void setLevel(String level){
        this.level = level;
    }
}
