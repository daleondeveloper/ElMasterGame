package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.Button.BackButton;
import com.daleondeveloper.Screens.GUI.Button.LevelButton;
import com.daleondeveloper.Screens.GUI.Button.ScrollArrowButtonLeft;
import com.daleondeveloper.Screens.GUI.Button.ScrollArrowButtonRight;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.tools.GameConstants;



public class LevelChangeMenuFiller extends MenuFiller {
    private static final String TAG = LevelChangeMenuFiller.class.getName();

    private static final int LAST_CHANGE_MODE_PAGE = 2;

    private MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private int pageShow;

    private ScrollPane scrollPane;
    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;
    private Label gameModeChangeTitleLabel;


    public LevelChangeMenuFiller(MenuScreen menuScreen) {

        this.menuScreen = menuScreen;
        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        // Styles
        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assets.getAssetFonts().getNormal();
        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assets.getAssetFonts().getSmall();

        pageShow = 0;
    }

    @Override
    public void build() {
        mainTable = menuScreen.getWindowTable();
        super.build();
    }

    @Override
    protected void defineElements() {
        // Title
        gameModeChangeTitleLabel = new Label(i18NGameThreeBundle.format("levelsScreen.title"), labelStyleMedium);
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
        mainTable.top();
        mainTable.add(new BackButton(menuScreen)).height(15).width(15).right().padRight(30);
        mainTable.row();
        Table labelTable = new Table();
        mainTable.add(labelTable).growX();
        labelTable.add().growX();
        labelTable.add(gameModeChangeTitleLabel);
        labelTable.add().growX();
        mainTable.row();
            chooseModePanel();
    }
    private void chooseModePanel(){
        // Таблиця вибору режиму гри
        Table gameModeTable = new Table();
        scrollPane = new ScrollPane(gameModeTable);
        scrollPane.setScrollingDisabled(true,false);
        mainTable.add(scrollPane);
        gameModeTable.defaults().pad(10).padLeft(10).padRight(10).width(GameConstants.BUTTON_WIDTH/8).height(GameConstants.BUTTON_HEIGHT).center();
        int level = prefs.getHighCompletedLvl() + 1;
        if(level > GameConstants.MAX_LEVEL){
            level = GameConstants.MAX_LEVEL;
        }
        for(int i = 1, j = 0 ; i < level; i++, j++){
            gameModeTable.add(new LevelButton(i));
            if(j == 5) {
                j = -1;
                gameModeTable.row();
            }
        }

        mainTable.row();
        //Таблиця з кнопками навішації по режимам гри
        Table moveArrowTable = new Table();
        mainTable.add(moveArrowTable).padBottom(20).padRight(50).padLeft(50).growX();
        moveArrowTable.add(new ScrollArrowButtonLeft(scrollPane,189)).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).left()
        .padRight(50);
        moveArrowTable.add().growX();
        moveArrowTable.add(new ScrollArrowButtonRight(scrollPane, 189)).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).right()
        .padLeft(50);
    }
}
