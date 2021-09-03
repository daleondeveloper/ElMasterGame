//package com.daleondeveloper.Game.tools.Level;
//
//import com.daleondeveloper.Game.GameWorld;
//import com.daleondeveloper.Game.Settings.GameSettings;
//import com.daleondeveloper.Game.tools.Checkers.ScoreLvlCondition;
//import com.daleondeveloper.Game.tools.Checkers.StarLvlCondition;
//import com.daleondeveloper.Game.tools.Checkers.TimeLvlCondition;
//import com.daleondeveloper.Sprites.BlockControllers.BlockController;
//import com.daleondeveloper.Sprites.Hero.WaterElement;
//
//import java.util.ArrayList;
//
//public class LevelGenerator {
//    private static final String TAG = LevelGenerator.class.getName();
//
//    private GameWorld gameWorld;
//    private Level level;
//    private LevelParser levelParser;
//    private LvlEndConditionController lvlEndConditionController;
//    private BlockController blockController;
//    private WaterElement waterElement;
//
//    private int score ;
//
//    public LevelGenerator(GameWorld gameWorld,int lvlNumber){
//        this.gameWorld = gameWorld;
//        level = new Level();
//        levelParser = new LevelParser(level.getLevel(lvlNumber));
//        lvlEndConditionController = new LvlEndConditionController();
//        score = levelParser.getScore();
//        createStartBlockController();
//        addStartBlocks();
//        createHero();
//        loadNotSavedElement();
//    }
//
//    public void loadNotSavedElement(){
//        levelParser = new LevelParser(level.getLevel(getLevelNumber()));
//        GameSettings.getInstance().setLevel(getLevelNumber());
//        createBlockSpawners();
//        createLevelConditions();
//    }
//    private void createLevelConditions(){
//        ArrayList<String> allConditions = levelParser.getObjectsByPattern(LevelParser.findLevelChecker);
//        for(String condition : allConditions){
//            String type = levelParser.getType(condition);
//            if(type.equals("time")){
//                int time = levelParser.getValue(condition);
//                lvlEndConditionController.addCondition(new TimeLvlCondition(time));
//            }else if(type.equals("score")){
//                int score = levelParser.getValue(condition);
//                lvlEndConditionController.addCondition(new ScoreLvlCondition(score));
//            }else if(type.equals("star")){
//                lvlEndConditionController.addCondition(new StarLvlCondition(blockController));
//            }else{
//
//            }
//        }
//    }
//
//    private void createStartBlockController(){
//        blockController = new BlockController(gameWorld);
//    }
//
//
//    public int getLevelNumber(){
//        return levelParser.getLevelNumber();
//    }
//    public int getScore(){
//        return score;
//    }
//    public BlockController getBlockController() {
//        return blockController;
//    }
//    public WaterElement getHero(){
//        if(waterElement == null){
//            createHero();
//        }
//        return waterElement;
//    }
//
//    public LvlEndConditionController getLvlEndConditionController() {
//        return lvlEndConditionController;
//    }
//
//}
