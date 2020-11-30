package com.daleondeveloper.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameCamera {
    private static final String TAG = GameCamera.class.getName();

    public static final float PPM = 2.1f;

    private OrthographicCamera camera;
    private Viewport viewPort;

    public GameCamera(){

        float width  = ElMaster.APPLICATION_WIDTH/PPM;
        float height = ElMaster.APPLICATION_HEIGHT/PPM;

        camera = new OrthographicCamera();
        viewPort = new FitViewport(width,height,camera);

        int cameraWidth = Gdx.graphics.getWidth();
        int cameraHeight = Gdx.graphics.getHeight();

        viewPort.update(cameraWidth,cameraHeight,true);

    }

    public void update(float deltaTime){

    }
    public void resize(int width, int height) {


//        if((height / width) < 2){
//            width = height / 2;
//            viewPort.update(width,height,true);
//
//        } else if((height / width) > 2){
//            height = width * 2;
//            viewPort.update(width,height,true);
//            viewPort.setScreenY(Gdx.graphics.getHeight() - height / 2);
//        }else {
//            viewPort.update(width,height,true);
//
//        }
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
