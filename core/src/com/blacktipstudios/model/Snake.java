package com.blacktipstudios.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<Cart> carts;
    private int direction;
    private boolean alive;
    private boolean invincible;

    public Snake() {
        direction = 90;
        alive = true;
        carts = new ArrayList<Cart>();
        carts.add(new Cart(11, 13, Type.Front));
        carts.add(new Cart(10, 13, Type.Back));
    }

    public void advance() {
        //advance head coordinate
        Vector2[] locations = new Vector2[carts.size()];
        int x = carts.get(0).getX();
        int y = carts.get(0).getY();
        switch (direction) {
            case 0:
                y++;
                break;
            case 90:
                x++;
                break;
            case 180:
                y--;
                break;
            case 270:
                x--;
                break;
        }
        if(invincible) {
            if(x < 0) {
                x = World.WIDTH;
            } else if(x > World.WIDTH - 1) {
                x = 0;
            } else if(y <= 0) {
                y = World.HEIGHT - 2;
            } else if(y > World.HEIGHT - 2) {
                y = 0;
            }
        }
        locations[0] = new Vector2(x, y);

        //advance rest of snake body
        for(int i = carts.size() - 1; i > 0; i--) {
            x = carts.get(i - 1).getX();
            y = carts.get(i - 1).getY();
            locations[i] = new Vector2(x, y);
        }
        for(int i = 0; i < carts.size(); i++) {
            Cart cart = carts.get(i);
            cart.setX((int) locations[i].x);
            cart.setY((int) locations[i].y);
        }

        //scale front cart because of weird dimensions
        Cart front = carts.get(0);
        if(direction == 180 || direction == 0) {
            front.setDirection(direction + 90);
            if(direction == 0) front.setyOffSet(-4f);
            else front.setyOffSet(4f);
        }
        else  {
            front.setDirection(direction - 90);
            front.setxOffSet(0);
            front.setyOffSet(0);
        }
        //adjust direction
        if(direction == 0 || direction == 180) front.setScaleY(.9f);
        else front.setScaleY(1);
    }

    public void grow(Type type) {
        Cart back = carts.get(carts.size() - 1);
        int x = back.getX();
        int y = back.getY();
        advance();
        carts.add(carts.size() - 1, new Cart(back.getX(), back.getY(), type));
        back.setX(x);
        back.setY(y);


    }

    public void draw(SpriteBatch batch) {
        for(int i = carts.size() - 1; i >= 0; i--) {
            carts.get(i).draw(batch);
        }
    }

   /***************************************************
    ************* GETTERS AND SETTERS *****************
    ***************************************************/

    public void setDirection(int direction) {
        Cart behindHead = carts.get(1);
        Cart head = carts.get(0);
        boolean setDirection = false;
        switch (direction) {
            case 0:
                if(head.getY() >= behindHead.getY());
                setDirection = true;
                break;
            case 90:
                if(head.getX() >= behindHead.getX())
                    setDirection = true;
                break;
            case 180:
                if(head.getY() <= behindHead.getY())
                    setDirection = true;
                break;
            case 270:
                if(head.getX() <= behindHead.getX())
                    setDirection = true;
                break;

        }
        if(Math.abs(direction - this.direction) != 180 && setDirection) {
            this.direction = direction;
        }
    }

    public int getDirection() {
        return direction;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }
}
