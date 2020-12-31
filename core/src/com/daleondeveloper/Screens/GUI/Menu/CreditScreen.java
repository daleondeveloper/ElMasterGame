package com.daleondeveloper.Screens.GUI.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;
import com.daleondeveloper.Screens.ListenerHelper;

public class CreditScreen extends GUIOverlayAbstractScreen {
    private static final String TAG = CreditScreen.class.getName();

    private MenuScreen menuScreen;
    private Assets assets;
    private I18NBundle i18NGameThreeBundle;
    private Image menuWindow;


    private Label.LabelStyle labelStyleSmall;
    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleLarge;

    private Label creditLabel;
    private Label textCreditLabel;

    private Image back;

    public CreditScreen(ElMaster game, MenuScreen menuScreen){
        super(game);
        this.menuScreen = menuScreen;

        assets = Assets.getInstance();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();


        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assets.getAssetFonts().getSmall();
        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assets.getAssetFonts().getNormal();
        labelStyleLarge = new Label.LabelStyle();
        labelStyleLarge.font = assets.getAssetFonts().getBig();

    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        creditLabel.setPosition(menuWindow.getX() + 130, menuWindow.getY() + 270);
        back.setHeight(24);
        back.setWidth(24);
        back.setPosition(menuWindow.getX() + menuWindow.getWidth() - 60 ,
                menuWindow.getY() + menuWindow.getHeight() - 40);
        textCreditLabel.setPosition(menuWindow.getX() + 55, menuWindow.getY() + 50);


    }

    @Override
    public void build() {
        menuWindow = menuScreen.getPauseWindow();

        creditLabel = new Label(i18NGameThreeBundle.format("creditsScreen.title"),labelStyleMedium);
        textCreditLabel = new Label(i18NGameThreeBundle.format("creditsScreen.text"),labelStyleSmall);


        defineButtons();

        stage.addActor(creditLabel);
        stage.addActor(textCreditLabel);
        stage.addActor(back);

    }

    private void defineButtons(){
        back  =new Image(new TextureRegionDrawable(assets.getAssetGUI().getButtonX()));

        back.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                menuScreen.hideMenuScreen();
            }
        }));
    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        if(menuScreen.getMenuState() == MenuScreen.MenuState.CREDIT){
            setVisible(true);
        }else{
            setVisible(false);
        }

    }

    @Override
    public void render() {
        stage.draw();
    }

    public boolean isCreditScreenVisible(){
        return creditLabel.isVisible();
    }

    public void showCreditScreen() {
        if (!isCreditScreenVisible()) {
            setVisible(true);

            // Only PauseScreen responds to events
            Gdx.input.setInputProcessor(stage);
        }
    }

    private void setVisible(boolean  visible){
        creditLabel.setVisible(visible);
        textCreditLabel.setVisible(visible);
        back.setVisible(visible);
    }

}
