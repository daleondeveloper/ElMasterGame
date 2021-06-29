package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Assets.help.AssetHelp;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.Screens.ListenerHelper;

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

    private com.daleondeveloper.Screens.GUI.MenuScreen menuScreen;
    private Assets assets;
    private AssetGUI assetGUI;
    private AssetHelp assetHelp;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;

    private HELP_TYPE_SHOW help_type_show;
    private int helpMenuShow;

    private Image menuWindow;
    private Image backButton;
    private Image help;
    private Image nextHelp;
    private Image previsionHelp;

    private Label helpLabel;


    public HelpMenuFiller(com.daleondeveloper.Screens.GUI.MenuScreen menuScreen){
        this(menuScreen,HELP_TYPE_SHOW.GAME_PLAYING);
    }
    public HelpMenuFiller(MenuScreen menuScreen, HELP_TYPE_SHOW help_type_show){
        this.menuScreen = menuScreen;
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        assetHelp = assets.getAssetHelp();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        // Styles
        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assets.getAssetFonts().getNormal();
        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assets.getAssetFonts().getSmall();

        this.help_type_show = help_type_show;
        helpMenuShow = 0;

    }
    @Override
    public void build() {
        mainTable = menuScreen.getWindowTable();
        super.build();

    }

    @Override
    protected void defineElements() {
        helpLabel = new Label(i18NGameThreeBundle.format("helpScreen.title"), labelStyleMedium);

        backButton  =new Image(new TextureRegionDrawable(assetGUI.getButtonX()));

        help = new Image(assetHelp.getHelp_block_fall());
        nextHelp = new Image(assetGUI.getButtonRight());
        previsionHelp = new Image(assetGUI.getButtonLeft());
        changeHelpImage();
    }

    @Override
    protected void addAction() {

        backButton.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.hideMenuScreen();
            }
        }));
        nextHelp.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                if(helpMenuShow < 2)helpMenuShow++;
                changeHelpImage();
            }
        }));
        previsionHelp.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                if(helpMenuShow > 0)helpMenuShow--;
                changeHelpImage();
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
        labelTable.add(helpLabel);
        labelTable.add().growX();
        mainTable.row();
        //Таблиця з зображенням
        Table imageTable = new Table();
        mainTable.add(imageTable).grow();
        imageTable.add(help);
        mainTable.row();
        //Таблиця з кнопками управлінням зображеннями допомоги
        if(help_type_show == HELP_TYPE_SHOW.GAME_PLAYING){
            Table moveArrowTable = new Table();
            mainTable.add(moveArrowTable).padBottom(30).padRight(50).padLeft(50).growX();
            moveArrowTable.add(previsionHelp).width(50).height(58).left();
            moveArrowTable.add().growX();
            moveArrowTable.add(nextHelp).width(50).height(58).right();
        }
    }

    public void changeHelpImage() {

        if(help_type_show == HELP_TYPE_SHOW.GAME_PLAYING) {
            switch (helpMenuShow) {
                case 0:
                    ((Image) help).setDrawable(new TextureRegionDrawable((TextureRegion) assetHelp.getHelp_block_fall()));
                    nextHelp.setVisible(true);
                    previsionHelp.setVisible(false);
                    break;
                case 1:
                    ((Image) help).setDrawable(new TextureRegionDrawable((TextureRegion) assetHelp.getHelp_block_push()));
                    nextHelp.setVisible(true);
                    previsionHelp.setVisible(true);
                    break;
                case 2:
                    ((Image) help).setDrawable(new TextureRegionDrawable((TextureRegion) assetHelp.getHelp_create_block_line()));
                    nextHelp.setVisible(false);
                    previsionHelp.setVisible(true);
                    break;
            }
            return;
        }

        if(help_type_show == HELP_TYPE_SHOW.GAME_MODE_LIGHT_INFO) {
            ((Image) help).setDrawable(new TextureRegionDrawable((TextureRegion) assetHelp.getHelp_light_mode()));
            return;
        }
        if(help_type_show == HELP_TYPE_SHOW.GAME_MODE_SNOW_INFO) {
            ((Image) help).setDrawable(new TextureRegionDrawable((TextureRegion) assetHelp.getHelp_snow_mode()));
            return;
        }
        if(help_type_show == HELP_TYPE_SHOW.GAME_MODE_FIRE_INFO) {
            ((Image) help).setDrawable(new TextureRegionDrawable((TextureRegion) assetHelp.getHelp_fire_mode()));
            return;
        }
        if(help_type_show == HELP_TYPE_SHOW.GAME_MODE_DARK_INFO) {
            ((Image) help).setDrawable(new TextureRegionDrawable((TextureRegion) assetHelp.getHelp_dark_mode()));
            return;
        }
        if(help_type_show == HELP_TYPE_SHOW.GAME_MODE_SPECIAL_INFO) {
            ((Image) help).setDrawable(new TextureRegionDrawable((TextureRegion) assetHelp.getHelp_special_mode()));
            return;
        }

    }

    public HELP_TYPE_SHOW getHelp_type_show() {
        return help_type_show;
    }

    public void setHelp_type_show(HELP_TYPE_SHOW help_type_show) {
        this.help_type_show = help_type_show;
    }
}
