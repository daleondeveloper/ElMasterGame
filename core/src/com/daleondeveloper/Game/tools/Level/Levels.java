package com.daleondeveloper.Game.tools.Level;

import com.daleondeveloper.Game.Settings.GameSettings;

public class Levels {
     public static final int maxLevel = 31;
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
          addLevel_20();
          addLevel_21();
          addLevel_22();
          addLevel_23();
          addLevel_24();
          addLevel_25();
          addLevel_26();
          addLevel_27();
          addLevel_28();
          addLevel_29();
          addLevel_30();
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
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 10></condition>";
     }
     private void addLevel_1(){
          levels[1] = "<lvlNmb>1</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "<blockController><type : classic></blockController>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 30></condition>";
     }
     private void addLevel_2(){
          levels[2] = "<lvlNmb>2</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : classic><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 8,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 8,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 8,2></block>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 40></condition>";
     }
     private void addLevel_3(){
          levels[3] = "<lvlNmb>3</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : star><position : 6,0></block>\" +\n" +
                  "                  \"<block><type : star><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : star><position : 6,1></block>\" +\n" +
                  "                  \"<block><type : star><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : star><position : 6,2></block>\" +\n" +
                  "                  \"<block><type : star><position : 7,2></block>\" +\n" +
                  "                  \"<hero><position : 6,4></hero>" +
                  "<condition><type : star></condition>";
     }
     private void addLevel_4(){
          levels[4] = "<lvlNmb>4</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : default><position : 6,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,1></block>\" +\n" +
                  "                  \"<block><type : star><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 2,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,0></block>\" +\n" +
                  "                  \"<block><type : star><position : 2,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,1></block>\" +\n" +
                  "                  \"<hero><position : 3,2></hero>" +
                  "<condition><type : star></condition>";
     }
     private void addLevel_5(){
          levels[5] = "<lvlNmb>5</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : default><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 2,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,0></block>\" +\n" +
                  "                  \"<block><type : star><position : 4,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 5,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : default><position : 2,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,1></block>\" +\n" +
                  "                  \"<block><type : star><position : 4,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 5,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 6,1></block>\" +\n" +
                  "                  \"<block><type : default><position : 3,2></block>\" +\n" +
                  "                  \"<block><type : star><position : 4,2></block>\" +\n" +
                  "                  \"<block><type : default><position : 5,2></block>\" +\n" +
                  "                  \"<block><type : star><position : 4,3></block>\" +\n" +
                  "                  \"<hero><position : 4,4></hero>" +
                  "<condition><type : star></condition>";
     }
     private void addLevel_6(){
          levels[6] = "<lvlNmb>6</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 70></condition>";
          for(int i = 0; i < 18; i++){
               levels[6] +="                  \"<block><type : default><position : 0," + i + "></block>\" +\n" +
                       "                  \"<block><type : default><position : 1," + i + "></block>\" +\n" +
                       "                  \"<block><type : default><position : 8," + i + "></block>\" +\n" +
                       "                  \"<block><type : default><position : 9," + i + "></block>\" +\n" ;
          }


     }
     private void addLevel_7(){
          levels[7] = "<lvlNmb>7</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
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
                  "                  \"<block><type : star><position : 0,3></block>\" +\n" +
                  "                  \"<block><type : star><position : 9,3></block>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : star><value : 100></condition>";
     }
     private void addLevel_8(){
          levels[8] = "<lvlNmb>8</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
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
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<hero><position : 4,4></hero>" +
                  "<condition><type : time><value : 60></condition>" +
                  "<condition><type : score><value :20></condition>";
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
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,2></block>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 50></condition>";
     }
     private void addLevel_11(){
          levels[11] = "<lvlNmb>11</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 3,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 9,0></block>\" +\n" +
                  "                  \"<hero><position : 5,1></hero>" +
                  "<condition><type : score><value : 50></condition>";
     }
     private void addLevel_12(){
          levels[12] = "<lvlNmb>12</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : classic><position : 0,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 2,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 3,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 4,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 0,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 2,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 3,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 4,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 4,2></block>\" +\n" +
                  "                  \"<hero><position : 3,2></hero>" +
                  "<condition><type : score><value : 50></condition>";
     }
     private void addLevel_13(){
          levels[13] = "<lvlNmb>13</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : classic><position : 0,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 8,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 9,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 0,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 8,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 9,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 0,2></block>\" +\n" +
                  "                  \"<block><type : classic><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : classic><position : 8,2></block>\" +\n" +
                  "                  \"<block><type : classic><position : 9,2></block>\" +\n" +
                  "                  \"<block><type : star><position : 1,3></block>\" +\n" +
                  "                  \"<block><type : star><position : 8,3></block>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : star><value : 50></condition>";
     }
     private void addLevel_14(){
          levels[14] = "<lvlNmb>14</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "<blockSpawner><type : dark><value : 5></blockSpawner>" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 50></condition>";
     }
     private void addLevel_15(){
          levels[15] = "<lvlNmb>15</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "<blockSpawner><type : dark><value : 6></blockSpawner>" +
                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 2,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 3,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 2,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 3,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,1></block>\" +\n" +
                  "                  \"<block><type : star><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : star><position : 8,2></block>\" +\n" +
                  "                  \"<block><type : star><position : 1,3></block>\" +\n" +
                  "                  \"<block><type : star><position : 8,3></block>\" +\n" +
                  "                  \"<hero><position : 7,3></hero>" +
                  "<condition><type : score><value : 50></condition>" +
                  "<condition><type : star><value : 50></condition>";
     }
     private void addLevel_16(){
          levels[16] = "<lvlNmb>16</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 2></blockSpawner>" +
                  "<blockSpawner><type : dark><value : 4></blockSpawner>" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : time><value : 120></condition>";
     }
     private void addLevel_17(){
          levels[17] = "<lvlNmb>17</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : dark><position : 0,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 2,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 4,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 9,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 0,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 9,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 0,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,2></block>\" +\n" +
                  "                  \"<hero><position : 5,2></hero>" +
                  "<condition><type : score><value : 50></condition>";
     }
     private void addLevel_18(){
          levels[18] = "<lvlNmb>18</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 2,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 4,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 2,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 4,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 2,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 4,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,3></block>\" +\n" +
                  "                  \"<block><type : dark><position : 2,3></block>\" +\n" +
                  "                  \"<block><type : dark><position : 4,3></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,3></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,3></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,3></block>\" +\n" +
                  "                  \"<block><type : star><position : 1,4></block>\" +\n" +
                  "                  \"<block><type : star><position : 8,4></block>\" +\n" +
                  "                  \"<block><type : star><position : 1,5></block>\" +\n" +
                  "                  \"<block><type : star><position : 8,5></block>\" +\n" +
                  "                  \"<hero><position : 5,4></hero>" +
                  "<condition><type : star><value : 50></condition>";
     }
     private void addLevel_19(){
          levels[19] = "<lvlNmb>19</lvlNmb>" +
                  "<score>0</score>" +
                  "                  \"<block><type : dark><position : 0,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 2,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 3,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 4,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 2,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 3,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 4,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 2,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 3,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 4,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 3,3></block>\" +\n" +
                  "                  \"<block><type : dark><position : 4,3></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,3></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,3></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,3></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,3></block>\" +\n" +
                  "                  \"<block><type : dark><position : 4,4></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,4></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,4></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,4></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,4></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,5></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,5></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,5></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,5></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,6></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,6></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,6></block>\" +\n" +
                  "                  \"<block><type : dark><position : 7,7></block>\" +\n" +
                  "                  \"<block><type : dark><position : 8,7></block>\" +\n" +
                  "                  \"<block><type : star><position : 8,8></block>\" +\n" +
                  "                  \"<hero><position : 0,1></hero>" +
                  "<condition><type : star><value : 50></condition>";
     }
     private void addLevel_20(){
          levels[20] = "<lvlNmb>20</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : classic><position : 0,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 9,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 0,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 9,1></block>\" +\n" +
                  "                  \"<block><type : snow><position : 0,2><value : 20></block>\" +\n" +
                  "                  \"<block><type : classic><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : classic><position : 8,2></block>\" +\n" +
                  "                  \"<block><type : snow><position : 9,2><value : 20></block>\" +\n" +
                  "                  \"<block><type : star><position : 1,3></block>\" +\n" +
                  "                  \"<block><type : star><position : 8,3></block>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : star><value : 50></condition>";
     }
     private void addLevel_21(){
          levels[21] = "<lvlNmb>21</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : classic><position : 2,2></block>\" +\n" +
                  "                  \"<block><type : snow><position : 3,2><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 4,2><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 5,2><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 6,2><value : 999></block>\" +\n" +
                  "                  \"<block><type : classic><position : 7,2></block>\" +\n" +
                  "                  \"<block><type : classic><position : 2,3></block>\" +\n" +
                  "                  \"<block><type : star><position : 3,3></block>\" +\n" +
                  "                  \"<block><type : star><position : 4,3></block>\" +\n" +
                  "                  \"<block><type : star><position : 5,3></block>\" +\n" +
                  "                  \"<block><type : star><position : 6,3></block>\" +\n" +
                  "                  \"<block><type : classic><position : 7,3></block>\" +\n" +
                  "                  \"<hero><position : 5,4></hero>" +
                  "<condition><type : star><value : 50></condition>";
     }
     private void addLevel_22(){
          levels[22] = "<lvlNmb>22</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : classic><position : 0,0></block>\" +\n" +
                  "                  \"<block><type : snow><position : 1,0><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 2,0><value : 999></block>\" +\n" +
                  "                  \"<block><type : star><position : 4,0></block>\" +\n" +
                  "                  \"<block><type : snow><position : 6,0><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 7,0><value : 999></block>\" +\n" +
                  "                  \"<block><type : classic><position : 8,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : snow><position : 2,1><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 3,1><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 5,1><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 6,1><value : 999></block>\" +\n" +
                  "                  \"<block><type : classic><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 2,2></block>\" +\n" +
                  "                  \"<block><type : snow><position : 3,2><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 4,2><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 5,2><value : 999></block>\" +\n" +
                  "                  \"<block><type : classic><position : 6,2></block>\" +\n" +
                  "                  \"<hero><position : 4,3></hero>" +
                  "<condition><type : star><value : 50></condition>";
     }
     private void addLevel_23(){
          levels[23] = "<lvlNmb>23</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : snow><position : 0,8><value : 60></block>\" +\n" +
                  "                  \"<block><type : snow><position : 1,8><value : 60></block>\" +\n" +
                  "                  \"<block><type : snow><position : 8,8><value : 60></block>\" +\n" +
                  "                  \"<block><type : snow><position : 9,8><value : 60></block>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 50></condition>";
     }
     private void addLevel_24(){
          levels[24] = "<lvlNmb>24</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "<blockSpawner><type : dark><value : 5></blockSpawner>" +
                  "<blockSpawner><type : snow><value : 7></blockSpawner>" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 100></condition>";
     }
     private void addLevel_25(){
          levels[25] = "<lvlNmb>25</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "<blockSpawner><type : dark><value : 6></blockSpawner>" +
                  "                  \"<block><type : dark><position : 4,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 5,0></block>\" +\n" +
                  "                  \"<block><type : dark><position : 3,1></block>\" +\n" +
                  "                  \"<block><type : snow><position : 4,1><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 5,1><value : 999></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,1></block>\" +\n" +
                  "                  \"<block><type : dark><position : 3,2></block>\" +\n" +
                  "                  \"<block><type : dark><position : 6,2></block>\" +\n" +
                  "                  \"<hero><position : 5,2></hero>" +
                  "<condition><type : score><value : 50></condition>";
     }
     private void addLevel_26(){
          levels[26] = "<lvlNmb>26</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "<blockSpawner><type : dark><value : 6></blockSpawner>" +
                  "                  \"<block><type : snow><position : 9,1></block>\" +\n" +
                  "                  \"<block><type : snow><position : 9,2><value : 999></block>\" +\n" +
                  "                  \"<block><type : dark><position : 9,3></block>\" +\n" +
                  "                  \"<block><type : star><position : 9,4></block>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : star><value : 50></condition>";
     }
     private void addLevel_27(){
          levels[27] = "<lvlNmb>27</lvlNmb>" +
                  "<score>0</score>" +
                  "                  \"<block><type : classic><position : 0,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 1,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 2,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 3,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 5,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 6,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 7,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 8,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 9,0></block>\" +\n" +
                  "                  \"<block><type : classic><position : 0,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 1,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 2,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 3,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 5,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 6,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 7,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 8,1></block>\" +\n" +
                  "                  \"<block><type : classic><position : 0,2></block>\" +\n" +
                  "                  \"<block><type : classic><position : 1,2></block>\" +\n" +
                  "                  \"<block><type : classic><position : 3,2></block>\" +\n" +
                  "                  \"<block><type : classic><position : 5,2></block>\" +\n" +
                  "                  \"<block><type : classic><position : 6,2></block>\" +\n" +
                  "                  \"<block><type : classic><position : 7,2></block>\" +\n" +
                  "                  \"<block><type : classic><position : 8,2></block>\" +\n" +
                  "                  \"<block><type : classic><position : 0,3></block>\" +\n" +
                  "                  \"<block><type : classic><position : 5,3></block>\" +\n" +
                  "                  \"<block><type : classic><position : 5,4></block>\" +\n" +
                  "                  \"<block><type : snow><position : 4,9><value : 60></block>\" +\n" +
                  "                  \"<block><type : snow><position : 4,9><value : 60></block>\" +\n" +
                  "                  \"<block><type : star><position : 4,9></block>\" +\n" +
                  "                  \"<hero><position : 2,2></hero>" +
                  "<condition><type : star><value : 50></condition>";
     }
     private void addLevel_28(){
          levels[28] = "<lvlNmb>28</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 2></blockSpawner>" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : score><value : 100></condition>" +
                  "<condition><type : time><value : 180></condition>";
     }
     private void addLevel_29(){
          levels[29] = "<lvlNmb>29</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "                  \"<block><type : star><position : 2,4></block>\" +\n" +
                  "                  \"<block><type : star><position : 2,6></block>\" +\n" +
                  "                  \"<block><type : star><position : 7,5></block>\" +\n" +
                  "                  \"<block><type : star><position : 7,7></block>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : star><value : 50></condition>";
          for(int i = 0; i < 20 ; i++){
               levels[29] += "<block><type : classic><position : 0,"+ i +"></block>\" +\n" +
                       "                  \"<block><type : snow><position : 1,"+ i +"><value : 180></block>\" +\n" +
                       "                  \"<block><type : snow><position : 8,"+ i +"><value : 180></block>\" +\n" +
                       "                  \"<block><type : classic><position : 9,"+ i +"></block>\" +\n" ;
          }
     }
     private void addLevel_30(){
          levels[30] = "<lvlNmb>30</lvlNmb>" +
                  "<score>0</score>" +
                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
                  "<blockSpawner><type : fire><value : 6></blockSpawner>" +
                  "                  \"<block><type : star><position : 4,6></block>\" +\n" +
                  "                  \"<block><type : star><position : 5,6></block>\" +\n" +
                  "                  \"<block><type : snow><position : 4,7><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 5,7><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 4,8><value : 999></block>\" +\n" +
                  "                  \"<block><type : snow><position : 5,8><value : 999></block>\" +\n" +
                  "                  \"<hero><position : 5,0></hero>" +
                  "<condition><type : star><value : 50></condition>";
     }
}
