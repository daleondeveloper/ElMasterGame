package com.daleondeveloper.Game.tools;

public class Levels {
     private String[] levels;

     //d - default, f - fire, s - snow, dk - dark, l - light, s - star;
     public Levels(){
          levels = new String[100];
          levels[0] = "<blockController><type : classic></blockController>" +
                  "<block><type : default><position : 1,0></block>" +
                  "<block><type : default><position : 7,0></block>" +
                  "<block><type : default><position : 1,1></block>" +
                  "<block><type : default><position : 7,1></block>" +
                  "<block><type : default><position : 1,2></block>" +
                  "<block><type : default><position : 7,2></block>" +
                  "<hero><position : 5,1></hero>";
          levels[2] = "";
          levels[3] = "";
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
