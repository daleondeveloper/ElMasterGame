package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.DebugConstants;
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

    private String[] tile;
    private String[] allTexts;

    private Image backButton;

    public TeacherMenuFiller(MenuScreen menuScreen){
        this.menuScreen = menuScreen;

        assets = Assets.getInstance();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();

        tile = new String[GameConstants.MAX_LEVEL];
        allTexts = new String[GameConstants.MAX_LEVEL];

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
        mainTable.top();
        mainTable.add(backButton).height(15).width(15).right().padRight(30);
        mainTable.row();
        Table labelTable = new Table();
        mainTable.add(labelTable).growX();
        labelTable.add().growX();
        addTile();
        mainLabel.setText(tile[0]);
        labelTable.add(mainLabel);
        labelTable.add().growX();
        labelTable.row();
        mainTable.row();
        addScrollPanelToMainTable(mainTable);
        mainTable.row();
        Table moveArrowTable = new Table();
        mainTable.add(moveArrowTable).padBottom(20).padRight(50).padLeft(50).growX();
        moveArrowTable.add(previsionModeImage).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).left()
                .padRight(50);
        moveArrowTable.add().growX();
        moveArrowTable.add(nextModesImage).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).right()
                .padLeft(50);
    }
    private void addScrollPanelToMainTable(Table mainTable){
        Table textTable = new Table();
        scrollPane = new ScrollPane(textTable);
        scrollPane.setScrollingDisabled(true,false);
        addAllTexts();
        textLabel.setText(allTexts[0]);
//        textLabel.setAlignment(Align.center);
        textLabel.setWrap(true);
        textTable.add(textLabel).width(mainTable.getMinWidth() * 2.5f);
        textTable.row();
        mainTable.add(scrollPane);
        mainTable.row();
    }

    private void addTile(){
        tile[0] = "Teaching";
        tile[1] = "Перший урок";
        tile[2] = "Перший урок";
    }
    private void addAllTexts(){
        allTexts[0] = "   Congratulations, young student !!! \n "+
                "   You are looking for the highest teacher and I will teach you what I know.\n" +
                "   I will make you a real hero, that we can defeat any evil that has ruined this world" +
                "you will be able to compare forces with real dragons and act in a hellish battle against them. \n \n" +
                "   Let's start with what you can do. With the buttons below you can do different actions. \n " +
                "   Yes, use the buttons on the left to move to the sides, and the buttons on the right to push the blocks and jump.\n" +
                "   Try to dodge the blocks and draw a line from them";
        allTexts[1] = "Тепер ти знаєш, що і як, перейдемо до навчання, для початку набери 100 очків";
        allTexts[2] = "Перший урок тобі легко дався, тому давай ускладнемо арену, я додав декілька блоків уже на неї," +
                "подивимося як ти з цим справишся.";
    }
}
