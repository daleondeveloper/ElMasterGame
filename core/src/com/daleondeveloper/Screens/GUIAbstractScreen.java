package com.daleondeveloper.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.tools.AudioManager;


public abstract class GUIAbstractScreen extends AbstractScreen {
    private static final String TAG = GUIAbstractScreen.class.getName();

    // GUI state
    protected enum GUIScreenState {
        PAUSED, RUNNING, STOPPED
    }
    protected GUIScreenState guiScreenState;

    protected OrthographicCamera guiCamera;
    protected Viewport guiViewport;
    protected Stage stage;

    public GUIAbstractScreen(ElMaster game) {
        super(game);
        guiScreenState = GUIScreenState.RUNNING;
        guiCamera = new OrthographicCamera();
        guiViewport = new ExtendViewport(ElMaster.APPLICATION_WIDTH, ElMaster.APPLICATION_HEIGHT, guiCamera);

        /* Internally calls guiViewport.update() (see {@link .game.GameWorld} and
         * this.resize(int width, int height)) */
        stage = new Stage(guiViewport, game.getGuiBatch());
    }
    public boolean isPlayScreenStateRunning() {
        return guiScreenState == GUIScreenState.RUNNING;
    }

    @Override
    public void applyViewport() {
        stage.getViewport().apply();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }

    @Override
    public void render(float deltaTime) {
        // Update logic
        update(deltaTime);

        // Render logic
        render();
    }

    private void update(float deltaTime) {
        if (guiScreenState == GUIScreenState.RUNNING) {
            handleInput(deltaTime);
            updateLogic(deltaTime);
        }
    }

    private void handleInput(float deltaTime) {
        // BACK button (Android) or ESCAPE key (desktop/html)
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            goBack();
        }
    }

    private void render() {
        clearScr();
        renderLogic();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void stop() {
        guiScreenState = GUIScreenState.STOPPED;
    }

    @Override
    public void pause() {
        AudioManager.getInstance().pauseMusic();
        guiScreenState = GUIScreenState.PAUSED;
    }

    @Override
    public void resume() {
        AudioManager.getInstance().resumeMusic();
        guiScreenState = GUIScreenState.RUNNING;
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose () {
        hide();
    }

    protected abstract void updateLogic(float deltaTime);
    protected abstract void renderLogic();
    protected abstract void goBack();

    public void setStatePaused(){
        guiScreenState = GUIScreenState.PAUSED;
    }
    public void setStateRunning(){
        guiScreenState = GUIScreenState.RUNNING;
    }
}
