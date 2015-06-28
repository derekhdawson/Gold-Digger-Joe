package com.blacktipstudios.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.blacktipstudios.resources.Assets;

public class Cart {

    public static int WIDTH = 54;
    public static int HEIGHT = 60;

    private int x;
    private int y;
    private int direction;
    private float scaleX;
    private float scaleY;
    private float xOffSet;
    private float yOffSet;

    private TextureRegion texture;
    private Type type;

    public Cart(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.scaleX = 1;
        this.scaleY = 1;
        switch (type) {
            case Coal:
                texture = Assets.coalCart;
                break;
            case Silver:
                texture = Assets.silverCart;
                break;
            case Gold:
                texture = Assets.goldCart;
                break;
            case Front:
                texture = Assets.frontCart;
                break;
            case Back:
                texture = Assets.backCart;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getxOffSet() {
        return xOffSet;
    }

    public void setxOffSet(float xOffSet) {
        this.xOffSet = xOffSet;
    }

    public float getyOffSet() {
        return yOffSet;
    }

    public void setyOffSet(float yOffSet) {
        this.yOffSet = yOffSet;
    }

    private void update() {
    }

    public void draw(SpriteBatch batch) {
        update();
        batch.draw(texture, x * WIDTH + xOffSet, y * HEIGHT + yOffSet,
                WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, scaleX, scaleY, direction);
    }
}
