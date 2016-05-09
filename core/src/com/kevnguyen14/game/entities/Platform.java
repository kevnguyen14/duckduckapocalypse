package com.kevnguyen14.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.kevnguyen14.game.duckgame;

/**
 * Created by Kevin on 10/14/2015.
 */
public class Platform extends B2DSprite{

    public Platform(Body body, int i) {

        super(body);
        grass(i);


    }

    public void grass(int i) {

        Texture tex = duckgame.res.getTexture("grass10");
        TextureRegion[] sprites = TextureRegion.split(tex, (32 * i), 64)[0];

        setAnimation(sprites, 1f);

    }
}