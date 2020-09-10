package com.daleondeveloper.Screens.Play;

import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Screens.AbstractScreen;


/**
 * Created by AGMCORP on 17/9/2018.
 */

public abstract class PlayAbstractScreen extends AbstractScreen {
    private static final String TAG = PlayAbstractScreen.class.getName();

    // Game state
    protected enum PlayScreenState {
        PAUSED, RUNNING, STOPPED
    }
    protected PlayScreenState playScreenState;


    public PlayAbstractScreen(ElMaster game) {
        super(game);
        playScreenState = PlayScreenState.RUNNING;
    }

    public boolean isPlayScreenStateRunning() {
        return playScreenState == PlayScreenState.RUNNING;
    }

    @Override
    public void stop() {
        playScreenState = PlayScreenState.STOPPED;
    }

    @Override
    public void pause() {
        playScreenState = PlayScreenState.PAUSED;
    }

    @Override
    public void resume() {
      playScreenState = PlayScreenState.RUNNING;
    }

    @Override
    public void dispose () {
        hide();
    }
}
