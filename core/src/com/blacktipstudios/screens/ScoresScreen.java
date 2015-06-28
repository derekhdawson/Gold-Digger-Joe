package com.blacktipstudios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.blacktipstudios.game.Game;
import com.blacktipstudios.model.Button;
import com.blacktipstudios.model.ButtonsFade;
import com.blacktipstudios.model.TransitionSprite;
import com.blacktipstudios.resources.Assets;
import com.blacktipstudios.resources.Scores;

public class ScoresScreen implements Screen {

    private Game game;
    private BitmapFont font;
    private SpriteBatch batch;
    private TransitionSprite mainMenu;
    private TransitionSprite scoresScreen;
    private Button button;
    private boolean doneTransition;
    private float alpha;
    private ButtonsFade fade;

    private Vector3 v = new Vector3();

    public ScoresScreen(Game game) {
        this.game = game;
        font = Assets.bangerFont_White;
        batch = game.batch;
        mainMenu = new TransitionSprite(Assets.tempBackground);
        scoresScreen = new TransitionSprite(Assets.scoreScreen);
        scoresScreen.setY(1920);
        doneTransition = false;
        button = new Button(Assets.backButton);
        button.setX(Game.WORLD_WIDTH / 2);
        button.setY(170);
        alpha = 1f;
        fade = new ButtonsFade();
        fade.setDirection(ButtonsFade.Direction.UP);
        game.ads.hide();
    }


    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setColor(1, 1, 1, 1);
        batch.begin();
        mainMenu.draw(batch);
        scoresScreen.draw(batch);
        if(doneTransition) {
            for (int i = 0; i < 5; i++) {
                int score = Scores.getScore(i);
                if (score > 0) {
                    font.draw(batch, "         " +  Scores.getScore(i), 100,
                            (Game.WORLD_HEIGHT - 425) - (i * 230));
                }
            }
            button.draw(batch);
        }

        batch.end();
        batch.begin();
        if(alpha <= 0) {
            batch.setColor(1, 1, 1, 0);
        } else {
            batch.setColor(1, 1, 1, alpha);
        }
        batch.draw(Assets.title, 58.0f, 1324.0f + mainMenu.getY());
        fade.draw(batch);
        batch.end();
    }

    private void update() {
        alpha -= 0.01f;
        if(mainMenu.getY() > -1920)
        mainMenu.setY(mainMenu.getY() - 15);
        if(scoresScreen.getY() > 0) {
            scoresScreen.setY(scoresScreen.getY() - 15);
        } else {
            doneTransition = true;
        }
        Vector3 v = new Vector3(game.touch.getX(), game.touch.getY(), 0);
        game.camera.unproject(v);
        if(game.touch.isTouchedDown()) {
            if(button.isTouched(v.x, v.y)) {
                button.setPressed(true);
            }
        } else if(game.touch.isTouchedUp()) {
            if(button.isTouched(v.x, v.y)) {
                game.setScreen(new MainMenu(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
        game.camera.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        game.dispose();
        game.disposeAssets();
        game.batch.dispose();
        font.dispose();
    }
}