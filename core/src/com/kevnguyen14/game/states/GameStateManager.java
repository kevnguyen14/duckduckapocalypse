package com.kevnguyen14.game.states;

import com.kevnguyen14.game.states.GameState;
import com.kevnguyen14.game.states.MenuState;
import com.kevnguyen14.game.states.Play;


public class GameStateManager {

    private GameState gameState;

    //Add other states here
    public static final int MENU = 0;
    //public static final int STORE = 1;
    public static final int PLAY = 912837;

    public GameStateManager () {
        setState(MENU);
    }

    public void update(float dt){
        gameState.update(dt);
    }

    public void render() {
        gameState.render();
    }

    public void setState (int state) {
        //set other states here
        if(gameState != null)
            gameState.dispose();

        if (state == MENU) {
            //switch to menu state
            gameState = new MenuState(this);
        }
        if (state == PLAY) {
            //swith to play state
            gameState = new Play(this);
        }
    }
}
