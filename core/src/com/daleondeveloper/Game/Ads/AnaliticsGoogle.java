package com.daleondeveloper.Game.Ads;

import com.daleondeveloper.Game.ElMaster;

public class AnaliticsGoogle {
    private ElMaster game;
    private static AnaliticsGoogle adsShower;
    private boolean adsWatched;
    private boolean adsShowed;

    public AnaliticsGoogle(ElMaster game){
        adsShower = this;
        this.game = game;
        adsWatched = false;
        adsShowed = false;
    }
    public static AnaliticsGoogle getInstance(){
        return adsShower;
    }

    public void levelUp(int level){
        game.getAnaliticsController().levelUp(level,1);
    }


}
