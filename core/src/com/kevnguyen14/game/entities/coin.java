package com.kevnguyen14.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.kevnguyen14.game.duckgame;

/**
 * Created by Kevin on 10/14/2015.
 */
public class coin extends B2DSprite{

    public coin(Body body) {

        super(body);

        Texture tex = duckgame.res.getTexture("coin");
        TextureRegion[] sprites = TextureRegion.split(tex, 16, 16)[0];

        setAnimation(sprites, 1 / 12f);
    }
}
