package com.blacktipstudios.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.blacktipstudios.resources.Assets;
import com.blacktipstudios.resources.Settings;

import java.util.Random;

public class Explosions {

    enum ExplosionType {
        Linear, Random, Slow
    }

    public static ExplosionType getExplosionType() {
        int n = MathUtils.random(0, 2);
        if(n == 0) return ExplosionType.Linear;
        else if(n == 1) return ExplosionType.Random;
        else return ExplosionType.Slow;
    }

    private Array<Explosion> explosions;

    public Explosions() {
        explosions = new Array<Explosion>();
    }


    public void draw(SpriteBatch batch) {
        for (int i = 0; i < explosions.size; i++) {
            Explosion explosion = explosions.get(i);
            explosion.draw(batch);
        }
    }

    //returns whether there is an early explosion
    //used for camera to know when to shake
    public boolean wasRecentExplosion() {
        for(int i = 0; i < explosions.size; i++) {
            Explosion exp = explosions.get(i);
            if(exp.explosionWentOff()) {
                return true;
            }
        }
        return false;
    }

    public Array<Explosion> getExplosions() {
        return explosions;
    }

    //adds at end
    public void addExplosion(Type type) {
        switch (type) {
            case Coal:
                explosions.add(new Explosion(Type.Coal));
                break;
            case Silver:
                explosions.add(new Explosion(Type.Silver));
                break;
            case Gold:
                explosions.add(new Explosion(Type.Gold));
                break;
            case Front:
                explosions.add(new Explosion(Type.Front));
                break;
            case Back:
                explosions.add(new Explosion(Type.Back));
                break;
        }
    }

    //adds at certain index
    public void addExplosion(int index, Type type) {
        switch (type) {
            case Coal:
                explosions.insert(index, new Explosion(Type.Coal));
                break;
            case Silver:
                explosions.insert(index, new Explosion(Type.Silver));
                break;
            case Gold:
                explosions.insert(index, new Explosion(Type.Gold));
                break;
            case Front:
                explosions.insert(index, new Explosion(Type.Front));
                break;
            case Back:
                explosions.insert(index, new Explosion(Type.Back));
                break;
        }
    }

    public boolean doneExploding() {
        boolean done = true;
        for(int i = 0; i < explosions.size; i++) {
            Explosion explosion = explosions.get(i);
            if(!explosion.doneExploding()) done = false;
        }
        return done;
    }
}

class Explosion {

    private Animation animation;
    private Texture spriteSheet;
    private float cumTime;
    private float speed;
    private int index;

    private int x;
    private int y;
    private int explosionStartTime;
    private int explosionTime;
    private boolean start;
    private int rotation;
    private float scale;
    private boolean playSound;
    private TextureRegion[] sprites;



    public Explosion(Type type) {
        speed = 1f / (MathUtils.random(40, 60));
        rotation = new Random().nextInt(360) + 1;
        //scale = MathUtils.random(.1f, 2.5f);
        scale = MathUtils.random(.3f, .8f);
        playSound = true;
        int rows = 0;
        int cols = 0;
        switch (type) {
            case Coal:
                spriteSheet = Assets.coalExplosion;
                rows = 6; cols = 8;
                break;
            case Silver:
                spriteSheet = Assets.silverExplosion;
                rows = 5; cols = 8;
                break;
            case Gold:
                spriteSheet = Assets.goldExplosion;
                rows = 6; cols = 8;
                break;
            case Front:
                spriteSheet = Assets.frontExplosion;
                rows = 5; cols = 8;
                break;
            case Back:
                spriteSheet = Assets.backExplosion;
                rows = 4; cols = 8;
                break;
        }
        sprites = new TextureRegion[rows * cols];
        TextureRegion[][] temp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / cols,
                spriteSheet.getHeight() / rows);
        int index = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                sprites[index++] = temp[i][j];
            }
        }
        animation = new Animation(speed, sprites);
    }

    private void update() {
        explosionTime++;
        if(explosionTime >= explosionStartTime)
            start = true;
    }

    public void draw(SpriteBatch batch) {
        update();
        if(start) {
            cumTime += Gdx.graphics.getDeltaTime();
            if(playSound && Settings.isSoundOn()) {
                playSound = false;
                int n = new Random().nextInt(3);
                switch (n) {
                    case 0:
                        Assets.explosion1.play();
                        break;
                    case 1:
                        Assets.explosion2.play();
                        break;
                    case 2:
                        Assets.explosion3.play();
                        break;
                }
            }
        }
        TextureRegion currentFrame = animation.getKeyFrame(cumTime);
        index = animation.getKeyFrameIndex(cumTime);
        //sprite sheet is a little off
            float originX = currentFrame.getRegionWidth() / 2;
            float originY = currentFrame.getRegionHeight() / 2;
            float xPos = x - (currentFrame.getRegionWidth() / 2) + 40;
            float yPos = y - (currentFrame.getRegionHeight() / 2) + 26;
            batch.draw(currentFrame, xPos, yPos, originX, originY,
                    currentFrame.getRegionWidth(), currentFrame.getRegionHeight(),
                    scale * 2, scale * 2, rotation);
    }

    /***************************************************
     ************* GETTERS AND SETTERS *****************
     ***************************************************/

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Texture getSpriteSheet() {
        return spriteSheet;
    }

    public void setSpriteSheet(Texture spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public float getCumTime() {
        return cumTime;
    }

    public void setCumTime(float cumTime) {
        this.cumTime = cumTime;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        animation = new Animation(speed, sprites);
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

    public int getExplosionStartTime() {
        return explosionStartTime;
    }

    public void setExplosionStartTime(int explosionStartTime) {
        this.explosionStartTime = explosionStartTime;
    }

    public int getExplosionTime() {
        return explosionTime;
    }

    public void setExplosionTime(int explosionTime) {
        this.explosionTime = explosionTime;
    }

    public boolean isStarted() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean explosionWentOff() {
        return animation.getKeyFrameIndex(cumTime) > 3 && animation.getKeyFrameIndex(cumTime) < 10;
    }

    public boolean explosionJustWentOff() {
        return animation.getKeyFrameIndex(cumTime) > 2;
    }

    public boolean doneExploding() {
        return animation.isAnimationFinished(cumTime);
    }


}
