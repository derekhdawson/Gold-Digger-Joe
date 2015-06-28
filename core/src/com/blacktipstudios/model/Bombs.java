package com.blacktipstudios.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.blacktipstudios.resources.Assets;

public class Bombs {

    private Array<Bomb> bombs = new Array<Bomb>();

    public void addBomb(int x, int y) {
        bombs.add(new Bomb(x, y));
    }

    public void draw(SpriteBatch batch) {
        for(int i = 0; i < bombs.size; i++) {
            Bomb bomb = bombs.get(i);
            bomb.draw(batch);
        }
    }

    public Array<Bomb> getBombs() {
        return bombs;
    }

    public void setBombs(Array<Bomb> bombs) {
        this.bombs = bombs;
    }

    public Array<Vector2> getLocations() {
        Array<Vector2> locs = new Array<Vector2>();
        for(int i = 0; i < bombs.size; i++) {
            Bomb b = bombs.get(i);
            locs.add(new Vector2(b.getX(), b.getY()));
        }
        return locs;
    }
}

class Bomb {

    private int x;
    private int y;

    Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

   /***************************************************
    ************* GETTERS AND SETTERS *****************
    ***************************************************/

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

    public void draw(SpriteBatch batch) {
        batch.draw(Assets.bomb, x * Cart.WIDTH, y * Cart.HEIGHT);
    }
}
