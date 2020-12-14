package com.daleondeveloper.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.daleondeveloper.Game.ElMaster;
//import uy.com.agm.gamefour.admob.IAdsController;
//import uy.com.agm.gamefour.playservices.IPlayServices;

public abstract class AbstractScreen implements Screen {
    private static final String TAG = AbstractScreen.class.getName();

    protected ElMaster game;

    public AbstractScreen(ElMaster game) {
        this.game = game;
  //      adsController =  game.getAdsController();
  //      playServices = game.getPlayServices();

        // Sets whether the BACK button on Android should be caught.
        // This will prevent the app from being paused. Will have no effect on the desktop/html.
        Gdx.input.setCatchBackKey(true);
    }

    public static void clearScr() {
        // Clear the screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    protected void showBannerAd() {
     //   if (adsController.isWifiConnected()) {
    //        adsController.showBannerAd();
     //   } else {
     //       Gdx.app.debug(TAG, "**** Not connected to the internet");
      //  }
    }

    protected void hideBannerAd() {
        //adsController.hideBannerAd();
    }

    protected void showInterstitialAd() {
     //   if (adsController.isWifiConnected()) {
     //       adsController.showInterstitialAd(null); // We don't need to execute anything after ad closes
     //   } else {
      //      Gdx.app.debug(TAG, "**** Not connected to the internet");
      //  }
    }


    public abstract void show();

    public abstract void render(float deltaTime);

    public abstract void resize(int width, int height);

    public abstract void pause();


    public abstract void stop();

    public abstract void resume();


    public abstract void hide();

    public abstract void dispose();

    public abstract InputProcessor getInputProcessor();

    public abstract void applyViewport();
}
