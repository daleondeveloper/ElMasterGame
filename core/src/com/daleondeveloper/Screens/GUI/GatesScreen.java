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
import com.daleondeveloper.Screens.GUI.widget.AnimatedActor;
import com.daleondeveloper.Screens.Play.PlayScreen;
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

    private Animation<TextureRegion> rightBowl;
    private Animation<TextureRegion> leftBowl;
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

        menuBg.setWidth(600);
        menuBg.setX(w/2 - menuBg.getWidth()/2);

        menuBg.setHeight(700);
        menuBg.setY(0);

        gates.setY(menuBg.getY() + 310);
        gates.setX(menuBg.getX() + 185);

        dragonHead.setX(menuBg.getX() + 205);
        dragonHead.setY(menuBg.getY() + 650);
        dragonLeftHand.setX(menuBg.getX() + 100);
        dragonLeftHand.setY(menuBg.getY() + 675);
        dragonRightHand.setX(menuBg.getX() + 385);
        dragonRightHand.setY(menuBg.getY() + 675);

        // Buttons Animations
        //setButtonsAnimation();
        leftBowlAnimatedActor.setY(menuBg.getY() + 435);
        leftBowlAnimatedActor.setX(menuBg.getX() + 65);

        rightBowlAnimatedActor.setY(menuBg.getY() + 435);
        rightBowlAnimatedActor.setX(menuBg.getX() + 410);

        portalAnimatedActor.setY(menuBg.getY() + 640);
        portalAnimatedActor.setX(menuBg.getX() + 145);
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
        leftBowl = assetGates.getLeftFireBowl();
        rightBowl = assetGates.getRightFireBowl();
        leftBowlAnimatedActor = new AnimatedActor(leftBowl);
        rightBowlAnimatedActor = new AnimatedActor(rightBowl);

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
        stage.addActor(leftBowlAnimatedActor);
        stage.addActor(rightBowlAnimatedActor);
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
