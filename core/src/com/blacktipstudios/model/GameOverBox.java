package com.blacktipstudios.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blacktipstudios.game.Game;
import com.blacktipstudios.resources.Assets;
import com.blacktipstudios.resources.Scores;

public class GameOverBox {

    private Texture gameOver;
    private int countTimeSwitch;
    private int index;
    private float x;
    private float y;
    private BitmapFont font;
    private Score score;

    public GameOverBox(Score score) {
        gameOver = Assets.gameOver1;
        x = Game.WORLD_WIDTH / 2 - gameOver.getWidth() / 2;
        y = -gameOver.getHeight();
        font = Assets.helveticaWhite;
        this.score = score;
    }

    public void update() {
       countTimeSwitch++;
       if(countTimeSwitch > 30) {
           countTimeSwitch = 0;
           if(index == 0) {
               gameOver = Assets.gameOver2;
               index = 1;
           } else if(index == 1) {
               gameOver = Assets.gameOver1;
               index = 0;
           }
       }
       if(y < 200) {
           y += 40;
       }
    }

    public void draw(SpriteBatch batch) {
        update();
        batch.draw(gameOver, x, y);
        font.draw(batch, "Your Score: " + score.getScore(), x + 50, y + 450);
        font.draw(batch, "Best Score: " + Scores.getScore(0), x + 50, y + 300);
    }

    public void reset() {
        y = -gameOver.getHeight();
    }
}
