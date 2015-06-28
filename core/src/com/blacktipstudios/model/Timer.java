package com.blacktipstudios.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blacktipstudios.game.Game;

public class Timer {

    private double time;
    private double timeLimit;
    private boolean done;
    private boolean start;
    private BitmapFont font;

    public Timer(double timeLimit) {
        this.timeLimit = timeLimit;
        font = new BitmapFont();
        //font.;
        done = false;
        start = false;
    }

    public void update() {
        if(!done && start) {
            time += Gdx.graphics.getDeltaTime();
            if(time >= timeLimit) {
                done = true;
                time = 0;
                start = false;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        font.draw(batch, "" + ((int) time), 50, Game.WORLD_HEIGHT - 50);
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(double timeLimit) {
        this.timeLimit = timeLimit;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void start() {
        start = true;
    }
}
