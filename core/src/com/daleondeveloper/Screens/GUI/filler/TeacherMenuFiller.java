package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.Button.BackButton;
import com.daleondeveloper.Screens.GUI.Button.ScrollArrowButtonLeft;
import com.daleondeveloper.Screens.GUI.Button.ScrollArrowButtonRight;
import com.daleondeveloper.Screens.GUI.Button.StartButton;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.tools.GameConstants;

public class TeacherMenuFiller extends MenuFiller {
    private static final String TAG = TeacherMenuFiller.class.getName();

    private MenuScreen menuScreen;
    private Assets assets;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private Label.LabelStyle labelStyleSmall;
    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleLarge;

    private ScrollPane scrollPane;
    private Label mainLabel;
    private Label textLabel;
    private Label secondTextLabel;


    private int level;

    private String[] tile;
    private String[] allTexts;
    private String[] tasks;

    private boolean tasksShow;

    private Image help1;
    private Image help2;
    private Image help3;

    public TeacherMenuFiller(MenuScreen menuScreen){
        this.menuScreen = menuScreen;

        assets = Assets.getInstance();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();

        tile = new String[GameConstants.MAX_LEVEL];
        allTexts = new String[GameConstants.MAX_LEVEL];
        tasks = new String[GameConstants.MAX_LEVEL];
        addTile();
        addTasks();
        addAllTexts();
        tasksShow = false;

        level = GameSettings.getInstance().getLevel();

        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assets.getAssetFonts().getSmall();
        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assets.getAssetFonts().getNormal();
        labelStyleLarge = new Label.LabelStyle();
        labelStyleLarge.font = assets.getAssetFonts().getBig();

    }

    @Override
    public void build() {
        mainTable = menuScreen.getWindowTable();
        super.build();
    }
    @Override
    protected void defineElements() {
        mainLabel = new Label(i18NGameThreeBundle.format("creditsScreen.title"),labelStyleMedium);
        textLabel = new Label(i18NGameThreeBundle.format("creditsScreen.text"),labelStyleSmall);
        textLabel.setWrap(true);
        secondTextLabel = new Label(i18NGameThreeBundle.format("creditsScreen.text"),labelStyleSmall);
        secondTextLabel.setWrap(true);

        help1 = new Image(assets.getAssetHelp().getHelp_block_fall());
        help2 = new Image(assets.getAssetHelp().getHelp_block_push());
        help3 = new Image(assets.getAssetHelp().getHelp_create_block_line());

    }

    @Override
    protected void addAction() {
    }

    @Override
    protected void addToTable() {
        mainTable.clearChildren();
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }
        mainTable.add(new BackButton(menuScreen)).height(15).width(15).right().padRight(30);
        mainTable.row();
        if(level >= 0) {
            addTitleToTable();
            addScrollPanelToMainTable();
            addFootTable();
        }else{
            Table labelTable = new Table();
            mainTable.add(labelTable).growX();
            mainLabel.setText("Continue");
            labelTable.add().growX();
            labelTable.add(mainLabel);
            labelTable.add().growX();
            labelTable.row();
            tasksShow = true;
            mainTable.row();
            addFootTable();
        }

    }
    private void addTitleToTable(){
        Table labelTable = new Table();
        mainTable.add(labelTable).growX();
        mainLabel.setText(tile[level]);
        labelTable.add().growX();
        labelTable.add(mainLabel);
        labelTable.add().growX();
        labelTable.row();
        mainTable.row();

    }
    private void addScrollPanelToMainTable(){
        Table textTable = new Table();
        scrollPane = new ScrollPane(textTable);
        scrollPane.setScrollingDisabled(true,false);
        if(tasksShow || allTexts[level] == null ){
            textLabel.setText(tasks[level]);
            tasksShow = true;
        }else {
            textLabel.setText(allTexts[level]);
        }
        textLabel.setWrap(true);
        textTable.add(textLabel).growX();
        textTable.row();
        if((!tasksShow && allTexts[level] != null) && level == 0 ) {
            textTable.add(help1);
            textTable.row();
            textTable.add(help2);
            textTable.row();
            textTable.add(help3);
            textTable.row();
            secondTextLabel.setText("So I showed you the basics, try this knowledge and happiness in practice now !!!");
            textTable.add(secondTextLabel).growX();
            textTable.row();
        }
        mainTable.add(scrollPane).growX().padRight(50).padLeft(50);
        mainTable.row();
    }

    private void addFootTable(){
        Table moveArrowTable = new Table();
        mainTable.add(moveArrowTable).padBottom(20).padRight(50).padLeft(50).growX();
        moveArrowTable.add(new ScrollArrowButtonLeft(scrollPane,150)).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).left()
                .padRight(50);
        moveArrowTable.add().growX();
        moveArrowTable.add(new StartButton(menuScreen,this)).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT);
        moveArrowTable.add().growX();
        moveArrowTable.add(new ScrollArrowButtonRight(scrollPane,150)).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).right()
                .padLeft(50);
    }
    public void tasksShow(){
            tasksShow = true;
            addToTable();
    }
    private void addTile(){
        tile[0] = "Teaching";
        tile[1] = "The first obstacles";
        tile[2] = "Dark Block";
        tile[3] = "Star Block";
        tile[4] = "Two towers";
        tile[5] = "Very Hard Level";
//        tile[6] = "Limited area";
//        tile[7] = "Two pyramid";
//        tile[8] = "Lesson at height";
//        tile[9] = "Survival at altitude";
//        tile[10] = "Again the first problems";
//        tile[11] = "Level 11";
//        tile[12] = "Level 12";
//        tile[13] = "Level 13";
//        tile[14] = "Level 14";
//        tile[15] = "Level 15";
//        tile[16] = "Level 16";
//        tile[17] = "Level 17";
//        tile[18] = "Level 18";
//        tile[19] = "Level 19";
//        tile[20] = "Level 20";
//        tile[21] = "Level 21";
//        tile[22] = "Level 22";
//        tile[23] = "Level 23";
//        tile[24] = "Level 24";
//        tile[25] = "Level 25";
//        tile[26] = "Level 26";
//        tile[27] = "Level 27";
//        tile[28] = "Level 28";
//        tile[29] = "Level 29";
//        tile[30] = "Level 30";
    }
    private void addTasks(){
        tasks[0] = "-Clear 1 block line";
        tasks[1] = "-Clear 4 block line";
        tasks[2] = "-Clear 5 block line \n\n" +
                "*** NEW DARK BLOCK *** \n" +
                "Dark block cannot be moved";
        tasks[3] = "-Clear all star block line" +
                "***NEW STAR BLOCK*** \n" +
                "To go to the next level you need to erase the star blocks, they are no different from normal.";
        tasks[4] ="-Clear all star block line";
        tasks[5] = "-Collect 500 score" +
                "***NEW FIRE BLOCK***";
//        tasks[6] = "-Clear 7 block line";
//        tasks[7] = "-Clear all star block line";
//        tasks[8] = "-Clear 5 block line";
//        tasks[9] = "-Survive 60 seconds \n" +
//                "-Clear 2 block line";
//        tasks[10] = "-Clear 5 block line\n\n" +
//                "***NEW HEAVY BLOCK***";
//        tasks[11] = "-Clear 5 block line";
//        tasks[12] = "-Clear 5 block line";
//        tasks[13] = "-Clear all stars";
//        tasks[14] = "-Clear 5 block line";
//        tasks[15] = "-Clear 5 block line \n" +
//                    "-Cleat all stars";
//        tasks[16] = "-Revive 120 seconds";
//        tasks[17] = "-Clear 5 block line";
//        tasks[18] = "-Clear all stars";
//        tasks[19] = "-Clear all stars";
//        tasks[20] = "-Clear all stars";
//        tasks[21] = "-Clear all stars";
//        tasks[22] = "-Clear all stars";
//        tasks[23] = "-Clear 5 block line";
//        tasks[24] = "-Clear 10 block line";
//        tasks[25] = "-Clear 5 block line";
//        tasks[26] = "-Clear all stars";
//        tasks[27] = "-Clear all stars";
//        tasks[28] = "-Revive 180 seconds";
//        tasks[29] = "-Clear all stars";
//        tasks[30] = "-Clear all stars";
//
    }
    private void addAllTexts(){
        allTexts[0] = "Congratulations Mighty Hero !!!!\n" +
                "I am your helper in this fascinating world of adventure.\n" +
                "My name is Boros.\n" +
                "And let me show you what and how here.";
        allTexts[1] = "The path of the hero is not always easy, and there are always quality obstacles, then add them.";
        allTexts[2] = "No you always have to chase the number of points, sometimes - the star ...";
         allTexts[4] = "I have prepared something interesting for you, I have filled the blocks with dark essence, and now only a few can move them.";
         allTexts[5] = "How did you like the towers?\n" +
                 "I replaced the towers with a pyramid, let's see how you like it.";
//        allTexts[6] = "Towers and pyramids have tired you, my student.\n" +
//                "You can relax a little, this time I just reduced the arena.";
//        allTexts[7] = "After a little rest, now you will try to practice with two pyramids.";
//        allTexts[8] = "Try to deal with the tall tower now.\n" +
//                "Don't fall, because the lesson will become harder in an instant.";
//        allTexts[9] = "Let's continue the lessons at height.\n" +
//                "In this lesson, you do not need to erase the lines, just survive for a minute.";
//         allTexts[10] = "Let's remember where we started, I think this level is familiar to you.";
    }
    public boolean getTasksBoolean(){
        return  tasksShow;
    }
}
