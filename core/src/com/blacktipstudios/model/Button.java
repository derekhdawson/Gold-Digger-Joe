package com.blacktipstudios.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blacktipstudios.resources.Assets;

public class Button {

    private Texture image;
    private Texture highlight;
    private boolean pressed;
    private float x;
    private float y;
    private float radius;


    public Button(Texture image) {
        this.image = image;
        highlight = Assets.buttonHighlight;
        this.radius = image.getWidth() / 2;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(image, x - radius, y - radius);
        if(pressed) {
            batch.draw(highlight, x - highlight.getWidth() / 2, y - highlight.getHeight() / 2);
        }
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

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean isTouched(float x, float y) {
        float disX = (this.x - x) * (this.x - x);
        float disY = (this.y - y) * (this.y - y);
        float dis = (float) Math.sqrt(disX + disY);
        return dis < radius;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
