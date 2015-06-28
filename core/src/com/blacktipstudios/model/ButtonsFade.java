package com.blacktipstudios.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blacktipstudios.game.Game;
import com.blacktipstudios.resources.Assets;
import com.blacktipstudios.resources.Settings;

public class ButtonsFade {

    private Sprite play, sound, scores;
    private Direction direction;
    private float alpha;

    public enum Direction {
        UP, DOWN
    }

    public ButtonsFade() {
        play = new Sprite(Assets.playButton);
        sound = new Sprite(Assets.soundOn);
        scores = new Sprite(Assets.highScoresButton);
        float x = play.getWidth() / 2;
        float y = play.getHeight() / 2;
        play.setPosition(Game.WORLD_WIDTH / 2 - x, Game.WORLD_HEIGHT / 2 - 250 - y);
        x = sound.getWidth() / 2;
        y = sound.getHeight() / 2;
        sound.setPosition(222 - x, 1276 - 250 - y);
        x = scores.getWidth() / 2;
        y = scores.getHeight() / 2;
        scores.setPosition(822 -x, 1286 - 250 - y);
        alpha = 1;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public void draw(SpriteBatch batch) {
        //could be performance buster
        boolean soundOn = Settings.isSoundOn();
        if(soundOn) {
            sound.setTexture(Assets.soundOn);
        } else {
            sound.setTexture(Assets.soundOff);
        }
        alpha -= 0.01f;
        int value = 0;
        if(direction == Direction.UP) value = -15;
        if(direction == Direction.DOWN) value = 15;
        play.setY(play.getY() + value);
        sound.setY(sound.getY() + value);
        scores.setY(scores.getY() + value);
        if(alpha <= 0) {
            play.setColor(1, 1, 1, 0);
            sound.setColor(1, 1, 1, 0);
            scores.setColor(1, 1, 1, 0);
        } else {
            play.setColor(1, 1, 1, alpha);
            sound.setColor(1, 1, 1, alpha);
            scores.setColor(1, 1, 1, alpha);
        }
        play.draw(batch);
        sound.draw(batch);
        scores.draw(batch);
    }


}
