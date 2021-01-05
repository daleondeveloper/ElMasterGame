package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Assets.fonts.AssetFonts;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.GameSettings;
import com.daleondeveloper.Screens.GUI.widget.AnimatedActor;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;
import com.daleondeveloper.Screens.GUIAbstractScreen;

import java.util.ArrayList;
import java.util.Random;


public class SplashScreen extends GUIAbstractScreen {
    private static final String TAG = SplashScreen.class.getName();

    private static final String TEXTURE_ATLAS_SPLASH_SCREEN = "atlas/loading/Splash.atlas";
    private static final float LOGO_OFFSET_Y = 100.0f;
    private static final float START_X = 35.0f;
    private static final float PIVOT = 405.0f;
    private static final float LOADING_BACKGROUND_HEIGHT = 55.0f;
    private static final float MIN_SPLASH_TIME = 3.0f;
    private static final float ALPHA = 0.1f;

    private AssetManager assetManager;
    private AssetFonts assetFonts;
    private float splashTime;
    private Label.LabelStyle labelStyleSmall;

    private Animation<TextureRegion> heroPush;
    private AnimatedActor heroActor;
    private Image heroBlock;
    private TextureRegion[] blocks;
    private Label loadPercentLabel;


    private float percent;

    public SplashScreen(ElMaster game) {
        super(game);

        this.assetManager = new AssetManager();
        splashTime = 0;
        assetFonts = new AssetFonts();
        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = assetFonts.getSmall();

        blocks = new TextureRegion[4];

    }

    @Override
    protected void updateLogic(float deltaTime) {
        splashTime += deltaTime;
        if (assetManager.update() && splashTime >= MIN_SPLASH_TIME) {
            // Load some, will return true if done loading
            Assets.getInstance().finishLoading();
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU, null);
        } else {
            // Interpolate the percentage to make it more smooth
            percent = Interpolation.linear.apply(percent, assetManager.getProgress(), ALPHA);

            // Update positions (and size) to match the percentage
            int intPercent = (int)(percent*100);
            System.out.println(percent);
            loadPercentLabel.setText(percent + "%");


            // Show the loading screen
            stage.act();
        }
    }

    protected void renderLogic() {
        stage.draw();
    }

    @Override
    protected void goBack() {
        // Nothing to do here
    }

    @Override
    public void show() {
        // Tell the manager to load assets for the loading screen
        assetManager.load(TEXTURE_ATLAS_SPLASH_SCREEN, TextureAtlas.class);

        // Wait until they are finished loading
        assetManager.finishLoading();

        // Get our texture atlas from the manager
        TextureAtlas atlas = assetManager.get(TEXTURE_ATLAS_SPLASH_SCREEN, TextureAtlas.class);

        // Grab the regions from the atlas and create some images
        heroPush = new Animation<TextureRegion>(3/24f,atlas.findRegions("push"), Animation.PlayMode.LOOP);
        blocks[0] = atlas.findRegion("fire");
        blocks[1] = atlas.findRegion("water");
        blocks[2] = atlas.findRegion("snow");
        blocks[3] = atlas.findRegion("light");
        Random rnd = new Random();
        heroBlock = new Image(blocks[rnd.nextInt(3)]);

        heroActor = new AnimatedActor(heroPush);

        loadPercentLabel = new Label(percent + "%",labelStyleSmall);
        // Add all the actors to the stage
        stage.addActor(heroBlock);
        stage.addActor(heroActor);
        stage.addActor(loadPercentLabel);


        // Load the rest of assets asynchronously
        Assets.getInstance().init(assetManager);

        // Sign in on Google play game services

         // I18NGameFourBundle probably hasn't been loaded yet
    }

    private void submitScore() {
        //playServices.submitScore(GameSettings.getInstance().getHighScore());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float w = stage.getWidth(); // Same as stage.getViewport().getWorldWidth()
        float h = stage.getHeight();


        heroActor.setPosition(w/2 - heroActor.getWidth() * 1.2f , h / 2 + heroActor.getHeight() * 0.5f);
        heroBlock.setPosition(heroActor.getX() + heroActor.getWidth(), h / 2 + heroActor.getHeight() * 0.5f);
        loadPercentLabel.setPosition(heroActor.getX() + heroActor.getWidth() * 0.4f, h / 2);
    }

    @Override
    public void hide() {
        super.hide();

        // Dispose the loading assets as we no longer need them
        assetManager.unload(TEXTURE_ATLAS_SPLASH_SCREEN);
    }

    @Override
    public void dispose() {
        assetFonts.dispose();
        assetFonts = null;
    }
}
