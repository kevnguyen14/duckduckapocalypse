package com.kevnguyen14.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.kevnguyen14.game.duckgame;

/**
 * Created by Kevin on 11/16/2015.
 */

public class Missile extends B2DSprite{

    public Missile(Body body) {

        super(body);

        Texture tex = duckgame.res.getTexture("missile");
        TextureRegion[] sprites = TextureRegion.split(tex, 44, 24)[0];

        setAnimation(sprites, 1/12f);
    }
}