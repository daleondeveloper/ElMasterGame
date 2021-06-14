package com.daleondeveloper.Game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevelParser {
    private static final String TAG = LevelParser.class.getName();

    private final Pattern findLevelNumber = Pattern.compile("<lvlNmb>.+?</lvlNmb>");
    private final Pattern findScore = Pattern.compile("<score>.+?</score>");
    private final Pattern findBlockController = Pattern.compile("blockController>.+?</blockController>");
    private final Pattern findBlock = Pattern.compile("<block>.+?</block>");
    private final Pattern findHero = Pattern.compile("<hero>.+?</hero>");
    private final Pattern findPosition = Pattern.compile("<position.+?>");
    private final Pattern findType = Pattern.compile("<type.+?>");

    private String level;


    public LevelParser (String level){
        this.level = level;
    }
    public int getLevelNumber(){
        return findNumberByPattern(findLevelNumber);
    }
    public int getScore(){
        int score =  findNumberByPattern(findScore);
        if(score < 0)score =0;
        return score;
    }
    public int findNumberByPattern(Pattern pattern){
        Matcher matcher = pattern.matcher(level);
        int number = -999;
        if(matcher.find()){
            String s = matcher.group();
            s = s.split(">",2)[1];
            s = s.split("<",2)[0].trim();
            try {
                number = Integer.parseInt(s);
            }catch (NumberFormatException e){
                Gdx.app.error(TAG, "Incorrect number format", e);
            }
        }
        return number;
    }
    public String getBlockController(){
        Matcher matcher = findBlockController.matcher(level);
        if(matcher.find()){
            return matcher.group();
        }
        return "";
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
            return matcher.group().split(":")[1].split(">")[0].trim().toLowerCase();
            }
        return "";
    }
    public Vector2 getPosition(String row){
        Matcher matcher = findPosition.matcher(row);
        if(matcher.find()){
            String[] posStr = matcher.group().split(":")[1].split(">")[0].trim().split(",");
            Vector2 position = new Vector2();
            position.x = (int)(Float.parseFloat(posStr[0])+5)*10;
            position.y = (int)(Float.parseFloat(posStr[1])+15 )*10;
            return position;
        }
        return null;
    }

    public void setLevel(String level){
        this.level = level;
    }
}
