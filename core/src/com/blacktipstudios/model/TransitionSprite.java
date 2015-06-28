package com.blacktipstudios.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by derekdawson on 1/25/15.
 */
public class TransitionSprite {

    private Texture texture;
    private float x;
    private float y;
    private boolean show;

    public TransitionSprite(Texture texture) {
        this.texture = texture;
        show = true;
        x = 0;
        y = 0;
    }

    private void update() {

    }

    public void draw(SpriteBatch batch) {
        if(show)
        batch.draw(texture, x, y);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
