package com.daleondeveloper.Screens.GUI.filler;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Game.tools.Level.Level;
import com.daleondeveloper.Screens.GUI.Button.BackButton;
import com.daleondeveloper.Screens.GUI.Button.ChooseCheckpointButton;
import com.daleondeveloper.Screens.GUI.Button.ContinueTextButton;
import com.daleondeveloper.Screens.GUI.Button.NewGameTextButton;
import com.daleondeveloper.Screens.GUI.Button.TutorialTextButton;
import com.daleondeveloper.Screens.GUI.MenuScreen;
import com.daleondeveloper.tools.GameConstants;


public class NewGameMenuFiller extends MenuFiller {
    private static final String TAG = NewGameMenuFiller.class.getName();

    private static final int LAST_CHANGE_MODE_PAGE = 2;

    private MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private I18NBundle i18NGameThreeBundle;
    private Table mainTable;

    private int pageShow;

    private ScrollPane scrollPane;
    private Label.LabelStyle labelStyleTitle;
    private Label.LabelStyle labelStyleSmall;
    private Label gameModeChangeTitleLabel;


    public NewGameMenuFiller(MenuScreen menuScreen) {

        this.menuScreen = menuScreen;
        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        // Styles
        labelStyleTitle = new Label.LabelStyle();
        labelStyleTitle.font = assets.getAssetFonts().getGameTitle();
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
        gameModeChangeTitleLabel = new Label(i18NGameThreeBundle.format("title.mainMenu"), labelStyleTitle);
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
            continueGamePanel();
    }

    private void continueGamePanel(){
        Table continueGameTable = new Table();
        mainTable.add(continueGameTable).grow().padBottom(40);
        if(Level.savedLevel.exists()) {
            continueGameTable.add(new ContinueTextButton()).pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();

            continueGameTable.row();
        }
        continueGameTable.add(new NewGameTextButton(menuScreen)).pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();;
        continueGameTable.row();
        continueGameTable.add(new ChooseCheckpointButton(menuScreen)).pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();;
        continueGameTable.row();
        continueGameTable.add(new TutorialTextButton(menuScreen)).pad(10).width(GameConstants.BUTTON_WIDTH).height(GameConstants.BUTTON_HEIGHT).center();;

    }
}
