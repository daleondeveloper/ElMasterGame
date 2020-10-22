package com.daleondeveloper.Sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Array;
import com.daleondeveloper.Game.GameWorld;
import com.daleondeveloper.Screens.Play.PlayScreen;

public class PlatformController {
    private static final String TAG = PlatformController.class.getName();

    private Array<Platform> platforms;
    private GameWorld gameWorld;
    private PlayScreen playScreen;

    public PlatformController(PlayScreen playScreen, GameWorld gameWorld){
        this.gameWorld = gameWorld;
        this.playScreen = playScreen;

        platforms = new Array<Platform>();
    }

    public Platform addPlatform(float x, float y, float hx, float hy){
        Platform platform = new Platform(gameWorld,x,y,hx,hy);
        platforms.add(platform);
        return platform;
    }

    public boolean deletePlatform(Platform platform){
        Platform platformToDel ;
        if(platforms.contains(platform,true)){
            platformToDel = platform;
        }else{
            return false;
        }
        platformToDel.delete();
        return true;
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }
}
