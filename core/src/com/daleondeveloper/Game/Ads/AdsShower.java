package com.daleondeveloper.Game.Ads;

import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.Settings.GameSettings;

public class AdsShower {
    private ElMaster game;
    private static AdsShower adsShower;
    private boolean adsWatched;
    private boolean adsShowed;

    public AdsShower (ElMaster game){
        adsShower = this;
        this.game = game;
    }
    public static AdsShower getInstance(){
        return adsShower;
    }
    public void showAds(){
        if(game.getAdsController() != null){
            game.getAdsController().showRewardedVideo();
            GameSettings.getInstance().setAdsContinueCount(0);
        }
    }

    public boolean isAdsWatched() {
        return adsWatched;
    }

    public void setAdsWatched(boolean adsWatched) {
        this.adsWatched = adsWatched;
    }

    public boolean isAdsShowed() {
        return adsShowed;
    }

    public void setAdsShowed(boolean adsShowed) {
        this.adsShowed = adsShowed;
    }
}
