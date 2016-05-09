package com.kevnguyen14.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kevnguyen14.game.duckgame;

/**
 * Created by Kevin on 10/14/2015.
 */
public class HUD {

    public Stage stage;
    public Table table;
    private Viewport viewport;

    private float time;
    private Integer distanceTimer;
    private Integer coinsCollected;

    Label distanceTimeLabel;
    Label distanceLabel;
    Label coinsCollectedLabel;
    Label coinsLabel;

    public HUD(SpriteBatch sb){

        time = 0;
        distanceTimer = 0;
        coinsCollected = 0;

        viewport = new FitViewport(duckgame.V_WIDTH, duckgame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        table = new Table();
        table.top();
        table.setFillParent(true);
        distanceTimeLabel = new Label(String.format("%06d", distanceTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        distanceLabel = new Label("Distance: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinsCollectedLabel = new Label(String.format("%06d", coinsCollected), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinsLabel = new Label("Coins: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(coinsLabel).expandX().padTop(10);
        table.add(coinsCollectedLabel).expandX().padTop(10);
        table.add(distanceLabel).expandX().padTop(10);
        table.add(distanceTimeLabel).expandX().padTop(10);
        stage.addActor(table);

    }

    public void update(float dt) {
        time += dt;
        if (time >= 0.2) {
            distanceTimer++;
            distanceTimeLabel.setText(String.format("%06d", distanceTimer));
            time = 0;
        }
    }

    public void addCoin(int value) {
        coinsCollected = value;
        coinsCollectedLabel.setText(String.format("%06d", coinsCollected));
    }

    public int getDistanceTimer() { return distanceTimer; }
}
