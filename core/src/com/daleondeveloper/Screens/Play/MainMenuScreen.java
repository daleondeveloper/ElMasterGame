package com.daleondeveloper.Screens.Play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetBackground;
import com.daleondeveloper.Assets.game.AssetGates;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Effects.ParticleEffectActor;
import com.daleondeveloper.Effects.ParticleEffectManager;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.Settings.GameSettings;
import com.daleondeveloper.Screens.GUI.GatesScreen;
import com.daleondeveloper.Screens.GUI.Menu.MenuScreen;
import com.daleondeveloper.Screens.GUI.widget.AnimatedActor;
import com.daleondeveloper.Screens.GUIAbstractScreen;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.tools.AudioManager;

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
    private MenuScreen menuScreen;
    private GatesScreen gatesScreen;

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
    private Image buttonCredit;

    private Image rightBowl;
    private Image rightBowlUp;
    private Image leftBowl;
    private Image leftBowlUp;
    private Animation<TextureRegion> portal;
    private Animation<TextureRegion> openGates;

    private ParticleEffectManager pef;
    private ParticleEffectActor rightBowlFireActor;
    private ParticleEffectActor leftBowlFireActor;

    private ParticleEffect rightBowlFire;
    private ParticleEffect leftBowlFire;

    private AnimatedActor portalAnimatedActor;

    public MainMenuScreen(ElMaster game) {
        super(game);
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        assetGates = assets.getAssetGates();
        assetBackground = assets.getAssetBackground();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        prefs = GameSettings.getInstance();
        menuScreen = new MenuScreen(game,this);
        gatesScreen = new GatesScreen(game);

        pef = new ParticleEffectManager();

        stateTime = 0;
        // Styles
        labelStyleGameTitle = new Label.LabelStyle();
        labelStyleGameTitle.font = assets.getAssetFonts().getGameTitle();

        // Play menu music
        AudioManager.getInstance().playMusic(Assets.getInstance().getAssetMusic().getSongMainMenu());
    }

    @Override
    public void render(float deltaTime) {
        stateTime += deltaTime;
        stage.act();


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

        menuScreen.update(deltaTime);

        //render
        stage.draw();

        menuScreen.render();

       // magicSpell.draw(stage.get,Gdx.graphics.getDeltaTime()); // draw it
    }

    @Override
    protected void updateLogic(float deltaTime) {


    }

    @Override
    protected void renderLogic() {

}

    @Override
    protected void goBack() {
        // Nothing to do here
    }

    @Override
    public void show() {
        hideBannerAd();
        menuScreen.build();

        pef.addParticleEffect(ParticleEffectManager.FIRE,assets.getAssetManager().get("effect/fire/fireeffect.p",ParticleEffect.class));
        Image image;

        // Background
        background = new Image(assetBackground.getBackground());
        dark_fog = new Image(assetBackground.getFogDark());
        fogLeft = new Image(assetBackground.getFogLeft());
        fogCenter = new Image(assetBackground.getFogCenter());
        fogRight = new Image(assetBackground.getFogRight());

        menuBg =new Image(assetGates.getStaticMain());

        //Dragon
;
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

        //Gates
        gates = new Image(assetGates.getClosedGates());

        // Title
        gameTitle = new Label(i18NGameThreeBundle.format("mainMenuScreen.gameTitle"), labelStyleGameTitle);
        //leftBowlImage = new Image((TextureRegion)leftBowl.getKeyFrame(0,true));
//        stage.addActor(leftBowlImage);
        //leftBowlAnimatedActor = new AnimatedActor(leftBowl);
        //rightBowlAnimatedActor = new AnimatedActor(rightBowl);
        portalAnimatedActor = new AnimatedActor(portal);

        rightBowlFire = pef.getPoolParticleEffect(ParticleEffectManager.FIRE);
        rightBowlFire.start();
        float[] f = new float[3];
        f[0] = 0.047058824f;
        f[1] = 0.06666667f;
        f[2] = 1f;
        rightBowlFire.getEmitters().get(0).getTint().setColors(f);
        rightBowlFireActor = new ParticleEffectActor(rightBowlFire);

        leftBowlFire = pef.getPoolParticleEffect(ParticleEffectManager.FIRE);
        leftBowlFire.start();
         f = new float[3];
        f[0] = 0.047058824f;
        f[1] = 0.4f;
        f[2] = 1f;
        leftBowlFire.getEmitters().get(0).getTint().setColors(f);
        leftBowlFireActor = new ParticleEffectActor(leftBowlFire);



        // Buttons
        defineButtons();
        stage.addActor(background);
        stage.addActor(fogLeft);
        stage.addActor(fogCenter);
        stage.addActor(fogRight);
        stage.addActor(dark_fog);
        stage.addActor(gates);
        stage.addActor(portalAnimatedActor);
        stage.addActor(menuBg);
        stage.addActor(dragonHead);
        stage.addActor(dragonLeftHand);
        stage.addActor(dragonRightHand);
        stage.addActor(leftBowl);
        stage.addActor(leftBowlFireActor);
        stage.addActor(leftBowlUp);
        stage.addActor(rightBowl);
        stage.addActor(rightBowlFireActor);
        stage.addActor(rightBowlUp);
        stage.addActor(gameTitle);
        stage.addActor(buttonHelp);
        stage.addActor(buttonSettings);
        stage.addActor(buttonHighScore);
        stage.addActor(buttonStart);
        stage.addActor(buttonCredit);



        setStateRunning();


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
        buttonCredit = new Image(assetGUI.getButtonHelp());


//        buttonStart.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_WHITE));
        buttonStart.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                setStatePaused();
                menuScreen.showMenuScreen(MenuScreen.MenuState.GAMEMODECHOOSE);
//                ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK);

            }
        }));
        buttonHelp.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                setStatePaused();
                menuScreen.getHelpScreen().setHelpMenuShow(0);
                menuScreen.showMenuScreen(MenuScreen.MenuState.HELP);
//                ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_BLACK);

            }
        }));
         buttonHighScore.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                setStatePaused();
                menuScreen.showMenuScreen(MenuScreen.MenuState.HIGH_SCORE);
            }
        }));
        buttonSettings.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                setStatePaused();
                menuScreen.showMenuScreen(MenuScreen.MenuState.SETTINGS);
            }
        }));
        buttonCredit.addListener(ListenerHelper.runnableListenerTouchDown(new Runnable() {
            @Override
            public void run() {
                setStatePaused();
                menuScreen.showMenuScreen(MenuScreen.MenuState.CREDIT);
            }
        }));

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
        menuScreen.resize(width,height);

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

        buttonCredit.setWidth(64);
        buttonCredit.setHeight(64);
        buttonCredit.setPosition(w - buttonCredit.getWidth(),h - buttonCredit.getHeight());

        // Buttons Animations
        //setButtonsAnimation();
        leftBowl.setY(menuBg.getY() + 435);
        leftBowl.setX(menuBg.getX() + 65);
        leftBowlUp.setPosition(menuBg.getX() + 65,menuBg.getY() + 435);
        leftBowlFire.setPosition(menuBg.getX() + 120, menuBg.getY() + 480);  // set the position


        rightBowl.setY(menuBg.getY() + 435);
        rightBowlUp.setY(menuBg.getY() + 435);
        rightBowl.setX(menuBg.getX() + 410);
        rightBowlUp.setX(menuBg.getX() + 410);
        rightBowlFire.setPosition(menuBg.getX() + 480, menuBg.getY() + 480);  // set the position


        portalAnimatedActor.setY(menuBg.getY() + 640);
        portalAnimatedActor.setX(menuBg.getX() + 145);

    }
    public void doPause() {
        hideBannerAd();
        super.pause();
    }

    public void setStatePaused() {
        doPause();
    }
    public void setStateRunning() {
        menuScreen.hideMenuScreen();
        Gdx.input.setInputProcessor(getInputProcessor());
        resume();
    }

    @Override
    public void resume() {
        AudioManager.getInstance().resumeMusic();
        if (!menuScreen.isMenuScreenVisible()) {
            showBannerAd();
            super.resume();
        }
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
