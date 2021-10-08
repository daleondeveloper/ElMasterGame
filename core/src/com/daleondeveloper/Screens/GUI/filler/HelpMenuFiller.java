package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Assets.help.AssetHelp;
import com.daleondeveloper.Screens.GUI.Button.ScrollArrowButtonLeft;
import com.daleondeveloper.Screens.GUI.Button.ScrollArrowButtonRight;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.tools.GameConstants;

public class HelpMenuFiller extends MenuFiller {
    private static final String TAG = SettingsMenuFiller.class.getName();

    public static enum HELP_TYPE_SHOW{
        GAME_PLAYING,
        GAME_MODE_FIRE_INFO,
        GAME_MODE_SNOW_INFO,
        GAME_MODE_LIGHT_INFO,
        GAME_MODE_WATER_INFO,
        GAME_MODE_DARK_INFO,
        GAME_MODE_SPECIAL_INFO,
    }

    private Assets assets;
    private AssetGUI assetGUI;
    private AssetHelp assetHelp;
    private I18NBundle i18NGameThreeBundle;

    private HELP_TYPE_SHOW help_type_show;
    private int helpMenuShow;

    private Image[] help;
    private Image nextHelp;
    private Image previsionHelp;
    private Image startButton;

    private ScrollPane scrollPane;


    public HelpMenuFiller(com.daleondeveloper.Screens.GUI.MenuScreen menuScreen){
        this(menuScreen,HELP_TYPE_SHOW.GAME_PLAYING);
    }
    public HelpMenuFiller(MenuScreen menuScreen, HELP_TYPE_SHOW help_type_show){
        super(menuScreen,"title.help");
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        assetHelp = assets.getAssetHelp();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();

        this.help_type_show = help_type_show;
        helpMenuShow = 0;
        help = new Image[3];

    }
    @Override
    public void build() {
        super.build();
        defineElements();

    }

    @Override
    protected void defineElements() {
        help[0] = new Image(assetHelp.getHelp_block_fall());
        help[1] = new Image(assetHelp.getHelp_block_push());
        help[2] = new Image(assetHelp.getHelp_create_block_line());
        nextHelp = new Image(assetGUI.getButtonRight());
        previsionHelp = new Image(assetGUI.getButtonLeft());
        startButton = new Image(assetGUI.getButtonStart());
    }

    @Override
    protected void addAction() {
    }

    @Override
    protected void addToTable() {
        addScrollPanelToMainTable();
        addFootTable();
    }
    private void addScrollPanelToMainTable(){
        Table textTable = new Table();
        scrollPane = new ScrollPane(textTable);
        scrollPane.setScrollingDisabled(true,false);
        for(Image image : help) {
            textTable.add(image);
            textTable.row();
        }
        mainTable.add(scrollPane).growX().padRight(50).padLeft(50);
        mainTable.row();
    }

    private void addFootTable(){
        Table moveArrowTable = new Table();
        mainTable.add(moveArrowTable).padBottom(20).padRight(50).padLeft(50).growX();
        moveArrowTable.add(new ScrollArrowButtonLeft(scrollPane, 50)).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).left()
                .padRight(50);
        moveArrowTable.add().growX();
        moveArrowTable.add(new ScrollArrowButtonRight(scrollPane,50)).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).right()
                .padLeft(50);
    }

}
