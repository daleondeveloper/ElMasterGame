package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.game.AssetGame;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.GameSettings;
import com.daleondeveloper.Screens.GUI.widget.AnimatedActor;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenTransitionEnum;
import com.daleondeveloper.tools.AudioManager;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by AGMCORP on 17/9/2018.
 */

public class MainMenuScreen extends GUIAbstractScreen {
    private static final String TAG = MainMenuScreen.class.getName();

    private static final float TITLE_OFFSET_Y = 600.0f;
    private static final float TITLE_BUTTON_WIDTH_COEFFICIENT = 0.18f;
    private static final float TITLE_BIG_BUTTON_WIDTH_COEFFICIENT = 0.33f;

    private Assets assets;
    private AssetGUI assetGUI;
    private AssetGame assetSprites;
    private I18NBundle i18NGameThreeBundle;
    private GameSettings prefs;
    private Label.LabelStyle labelStyleGameTitle;
    private Image menuBg;
    private Label gameTitle;

    private Image background;
    private ImageButton buttonStart;
    private ImageButton buttonHelp;
    private ImageButton buttonSettings;
    private ImageButton buttonHighScore;

    public MainMenuScreen(ElMaster game) {
        super(game);
        assets = Assets.getInstance();
        assetGUI = assets.getAssetGUI();
        assetSprites = assets.getAssetGame();
        i18NGameThreeBundle = assets.getI18NElementMaster().getI18NElmasterBundle();
        prefs = GameSettings.getInstance();

        // Styles
        labelStyleGameTitle = new Label.LabelStyle();
        labelStyleGameTitle.font = assets.getAssetFonts().getGameTitle();

        // Play menu music
        AudioManager.getInstance().playMusic(Assets.getInstance().getAssetMusic().getSongMainMenu());
    }

    @Override
    protected void updateLogic(float deltaTime) {
        stage.act();
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

        // Background
        background = new Image(assetGUI.getBackground());
        stage.addActor(background);
        menuBg = new Image(assetGUI.getBackgroundGates());
        stage.addActor(menuBg);

        // Title
        gameTitle = new Label(i18NGameThreeBundle.format("mainMenuScreen.gameTitle"), labelStyleGameTitle);
        stage.addActor(gameTitle);


        // Buttons
        defineButtons();
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
       background.setHeight(height);
        background.setWidth(height * 1.8f);
        background.setPosition(w/2 - width /2, h/2 - height/2);
        menuBg.setWidth(420);
        menuBg.setX(w/2 - menuBg.getWidth()/2);
        //if(h > assetGUI.getBackgroundGates().getRegionHeight()) {
//        }else{
//            menuBg.setY(0);
//        }
        menuBg.setHeight(840);
        menuBg.setY(0);

        w = 420 + ((w - 420) /2);
        // Place the title
        gameTitle.setPosition(50 + offSetX, TITLE_OFFSET_Y);

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


//        buttonStart.setWidth(w * TITLE_BIG_BUTTON_WIDTH_COEFFICIENT);
//        buttonStart.setX((w / 2) - buttonStart.getWidth() / 2 + offSetX);
//        buttonStart.setHeight(w * TITLE_BIG_BUTTON_WIDTH_COEFFICIENT);
//        buttonStart.setY(h - (buttonStart.getHeight() * 3));

//        buttonSettings.setWidth(w * TITLE_BUTTON_WIDTH_COEFFICIENT);
//        buttonSettings.setX(w * 0.24f + offSetX);
//        buttonSettings.setHeight(w * TITLE_BUTTON_WIDTH_COEFFICIENT);
//        buttonSettings.setY(buttonStart.getY() - buttonSettings.getHeight());
//
//        buttonHighScore.setWidth(w * TITLE_BUTTON_WIDTH_COEFFICIENT);
//        buttonHighScore.setX(buttonSettings.getX() + buttonSettings.getWidth());
//        buttonHighScore.setHeight(w * TITLE_BUTTON_WIDTH_COEFFICIENT);
//        buttonHighScore.setY(buttonStart.getY() - buttonSettings.getHeight() * 2);
//
//        buttonHelp.setWidth(w * TITLE_BUTTON_WIDTH_COEFFICIENT);
//        buttonHelp.setX(buttonHighScore.getX() + buttonHighScore.getWidth());
//        buttonHelp.setHeight(w * TITLE_BUTTON_WIDTH_COEFFICIENT);
//        buttonHelp.setY(buttonStart.getY() - buttonSettings.getHeight());
//

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
