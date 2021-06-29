package com.daleondeveloper.Game.tools.Level;

import com.daleondeveloper.Game.Settings.GameSettings;

public class Levels {
     public static final int maxLevel = 9;
     private String[] levels;
     private String savesLevel;

     //d - default, f - fire, s - snow, dk - dark, l - light, s - star;
     public Levels(){
          levels = new String[maxLevel];
          levels[0] = "<lvlNmb>0</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>" +
                  "<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 10></condition>";
          levels[1] = "<lvlNmb>1</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 50></condition>";
          levels[2] = "<lvlNmb>2</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<block><type : classic><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 8,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 8,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 8,2></block>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 50></condition>";
          levels[3] = "<lvlNmb>3</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<block><type : default><position : 6,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,2></block>\" +\n" +
                  "                  \"<hero><position : 6,4></hero>" +
                  "<condition><type : score><value : 50></condition>";
          levels[4] = "";
          levels[5] = "";
          levels[6] = "";
          levels[7] = "";
          levels[8] = "";
          savesLevel = GameSettings.getInstance().loadSavedLevel();
     }
     public String getLevel(int level){
          if(level < levels.length && level >= 0){
               return levels[level];
          }else if(level == -1){
               return savesLevel;
          }
          else
               return "";
     }
}
