package com.kevnguyen14.game.states;

public abstract class GameState {
    protected GameStateManager gsm;
    protected GameState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }
    public abstract void init();
    public abstract void handleInput();
    public abstract void update (float dt);
    public abstract void render();
    public abstract void dispose();

}
