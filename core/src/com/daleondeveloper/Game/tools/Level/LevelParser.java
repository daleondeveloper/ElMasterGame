package com.daleondeveloper.Game.tools.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevelParser {
    private static final String TAG = LevelParser.class.getName();

    public static final Pattern findLevelNumber = Pattern.compile("<lvlNmb>.+?</lvlNmb>");
    public static final Pattern findLevelChecker = Pattern.compile("<condition>.+?</condition>");
    public static final Pattern findScore = Pattern.compile("<score>.+?</score>");
    public static final Pattern findBlockController = Pattern.compile("blockController>.+?</blockController>");
    public static final Pattern findBlock = Pattern.compile("<block>.+?</block>");
    public static final Pattern findBlockSpawner = Pattern.compile("<blockSpawner>.+?</blockSpawner>");
    public static final Pattern findStar = Pattern.compile("<star>.+?</star>");
    public static final Pattern findHero = Pattern.compile("<hero>.+?</hero>");
    public static final Pattern findPosition = Pattern.compile("<position.+?>");
    public static final Pattern findType = Pattern.compile("<type.+?>");
    public static final Pattern findValue = Pattern.compile("<value.+?>");

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
    public String getDateByPattern(Pattern pattern){
        Matcher matcher = pattern.matcher(level);
        if(matcher.find()){
            return matcher.group();
        }
        return "";
    }
    public ArrayList<String> getObjectsByPattern(Pattern pattern){
        ArrayList<String> objects = new ArrayList<String>();
        Matcher matcher = pattern.matcher(level);
        while (matcher.find()){
            objects.add(matcher.group());
        }
        return objects;
    }
    public int getValue(String row){
        Matcher matcher = findValue.matcher(row);
        if(matcher.find()){
            return Integer.parseInt(matcher.group().split(":")[1].split(">")[0].trim().toLowerCase());
        }
        return 0;
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

    public void setLevel(String level){
        this.level = level;
    }
}
