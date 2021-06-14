package com.daleondeveloper.Game.tools;

import com.daleondeveloper.Game.Settings.GameSettings;

public class Levels {
     public static final int maxLevel = 9;
     private String[] levels;
     private String savesLevel;

     //d - default, f - fire, s - snow, dk - dark, l - light, s - star;
     public Levels(){
          levels = new String[maxLevel];
          levels[0] = "<blockController><type : fire></blockController>" +
                  "<block><type : default><position : 1,0></block>" +
                  "<block><type : default><position : 7,0></block>" +
                  "<block><type : default><position : 1,1></block>" +
                  "<block><type : default><position : 7,1></block>" +
                  "<block><type : default><position : 1,2></block>" +
                  "<block><type : default><position : 7,2></block>" +
                  "<hero><position : 5,5></hero>";
          levels[1] = "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,2></block>\" +\n" +
                  "                  \"<hero><position : 3,5></hero>";
          levels[2] = "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<block><type : light><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,5></block>\" +\n" +
                  "                  \"<block><type : default><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,2></block>\" +\n" +
                  "                  \"<hero><position : 5,5></hero>";
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
