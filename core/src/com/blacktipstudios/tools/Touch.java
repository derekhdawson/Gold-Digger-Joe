package com.blacktipstudios.tools;

import com.badlogic.gdx.InputAdapter;

/**
 * Created by derekdawson on 1/25/15.
 */
public class Touch extends InputAdapter {

    private boolean touchedDown;
    private boolean touchedUp;
    private float x;
    private float y;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchedDown = true;
        touchedUp = false;
        this.x = screenX;
        this.y = screenY;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchedUp = true;
        touchedDown = false;
        this.x = screenX;
        this.y = screenY;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean isTouchedDown() {
        return touchedDown;
    }

    public boolean isTouchedUp() {
        return touchedUp;
    }

    public void setTouchedDown(boolean touchedDown) {
        this.touchedDown = touchedDown;
    }

    public void setTouchedUp(boolean touchedUp) {
        this.touchedUp = touchedUp;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
