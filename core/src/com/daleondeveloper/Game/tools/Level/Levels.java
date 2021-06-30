package com.daleondeveloper.Game.tools.Level;

import com.daleondeveloper.Game.Settings.GameSettings;

public class Levels {
     public static final int maxLevel = 11;
     private String[] levels;
     private String savesLevel;

     //d - default, f - fire, s - snow, dk - dark, l - light, s - star;
     public Levels(){
          levels = new String[maxLevel];
          addLevel_0();
          addLevel_1();
          addLevel_2();
          addLevel_3();
          addLevel_4();
          addLevel_5();
          addLevel_6();
          addLevel_7();
          addLevel_8();
          addLevel_9();
          addLevel_10();
          addLevel_11();
          addLevel_12();
          addLevel_13();
          addLevel_14();
          addLevel_15();
          addLevel_16();
          addLevel_17();
          addLevel_18();
          addLevel_19();
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
     private void addLevel_0(){
          levels[0] = "<lvlNmb>0</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>" +
                  "<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 10></condition>";
     }
     private void addLevel_1(){
          levels[1] = "<lvlNmb>1</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 50></condition>";
     }
     private void addLevel_2(){
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
     }
     private void addLevel_3(){
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
     }
     private void addLevel_4(){
          levels[4] = "<lvlNmb>4</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<block><type : default><position : 6,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 2,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 2,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,1></block>\" +\n" +
                  "                  \"<hero><position : 3,2></hero>" +
                  "<condition><type : score><value : 50></condition>";
     }
     private void addLevel_5(){
          levels[5] = "<lvlNmb>5</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<block><type : default><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 2,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 4,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 5,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 2,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 4,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 5,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 4,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 5,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 4,3></block>\" +\n" +
                  "                  \"<hero><position : 4,4></hero>" +
                  "<condition><type : score><value : 100></condition>";
     }
     private void addLevel_6(){
          levels[6] = "<lvlNmb>6</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 100></condition>";
          for(int i = 0; i < 10; i++){
               levels[6] +="                  \"<block><type : default><position : 0," + i + "></block>\" +\n" +
                       "                  \"<block><type : default><position : 1," + i + "></block>\" +\n" +
                       "                  \"<block><type : default><position : 8," + i + "></block>\" +\n" +
                       "                  \"<block><type : default><position : 9," + i + "></block>\" +\n" ;
          }


     }
     private void addLevel_7(){
          levels[7] = "<lvlNmb>7</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<block><type : default><position : 0,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 2,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 8,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 9,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 0,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 2,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 8,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 9,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 0,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 9,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 8,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 0,3></block>\" +\n" +
                  "                  \"<block><type : default><position : 9,3></block>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 100></condition>";
     }
     private void addLevel_8(){
          levels[8] = "<lvlNmb>8</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<block><type : default><position : 4,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 5,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 4,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 5,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 4,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 5,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 4,3></block>\" +\n" +
                  "                  \"<block><type : default><position : 5,3></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,3></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,3></block>\" +\n" +
                  "                  \"<block><type : default><position : 4,4></block>\" +\n" +
                  "                  \"<block><type : default><position : 5,4></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,4></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,4></block>\" +\n" +
                  "                  \"<hero><position : 5,5></hero>" +
                  "<condition><type : score><value : 50></condition>";
     }
     private void addLevel_9(){
          levels[9] = "<lvlNmb>9</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>\" +\n"+
                  "                  \"<hero><position : 4,4></hero>" +
                  "<condition><type : time><value : 60></condition>";
          for(int i = 0; i < 5; i++){
               levels[9] += "                  \"<block><type : default><position : 0," + i + "></block>\" +\n" +
                       "                  \"<block><type : default><position : 1," + i + "></block>\" +\n" +
                       "                  \"<block><type : default><position : 3," + i + "></block>\" +\n" +
                       "                  \"<block><type : default><position : 4," + i + "></block>\" +\n" +
                       "                  \"<block><type : default><position : 6," + i + "></block>\" +\n" +
                       "                  \"<block><type : default><position : 8," + i + "></block>\" +\n" +
                       "                  \"<block><type : default><position : 9," + i + "></block>\" +\n";
          }
     }
     private void addLevel_10(){
          levels[10] = "<lvlNmb>10</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,2></block>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 30></condition>";
     }
     private void addLevel_11(){

     }
     private void addLevel_12(){

     }
     private void addLevel_13(){

     }
     private void addLevel_14(){

     }
     private void addLevel_15(){

     }
     private void addLevel_16(){

     }
     private void addLevel_17(){

     }
     private void addLevel_18(){

     }
     private void addLevel_19(){

     }
}
