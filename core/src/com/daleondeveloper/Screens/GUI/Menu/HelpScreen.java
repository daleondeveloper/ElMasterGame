package com.daleondeveloper.Screens.GUI.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Assets.help.AssetHelp;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.GameSettings;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;
import com.daleondeveloper.Screens.ListenerHelper;

public class HelpScreen extends GUIOverlayAbstractScreen {
    private static final String TAG = SettingsScreen.class.getName();

    private static final float DIM_ALPHA = 0.8f;


    private MenuScreen menuScreen;
    private GameSettings prefs;
    private Assets assets;
    private AssetGUI assetGUI;
    private AssetHelp assetHelp;
    private I18NBundle i18NGameThreeBundle;

    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;

    private int helpMenuShow;

    private Image menuWindow;
    private Image back;
    private Image help;
    private Image nextHelp;
    private Image previsionHelp;

    private Actor helpActor;

    private Label helpLabel;


    public HelpScreen(ElMaster game, MenuScreen menuScreen){
        super(game);
        this.menuScreen = menuScreen;
        prefs = GameSettings.getInstance();
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        assetHelp = assets.getAssetHelp();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        // Styles
        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assets.getAssetFonts().getNormal();
        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assets.getAssetFonts().getSmall();

        helpMenuShow = 0;

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);



//        helpLabel.setFontScale(width / 500, height / 1000);
        helpLabel.setPosition(menuWindow.getX() + 170, menuWindow.getY() + 270);
        back.setHeight(24);
        back.setWidth(24);
        back.setPosition(menuWindow.getX() + menuWindow.getWidth() - 60 ,
                menuWindow.getY() + menuWindow.getHeight() - 40);

        nextHelp.setWidth(50);
        nextHelp.setHeight(58);
        nextHelp.setPosition(menuWindow.getX() + 300, menuWindow.getY() + 35);
        previsionHelp.setWidth(50);
        previsionHelp.setHeight(58);
        previsionHelp.setPosition(menuWindow.getX() + 55 , menuWindow.getY() + 35);

        help.setWidth(200);
        help.setHeight(200);
        help.setPosition(menuWindow.getX() + 105, menuWindow.getY() + 75);

    }

    @Override
    public void build() {

        menuWindow = menuScreen.getPauseWindow();

        helpLabel = new Label(i18NGameThreeBundle.format("helpScreen.title"), labelStyleMedium);

        help = new Image(assetHelp.getHelp_block_fall());
        nextHelp = new Image(assetGUI.getButtonLeft());
        previsionHelp = new Image(assetGUI.getButtonRight());
        defineButtons();

        helpActor = help;

        stage.addActor(helpActor);
        stage.addActor(nextHelp);
        stage.addActor(previsionHelp);
        stage.addActor(helpLabel);
        stage.addActor(back);
    }
    private void defineButtons(){
        back  =new Image(new TextureRegionDrawable(assetGUI.getButtonX()));

        back.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.hideMenuScreen();
            }
        }));
        nextHelp.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                if(helpMenuShow < 2)helpMenuShow++;
            }
        }));
        previsionHelp.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                if(helpMenuShow > 0)helpMenuShow--;
            }
        }));

    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        if(menuScreen.getMenuState() == MenuScreen.MenuState.HELP){
            setVisible(true);
        }else{
            setVisible(false);
        }
        if(isHelpScreenVisible()) {
            switch (helpMenuShow) {
                case 0:
                    ((Image) helpActor).setDrawable(new TextureRegionDrawable((TextureRegion) assetHelp.getHelp_block_fall()));
                    nextHelp.setVisible(true);
                    previsionHelp.setVisible(false);
                    break;
                case 1:
                    ((Image) helpActor).setDrawable(new TextureRegionDrawable((TextureRegion) assetHelp.getHelp_block_push()));
                    nextHelp.setVisible(true);
                    previsionHelp.setVisible(true);
                    break;
                case 2:
                    ((Image) helpActor).setDrawable(new TextureRegionDrawable((TextureRegion) assetHelp.getHelp_create_block_line()));
                    nextHelp.setVisible(false);
                    previsionHelp.setVisible(true);
                    break;


            }
        }else{
            helpActor.setVisible(false);
            nextHelp.setVisible(false);
            previsionHelp.setVisible(false);
        }

    }

    @Override
    public void render() {
        stage.draw();
    }

    public boolean isHelpScreenVisible(){
        return helpLabel.isVisible();
    }
    public void showHelpScreen() {
        if (!isHelpScreenVisible()) {
            setVisible(true);

            // Only PauseScreen responds to events
            Gdx.input.setInputProcessor(stage);
        }
    }

    private void setVisible(boolean  visible){
        helpLabel.setVisible(visible);
        back.setVisible(visible);
        helpActor.setVisible(true);
    }
}
