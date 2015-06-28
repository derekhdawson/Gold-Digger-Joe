package com.blacktipstudios.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.blacktipstudios.resources.Assets;

public class Diamond {

    private Animation animation;
    private Texture spriteSheet;
    private float cumTime;

    private int x;
    private int y;
    private boolean active;

    public Diamond() {
        spriteSheet = Assets.diamond;
        TextureRegion[][] regions = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 20,
                spriteSheet.getHeight());
        animation = new Animation(1 / 30f, regions[0]);
        active = false;
    }

    public void draw(SpriteBatch batch) {
        if(active) {
            cumTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = animation.getKeyFrame(cumTime, true);
            batch.draw(currentFrame, x * Cart.WIDTH, y * Cart.HEIGHT);
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
