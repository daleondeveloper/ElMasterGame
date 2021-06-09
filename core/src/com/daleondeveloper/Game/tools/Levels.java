package com.daleondeveloper.Game.tools;

public class Levels {
     public static final int maxLevel = 9;
     private String[] levels;

     //d - default, f - fire, s - snow, dk - dark, l - light, s - star;
     public Levels(){
          levels = new String[maxLevel];
          levels[0] = "<blockController><type : classic></blockController>" +
                  "<block><type : default><position : 1,0></block>" +
                  "<block><type : default><position : 7,0></block>" +
                  "<block><type : default><position : 1,1></block>" +
                  "<block><type : default><position : 7,1></block>" +
                  "<block><type : default><position : 1,2></block>" +
                  "<block><type : default><position : 7,2></block>" +
                  "<hero><position : 5,5></hero>";
          levels[2] = "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,2></block>\" +\n" +
                  "                  \"<hero><position : 3,5></hero>";
          levels[3] = "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<block><type : light><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,5></block>\" +\n" +
                  "                  \"<block><type : default><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,2></block>\" +\n" +
                  "                  \"<hero><position : 5,5></hero>";
          levels[4] = "";
          levels[5] = "";
     }
     public String getLevel(int level){
          if(level < levels.length){
               return levels[level];
          }
          else
               return "";
     }
}
