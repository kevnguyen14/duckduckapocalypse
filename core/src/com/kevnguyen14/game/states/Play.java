package com.kevnguyen14.game.states;

import static com.kevnguyen14.game.handler.B2DVars.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kevnguyen14.game.duckgame;
import com.kevnguyen14.game.entities.*;
import com.kevnguyen14.game.handler.B2DVars;
import com.kevnguyen14.game.handler.MyContactListener;
import com.kevnguyen14.game.handler.MyInput;


/**
 * TODO
 *
 * Need to generate plantforms and coins in smaller increments.
 * Currently, Android can't handle fixed number of objects in
 * large quantaties.
 */


import java.util.Random;

/**
 * Created by Kevin on 10/13/2015.
 */
public class Play extends GameState {



    private boolean debug = false;
    private World world;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera b2dCam;
    private MyContactListener cl;
    private Player player;
    private Zombie zombie;
    private Missile missile;
    private Array<coin> coins;
    private Array<Platform> platform;
    private HUD hud;
    private Random rand;
    private static final int coinMininumY = 55;
    private Texture background;


    public Play(GameStateManager gsm) {
        super(gsm);

        duckgame.res.getMusic("delfino").setLooping(true);
        //duckgame.res.getMusic("delfino").setVolume(0.5f);
        duckgame.res.getMusic("delfino").play();

        // set up box2d stuff
        world = new World(new Vector2(0, -9.81f), true);
        cl = new MyContactListener();
        world.setContactListener(cl);
        b2dr = new Box2DDebugRenderer();
        background = new Texture("images/bg.png");

        // create player
        createPlayer();

        // create platforms
        createPlatforms();

        // create zombies
        createZombies();

        // create missiles
        createMissiles();

        // create coins
        createCoins();

        // set up box2d cam
        b2dCam = new OrthographicCamera();

        b2dCam.setToOrtho(false, duckgame.V_WIDTH / PPM, duckgame.V_HEIGHT / PPM);

        //create hud
        hud = new HUD(duckgame.sb);
    }

    @Override
    public void handleInput() {

        // player jump
        if(MyInput.isPressed(MyInput.BUTTON1)) {
            playerJump();
        }

        if(Gdx.input.justTouched()) {
            if(Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
                playerShortJump();
            }
            else {
                playerJump();
            }
        }

    }

    @Override
    public void update(float dt) {

        handleInput(); //first thing in every update I think

        world.step(duckgame.STEP, 6, 2); //can be 1 and 1

        duckgame.cam.position.set(
                player.getBody().getPosition().x * PPM + duckgame.V_WIDTH / 4,
                duckgame.V_HEIGHT / 2, 0
        );
        player.update(dt);
        zombie.update(dt);
        missile.update(dt);
        duckgame.cam.update();

        // remove coins
        Array<Body> bodies = cl.getBodiesToRemove();
        for(int i = 0; i < bodies.size; i++) {
            Body b = bodies.get(i);
            //coins.removeValue((coin) b.getUserData(), true);
            player.collectedCoin();
            //world.destroyBody(b);
            hud.addCoin(player.getNumCoins());
            duckgame.res.getSound("crystal").play();
            float y = coinMininumY + rand.nextInt(100);
            b.setTransform(1500 / PPM, y / PPM, 0);
        }
        bodies.clear();

        //reposition coins that go past player
        for(int i = 0; i < coins.size; i++) {
            if(player.getBody().getPosition().x > (coins.get(i).getBody().getPosition().x + (200 / PPM))) {
                float y = coinMininumY + rand.nextInt(100);
                coins.get(i).getBody().setTransform(1500/PPM, y/PPM, 0);
            }
        }

        //reposition platforms
        for(int i = 0; i < platform.size; i++) {
            if(player.getBody().getPosition().x >= (platform.get(i).getBody().getPosition().x + (400 / PPM))) {
                platform.get(i).getBody().setTransform((300/PPM) + (400/PPM), 0, 0);
            }
        }


        //reposition zombie
        while(player.getBody().getPosition().x > (zombie.getBody().getPosition().x + (200 / PPM))) {
            float x = rand.nextInt(1000)+600;
            zombie.getBody().setTransform(x/PPM, 45/PPM, 0);
        }

        //reposition missile
        while(player.getBody().getPosition().x > (missile.getBody().getPosition().x + (200 / PPM))) {
            float x = rand.nextInt(1000)+800;
            float y = rand.nextInt(100)+60;
            missile.getBody().setTransform(x/PPM, y/PPM, 0);
        }


        hud.update(dt);
       // Gdx.app.log("current score", hud.getDistanceTimer() + "");
        // check player failed


        if (player.getBody().getPosition().y < 0) {
            duckgame.res.getSound("hit").play();
            duckgame.res.getMusic("bbsong").stop();
            //Gdx.app.log("death timer" , hud.getDistanceTimer()+"");
            gsm.setState(GameStateManager.MENU);
            duckgame.cam.translate(0,0);
        }
        if(player.getBody().getLinearVelocity().x < 0f) {
            duckgame.res.getSound("hit").play();
            duckgame.res.getMusic("delfino").stop();
             //submit score
            duckgame.googleServices.submitScore(hud.getDistanceTimer());
            duckgame.googleServices.showScores();
            gsm.setState(GameStateManager.MENU);
        }

        // update coins
        for(int i = 0; i < coins.size; i++) {
            coins.get(i).update(dt);
        }

        // update platforms
        for(int i = 0; i < platform.size; i++) {
            platform.get(i).update(dt);
        }
        // Gdx.app.log("COUNT", count + " ");

    }

    @Override
    public void init() {
        //can move init stuff here but...
    }

    @Override
    public void render() {
        // clear screen
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        duckgame.sb.setProjectionMatrix(duckgame.cam.combined);

        duckgame.sb.begin();
        duckgame.sb.draw(background, 0, 0);
        duckgame.sb.end();

        player.render(duckgame.sb);
        zombie.render(duckgame.sb);
        missile.render(duckgame.sb);
        b2dCam.update();
        duckgame.cam.update();

        // draw platforms
        for(int i = 0; i < platform.size; i++) {
            platform.get(i).render(duckgame.sb);
        }

        // draw coins
        for(int i = 0; i < coins.size; i++) {
            coins.get(i).render(duckgame.sb);
        }

        // draw box2D world
        if(debug) {
            b2dr.render(world, b2dCam.combined);
        }

        // draw HUD
        duckgame.sb.setProjectionMatrix(duckgame.hudCam.combined);
        hud.stage.draw();

    }

    @Override
    public void dispose() {

    }

    public void createPlayer() {

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // create player
        bdef.position.set(300  / PPM, 50 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;


        //bdef.linearVelocity.set(2f, 0);


        Body body = world.createBody(bdef);

        shape.setAsBox(13 / PPM, 15 / PPM);

        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_GRASS | B2DVars.BIT_COIN | B2DVars.BIT_ZOMBIE | B2DVars.BIT_MISSILE;
        body.createFixture(fdef).setUserData("player");

        // create foot sensor
        shape.setAsBox(13 / PPM, 3 /PPM, new Vector2(0, -15 / PPM), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_GRASS;
        // foot will sense ground and go through it a bit
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");

        // create player

        player = new Player(body);

        body.setUserData(player);

        shape.dispose();

    }

    private void createCoins() {
        float x = 0, y;

        coins = new Array<coin>();

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.linearVelocity.set(-1.5f, 0);
        FixtureDef fdef = new FixtureDef();

        int k= 0;
        while (k<20)
        {
            rand = new Random();
            x += rand.nextInt(640)+160;
            y = coinMininumY + rand.nextInt(100);

            int i=rand.nextInt(6)+1;
            int l = 0;
            while (l<i)
            {
                bdef.position.set(x / PPM, y / PPM);
                CircleShape cshape = new CircleShape();
                cshape.setRadius(8 / PPM);

                fdef.shape = cshape;
                fdef.isSensor = true;
                fdef.filter.categoryBits = B2DVars.BIT_COIN;
                fdef.filter.maskBits = B2DVars.BIT_PLAYER;

                Body body = world.createBody(bdef);
                body.createFixture(fdef).setUserData("coin");
                x+=16;

                coin c = new coin(body);
                coins.add(c);

                body.setUserData(c);
                l++;
                cshape.dispose();
            }
            k++;
        }

    }

    private void createPlatforms() {

        platform = new Array<Platform>();

        float y = 32, xrange = 400;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.position.set(300 / PPM , 0 );
        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.linearVelocity.set(-1.5f, 0);
        Body body = world.createBody(bdef);

        shape.setAsBox(xrange / PPM, y / PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_GRASS;
        fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ZOMBIE;
        fdef.friction = 0;
        body.createFixture(fdef).setUserData("grass");

        Platform p = new Platform(body, 26);
        platform.add(p);
        body.setUserData(p);

        bdef.position.set((300/PPM) + (xrange/PPM), 0);
        bdef.linearVelocity.set(-1.5f, 0);
        Body body2 = world.createBody(bdef);

        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_GRASS;
        fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ZOMBIE;
        fdef.friction = 0;
        body2.createFixture(fdef).setUserData("grass");

        Platform p2 = new Platform(body2, 26);
        platform.add(p2);
        body.setUserData(p2);

        shape.dispose();

    }

    private void createZombies() {

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // create zombie
        Random rand = new Random();
        float x = rand.nextInt(1000)+600;
        bdef.position.set(x / PPM, 45 / PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;

        bdef.linearVelocity.set(-2f, 0);

        Body body = world.createBody(bdef);

        shape.setAsBox(12 / PPM, 15 / PPM);

        fdef.shape = shape;
        fdef.isSensor = false;
        fdef.filter.categoryBits = B2DVars.BIT_ZOMBIE;
        fdef.filter.maskBits = B2DVars.BIT_PLAYER;
        body.createFixture(fdef).setUserData("zombie");

        // create zombie
        zombie = new Zombie(body);

        body.setUserData(zombie);

        shape.dispose();

    }

    private void createMissiles() {

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // create missile
        Random rand = new Random();
        float x = rand.nextInt(1200)+800;
        float y = rand.nextInt(100)+94;
        bdef.position.set(x/PPM, y/PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;

        bdef.linearVelocity.set(-2.5f, 0);

        Body body = world.createBody(bdef);

        shape.setAsBox(17 / PPM, 8 / PPM);

        fdef.shape = shape;
        fdef.isSensor = false;
        fdef.filter.categoryBits = B2DVars.BIT_MISSILE;
        fdef.filter.maskBits = B2DVars.BIT_PLAYER;
        body.createFixture(fdef).setUserData("missile");

        // create missile
        missile = new Missile(body);

        body.setUserData(missile);

        shape.dispose();

    }

    private void playerJump() {
        if (cl.isPlayerOnGround()) {
            player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
            player.getBody().applyForceToCenter(0, 300, true);
            duckgame.res.getSound("jump").play();
        }
    }

    private void playerShortJump() {
        if(cl.isPlayerOnGround()) {
            player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
            player.getBody().applyForceToCenter(0, 200, true);
            duckgame.res.getSound("jump").play();
        }
    }

}
