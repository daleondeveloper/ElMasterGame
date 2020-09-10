package com.daleondeveloper.Screens;

import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Screens.GUI.MainMenuScreen;
import com.daleondeveloper.Screens.GUI.SplashScreen;
import com.daleondeveloper.Screens.Play.PlayScreen;

/**
 * Created by AGMCORP on 17/9/2018.
 */

public enum ScreenEnum {
    SPLASH {
        public AbstractScreen getScreen(ElMaster game, Object... params) {
            return new SplashScreen(game);
        }
    },

    MAIN_MENU {
        public AbstractScreen getScreen(ElMaster game, Object... params) {
            return new MainMenuScreen(game);
        }
    },

    PLAY_GAME {
        public AbstractScreen getScreen(ElMaster game, Object... params) {
            return new PlayScreen(game);
        }
    };

    public abstract AbstractScreen getScreen(ElMaster game, Object... params);
}