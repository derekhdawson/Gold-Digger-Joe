package com.blacktipstudios.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blacktipstudios.resources.Assets;

public class Item {

    private int x;
    private int y;
    private Type type;

    public void draw(SpriteBatch batch) {
        switch (type) {
            case Coal:
                batch.draw(Assets.coalPiece, x * Cart.WIDTH, y * Cart.HEIGHT);
                break;
            case Silver:
                batch.draw(Assets.silverPiece, x * Cart.WIDTH, y * Cart.HEIGHT);
                break;
            case Gold:
                batch.draw(Assets.goldPiece, x * Cart.WIDTH, y * Cart.HEIGHT);
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

}
