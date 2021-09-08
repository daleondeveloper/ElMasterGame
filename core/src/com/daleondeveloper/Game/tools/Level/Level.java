package com.daleondeveloper.Game.tools.Level;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Game.tools.Checkers.ScoreLvlCondition;
import com.daleondeveloper.Game.tools.Checkers.StarLvlCondition;
import com.daleondeveloper.Game.tools.Checkers.TimeLvlCondition;
import com.daleondeveloper.Sprites.BlockControllers.BlockController;
import com.daleondeveloper.Sprites.BlockControllers.BlockSpawner;
import com.daleondeveloper.Sprites.Blocks.Block;
import com.daleondeveloper.Sprites.Blocks.SnowBlock;
import com.daleondeveloper.Sprites.Hero.WaterElement;
import com.daleondeveloper.tools.GameConstants;

import java.util.ArrayList;
import java.util.Iterator;

public class Level {
     public static final int maxLevel = GameConstants.MAX_LEVEL;
     public static final FileHandle savedLevel = Gdx.files.local("saved_level.xml");


     private FileHandle currentLevel;
     private XmlReader.Element xmlLevel;
     private ArrayList<ElementSaved> elementToSave;
     //d - default, f - fire, s - snow, dk - dark, l - light, s - star;
     public Level(int level){
          elementToSave = new ArrayList<ElementSaved>();
          if(level >= 0) {
               if (Gdx.app.getType() == Application.ApplicationType.Android) {
                    currentLevel = Gdx.files.internal("levels/" + level + ".xml");
               } else {
                    currentLevel = Gdx.files.internal(System.getProperty("user.dir") + "/levels/" + level + ".xml"); // хак для desktop проекта, так как он почему-то не видел этих файлов. Создайте символическую ссылку папки assets в в корне desktop-проекта на папку assets android-проекта
               }
          }else{
               currentLevel = savedLevel;
          }
          xmlLevel = new XmlReader().parse(currentLevel);
     }
     public int getLevelNumber(){
          return xmlLevel.getIntAttribute("number");
     }
     public WaterElement getHero(GameWorld gameWorld){
          XmlReader.Element xmlHero = xmlLevel.getChildByName("hero");
          int x = (int)(Float.parseFloat(String.valueOf(xmlHero.getIntAttribute("positionX")))+5)*10;
          int y = (int)(Float.parseFloat(String.valueOf(xmlHero.getIntAttribute("positionY")))+15 )*10;
          WaterElement hero = new WaterElement(gameWorld,x,y);
          elementToSave.add(hero);
          return hero;

     }
     public void getBlock(BlockController blockController){
          Iterator<XmlReader.Element> iterator = xmlLevel.getChildrenByName("block").iterator();
          Block createdBlock = null;
          while (iterator.hasNext()) {
               XmlReader.Element block = (XmlReader.Element)iterator.next();
               int x = (int)(Float.parseFloat(String.valueOf(block.getIntAttribute("positionX")))+5)*10;
               int y = (int)(Float.parseFloat(String.valueOf(block.getIntAttribute("positionY")))+15 )*10;
               createdBlock =  blockController.addBlock(x,y, GameConstants.getBlockTypeByName(block.getAttribute("type")));
               if(createdBlock instanceof SnowBlock) {
                    SnowBlock snowBlock = (SnowBlock) createdBlock;
                    snowBlock.setFreezingTime(block.getIntAttribute("freezeTime"));
               }
               String bodyStr = block.getAttributes().get("body");
               if(bodyStr != null && bodyStr.equals("static")){
                    createdBlock.setStaticBody();
               }
          }
     }
     public void getBlockSpawner(BlockController blockController){
          Iterator<XmlReader.Element> iterator = xmlLevel.getChildrenByName("blockSpawner").iterator();
          while (iterator.hasNext()) {
               XmlReader.Element xmlSpawner = iterator.next();
               new BlockSpawner(blockController,
                       GameConstants.getBlockTypeByName(xmlSpawner.getAttribute("type")),xmlSpawner.getFloatAttribute("value"));
          }
     }
     public void getLevelTasks(LvlEndConditionController lvlEndConditionController, BlockController blockController){
          Iterator<XmlReader.Element> iterator = xmlLevel.getChildrenByName("levelTasks").iterator();
          BlockSpawner lvlTasks = null;
          while (iterator.hasNext()) {
               XmlReader.Element xmlLvlTasks = iterator.next();
               String type = xmlLvlTasks.getAttribute("type");
               if(type.equals("time")){
                    int time = xmlLvlTasks.getIntAttribute("value");
                    lvlEndConditionController.addCondition(new TimeLvlCondition(time));
               }else if(type.equals("score")){
                    int score = xmlLvlTasks.getIntAttribute("value");
                    lvlEndConditionController.addCondition(new ScoreLvlCondition(score));
               }else if(type.equals("star")) {
                    lvlEndConditionController.addCondition(new StarLvlCondition(blockController));
               }
          }

     }
     public int getScore(){
          return xmlLevel.getIntAttribute("score");
     }

     public void saveLevel(String s) {
          savedLevel.delete();
          savedLevel.writeString(s,false);
     }
     //     private void addLevel_0(){
//          XmlReader.Element element = new XmlReader().parse(Gdx.files.internal( "levels/0.xml"));
//          Iterator iterator = element.getChildrenByName("block").iterator();
//          while (iterator.hasNext()) {
//               XmlReader.Element block = (XmlReader.Element)iterator.next();
//               System.out.println(block.getAttribute("type"));
//               System.out.println(block.getAttribute("position"));
//          }
//               levels[0] = "<lvlNmb>0</lvlNmb>" +
//                       "<score>0</score>" +
//                       "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                       "<hero><position : 5,0></hero>" +
//                       "<condition><type : score><value : 30></condition>";
//
//     }
//     private void addLevel_1() {
//          levels[1] = "<lvlNmb>1</lvlNmb>\" +\n" +
//                  "<score>0</score>\" +\n" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>\" +\n" +
//                  "<block><type : classic><position : 1,0></block>\n" +
//                  "<block><type : classic><position : 8,0></block>\n" +
//                  "<block><type : classic><position : 1,1></block>\n" +
//                  "<block><type : classic><position : 8,1></block>\n" +
//                  "<block><type : default><position : 1,2></block>\n" +
//                  "<block><type : default><position : 8,2></block>\n" +
//                  "<block><type : default><position : 1,3></block>\n" +
//                  "<block><type : default><position : 8,3></block>\n" +
//                  "<hero><position : 5,0></hero>\n" +
//                  "<condition><type : score><value : 40></condition>";
//     }
//     private void addLevel_2(){
//          levels[2] = "<lvlNmb>2</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "                  \"<block><type : default><position : 0,0></block>\" +\n" +
//                  "                  \"<block><type : default><position : 1,0></block>\" +\n" +
//                  "                  \"<block><type : default><position : 2,0></block>\" +\n" +
//                  "                  \"<block><type : default><position : 3,0></block>\" +\n" +
//                  "                  \"<block><type : default><position : 6,0></block>\" +\n" +
//                  "                  \"<block><type : default><position : 7,0></block>\" +\n" +
//                  "                  \"<block><type : default><position : 8,0></block>\" +\n" +
//                  "                  \"<block><type : default><position : 9,0></block>\" +\n" +
//                  "                  \"<block><type : default><position : 0,1></block>\" +\n" +
//                  "                  \"<block><type : default><position : 1,1></block>\" +\n" +
//                  "                  \"<block><type : default><position : 2,1></block>\" +\n" +
//                  "                  \"<block><type : default><position : 7,1></block>\" +\n" +
//                  "                  \"<block><type : default><position : 8,1></block>\" +\n" +
//                  "                  \"<block><type : default><position : 9,1></block>\" +\n" +
//                  "                  \"<block><type : default><position : 0,2></block>\" +\n" +
//                  "                  \"<block><type : default><position : 1,2></block>\" +\n" +
//                  "                  \"<block><type : default><position : 9,2></block>\" +\n" +
//                  "                  \"<block><type : default><position : 8,2></block>\" +\n" +
//                  "                  \"<block><type : star><position : 0,3></block>\" +\n" +
//                  "                  \"<block><type : star><position : 9,3></block>\" +\n" +
//                  "                  \"<hero><position : 5,0></hero>" +
//                  "<condition><type : star></condition>";
//     }
//     private void addLevel_3(){
//          levels[3] = "<lvlNmb>3</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "<blockSpawner><type : star><value : 8></blockSpawner>" +
//                  "                  \"<hero><position : 5,1></hero>" +
//                  "<condition><type : score><value : 50></condition>" +
//                  "<condition><type : star><value : 50></condition>";
//     }
//     private void addLevel_4(){
//          levels[4] = "<lvlNmb>4</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "<blockSpawner><type : star><value : 8></blockSpawner>" +
//                  "<blockSpawner><type : dark><value : 7></blockSpawner>" +
//                  "<hero><position : 5,1></hero>" +
//                  "<condition><type : score><value : 20></condition>" +
//                  "<condition><type : star></condition>";
//     }
//     private void addLevel_5(){
//          levels[5] = "<lvlNmb>5</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "<block><type : dark><position : 1,0><body : static></block>"+
//                  "<block><type : dark><position : 3,0><body : static></block>"+
//                  "<block><type : dark><position : 5,0><body : static></block>"+
//                  "<block><type : dark><position : 7,0><body : static></block>"+
//                  "<block><type : dark><position : 9,0><body : static></block>"+
//                  "<block><type : dark><position : 1,0><body : static></block>"+
//                  "<block><type : dark><position : 0,1><body : static></block>"+
//                  "<block><type : dark><position : 2,1><body : static></block>"+
//                  "<block><type : dark><position : 4,1><body : static></block>"+
//                  "<block><type : dark><position : 6,1><body : static></block>"+
//                  "<block><type : dark><position : 8,1><body : static></block>"+
//                  "<block><type : dark><position : 1,2><body : static></block>"+
//                  "<block><type : dark><position : 3,2><body : static></block>"+
//                  "<block><type : dark><position : 5,2><body : static></block>"+
//                  "<block><type : dark><position : 7,2><body : static></block>"+
//                  "<block><type : dark><position : 9,2><body : static></block>"+
//                  "<block><type : dark><position : 0,3><body : static></block>"+
//                  "<block><type : dark><position : 2,3><body : static></block>"+
//                  "<block><type : dark><position : 4,3><body : static></block>"+
//                  "<block><type : dark><position : 6,3><body : static></block>"+
//                  "<block><type : dark><position : 8,3><body : static></block>"+
//                  "<block><type : dark><position : 1,4><body : static></block>"+
//                  "<block><type : dark><position : 3,4><body : static></block>"+
//                  "<block><type : dark><position : 5,4><body : static></block>"+
//                  "<block><type : dark><position : 7,4><body : static></block>"+
//                  "<block><type : dark><position : 9,4><body : static></block>"+
//                  "<block><type : dark><position : 0,5><body : static></block>"+
//                  "<block><type : dark><position : 2,5><body : static></block>"+
//                  "<block><type : dark><position : 4,5><body : static></block>"+
//                  "<block><type : dark><position : 6,5><body : static></block>"+
//                  "<block><type : dark><position : 8,5><body : static></block>"+
//                  "<block><type : dark><position : 1,6><body : static></block>"+
//                  "<block><type : dark><position : 3,6><body : static></block>"+
//                  "<block><type : dark><position : 5,6><body : static></block>"+
//                  "<block><type : dark><position : 7,6><body : static></block>"+
//                  "<block><type : dark><position : 9,6><body : static></block>"+
//
//
//                  "<block><type : star><position : 0,0><body : static></block>"+
//                  "<block><type : star><position : 8,0><body : static></block>"+
//                  "<block><type : star><position : 1,1><body : static></block>"+
//                  "<block><type : star><position : 7,1><body : static></block>"+
//                  "<block><type : star><position : 2,2><body : static></block>"+
//                  "<block><type : star><position : 6,2><body : static></block>"+
//                  "<block><type : star><position : 3,3><body : static></block>"+
//                  "<block><type : star><position : 5,3><body : static></block>"+
//                  "<block><type : star><position : 4,4><body : static></block>"+
//
//                  "<hero><position : 5,5></hero>" +
//                  "<condition><type : star></condition>";
//     }
//
//     private void addLevel_10(){
//          levels[10] = "<lvlNmb>10</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 1,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,2></block>\" +\n" +
//                  "                  \"<hero><position : 5,0></hero>" +
//                  "<condition><type : score><value : 50></condition>";
//     }
//     private void addLevel_11(){
//          levels[11] = "<lvlNmb>11</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 3,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 9,0></block>\" +\n" +
//                  "                  \"<hero><position : 5,1></hero>" +
//                  "<condition><type : score><value : 50></condition>";
//     }
//     private void addLevel_12(){
//          levels[12] = "<lvlNmb>12</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "                  \"<block><type : classic><position : 0,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 1,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 2,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 3,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 4,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 0,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 1,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 2,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 3,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 4,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,2></block>\" +\n" +
//                  "                  \"<hero><position : 3,2></hero>" +
//                  "<condition><type : score><value : 50></condition>";
//     }
//     private void addLevel_13(){
//          levels[13] = "<lvlNmb>13</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "                  \"<block><type : classic><position : 0,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 1,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 8,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 9,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 0,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 1,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 8,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 9,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 0,2></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 1,2></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 8,2></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 9,2></block>\" +\n" +
//                  "                  \"<block><type : star><position : 1,3></block>\" +\n" +
//                  "                  \"<block><type : star><position : 8,3></block>\" +\n" +
//                  "                  \"<hero><position : 5,0></hero>" +
//                  "<condition><type : star><value : 50></condition>";
//     }
//     private void addLevel_14(){
//          levels[14] = "<lvlNmb>14</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "<blockSpawner><type : dark><value : 5></blockSpawner>" +
//                  "                  \"<hero><position : 5,0></hero>" +
//                  "<condition><type : score><value : 50></condition>";
//     }
//     private void addLevel_15(){
//          levels[15] = "<lvlNmb>15</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "<blockSpawner><type : dark><value : 6></blockSpawner>" +
//                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 2,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 3,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 2,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 3,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,1></block>\" +\n" +
//                  "                  \"<block><type : star><position : 1,2></block>\" +\n" +
//                  "                  \"<block><type : star><position : 8,2></block>\" +\n" +
//                  "                  \"<block><type : star><position : 1,3></block>\" +\n" +
//                  "                  \"<block><type : star><position : 8,3></block>\" +\n" +
//                  "                  \"<hero><position : 7,3></hero>" +
//                  "<condition><type : score><value : 50></condition>" +
//                  "<condition><type : star><value : 50></condition>";
//     }
//     private void addLevel_16(){
//          levels[16] = "<lvlNmb>16</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 2></blockSpawner>" +
//                  "<blockSpawner><type : dark><value : 4></blockSpawner>" +
//                  "                  \"<hero><position : 5,0></hero>" +
//                  "<condition><type : time><value : 120></condition>";
//     }
//     private void addLevel_17(){
//          levels[17] = "<lvlNmb>17</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "                  \"<block><type : dark><position : 0,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 2,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 9,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 0,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 9,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 0,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,2></block>\" +\n" +
//                  "                  \"<hero><position : 5,2></hero>" +
//                  "<condition><type : score><value : 50></condition>";
//     }
//     private void addLevel_18(){
//          levels[18] = "<lvlNmb>18</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 2,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 2,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 1,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 2,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 1,3></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 2,3></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,3></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,3></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,3></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,3></block>\" +\n" +
//                  "                  \"<block><type : star><position : 1,4></block>\" +\n" +
//                  "                  \"<block><type : star><position : 8,4></block>\" +\n" +
//                  "                  \"<block><type : star><position : 1,5></block>\" +\n" +
//                  "                  \"<block><type : star><position : 8,5></block>\" +\n" +
//                  "                  \"<hero><position : 5,4></hero>" +
//                  "<condition><type : star><value : 50></condition>";
//     }
//     private void addLevel_19(){
//          levels[19] = "<lvlNmb>19</lvlNmb>" +
//                  "<score>0</score>" +
//                  "                  \"<block><type : dark><position : 0,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 1,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 2,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 3,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 1,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 2,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 3,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 2,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 3,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 3,3></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,3></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,3></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,3></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,3></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,3></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,4></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,4></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,4></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,4></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,4></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,5></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,5></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,5></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,5></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,6></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,6></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,6></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,7></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 8,7></block>\" +\n" +
//                  "                  \"<block><type : star><position : 8,8></block>\" +\n" +
//                  "                  \"<hero><position : 0,1></hero>" +
//                  "<condition><type : star><value : 50></condition>";
//     }
//     private void addLevel_20(){
//          levels[20] = "<lvlNmb>20</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "                  \"<block><type : classic><position : 0,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 9,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 0,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 9,1></block>\" +\n" +
//                  "                  \"<block><type : snow><position : 0,2><value : 20></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 1,2></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 8,2></block>\" +\n" +
//                  "                  \"<block><type : snow><position : 9,2><value : 20></block>\" +\n" +
//                  "                  \"<block><type : star><position : 1,3></block>\" +\n" +
//                  "                  \"<block><type : star><position : 8,3></block>\" +\n" +
//                  "                  \"<hero><position : 5,0></hero>" +
//                  "<condition><type : star><value : 50></condition>";
//     }
//     private void addLevel_21(){
//          levels[21] = "<lvlNmb>21</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "                  \"<block><type : classic><position : 2,2><body : static ></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 3,2><body : static ></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 6,2><body : static ></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 4,2><body : static ></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 5,2><body : static ></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 7,2><body : static ></block>\" +\n" +
//                  "                  \"<block><type : star><position : 2,3></block>\" +\n" +
//                  "                  \"<block><type : star><position : 3,3></block>\" +\n" +
//                  "                  \"<block><type : star><position : 4,3></block>\" +\n" +
//                  "                  \"<block><type : star><position : 5,3></block>\" +\n" +
//                  "                  \"<block><type : star><position : 6,3></block>\" +\n" +
//                  "                  \"<block><type : star><position : 7,3></block>\" +\n" +
//                  "                  \"<hero><position : 5,4></hero>" +
//                  "<condition><type : star><value : 50></condition>";
//     }
//     private void addLevel_22(){
//          levels[22] = "<lvlNmb>22</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "                  \"<block><type : dark><position : 1,0><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 2,0><body : static></block>\" +\n" +
//                  "                  \"<block><type : star><position : 4,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,0><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 7,0><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 2,1><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 3,1><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,1><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,1><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 3,2><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,2><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,2><body : static></block>\" +\n" +
//                  "                  \"<hero><position : 4,3></hero>" +
//                  "<condition><type : star><value : 50></condition>";
//     }
//     private void addLevel_23(){
//          levels[23] = "<lvlNmb>23</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "                  \"<block><type : snow><position : 0,8><value : 60></block>\" +\n" +
//                  "                  \"<block><type : snow><position : 1,8><value : 60></block>\" +\n" +
//                  "                  \"<block><type : snow><position : 8,8><value : 60></block>\" +\n" +
//                  "                  \"<block><type : snow><position : 9,8><value : 60></block>\" +\n" +
//                  "                  \"<hero><position : 5,0></hero>" +
//                  "<condition><type : score><value : 50></condition>";
//     }
//     private void addLevel_24(){
//          levels[24] = "<lvlNmb>24</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "<blockSpawner><type : dark><value : 5></blockSpawner>" +
//                  "<blockSpawner><type : snow><value : 7></blockSpawner>" +
//                  "                  \"<hero><position : 5,0></hero>" +
//                  "<condition><type : score><value : 100></condition>";
//     }
//     private void addLevel_25(){
//          levels[25] = "<lvlNmb>25</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "<blockSpawner><type : dark><value : 6></blockSpawner>" +
//                  "                  \"<block><type : dark><position : 4,0><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,0><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 3,1><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 4,1><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 5,1><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,1><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 3,2><body : static></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 6,2><body : static></block>\" +\n" +
//                  "                  \"<hero><position : 5,2></hero>" +
//                  "<condition><type : score><value : 50></condition>";
//     }
//     private void addLevel_26(){
//          levels[26] = "<lvlNmb>26</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "<blockSpawner><type : dark><value : 6></blockSpawner>" +
//                  "                  \"<block><type : dark><position : 9,0></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 9,1></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 9,2></block>\" +\n" +
//                  "                  \"<block><type : dark><position : 9,3></block>\" +\n" +
//                  "                  \"<block><type : star><position : 9,4></block>\" +\n" +
//                  "                  \"<hero><position : 5,0></hero>" +
//                  "<condition><type : star><value : 50></condition>";
//     }
//     private void addLevel_27(){
//          levels[27] = "<lvlNmb>27</lvlNmb>" +
//                  "<score>0</score>" +
//                  "                  \"<block><type : classic><position : 0,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 1,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 2,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 3,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 5,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 6,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 7,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 8,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 9,0></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 0,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 1,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 2,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 3,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 5,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 6,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 7,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 8,1></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 0,2></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 1,2></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 3,2></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 5,2></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 6,2></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 7,2></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 8,2></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 0,3></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 5,3></block>\" +\n" +
//                  "                  \"<block><type : classic><position : 5,4></block>\" +\n" +
//                  "                  \"<block><type : snow><position : 4,9><value : 30></block>\" +\n" +
//                  "                  \"<block><type : snow><position : 4,10><value : 30></block>\" +\n" +
//                  "                  \"<block><type : star><position : 4,11></block>\" +\n" +
//                  "                  \"<hero><position : 2,2></hero>" +
//                  "<condition><type : star><value : 50></condition>";
//     }
//     private void addLevel_28(){
//          levels[28] = "<lvlNmb>28</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 2></blockSpawner>" +
//                  "                  \"<hero><position : 5,0></hero>" +
//                  "<condition><type : score><value : 100></condition>" +
//                  "<condition><type : time><value : 180></condition>";
//     }
//     private void addLevel_29(){
//          levels[29] = "<lvlNmb>29</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : classic><value : 3></blockSpawner>" +
//                  "                  \"<block><type : star><position : 1,4></block>\" +\n" +
//                  "                  \"<block><type : star><position : 0,4></block>\" +\n" +
//                  "                  \"<block><type : star><position : 8,4></block>\" +\n" +
//                  "                  \"<block><type : star><position : 9,4></block>\" +\n" +
//                  "                  \"<block><type : star><position : 0,6></block>\" +\n" +
//                  "                  \"<block><type : star><position : 1,6></block>\" +\n" +
//                  "                  \"<block><type : star><position : 8,6></block>\" +\n" +
//                  "                  \"<block><type : star><position : 9,6></block>\" +\n" +
//                  "                  \"<block><type : star><position : 0,5></block>\" +\n" +
//                  "                  \"<block><type : star><position : 1,5></block>\" +\n" +
//                  "                  \"<block><type : star><position : 9,5></block>\" +\n" +
//                  "                  \"<block><type : star><position : 8,5></block>\" +\n" +
//                  "                  \"<block><type : star><position : 0,7></block>\" +\n" +
//                  "                  \"<block><type : star><position : 1,7></block>\" +\n" +
//                  "                  \"<block><type : star><position : 9,7></block>\" +\n" +
//                  "                  \"<block><type : star><position : 8,7></block>\" +\n" +
//                  "                  \"<hero><position : 5,0></hero>" +
//                  "<condition><type : star><value : 50></condition>";
//          for(int i = 0; i < 20 ; i++) {
//               if (i < 3 || i > 8 && i < 20) {
//                    levels[29] += "<block><type : classic><position : 0," + i + "></block>\" +\n" +
//                            "                  \"<block><type : classic><position : 1," + i + "><value : 180></block>\" +\n" +
//                            "                  \"<block><type : classic><position : 8," + i + "><value : 180></block>\" +\n" +
//                            "                  \"<block><type : classic><position : 9," + i + "></block>\" +\n";
//               }
//          }
//     }
//     private void addLevel_30(){
//          levels[30] = "<lvlNmb>30</lvlNmb>" +
//                  "<score>0</score>" +
//                  "<blockSpawner><type : fire><value : 2></blockSpawner>" +
//                  "                  \"<block><type : star><position : 4,4><body : static></block>\" +\n" +
//                  "                  \"<block><type : star><position : 5,4><body : static></block>\" +\n" +
//                  "                  \"<block><type : star><position : 4,5><body : static></block>\" +\n" +
//                  "                  \"<block><type : star><position : 5,5><body : static></block>\" +\n" +
//                  "                  \"<block><type : star><position : 4,6><body : static></block>\" +\n" +
//                  "                  \"<block><type : star><position : 5,6><body : static></block>\" +\n" +
//                  "                  \"<hero><position : 5,0></hero>" +
//                  "<condition><type : star><value : 50></condition>";
//     }
}
