package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.Screens.ListenerHelper;
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
    private Image nextModesImage;
    private Image previsionModeImage;
    private Image startButton;

    private int level;

    private String[] tile;
    private String[] allTexts;
    private String[] tasks;

    private boolean tasksShow;

    private Image backButton;

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
        defineButtons();
    }

    private void defineButtons(){


    }

    @Override
    protected void defineElements() {
        mainLabel = new Label(i18NGameThreeBundle.format("creditsScreen.title"),labelStyleMedium);
        textLabel = new Label(i18NGameThreeBundle.format("creditsScreen.text"),labelStyleSmall);
        textLabel.setWrap(true);

        nextModesImage = new Image(Assets.getInstance().getAssetGUI().getButtonRight());
        previsionModeImage = new Image(assets.getAssetGUI().getButtonLeft());
        startButton = new Image(assets.getAssetGUI().getButtonStart());
        backButton  =new Image(new TextureRegionDrawable(assets.getAssetGUI().getButtonX()));

    }

    @Override
    protected void addAction() {
        nextModesImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                scrollPane.setScrollY(scrollPane.getVisualScrollY() + 150);
                scrollPane.updateVisualScroll();
            }
        }));

        previsionModeImage.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                scrollPane.setScrollY(scrollPane.getVisualScrollY() - 150);
                scrollPane.updateVisualScroll();

            }
        }));
        startButton.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                if(tasksShow) {
                    menuScreen.hideMenuScreen();
                }else {
                    tasksShow = true;
                    addToTable();
                }
            }
        }));
        backButton.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.hideMenuScreen();
            }
        }));
    }

    @Override
    protected void addToTable() {
        mainTable.clearChildren();
        if(DebugConstants.DEBUG_GUI){
            mainTable.debug();
        }
        addBackButtonToTable(mainTable,backButton);
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
            mainTable.row();
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
        if(tasksShow){
            textLabel.setText(tasks[level]);
        }else {
            textLabel.setText(allTexts[level]);
        }
        textLabel.setWrap(true);
        textTable.add(textLabel).growX();
        textTable.row();
        mainTable.add(scrollPane).growX().padRight(50).padLeft(50);
        mainTable.row();
    }

    private void addFootTable(){
        Table moveArrowTable = new Table();
        mainTable.add(moveArrowTable).padBottom(20).padRight(50).padLeft(50).growX();
        moveArrowTable.add(previsionModeImage).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).left()
                .padRight(50);
        moveArrowTable.add().growX();
        moveArrowTable.add(startButton).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT); moveArrowTable.add().growX();
        moveArrowTable.add(nextModesImage).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).right()
                .padLeft(50);
    }

    private void addTile(){
        tile[0] = "Teaching";
        tile[1] = "First lesson";
        tile[2] = "First problems";
        tile[3] = "Tower";
        tile[4] = "Two towers";
        tile[5] = "Pyramid";
        tile[6] = "Limited area";
        tile[7] = "Two pyramid";
        tile[8] = "Lesson at height";
        tile[9] = "Survival at altitude";
        tile[10] = "Again the first problems";
    }
    private void addTasks(){
        tasks[0] = "-Clear 1 block line";
        tasks[1] = "-Clear 5 block line";
        tasks[2] = "-Clear 5 block line";
        tasks[3] = "-Clear 5 block line";
        tasks[4] = "-Clear 5 block line";
        tasks[5] = "-Clear 10 block line";
        tasks[6] = "-Clear 10 block line";
        tasks[7] = "-Clear 10 block line";
        tasks[8] = "-Clear 5 block line";
        tasks[9] = "-Survive 60 seconds";
        tasks[10] = "-Clear 3 block line";
    }
    private void addAllTexts(){
        allTexts[0] = "   Congratulations, young student !!! \n "+
                "   You are looking for the highest teacher and I will teach you what I know.\n" +
                "   I will make you a real hero, that we can defeat any evil that has ruined this world" +
                "you will be able to compare forces with real dragons and act in a hellish battle against them. \n \n" +
                "   Let's start with what you can do. With the buttons below you can do different actions. \n " +
                "   Yes, use the buttons on the left to move to the sides, and the buttons on the right to push the blocks and jump.\n" +
                "   Try to dodge the blocks and draw a line from them";
        allTexts[1] = "Now you know what and how, let's move on to training, to score 50 points to begin with";
        allTexts[2] = "The first lesson was easy for you, so let's complicate the arena, I've added a few blocks to it, let's see how you handle it.";
        allTexts[3] = "I managed the first tests skillfully, now I have built one tower in the arena.\n" +
                "Good luck !!";
         allTexts[4] = "And try to deal with two towers.";
         allTexts[5] = "How did you like the towers?\n" +
                 "I replaced the towers with a pyramid, let's see how you like it.";
        allTexts[6] = "Towers and pyramids have tired you, my student.\n" +
                "You can relax a little, this time I just reduced the arena.";
        allTexts[7] = "After a little rest, now you will try to practice with two pyramids.";
        allTexts[8] = "Try to deal with the tall tower now.\n" +
                "Don't fall, because the lesson will become harder in an instant.";
        allTexts[9] = "Let's continue the lessons at height.\n" +
                "In this lesson, you do not need to erase the lines, just survive for a minute.";
         allTexts[10] = "Let's remember where we started, I think this level is familiar to you.";
    }
}
