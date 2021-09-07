package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.fonts.AssetFonts;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.DebugConstants;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;
import com.daleondeveloper.Screens.Play.PlayScreen;

//Екран відповідає за відображення панелі упарвління персонажем
//Покажчиками набраних очків і фпс
public class TeachingHud extends GUIOverlayAbstractScreen {
    private static final String TAG = TeachingHud.class.getName();

    private static final float PAD_BOTTOM = 800.0f;
    private static final int AVERAGE_SCORE = 8;

    private PlayScreen playScreen;
    private I18NBundle i18NGameThreeBundle;
    private AssetGUI assetGUI;
    private Label.LabelStyle labelStyleBig;
    private Label.LabelStyle labelStyleMedium;
    private Label.LabelStyle labelStyleSmall;
    private float stateTime;

    private int helpShowingCount;
    private Table mainTable;
    private Table topTable;
    private Table centerTable;
    private Table bottomTable;

    private ImageTextButton helpScreen;
    private Image helpButtonRight;
    private Image helpButtonLeft;
    private Image pushHelp;
    private Image backPushHelp;

    private boolean firstCondition;
    private boolean secondCondition;
    private boolean thirdCondition;



    public TeachingHud(ElMaster game, PlayScreen playScreen) {
        super(game);

        this.playScreen = playScreen;
        i18NGameThreeBundle = Assets.getInstance().getI18NElementMaster().getI18NElmasterBundle();
        stateTime = 0;

        helpShowingCount = 0;

        assetGUI = Assets.getInstance().getAssetGUI();

        // Styles
        AssetFonts assetFonts = Assets.getInstance().getAssetFonts();
        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = assetFonts.getBig();

        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assetFonts.getSmall();

        labelStyleMedium = new Label.LabelStyle();
        labelStyleMedium.font = assetFonts.getNormal();
    }

    @Override
    public void build() {
        mainTable = new Table();
        topTable = new Table();
        centerTable = new Table();
        bottomTable = new Table();
        defineButtons();

        firstCondition = false;
        secondCondition = false;
        thirdCondition = false;

        mainTable.setDebug(DebugConstants.DEBUG_LINES);
        topTable.setDebug(DebugConstants.DEBUG_LINES);
        centerTable.setDebug(DebugConstants.DEBUG_LINES);
        bottomTable.setDebug(DebugConstants.DEBUG_LINES);
        mainTable.center();
        mainTable.setFillParent(true);

        mainTable.add(topTable).growX().row();
        mainTable.add(centerTable).grow().row();
        mainTable.add(bottomTable).growX().row();


        updateTopTable();
        updateCenterTable();
        updateBottomTable();
        stage.addActor(mainTable);

    }

    private void defineButtons() {
        TextureRegionDrawable textureRegion = new TextureRegionDrawable(assetGUI.getPauseWindow());

        helpScreen = new ImageTextButton("Restart",new ImageTextButton.ImageTextButtonStyle(
                textureRegion, textureRegion, textureRegion, Assets.getInstance().getAssetFonts().getSmall()
        ));
        helpButtonRight = new Image(new TextureRegionDrawable(assetGUI.getButtonRight()));
        helpButtonLeft = new Image(new TextureRegionDrawable(assetGUI.getButtonLeft()));
    }

    private void updateTopTable() {
        topTable.clearChildren();
        switch (helpShowingCount){
            case 2 :
                topTable.add().growX();
                topTable.add(helpScreen).width(350).height(150).padTop(50);
                helpScreen.setText("Use move button and \njump button to do diagonal \njumping");
                topTable.add().growX();
        }
    }

    private void updateCenterTable() {
        centerTable.clearChildren();

    }

    private void updateBottomTable() {
        bottomTable.clearChildren();
        switch (helpShowingCount){
            case 0 :
                bottomTable.add(helpScreen).width(200).height(250);
                helpScreen.setText("Press this \n button \nto move");
                bottomTable.add(helpButtonRight).width(50).height(50).padLeft(-15);
                bottomTable.add().growX();
                bottomTable.add().growX();
                break;
            case 1 :
                bottomTable.add().growX();
                bottomTable.add().growX();
                bottomTable.add(helpButtonLeft).width(50).height(50).padRight(-15).padBottom(50);
                bottomTable.add(helpScreen).width(200).height(250);
                helpScreen.setText("Press this \n button \n to jump");
                break;
            case 3:
                bottomTable.add().growX();
                bottomTable.add(helpButtonLeft).width(50).height(50).padTop(130);
                bottomTable.add(helpScreen).width(200).height(250);
                helpScreen.setText("Press this\n button \n to push block");
                bottomTable.add().growX();
        }
    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        stateTime += deltaTime;
        updateTopTable();
        updateCenterTable();
        updateBottomTable();
        switch (helpShowingCount) {
            case 0:
                if(playScreen.getHud().isButtonLeftPressed()){
                    firstCondition = true;
                }
                if(playScreen.getHud().isButtonRightPressed()){
                    secondCondition = true;
                }
                if(stateTime > 2){
                    thirdCondition = true;
                }
            if (firstCondition && secondCondition && thirdCondition) {
                helpShowingCount = 1;
                stateTime = 0;
                firstCondition = false;
                secondCondition = false;
                thirdCondition = false;
            }
            break;
            case 1 :
                if(playScreen.getHud().isButtonJumpPressed()){
                    firstCondition = true;
                }
                if(stateTime > 2){
                    secondCondition = true;
                }
                if(firstCondition && secondCondition){
                    helpShowingCount = 2;
                    stateTime = 0;
                    firstCondition = false;
                    secondCondition = false;
                }
                break;
            case 2:
                if((Gdx.input.isKeyPressed(21) && Gdx.input.isKeyPressed(62))
                || (Gdx.input.isKeyPressed(22) && Gdx.input.isKeyPressed(62)) ||
                        (playScreen.getHud().isButtonLeftPressed() && playScreen.getHud().isButtonJumpPressed())
                || (playScreen.getHud().isButtonRightPressed() && playScreen.getHud().isButtonJumpPressed())){
                    firstCondition = true;
            }
            if(firstCondition){
                helpShowingCount = 3;
                stateTime = 0;
                firstCondition = false;
                Gdx.input.getInputProcessor().keyDown(54);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float x = stage.getWidth() / 2;
        float y = stage.getHeight() / 2;

    }

    @Override
    public void render() {
        stage.draw();
    }

    public void setVisible(boolean visible) {
        mainTable.setVisible(visible);
    }
}
