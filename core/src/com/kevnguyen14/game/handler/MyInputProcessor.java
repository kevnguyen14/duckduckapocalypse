package com.kevnguyen14.game.handler;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by Kevin on 10/13/2015.
 */
public class MyInputProcessor extends InputAdapter{

    public boolean keyDown(int k) {
        if(k == Input.Keys.DPAD_UP) {
            MyInput.setKey(MyInput.BUTTON1, true);
        }
        if(k == Input.Keys.DPAD_DOWN) {
            MyInput.setKey(MyInput.BUTTON2, true);
        }
        return true;
    }

    public boolean keyUp(int k) {
        if(k == Input.Keys.DPAD_UP) {
            MyInput.setKey(MyInput.BUTTON1, false);
        }
        if(k == Input.Keys.DPAD_DOWN) {
            MyInput.setKey(MyInput.BUTTON2, false);
        }
        return true;
    }
}
