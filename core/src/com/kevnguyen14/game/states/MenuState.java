package com.kevnguyen14.game.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.kevnguyen14.game.duckgame;

import java.util.Vector;


public class MenuState extends GameState {
    private final String title = "Duck Runner";
    private Texture background;
    private OrthographicCamera cam;
    private SpriteBatch sb;
    //private Sprite storeBtn;
    private Sprite playBtn;
    private Vector3 touchPos;
    //Table most likely


    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, duckgame.V_WIDTH / 2, duckgame.V_HEIGHT / 2);
        background = new Texture("images/background.png");
        playBtn = new Sprite(new Texture("images/playbtn.png"));
        //storeBtn = new Sprite(new Texture("images/open-sign.png"));
        sb = new SpriteBatch();
        touchPos = new Vector3();
    }

    @Override
    public void init() {
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        //sb.draw(storeBtn, 0, cam.position.y/8, 30, 20);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y/4,100,150);
        sb.end();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);//when screen is touched, the coordinates
            //are inserted into vector
            cam.unproject(touchPos);//calibrates the input to your camera's dimensions
            Rectangle playTextureBounds = new Rectangle(playBtn.getX(),playBtn.getY(),playBtn.getWidth(),playBtn.getHeight());
            if(playTextureBounds.contains(cam.position.x, cam.position.y)) {
                gsm.setState(GameStateManager.PLAY);
            }

        }//end isTouched() if


//        //WILL TEST LATER WHEN STORE IS MADE
//        if(Gdx.input.isTouched()) {
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);//when screen is touched, the coordinates
//            //are inserted into vector
//            cam.unproject(touchPos);//calibrates the input to your camera's dimensions
//            Rectangle storeTextureBounds = new Rectangle(storeBtn.getX(),storeBtn.getY(),storeBtn.getWidth(),storeBtn.getHeight());
//            if(storeTextureBounds.contains(cam.position.x,cam.position.y)) {
//                gsm.setState(GameStateManager.STORE);
//            }
//        }//end isTouched() if
    }//end handleInput()

//    public void select() {
//        //can be used to select other options
//        // write your own
//    }

    @Override
    public void dispose() {
        background.dispose();
        //storeBtn.getTexture().dispose();
        playBtn.getTexture().dispose();
    }
}
