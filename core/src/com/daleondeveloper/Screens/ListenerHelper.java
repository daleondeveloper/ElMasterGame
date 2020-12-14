package com.daleondeveloper.Screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.tools.AudioManager;


public class ListenerHelper {
    private static final String TAG = ListenerHelper.class.getName();

    public static InputListener screenNavigationListener(final ScreenEnum screenEnum, final ScreenTransitionEnum screenTransitionEnum, final Object... params) {
        return
                new InputListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        // Audio effect
                        AudioManager.getInstance().playSound(Assets.getInstance().getAssetSounds().getClick());
                        ScreenManager.getInstance().showScreen(screenEnum, screenTransitionEnum, params);
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                };
    }

    public static InputListener runnableListener(final Runnable runnable) {
        return
                new InputListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        // Audio effect
                        AudioManager.getInstance().playSound(Assets.getInstance().getAssetSounds().getClick());
                        runnable.run();
                    }
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                        return true;
                    }

                };
    }
    public static InputListener runnableListenerTouchDown(final Runnable runnable) {
        return
                new InputListener() {

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        // Audio effect
                        AudioManager.getInstance().playSound(Assets.getInstance().getAssetSounds().getClick());
                        runnable.run();
                        return true;
                    }

                };
    }

}
