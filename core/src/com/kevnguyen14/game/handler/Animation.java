package com.kevnguyen14.game.handler;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Kevin on 10/14/2015.
 */
public class Animation {

    private TextureRegion[] frames;
    private float time; // current time
    private float delay; // time between each frame
    private int currentFrame; // keeps track of which frame were on
    private int timesPlayed; // number of repeat animations

    public Animation() {}

    public Animation(TextureRegion[] frames){
        this(frames, 1 / 12f);
    }

    public Animation(TextureRegion[] frames, float delay) {
        setFrames(frames, delay);
    }

    public void setFrames(TextureRegion[] frames, float delay) {
        this.frames = frames;
        this.delay = delay;
        time = 0;
        currentFrame = 0;
        timesPlayed = 0;
    }

    public void update(float dt) {
        if(delay <=0) return;
        time += dt;
        while(time >= delay) {
            step();
        }
    }

    public void step() {
        time -= delay;
        currentFrame++;
        if(currentFrame == frames.length) {
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public TextureRegion getFrame() { return frames[currentFrame]; }
    public int getTimesPlayed() {return timesPlayed; }
    public float getTime() {return time; }

}
