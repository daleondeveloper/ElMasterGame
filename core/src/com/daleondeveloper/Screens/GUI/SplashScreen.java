package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.GameSettings;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;

public class SplashScreen extends GUIAbstractScreen {
    private static final String TAG = SplashScreen.class.getName();

    private static final String TEXTURE_ATLAS_SPLASH_SCREEN = "atlas/loading/download_bar.atlas";
    private static final String TEXTURE_ATLAS_NUMBERS = "atlas/loading/qweqwe.atlas";
    private static final float LOGO_OFFSET_Y = 100.0f;
    private static final float START_X = 35.0f;
    private static final float PIVOT = 405.0f;
    private static final float LOADING_BACKGROUND_HEIGHT = 55.0f;
    private static final float MIN_SPLASH_TIME = 3.0f;
    private static final float ALPHA = 0.1f;

    private AssetManager assetManager;
    private float splashTime;

    private Image heroBlock;
    private Image loadingBar;
    private Image[] numbers;

    private float startX, endX;
    private float percent;

    public SplashScreen(ElMaster game) {
        super(game);

        this.assetManager = new AssetManager();
        splashTime = 0;

        numbers = new Image[10];
    }

    @Override
    protected void updateLogic(float deltaTime) {
        splashTime += deltaTime;
        if (assetManager.update() && splashTime >= MIN_SPLASH_TIME) { // Load some, will return true if done loading
            Assets.getInstance().finishLoading();
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU, null);
        } else {
            // Interpolate the percentage to make it more smooth
            percent = Interpolation.linear.apply(percent, assetManager.getProgress(), ALPHA);

            // Update positions (and size) to match the percentage
            heroBlock.setPosition(loadingBar.getX() + ((loadingBar.getWidth()- heroBlock.getWidth()) * percent) ,loadingBar.getY() * 1.01f);
            int intPercent = (int)(percent*100);
            System.out.println(percent);


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
        heroBlock = new Image(atlas.findRegion("hero_push_block"));
        loadingBar = new Image(atlas.findRegion("download_bar"));

        // Add all the actors to the stage
        stage.addActor(heroBlock);
        stage.addActor(loadingBar);

//        atlas = assetManager.get(TEXTURE_ATLAS_NUMBERS, TextureAtlas.class);
//        for(int i = 0; i < numbers.length; i++){
//            numbers[i] = new Image(atlas.findRegion(String.valueOf(i)));
//            stage.addActor(numbers[i]);
//        }



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
        loadingBar.setWidth(w * 0.9f);
        loadingBar.setPosition(w / 2 - loadingBar.getWidth() / 2,410);
//        // Make the background fill the screen
//        screenBg.setSize(w, h);
//
//        // Place the logo in the middle of the screen and LOGO_OFFSET_Y px up
//        logo.setX((w - logo.getWidth()) / 2);
//        logo.setY((h - logo.getHeight()) / 2 + LOGO_OFFSET_Y);
//
//        // Place the loading frame in the middle of the screen
//        loadingFrame.setX((w - loadingFrame.getWidth()) / 2);
//        loadingFrame.setY((h - loadingFrame.getHeight()) / 2);
//
//        // Place the loading bar at the same spot as the frame
//        loadingBar.setX(loadingFrame.getX());
//        loadingBar.setY(loadingFrame.getY());
//
//        // The start position and how far to move the hidden loading bar
//        startX = loadingBar.getX() + START_X;
//        endX = PIVOT;
//        percent = 0;
//
//        // Place the image that will hide the bar on top of the bar
//        loadingBarHidden.setX(startX);
//        loadingBarHidden.setY(loadingBar.getY());
//
//        // The rest of the hidden bar
//        loadingFrameBg.setSize(PIVOT, LOADING_BACKGROUND_HEIGHT);
//        loadingFrameBg.setX(loadingBarHidden.getX() + loadingBarHidden.getWidth());
//        loadingFrameBg.setY(loadingBarHidden.getY());
    }

    @Override
    public void hide() {
        super.hide();

        // Dispose the loading assets as we no longer need them
        assetManager.unload(TEXTURE_ATLAS_SPLASH_SCREEN);
    }
}
