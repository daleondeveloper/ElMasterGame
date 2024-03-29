package com.daleondeveloper.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameCamera {
    private static final String TAG = GameCamera.class.getName();

    public static final float PPM = 2.1f;

    private OrthographicCamera camera;
    private Viewport viewPort;
    private float width;
    private float height;

    public GameCamera(){

        float width  = ElMaster.APPLICATION_WIDTH/PPM;
        float height = ElMaster.APPLICATION_HEIGHT/PPM;

        this.width = width;
        this.height = height;

        camera = new OrthographicCamera();
        viewPort = new ExtendViewport(width,height,camera);

        int cameraWidth = Gdx.graphics.getWidth();
        int cameraHeight = Gdx.graphics.getHeight();

        viewPort.update(cameraWidth,cameraHeight,true);
        viewPort.getLeftGutterWidth();


    }

    public Viewport setFitViewPort(){
        viewPort.setCamera(null);
        viewPort = new FitViewport(width,height,camera);

        int cameraWidth = Gdx.graphics.getWidth();
        int cameraHeight = Gdx.graphics.getHeight();

        viewPort.update(cameraWidth,cameraHeight,true);

        return viewPort;

    }
    public Viewport setScreenViewport(){
        viewPort.setCamera(null);
        viewPort = new ScreenViewport(camera);

        int cameraWidth = Gdx.graphics.getWidth();
        int cameraHeight = Gdx.graphics.getHeight();

        viewPort.update(cameraWidth,cameraHeight,true);
        return viewPort;
    }
    public void update(float deltaTime){

    }
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        camera.position.set(100,200,0);

        viewPort.update(width, height, false);
    }

    public float getWorldWidth() {
        return viewPort.getWorldWidth();
    }

    public float getWorldHeight() {
        return viewPort.getWorldHeight();
    }

    public OrthographicCamera getCamera() {
        return camera;
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
