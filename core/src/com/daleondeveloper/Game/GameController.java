package com.daleondeveloper.Game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.daleondeveloper.Screens.GUI.Hud;
import com.daleondeveloper.Screens.ListenerHelper;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;
import com.daleondeveloper.Sprites.Block;
import com.daleondeveloper.Sprites.Hero.WaterElement;

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
    public boolean tap(float x, float y, int count, int button) {
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

    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.BACK : {
                ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU, ScreenTransitionEnum.COLOR_FADE_WHITE);
            }break;
            case Input.Keys.SPACE:
                 waterElement.jump();
                break;
            case Input.Keys.RIGHT:
                waterElement.stopWalk();
                gameWorld.setRightButtonPressed(true);
                break;
            case Input.Keys.LEFT:
                waterElement.stopWalk();
                gameWorld.setLeftButtonPressed(true);
                break;
            case Input.Keys.C:
                gameWorld.setButtonPushPressed(true);
                break;
            case Input.Keys.Z:
                gameWorld.getBlockController().addBlock();

                break;
            case Input.Keys.D:
                    Block block = gameWorld.getBlockController().getArrayBlock().get(0);
                    block.delete();
                    break;

                    case Input.Keys.NUM_1:
                        gameWorld.getBlockController().addBlock(50,400);
                    break;
                    case Input.Keys.NUM_2:
                        gameWorld.getBlockController().addBlock(60,400);
                    break;
                    case Input.Keys.NUM_3:
                        gameWorld.getBlockController().addBlock(70,400);
                    break;
                    case Input.Keys.NUM_4:
                        gameWorld.getBlockController().addBlock(80,400);
                    break;
                    case Input.Keys.NUM_5:
                        gameWorld.getBlockController().addBlock(90,400);
                    break;
                    case Input.Keys.NUM_6:
                        gameWorld.getBlockController().addBlock(100,400);
                    break;
                    case Input.Keys.NUM_7:
                        gameWorld.getBlockController().addBlock(110,400);
                    break;
                    case Input.Keys.NUM_8:
                        gameWorld.getBlockController().addBlock(120,400);
                    break;
                    case Input.Keys.NUM_9:
                        gameWorld.getBlockController().addBlock(130,400);
                    break;

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT :
                waterElement.stopWalk();
                gameWorld.setLeftButtonPressed(false);
                break;
            case Input.Keys.RIGHT :
                waterElement.stopWalk();
                gameWorld.setRightButtonPressed(false);
                break;
            case Input.Keys.C :
                gameWorld.setButtonPushPressed(false);
                waterElement.idle();
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
    public boolean touchDown(float screenX, float screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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

}

