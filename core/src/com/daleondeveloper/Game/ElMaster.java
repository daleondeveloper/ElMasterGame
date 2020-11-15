package com.daleondeveloper.Game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.daleondeveloper.Assets.Assets;
import com.daleondeveloper.Screens.ScreenEnum;
import com.daleondeveloper.Screens.ScreenManager;
import com.daleondeveloper.Screens.ScreenTransitionEnum;
import com.rafaskoberg.gdx.typinglabel.TypingConfig;

public class ElMaster extends DirectedGame {
	private static final String TAG = ElMaster.class.getName();

	public static final int  APPLICATION_WIDTH = 420;
	public static final int APPLICATION_HEIGHT = 840;
	public static final int GAME_WORLD_CELL = 24;
	public static final String TITLE = "ElMaster";

	private SpriteBatch gameBatch;
	private SpriteBatch guiBatch;
	private ShapeRenderer gameShapeRenderer;
	private Box2DDebugRenderer box2DDebugRenderer;

	public ElMaster (){

	}

	@Override
	public void create () {
		//Debug
		if(DebugConstants.DEBUG_MODE){
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
		}else{
			Gdx.app.setLogLevel(Application.LOG_INFO);
			Gdx.app.log(TAG, "**** Debug messages not enabled (set DEBUG_MODE = true to enable them) ****");

		}

		//Load preferences and settings
		GameSettings.getInstance().load();

		//Set TypingConfig new line character interval multipler
		TypingConfig.INTERVAL_MULTIPLIERS_BY_CHAR.put('\n',0);

		//Constructs a new guiBAtch
		guiBatch = new SpriteBatch();

		//Constructs a new gameBatch
		gameBatch = new SpriteBatch();

		//Constructor a new GameShapeRender for debuging processing
		if(DebugConstants.DEBUG_LINES){
			gameShapeRenderer = new ShapeRenderer();
		}else{
			gameShapeRenderer = null;
		}

		//Constructs a new Box2DDebugRender for debuging process
		if(DebugConstants.DEBUG_BOX2D){
			box2DDebugRenderer = new Box2DDebugRenderer();
		}else{
			box2DDebugRenderer = null;
		}

		//Sets a splash Screen
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen(ScreenEnum.SPLASH, ScreenTransitionEnum.ROTATING,this);

	}

	public SpriteBatch getGameBatch() {
		return gameBatch;
	}

	public SpriteBatch getGuiBatch() {
		return guiBatch;
	}

	public ShapeRenderer getGameShapeRenderer() {
		return gameShapeRenderer;
	}

	public Box2DDebugRenderer getBox2DDebugRenderer() {
		return box2DDebugRenderer;
	}

	@Override
	public void dispose () {
		super.dispose();
		gameBatch.dispose();
		if (DebugConstants.DEBUG_LINES) {
			gameShapeRenderer.dispose();
		}
		if (DebugConstants.DEBUG_BOX2D) {
			box2DDebugRenderer.dispose();
		}
		Assets.getInstance().dispose();
		gameBatch = null;
		gameShapeRenderer = null;
		box2DDebugRenderer = null;
	}

}
