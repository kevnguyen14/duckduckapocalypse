package com.kevnguyen14.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
//import com.tony.game.Duck;
//import com.tony.game.screens.PlayScreen;

import java.util.Random;

/**
 * Created by tony on 10/4/2015.
 */
public class Box extends Sprite {
    private BodyDef playerDef;
    public Body playerBody;
    private static final float WORLD_TO_BOX = 0.01f;

    public World world;private static final float BOX_TO_WORLD = 100f;
    private Vector2 pos;
    private Random rand;


    public Box(World world) {
        this.world = world;

    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(int a, int b) {
        playerDef.position.set(new Vector2(a * WORLD_TO_BOX, b * WORLD_TO_BOX));
    }

    public void update(float dt) {
        setPosition(playerBody.getPosition().x - getWidth() / 2, playerBody.getPosition().y - getHeight() / 2);
    }

    public void defineBox(int a, int b, int c, int d) {
        playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.set((a / 2)/ 100, (b / 2)/100);

        playerBody = world.createBody(playerDef);
        PolygonShape playerShape = new PolygonShape();

        //size and shape of the box
        playerShape.setAsBox(c * WORLD_TO_BOX, d * WORLD_TO_BOX);
        //playerShape.setAsBox(c/2/ Duck.PPM, d/ 2/Duck.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerShape;
        fixtureDef.density = 20f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0.6f;


        Fixture fixture = playerBody.createFixture(fixtureDef);

        playerShape.dispose();
    }

    public void motion(float a, float b) {
        playerBody.setLinearVelocity(a, b);
    }

    public Vector2 getP() {

        Vector2 myV = playerBody.getPosition();
        return myV;
    }




}
