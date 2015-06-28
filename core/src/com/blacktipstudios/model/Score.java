package com.blacktipstudios.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blacktipstudios.resources.Assets;

public class Score {

    private BitmapFont redFont;
    private GlyphLayout layout = new GlyphLayout();
    private float width;
    private float height;
    private int delay;

    private int score;
    private int displayScore;
    private boolean doublePoints;

    public Score(float width, float height) {
        this.width = width;
        this.height = height;
        score = 0;
        redFont = Assets.bangerFont_Red;
        layout.setText(redFont, score + "");
    }

    public void add(Type type) {
        switch (type) {
            case Coal:
                if(doublePoints) score += 10;
                else
                score += 5;
                break;
            case Silver:
                if(doublePoints) score += 20;
                else
                score += 10;
                break;
            case Gold:
                if(doublePoints) score += 40;
                else
                score += 20;
                break;
        }
    }

    private void update() {
        delay++;
        if(delay > 2) {
            delay = 0;
            if (displayScore < score) {
                displayScore++;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        update();
        redFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,
        Texture.TextureFilter.Linear);
        redFont.draw(batch, displayScore + "",
                width / 2 - (layout.width / 2), height - 260);
    }

    public void reset() {
        score = 0;
        displayScore = 0;
    }

    public int getScore() {
        return score;
    }

    public void dispose() {
        redFont.dispose();
    }

    public boolean isDoublePoints() {
        return doublePoints;
    }

    public void setDoublePoints(boolean doublePoints) {
        this.doublePoints = doublePoints;
    }
}
