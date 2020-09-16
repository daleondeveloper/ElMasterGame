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

    private static final float JUMPER_SCALE = 1.3f;
    private static final float ROCKET_BACKGROUND_SCALE = 0.2f;
    private static final float TITLE_OFFSET_Y = 600.0f;
    private static final float ROCKET_BG_OFFSET_X = 200.0f;
    private static final float ROCKET_BG_OFFSET_Y = 50.0f;
    private static final float CLOUD_OFFSET_X = 40.0f;
    private static final float CLOUD_OFFSET_Y = 20.0f;
    private static final float JUMPER_OFFSET_Y = 200.0f;
    private static final float ROCKET_FG_OFFSET_Y = 40.0f;
    private static final float BUTTONS_OFFSET_Y = 350.0f;
    private static final float BUTTONS_ANIM_DURATION = 1.0f;
    private static final float BUTTONS_MOVE_BY_AMOUNT = 10.0f;
    private static final float BUTTON_OFFSET_X = 75.0f;

    private Assets assets;
    private AssetGUI assetGUI;
    private AssetGame assetSprites;
    private I18NBundle i18NGameThreeBundle;
    private GameSettings prefs;
    private Label.LabelStyle labelStyleGameTitle;
    private Image menuBg;
    private Label gameTitle;

    private ImageButton play;

    private ImageButton exit;

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
        menuBg = new Image(assetGUI.getMainFon());
        stage.addActor(menuBg);

        // Title
        gameTitle = new Label(i18NGameThreeBundle.format("mainMenuScreen.gameTitle"), labelStyleGameTitle);
        stage.addActor(gameTitle);


        // Buttons
        defineButtons();
        stage.addActor(exit);
        stage.addActor(play);
    }

    private void defineButtons() {
        play = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonRight()),
                new TextureRegionDrawable(assetGUI.getButtonRightMini()));

        exit = new ImageButton(new TextureRegionDrawable(assetGUI.getButtonExit()),
                new TextureRegionDrawable(assetGUI.getButtonExit()));

        // Events
        play.addListener(ListenerHelper.screenNavigationListener(ScreenEnum.PLAY_GAME, ScreenTransitionEnum.COLOR_FADE_WHITE));
        exit.addListener(ListenerHelper.runnableListener(new Runnable() {
            @Override
            public void run() {
                signOut();
                Gdx.app.exit();
            }
        }));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float w = stage.getWidth(); // Same as stage.getViewport().getWorldWidth()
        float h = stage.getHeight();

        // Place the menu background in the middle of the screen
        menuBg.setX(0);
        menuBg.setY(0);
        menuBg.setWidth(w);
        menuBg.setHeight(h);

        // Place the title
        gameTitle.setPosition(((w  / 2)-gameTitle.getPrefWidth()/2), TITLE_OFFSET_Y);

        // Place buttons
        play.setX((w  / 2) - play.getWidth()/2);
        play.setY((h / 2 ) - play.getHeight());

        float x = play.getX();//- audio.getWidth() / 2;
        float y = play.getY() - play.getHeight();// + play.getHeight()/2 ;

        exit.setPosition(x, y);

        // Buttons Animations
        //setButtonsAnimation();
    }

    private void setButtonsAnimation() {
        // Disable events
        play.setTouchable(Touchable.disabled);

        exit.setTouchable(Touchable.disabled);

        // Only available on Android version
        //rateGame.setVisible(!(playServices instanceof DummyPlayServices));
        //showLeaderboards.setVisible(!(playServices instanceof DummyPlayServices));

        // Set actions
        play.clearActions();

        play.addAction(sequence(moveBy(0, BUTTONS_MOVE_BY_AMOUNT, BUTTONS_ANIM_DURATION, Interpolation.bounceOut),
                run(new Runnable() {
                    public void run () {
                        // Enable events
                        play.setTouchable(Touchable.enabled);
                    }
                })));
        play.addAction(sequence(moveBy(0, BUTTONS_MOVE_BY_AMOUNT, BUTTONS_ANIM_DURATION, Interpolation.bounceOut),
                run(new Runnable() {
                    public void run () {
                        // Enable events
                        exit.setTouchable(Touchable.enabled);
                    }
                })));
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
