package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetBackground;
import com.daleondeveloper.Assets.game.AssetGates;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.GameSettings;
import com.daleondeveloper.Screens.GUI.widget.AnimatedActor;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenTransitionEnum;
import com.daleondeveloper.tools.AudioManager;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MainMenuScreen extends GUIAbstractScreen {
    private static final String TAG = MainMenuScreen.class.getName();

    private static final float TITLE_OFFSET_Y = 590.0f;
    private static final float TITLE_BUTTON_WIDTH_COEFFICIENT = 0.18f;
    private static final float TITLE_BIG_BUTTON_WIDTH_COEFFICIENT = 0.33f;

    private Assets assets;
    private AssetGUI assetGUI;
    private AssetGates assetGates;
    private AssetBackground assetBackground;
    private I18NBundle i18NGameThreeBundle;
    private GameSettings prefs;
    private Label.LabelStyle labelStyleGameTitle;
    private Image menuBg;
    private Label gameTitle;
    private float stateTime;

    private Image background;
    private Image dark_fog;
    private Image fogLeft;
    private Image fogCenter;
    private Image fogRight;
    private Image dragonHead;
    private Image dragonLeftHand;
    private Image dragonRightHand;
    private Image leftBowlImage;
    private Image rightBowlImage;
    private Image portalImage;
    private Image gates;
    private ImageButton buttonStart;
    private ImageButton buttonHelp;
    private ImageButton buttonSettings;
    private ImageButton buttonHighScore;

    private Animation rightBowl;
    private Animation leftBowl;
    private Animation portal;
    private Animation openGates;

    public MainMenuScreen(ElMaster game) {
        super(game);
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        assetGates = assets.getAssetGates();
        assetBackground = assets.getAssetBackground();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        prefs = GameSettings.getInstance();

        stateTime = 0;
        // Styles
        labelStyleGameTitle = new Label.LabelStyle();
        labelStyleGameTitle.font = assets.getAssetFonts().getGameTitle();

        // Play menu music
        AudioManager.getInstance().playMusic(Assets.getInstance().getAssetMusic().getSongMainMenu());
    }

    @Override
    protected void updateLogic(float deltaTime) {
        stateTime += deltaTime;
        stage.act();
        Image image;
        image = new Image((TextureRegion)leftBowl.getKeyFrame(stateTime,true));
        image.setName("leftBowl");
        leftBowlImage = image;
        leftBowlImage.setY(menuBg.getY() + 435);
        leftBowlImage.setX(menuBg.getX() + 65);
        image = new Image((TextureRegion)rightBowl.getKeyFrame(stateTime,true));
        image.setName("rightBowl");
        rightBowlImage = image;
        rightBowlImage.setY(menuBg.getY() + 435);
        rightBowlImage.setX(menuBg.getX() + 410);
        image = new Image((TextureRegion)portal.getKeyFrame(stateTime,true));
        image.setName("portal");
        portalImage = image;
        portalImage.setY(menuBg.getY() + 640);
        portalImage.setX(menuBg.getX() + 145);
        if(fogCenter.getX() < 0 && fogCenter.getX() > -fogCenter.getWidth()){
            fogCenter.setX(fogCenter.getX() - 3);
            fogRight.setX(fogCenter.getX() + fogCenter.getWidth() - 3);
            fogLeft.setX(fogRight.getX() + fogRight.getWidth() - 3);
        }else if(fogRight.getX() < 0 && fogRight.getX() > - fogRight.getWidth()){
            fogRight.setX(fogRight.getX() - 3);
            fogLeft.setX(fogRight.getX() + fogRight.getWidth() - 3);
            fogCenter.setX(fogLeft.getX() + fogLeft.getWidth() - 3);
        }else{
            fogLeft.setX(fogLeft.getX() - 3);
            fogCenter.setX(fogLeft.getX() + fogLeft.getWidth() - 3);
            fogRight.setX(fogCenter.getX() + fogCenter.getWidth() - 3);
        }

        for(Actor actor : stage.getActors()){
            System.out.println("deltaTime = " + deltaTime);
           if(actor instanceof Image){
               Image image1 = (Image)actor;
               if(image1.getName() == null){
                   continue;
               }
               if(image1.getName().equals("leftBowl")){
                   ((Image) actor).setDrawable(new TextureRegionDrawable((TextureRegion) leftBowl.getKeyFrame(stateTime)));
               }else  if(image1.getName().equals("rightBowl")){
                   ((Image) actor).setDrawable(new TextureRegionDrawable((TextureRegion) rightBowl.getKeyFrame(stateTime)));
               }else  if(image1.getName().equals("portal")){
                   ((Image) actor).setDrawable(new TextureRegionDrawable((TextureRegion) portal.getKeyFrame(stateTime)));
               }
           }
        }

        stage.clear();
        stage.addActor(background);
        stage.addActor(fogLeft);
        stage.addActor(fogCenter);
        stage.addActor(fogRight);
        stage.addActor(dark_fog);
        stage.addActor(gates);
        stage.addActor(portalImage);
        stage.addActor(menuBg);
        stage.addActor(dragonHead);
        stage.addActor(dragonLeftHand);
        stage.addActor(dragonRightHand);
        stage.addActor(gameTitle);
        stage.addActor(buttonHelp);
        stage.addActor(buttonSettings);
        stage.addActor(buttonHighScore);
        stage.addActor(buttonStart);
        stage.addActor(leftBowlImage);
        stage.addActor(rightBowlImage);

    }

    @Override
    protected void renderLogic() {
        stage.draw();
    }

    @Override
    protected void goBack() {
        // Nothing to do here
    }

    @Override
    public void show() {
        hideBannerAd();
        Image image;

        // Background
        background = new Image(assetBackground.getBackground());
        dark_fog = new Image(assetBackground.getFogDark());

        image =  new Image(assetBackground.getFogLeft());
        image.setName("fogLeft");
        fogLeft = image;

        image = new Image(assetBackground.getFogCenter());
        image.setName("fogCenter");
        fogCenter = image;

        image = new Image(assetBackground.getFogRight());
        image.setName("fogRight");
        fogRight = image;

        image = new Image(assetGates.getStaticMain());
        image.setName("menuBg");
        menuBg = image;

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
        stage.addActor(leftBowlImage);


        rightBowl = assetGates.getRightFireBowl();
        image = new Image((TextureRegion)rightBowl.getKeyFrame(stateTime,true));
        image.setName("rightBowl");
        rightBowlImage = image;
        stage.addActor(rightBowlImage);


        //Portal
        portal = assetGates.getPortal();
        image = new Image((TextureRegion)portal.getKeyFrame(stateTime,true));
        image.setName("portal");
        portalImage = image;


        //Gates
        image = new Image(assetBackground.getFogRight());
        image.setName("gates");
        gates = new Image(assetGates.getClosedGates());

        // Title
        gameTitle = new Label(i18NGameThreeBundle.format("mainMenuScreen.gameTitle"), labelStyleGameTitle);
        leftBowlImage = new Image((TextureRegion)leftBowl.getKeyFrame(0,true));
//        stage.addActor(leftBowlImage);

        // Buttons
        defineButtons();
        stage.addActor(background);
        stage.addActor(fogLeft);
        stage.addActor(fogCenter);
        stage.addActor(fogRight);
        stage.addActor(dark_fog);
        stage.addActor(gates);
        stage.addActor(portalImage);
        stage.addActor(menuBg);
        stage.addActor(dragonHead);
        stage.addActor(dragonLeftHand);
        stage.addActor(dragonRightHand);
        stage.addActor(gameTitle);
        stage.addActor(buttonHelp);
        stage.addActor(buttonSettings);
        stage.addActor(buttonHighScore);
        stage.addActor(buttonStart);

     }

    private void defineButtons() {
        buttonStart = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonStart()),
                new TextureRegionDrawable(assetGUI.getButtonStart()));
        buttonHelp = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonHelp()),
                new TextureRegionDrawable(assetGUI.getButtonHelp()));
        buttonHighScore = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonHighScore()),
                new TextureRegionDrawable(assetGUI.getButtonHighScore()));
        buttonSettings = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonSettings()),
                new TextureRegionDrawable(assetGUI.getButtonSettings()));


        // Events
        buttonStart.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_WHITE));
//        exit.addListener(ListenerHelper.runnableListener(new Runnable() {
//            @Override
//            public void run() {
//                signOut();
//                Gdx.app.exit();
//            }
//        }));
    }

    @Override
    public void resize(int width, int height) {
        float offSetX = 0;
        super.resize(width, height);

        float w = stage.getWidth(); // Same as stage.getViewport().getWorldWidth()
        float h = stage.getHeight();


        if( height / width < 1.5f){
//            offSetX = (width - ( height / 2))/2;
            offSetX = (w - 420)/2;
//            w = height / 2;
        }



        // Place the menu background in the middle of the screen
       background.setHeight(h);
//        background.setWidth(500);
        background.setPosition(w/2 - background.getWidth() /2, h/2 - background.getHeight()/2);
        dark_fog.setPosition(w/2 - background.getWidth() /2, h/2 - background.getHeight()/2);
        //fog.setPosition(w/2 - background.getWidth() /2, h/2 - background.getHeight()/2);
        menuBg.setWidth(600);
        menuBg.setX(w/2 - menuBg.getWidth()/2);
        //if(h > assetGUI.getBackgroundGates().getRegionHeight()) {
//        }else{
//            menuBg.setY(0);
//        }
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


        w = 420 + ((w - 420) /2);
        // Place the title
        gameTitle.setPosition(125 + offSetX, TITLE_OFFSET_Y);

        //Place buttons
        buttonStart.setWidth(128);
        buttonStart.setX(148 + offSetX);
        buttonStart.setHeight(128);
        buttonStart.setY(840 - 255 - 128);

        buttonSettings.setWidth(75);
        buttonSettings.setX(96 + offSetX);
        buttonSettings.setHeight(75);
        buttonSettings.setY(840 - 384 - 75);

        buttonHighScore.setWidth(75);
        buttonHighScore.setX(171 + offSetX);
        buttonHighScore.setHeight(75);
        buttonHighScore.setY(840 - 455 - 75);

        buttonHelp.setWidth(75);
        buttonHelp.setX(249 + offSetX);
        buttonHelp.setHeight(75);
        buttonHelp.setY(840 - 378 - 75);

        // Buttons Animations
        //setButtonsAnimation();
    }

    private void setButtonsAnimation() {
        // Disable events
//        play.setTouchable(Touchable.disabled);
//
//        exit.setTouchable(Touchable.disabled);
//
//        // Only available on Android version
//        //rateGame.setVisible(!(playServices instanceof DummyPlayServices));
//        //showLeaderboards.setVisible(!(playServices instanceof DummyPlayServices));
//
//        // Set actions
//        play.clearActions();
//
//        play.addAction(sequence(moveBy(0, BUTTONS_MOVE_BY_AMOUNT, BUTTONS_ANIM_DURATION, Interpolation.bounceOut),
//                run(new Runnable() {
//                    public void run () {
//                        // Enable events
//                        play.setTouchable(Touchable.enabled);
//                    }
//                })));
//        play.addAction(sequence(moveBy(0, BUTTONS_MOVE_BY_AMOUNT, BUTTONS_ANIM_DURATION, Interpolation.bounceOut),
//                run(new Runnable() {
//                    public void run () {
//                        // Enable events
//                        exit.setTouchable(Touchable.enabled);
//                    }
//                })));
    }

    private void showLeaderboards() {
    }

    private void showLeaderboardsImp() {
       // playServices.submitScore(prefs.getHighScore());
       // playServices.showLeaderboards();
    }

    private void rateGame() {
        //playServices.rateGame();
    }

    private void signOut() {
        //playServices.signOut();
    }
}
