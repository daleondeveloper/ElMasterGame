package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetGates;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Screens.GUI.widget.AnimatedActor;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;


public class GatesScreen extends GUIOverlayAbstractScreen {
    private static final String TAG = GatesScreen.class.getName();

    private Assets assets;
    private AssetGates assetGates;

    private float stateTime;

    private Image dragonHead;
    private Image dragonLeftHand;
    private Image dragonRightHand;
    private Image gates;
    private Image menuBg;
    private Image playBackground;

    private Image rightBowl;
    private Image rightBowlUp;
    private Image leftBowl;
    private Image leftBowlUp;
    private Animation<TextureRegion> portal;
    private Animation<TextureRegion> openGates;
    private Animation<TextureRegion> closeGates;

    private AnimatedActor leftBowlAnimatedActor;
    private AnimatedActor rightBowlAnimatedActor;
    private AnimatedActor portalAnimatedActor;

    public GatesScreen(ElMaster game){
        super(game);

        assets = Assets.getInstance();
        assetGates = assets.getAssetGates();

        stateTime = 0;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float w = stage.getWidth(); // Same as stage.getViewport().getWorldWidth()
        float h = stage.getHeight();

        menuBg.setWidth(630);
        menuBg.setX(w/2 - menuBg.getWidth()/2);

        menuBg.setHeight(700);
        menuBg.setY(0);

        gates.setY(menuBg.getY() + 310);
        gates.setX(menuBg.getX() + 185);

        dragonHead.setX(stage.getWidth() / 2 - dragonHead.getWidth() / 2);
        dragonHead.setY(menuBg.getY() + 640);
        dragonLeftHand.setX(menuBg.getX() + 110);
        dragonLeftHand.setY(menuBg.getY() + 670);
        dragonRightHand.setX(menuBg.getX() + 415);
        dragonRightHand.setY(menuBg.getY() + 675);

        // Buttons Animations
        //setButtonsAnimation();
        leftBowlAnimatedActor.setY(menuBg.getY() + 435);
        leftBowlAnimatedActor.setX(menuBg.getX() + 65);

        rightBowlAnimatedActor.setY(menuBg.getY() + 435);
        rightBowlAnimatedActor.setX(menuBg.getX() + 430);

        portalAnimatedActor.setY(menuBg.getY() + 630);
        portalAnimatedActor.setX(stage.getWidth() / 2 - portalAnimatedActor.getWidth() / 2);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void build() {
        Image image;

        menuBg = new Image(assetGates.getStaticMain());
        playBackground = new Image(assetGates.getPlay_background());

        //Dragon
        dragonHead = new Image(assetGates.getDragonHead());
        dragonLeftHand = new Image(assetGates.getDragonLeftHandWithSphere());
        dragonRightHand = new Image(assetGates.getDragonRightHandWithSphere());

        //Bowl
        leftBowl = new Image(assetGates.getLeftFireBowl());
        leftBowlUp = new Image(assetGates.getLeftFireBowlUp());
        rightBowl = new Image(assetGates.getRightFireBowl());
        rightBowlUp = new Image(assetGates.getRightFireBowlUp());


        //Portal
        portal = assetGates.getPortal();
        portalAnimatedActor = new AnimatedActor(portal);

        //Gates
        image = new Image(assetGates.getClosedGates());
        image.setName("gates");
        gates = image;



        // Buttons
        stage.addActor(menuBg);
      //  stage.addActor(gates);
        stage.addActor(portalAnimatedActor);
        stage.addActor(dragonHead);
        stage.addActor(dragonLeftHand);
        stage.addActor(dragonRightHand);
        stage.addActor(leftBowl);
        stage.addActor(leftBowlUp);
        stage.addActor(rightBowl);
        stage.addActor(rightBowlUp);
    }

    @Override
    public void update(float deltaTime) {
        stage.act();

        stateTime += deltaTime;

        float w = stage.getWidth();
        float h = stage.getHeight();

    }

    @Override
    public void render() {
        stage.draw();
    }
}
