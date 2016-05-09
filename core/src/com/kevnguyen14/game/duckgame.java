package com.kevnguyen14.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kevnguyen14.game.handler.Content;
import com.kevnguyen14.game.states.GameStateManager;
import com.kevnguyen14.game.handler.MyInputProcessor;


public class duckgame implements ApplicationListener {
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public static final String TITLE = "Duck Game";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;

	public static final float STEP = 1/60f;
	private float accum;

	public BitmapFont font;
	public static SpriteBatch sb;
	public static OrthographicCamera cam;
	public static OrthographicCamera hudCam;

	private GameStateManager gsm;

	public static Content res;
	public static IGoogleServices googleServices;

	//to be able to pass in google login from android
	public duckgame(IGoogleServices googleServices) {
		super();
		duckgame.googleServices = googleServices;
	}

	@Override
	public void create () {
		font = new BitmapFont();

		Gdx.input.setInputProcessor(new MyInputProcessor());

		//load stuff
		//move to atlas or some other resource manager
		res = new Content();
		res.loadTexture("images/duck.png", "duck");
		res.loadTexture("images/jump.png", "jump");
		res.loadTexture("images/coin.png", "coin");
		res.loadTexture("images/health.png", "health");
		res.loadTexture("images/grass10.png", "grass10");
		res.loadTexture("images/zombiewalk.png", "zombie");
		res.loadTexture("images/missiles.png", "missile");
		res.loadTexture("images/bg.png", "bg");

		res.loadSound("sfx/jump.wav");
		res.loadSound("sfx/crystal.wav");
		res.loadSound("sfx/levelselect.wav");
		res.loadSound("sfx/hit.wav");
		res.loadSound("sfx/changeblock.wav");

		res.loadMusic("music/delfino.mp3");


		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		cam.update();
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		sb = new SpriteBatch();
		gsm = new GameStateManager();
	}

	@Override
	public void render () {
		Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
	}

	public void dispose() {
		res.removeAll();
		sb.dispose();
		font.dispose();

	}
}

