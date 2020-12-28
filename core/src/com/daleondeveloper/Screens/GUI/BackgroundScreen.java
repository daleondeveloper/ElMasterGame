package com.daleondeveloper.Screens.GUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Game.ElMaster;
import com.daleondeveloper.Game.GameCamera;
import com.daleondeveloper.Screens.Play.PlayScreen;
import com.daleondeveloper.Screens.GUIOverlayAbstractScreen;


public class BackgroundScreen extends GUIOverlayAbstractScreen {
    public static final String TAG = BackgroundScreen.class.getName();

    private PlayScreen playScreen;
    private Camera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Assets assets;
    private Image background;
    private Image fog_dark;
    private Image fog_left;
    private Image fog_center;
    private Image fog_right;

    public BackgroundScreen(ElMaster game, PlayScreen playScreen) {
        super(game);

        this.playScreen = playScreen;
        assets = Assets.getInstance();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        float w = stage.getWidth(); // Same as stage.getViewport().getWorldWidth()
        float h = stage.getHeight();


        background.setHeight(h);
        background.setWidth(w*3);
        background.setPosition(w/2 - background.getWidth() /2, h/2 - background.getHeight()/2);
        fog_dark.setHeight(h * 0.87f);
        fog_dark.setWidth(w * 3);
        fog_dark.setPosition(w/2 - background.getWidth() /2, h/2 - background.getHeight()/2);

        fog_left.setWidth(w * 4.87f);
        fog_left.setHeight(h);

        fog_center.setWidth(w * 4.875f);
        fog_center.setHeight(h);

        fog_right.setWidth(w * 4.7f);
        fog_right.setHeight(h);
    }

    @Override
    public void build() {
        background = new Image(assets.getAssetBackground().getBackground());
        fog_dark = new Image(assets.getAssetBackground().getFogDark());
        fog_left = new Image(assets.getAssetBackground().getFogLeft());
        fog_center = new Image(assets.getAssetBackground().getFogCenter());
        fog_right = new Image(assets.getAssetBackground().getFogRight());

        stage.addActor(background);

    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        float fog_force = fog_center.getWidth() * 0.001f;
        if(fog_center.getX() < 0 && fog_center.getX() > -fog_center.getWidth()){
            fog_center.setX(fog_center.getX() - fog_force );
            fog_right.setX(fog_center.getX() + fog_center.getWidth() - fog_force);
            fog_left.setX(fog_right.getX() + fog_right.getWidth() - fog_force);
        }else if(fog_right.getX() < 0 && fog_right.getX() > - fog_right.getWidth()){
            fog_right.setX(fog_right.getX() - fog_force);
            fog_left.setX(fog_right.getX() + fog_right.getWidth() - fog_force);
            fog_center.setX(fog_left.getX() + fog_left.getWidth() - fog_force);
        }else{
            fog_left.setX(fog_left.getX() - fog_force);
            fog_center.setX(fog_left.getX() + fog_left.getWidth() - fog_force);
            fog_right.setX(fog_center.getX() + fog_center.getWidth() - fog_force);
        }
        stage.clear();

        stage.addActor(background);
        stage.addActor(fog_left);
        stage.addActor(fog_center);
        stage.addActor(fog_right);
        stage.addActor(fog_dark);
    }

    @Override
    public void render() {
        SpriteBatch sp = new SpriteBatch();
     //   stage.setViewport(playScreen.getGameWorld().getGameCamera().setScreenViewport());
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//        batch.draw(assets.getAssetBackground().getFogLeft(),fog_left.getX(),fog_left.getY());
//        batch.end();
        stage.draw();
    }
}
