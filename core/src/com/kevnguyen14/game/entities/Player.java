package com.kevnguyen14.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.kevnguyen14.game.duckgame;


/**
 * Created by Kevin on 10/14/2015.
 */
public class Player extends B2DSprite{

    private int numCoins;
    private int totalCoins;
    private int distanceScore;

    public Player(Body body) {

        super(body);
        run();
    }

    public void run() {

        Texture tex = duckgame.res.getTexture("duck");
        TextureRegion[] sprites = TextureRegion.split(tex, 32, 35)[0];

        setAnimation(sprites, 1 / 12f);
        //animation.setFrames(sprites, 1 / 12f);

    }

    public void jump() {
        Texture tex = duckgame.res.getTexture("jump");
        TextureRegion[] sprites = TextureRegion.split(tex, 42, 35)[0];

        setAnimation(sprites, 1 / 12f);
    }


    public void collectedCoin() {
        numCoins++;
    }
    public void distanceTime() { distanceScore++; }
    public int getDistanceScore() { return distanceScore; }
    public int getNumCoins() { return numCoins; }
    public void setTotalCoins(int i) { totalCoins = i; }
    public int getTotalCoins() { return totalCoins; }
}
