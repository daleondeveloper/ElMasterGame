package com.daleondeveloper.Game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.daleondeveloper.Screens.GUI.Hud;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Sprites.Hero.WaterElement;


/**
 * Created by AGMCORP on 19/9/2018.
 */

public class GameController implements GestureDetector.GestureListener, InputProcessor {
    private static final String TAG = GameController.class.getName();

    private GameWorld gameWorld;
    private PlayScreen playScreen;
    private WaterElement waterElement;
    private Hud hud;

    public GameController(GameWorld gameWorld, PlayScreen playScreen) {
        this.gameWorld = gameWorld;
        this.playScreen = playScreen;
        waterElement = gameWorld.getWaterElement();
        hud = playScreen.getHud();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        startJump();
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        shoot();
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
                         Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {
        cheatMode();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.SPACE:
                 waterElement.jump();
                 gameWorld.getBlockController().addBlock(4,4);
                break;
            case Input.Keys.RIGHT:
                waterElement.turn(1);
                break;
            case Input.Keys.LEFT:
                waterElement.turn(-1);
                break;
            case Input.Keys.Z:

                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.SPACE:
                if (true) {
                    shoot();
                } else {
                    performJump();
                }
                break;
            case Input.Keys.P:
                cheatMode();
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        performJump();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private void startJump() {
        if (playScreen.isPlayScreenStateRunning()) {
            if (waterElement.isIdle()) {
                hud.startSwing();
            }
        }
    }

    private void performJump() {
        if (playScreen.isPlayScreenStateRunning()) {
            if (waterElement.isIdle()) {
               // hud.stopSwing();
                //waterElement.jump(hud.getPowerBarValue());
            }
        }
    }

    private void cheatMode() {
        if (playScreen.isPlayScreenStateRunning()) {
            if (DebugConstants.POWER_JUMP_ENABLED && !waterElement.isDisposable()) {

                //waterElement.powerJump();
            }
        }
    }

    private void shoot() {
        if (playScreen.isPlayScreenStateRunning()) {
            if (true) {
                //jumper.shoot();
            }
        }
    }
}

