package com.kevnguyen14.game.entities;

/**
 * Created by Kevin on 11/2/2015.
 */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.kevnguyen14.game.duckgame;

public class Zombie extends B2DSprite{

    public Zombie(Body body) {

        super(body);

        Texture tex = duckgame.res.getTexture("zombie");
        TextureRegion[] sprites = TextureRegion.split(tex, 28, 35)[0];

        setAnimation(sprites, 1/4f);
    }
}