package com.blacktipstudios.tools;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class KeyStroke extends InputAdapter {

    //snake starts at 90 degrees
    public static int direction = 90;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                direction = 0;
                break;
            case Input.Keys.RIGHT:
                direction = 90;
                break;
            case Input.Keys.DOWN:
                direction = 180;
                break;
            case Input.Keys.LEFT:
                direction = 270;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
}
