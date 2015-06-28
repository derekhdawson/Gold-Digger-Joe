package com.blacktipstudios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blacktipstudios.game.Game;
import com.blacktipstudios.model.Button;
import com.blacktipstudios.model.StateButton;
import com.blacktipstudios.model.TumbleWeed;
import com.blacktipstudios.resources.Assets;
import com.blacktipstudios.resources.Settings;

public class MainMenu implements Screen {

    private Game game;
    private Viewport viewport;
    private SpriteBatch batch;
    private Camera camera;
    private Button play, scores;
    private StateButton sound;
    private TumbleWeed tumbleWeed;

    Logger logger;

    public MainMenu(Game game) {
        this.game = game;
        game.ads.load();
        viewport = game.viewport;
        batch = game.batch;
        camera = game.viewport.getCamera();
        camera.position.set(Game.WORLD_WIDTH / 2, Game.WORLD_HEIGHT / 2, 0);
        camera.update();
        play = new Button(Assets.playButton);
        scores = new Button(Assets.highScoresButton);
        play.setPosition(Game.WORLD_WIDTH / 2, Game.WORLD_HEIGHT / 2 - 250);
        sound = new StateButton(new Button(Assets.soundOn), new Button(Assets.soundOff));
        sound.setPosition(222, 1276 - 250);
        scores.setPosition(822, 1286 - 250);
        tumbleWeed = new TumbleWeed();
        tumbleWeed.setX(-100);
        tumbleWeed.setGroundLevel(12);
        Settings.setSoundOn(Settings.isSoundOn());
        logger = new Logger("Life Cycle");
        logger.setLevel(Logger.INFO);
        game.ads.hide();
        System.out.println("main menu");
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(Assets.mainMenu, 0, 0);
        play.draw(batch);
        scores.draw(batch);
        sound.draw(batch);
        tumbleWeed.draw(batch);
        batch.setColor(1, 1, 1, 1);
        batch.draw(Assets.title, 58.0f, 1324.0f);
        batch.end();
        update();
    }

    private void update() {
        if(Settings.isSoundOn()) {
            if(!Assets.soundTrack.isPlaying()) {
                Assets.soundTrack.play();
                Assets.soundTrack.setLooping(true);
            }
        } else if(!Settings.isSoundOn()) {
            Assets.soundTrack.pause();
        }
        handleButtons();
    }

    public void handleButtons() {
        if(game.touch.isTouchedDown()) {
            Vector3 v = new Vector3(game.touch.getX(), game.touch.getY(), 0);
            camera.unproject(v);
            if(play.isTouched(v.x, v.y)) {
                play.setPressed(true);
            } else if(scores.isTouched(v.x, v.y)) {
                scores.setPressed(true);
            } else if(sound.isTouched(v.x, v.y)) {

            }
        } else if(game.touch.isTouchedUp()) {
            Vector3 v = new Vector3(game.touch.getX(), game.touch.getY(), 0);
            camera.unproject(v);
            if(play.isTouched(v.x, v.y)) {
                game.setScreen(new GameScreen(game));
                game.swiped.setSwiped(false);
            } else if(scores.isTouched(v.x, v.y)) {
                game.setScreen(new ScoresScreen(game));
            } else if(sound.isTouched(v.x, v.y) && game.touch.isTouchedUp()) {
                game.touch.setTouchedUp(false);
                if(Settings.isSoundOn()) {
                    Settings.setSoundOn(false);
                } else {
                    Settings.setSoundOn(true);
                }
            }
            play.setPressed(false);
            scores.setPressed(false);

        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.getCamera().update();
    }

    @Override
    public void show() {
        logger.info("show");
    }

    @Override
    public void hide() {
        logger.info("hide");
    }

    @Override
    public void pause() {
        logger.info("pause");
    }

    @Override
    public void resume() {
        logger.info("resume");
    }

    @Override
    public void dispose() {
        game.dispose();
        game.disposeAssets();
        game.batch.dispose();
    }
}
