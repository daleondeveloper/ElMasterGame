package com.daleondeveloper.Screens.GUI.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Assets.help.AssetHelp;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Screens.GUI.Button.ScrollArrowButtonLeft;
import com.daleondeveloper.Screens.GUI.Button.ScrollArrowButtonRight;
import com.daleondeveloper.Screens.GUI.filler.HelpMenuFiller;
import com.daleondeveloper.Screens.GUIAbstractScreen;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;
import com.daleondeveloper.tools.GameConstants;


public class HelpOverlayScreen extends GUIOverlayAbstractScreen {

    private Table main;
    private Assets assets;
    private AssetGUI assetGUI;
    private AssetHelp assetHelp;
    private I18NBundle i18NGameThreeBundle;

    private HelpMenuFiller.HELP_TYPE_SHOW help_type_show;
    private int helpMenuShow;

    private Image[] help;
    private Image nextHelp;
    private Image previsionHelp;
    private Image startButton;

    private ScrollPane scrollPane;


    public HelpOverlayScreen(ElMaster game, GUIAbstractScreen guiAbstractScreen) {
        super(game, guiAbstractScreen);
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        assetHelp = assets.getAssetHelp();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();

        this.help_type_show = help_type_show;
        helpMenuShow = 0;
        help = new Image[3];
        build();
    }

    @Override
    public void build() {
        main = new Table();
        defineElements();
        addScrollPanelToMainTable();
        addFootTable();
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(
                Assets.getInstance().getAssetGUI().getPauseWindow()
        );
        main.setBackground(textureRegionDrawable);
        main.setWidth(420);
        main.setHeight(840);
        main.setPosition(0,0);
    }


    protected void defineElements() {
        help[0] = new Image(assetHelp.getHelp_block_fall());
        help[1] = new Image(assetHelp.getHelp_block_push());
        help[2] = new Image(assetHelp.getHelp_create_block_line());
        nextHelp = new Image(assetGUI.getButtonRight());
        previsionHelp = new Image(assetGUI.getButtonLeft());
        startButton = new Image(assetGUI.getButtonStart());
    }

    @Override
    public void update(float deltaTime) {
        stage.addActor(main);
        stage.act();
    }

    @Override
    public void render() {
        stage.draw();
    }

    private void addScrollPanelToMainTable(){
        Table textTable = new Table();
        scrollPane = new ScrollPane(textTable);
        scrollPane.setScrollingDisabled(true,false);
        for(Image image : help) {
            textTable.add(image);
            textTable.row();
        }
        main.add(scrollPane).growX();
        main.row();
    }

    private void addFootTable(){
        Table moveArrowTable = new Table();
        moveArrowTable.add(new ScrollArrowButtonLeft(scrollPane, 50)).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).left()
                .padRight(50);
        moveArrowTable.add().growX();
        moveArrowTable.add(new ScrollArrowButtonRight(scrollPane,50)).width(GameConstants.BUTTON_ARROW_WIDTH).height(GameConstants.BUTTON_ARROW_HEIGHT).right()
                .padLeft(50);
        main.add(moveArrowTable).growX();
    }
}
