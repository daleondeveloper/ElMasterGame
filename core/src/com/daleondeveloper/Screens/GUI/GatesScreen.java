package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetBackground;
import com.daleondeveloper.Assets.game.AssetGates;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;


public class GatesScreen extends GUIOverlayAbstractScreen {
    private static final String TAG = GatesScreen.class.getName();

    private PlayScreen playScreen;
    private Assets assets;
    private AssetGates assetGates;

    private float stateTime;

    private Image dragonHead;
    private Image dragonLeftHand;
    private Image dragonRightHand;
    private Image leftBowlImage;
    private Image rightBowlImage;
    private Image portalImage;
    private Image gates;
    private Image menuBg;
    private Image playBackground;

    private Animation rightBowl;
    private Animation leftBowl;
    private Animation portal;
    private Animation openGates;
    private Animation closeGates;

    public GatesScreen(ElMaster game, PlayScreen playScreen){
        super(game);

        this.playScreen = playScreen;
        assets = Assets.getInstance();
        assetGates = assets.getAssetGates();

        stateTime = 0;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float w = stage.getWidth(); // Same as stage.getViewport().getWorldWidth()
        float h = stage.getHeight();

        menuBg.setWidth(w * 1.43f);
        menuBg.setX(w/2 - menuBg.getWidth()/2);
        //if(h > assetGUI.getBackgroundGates().getRegionHeight()) {
//        }else{
//            menuBg.setY(0);
//        }
        menuBg.setHeight(h * 0.84f);
        menuBg.setY(0);
        playBackground.setX(w * 14f);
        playBackground.setY(h * 14f);
        playBackground.setWidth(w * 0.85f);
        playBackground.setHeight(h * 0.77f);

        gates.setY(menuBg.getY() + 310);
        gates.setX(menuBg.getX() + 185);

        dragonHead.setWidth(w * 0.44f);
        dragonHead.setHeight(h * 0.2f);
        dragonHead.setX(menuBg.getX() + w * 0.49f);
        dragonHead.setY(menuBg.getY() + h * 0.774f);
        dragonLeftHand.setWidth(w * 0.26f);
        dragonLeftHand.setHeight(h * 0.1f);
        dragonLeftHand.setX(menuBg.getX() + w * 0.24f);
        dragonLeftHand.setY(menuBg.getY() + h * 0.8f);
        dragonRightHand.setWidth(w * 0.26f);
        dragonRightHand.setHeight(h * 0.1f);
        dragonRightHand.setX(menuBg.getX() + w * 0.92f);
        dragonRightHand.setY(menuBg.getY() + h * 0.8f);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void build() {
        Image image;

        image = new Image(assetGates.getStaticMain());
        image.setName("menuBg");
        menuBg = image;

        image = new Image(assetGates.getPlay_background());
        image.setName("playBackGround");
        playBackground = image;

        //Dragon
        image = new Image(assetGates.getDragonHead());
        image.setName("dragonHead");
        dragonHead = image;

        image = new Image(assetGates.getDragonLeftHandWithSphere());
        image.setName("dragonLeftHand");
        dragonLeftHand = image;

        image = new Image(assetGates.getDragonRightHandWithSphere());
        image.setName("dragonRightHand");
        dragonRightHand = image;

        //Bowl
        leftBowl = assetGates.getLeftFireBowl();
        image = new Image((TextureRegion)leftBowl.getKeyFrame(stateTime,true));
        image.setName("leftBowl");
        leftBowlImage = image;


        rightBowl = assetGates.getRightFireBowl();
        image = new Image((TextureRegion)rightBowl.getKeyFrame(stateTime,true));
        image.setName("rightBowl");
        rightBowlImage = image;


        //Portal
        portal = assetGates.getPortal();
        image = new Image((TextureRegion)portal.getKeyFrame(stateTime,true));
        image.setName("portal");
        portalImage = image;


        //Gates
        image = new Image(assetGates.getClosedGates());
        image.setName("gates");
        gates = image;



        // Buttons
        stage.addActor(playBackground);
        stage.addActor(menuBg);
      //  stage.addActor(gates);
        stage.addActor(portalImage);
        stage.addActor(dragonHead);
        stage.addActor(dragonLeftHand);
        stage.addActor(dragonRightHand);
        stage.addActor(leftBowlImage);
        stage.addActor(rightBowlImage);



    }

    @Override
    public void update(float deltaTime) {
        stage.act();

        stateTime += deltaTime;

        float w = stage.getWidth();
        float h = stage.getHeight();

        Image image;
        image = new Image((TextureRegion)leftBowl.getKeyFrame(stateTime,true));
        image.setName("leftBowl");
        leftBowlImage = image;
        leftBowlImage.setY(menuBg.getY() + h * 0.52f);
        leftBowlImage.setX(menuBg.getX() + w * 0.155f);
        leftBowlImage.setWidth(stage.getWidth() * (leftBowlImage.getWidth()/420));
        leftBowlImage.setHeight(stage.getHeight() * (leftBowlImage.getHeight()/840));
        image = new Image((TextureRegion)rightBowl.getKeyFrame(stateTime,true));
        image.setName("rightBowl");
        rightBowlImage = image;
        rightBowlImage.setY(menuBg.getY() + h * 0.52f);
        rightBowlImage.setX(menuBg.getX() + w * 0.98f);
        rightBowlImage.setWidth(w * (rightBowlImage.getWidth()/420));
        rightBowlImage.setHeight(h * (rightBowlImage.getHeight()/840));
        image = new Image((TextureRegion)portal.getKeyFrame(stateTime,true));
        image.setName("portal");
        portalImage = image;
        portalImage.setY(menuBg.getY() + h * 0.76f);
        portalImage.setX(menuBg.getX() + w * 0.35f);
        portalImage.setWidth(w * portalImage.getWidth() / 420);
        portalImage.setHeight(h * portalImage.getHeight() / 840);

        stage.clear();
        stage.addActor(playBackground);
        stage.addActor(menuBg);
      //  stage.addActor(gates);
        stage.addActor(portalImage);
        stage.addActor(dragonHead);
        stage.addActor(dragonLeftHand);
        stage.addActor(dragonRightHand);
        stage.addActor(leftBowlImage);
        stage.addActor(rightBowlImage);

    }

    @Override
    public void render() {
        stage.draw();
    }
}
