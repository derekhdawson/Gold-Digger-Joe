package com.blacktipstudios.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.blacktipstudios.game.Game;
import com.blacktipstudios.resources.Assets;

public class TumbleWeed {

    private float groundLevel;
    private Texture texture;
    private float x;
    private float y;
    private float velX;
    private Sprite sprite;
    private float rotation;
    private float jumpHeight;
    private boolean jumping;
    private boolean active;

    public TumbleWeed() {
        texture = Assets.tumbleWeed;
        groundLevel = 60 * 24 + 12;
        x = -100;
        y = groundLevel;
        velX = 3.5f;
        sprite = new Sprite(texture);
        sprite.setX(x);
        sprite.setY(y);
        rotation = -8;
    }

    private void update() {
        if(MathUtils.random(0, 3000) == 0) {
            active = true;
        }
        if(!active) return;
        x += velX;
        if(y > groundLevel) {
            y--;
        } else {
            int rand = MathUtils.random(0, 200);
            if(rand == 0) {
                jumpHeight = 40;
                jumping = true;
            }
        }
        if(jumping) {
            y += 3;
            if(y >= groundLevel + jumpHeight) {
                jumping = false;
            }
        }
        if(x > Game.WORLD_WIDTH) {
            x = -100;
            active = false;
        }
        sprite.setX(x);
        sprite.setY(y);
        sprite.rotate(rotation);
    }

    public void draw(SpriteBatch batch) {
        update();
        sprite.draw(batch);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setGroundLevel(float groundLevel) {
        this.groundLevel = groundLevel;
        y = groundLevel;
    }
}
