package com.daleondeveloper.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameCamera {
    private static final String TAG = GameCamera.class.getName();

    public static final float PPM = 4.2f;

    private OrthographicCamera camera;
    private Viewport viewPort;

    public GameCamera(){

        float width  = ElMaster.APPLICATION_WIDTH/PPM;
        float height = ElMaster.APPLICATION_HEIGHT/PPM;

        camera = new OrthographicCamera();
        viewPort = new ExtendViewport(width,height,camera);

        viewPort.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
    }

    public void update(float deltaTime){

    }
    public void resize(int width, int height) {
        viewPort.update(width, height, false);
    }

    public float getWorldWidth() {
        return viewPort.getWorldWidth();
    }

    public float getWorldHeight() {
        return viewPort.getWorldHeight();
    }

    public Vector3 position() {
        return camera.position;
    }

    // Returns the combined projection and view matrix
    public Matrix4 getCombined() {
        return camera.combined;
    }

    public void applyViewport() {
        viewPort.apply();
    }
}
