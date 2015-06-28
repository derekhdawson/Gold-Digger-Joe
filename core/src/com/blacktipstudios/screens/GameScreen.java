package com.blacktipstudios.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blacktipstudios.game.Game;
import com.blacktipstudios.model.*;
import com.blacktipstudios.resources.Assets;
import com.blacktipstudios.resources.Scores;

import java.util.Random;

public class GameScreen implements Screen {

    enum GameState {
        Playing, GameOver, Waiting, Transition, PlayAgain, Paused
    }

    //View Components
    private SpriteBatch batch;
    private Viewport viewport;
    private Camera camera;

    //Game Components
    private Game game;
    private World world;
    private GameState state;
    private BitmapFont font;
    private TransitionSprite tempBackground;
    private TransitionSprite background;
    private GameOverBox gameOverBox;
    private Button back, pause, resume, playAgain, home;
    private ButtonsFade buttonsFade;


    private Logger logger;


    public GameScreen(Game game) {
        //View Components
        this.game = game;
        batch = game.batch;
        viewport = game.viewport;
        camera = viewport.getCamera();
        camera.position.set(Game.WORLD_WIDTH / 2, Game.WORLD_HEIGHT / 2, 0);
        camera.update();
        font = Assets.helveticaWhite;
        back = new Button(Assets.backButton);
        pause = new Button(Assets.pauseButton);
        resume = new Button(Assets.playButton);
        playAgain = new Button(Assets.buttonPlaySmall);
        home = new Button(Assets.buttonHome);
        pause.setPosition(985.0f, 1532.0f);
        resume.setPosition(Game.WORLD_WIDTH / 2, 1000);
        back.setPosition(Game.WORLD_WIDTH / 2, 500);
        playAgain.setPosition(Game.WORLD_WIDTH / 2 - 200, 250);
        home.setPosition(Game.WORLD_WIDTH / 2 + 200, 250);
        buttonsFade = new ButtonsFade();
        buttonsFade.setDirection(ButtonsFade.Direction.DOWN);

        //Game Components
        world = new World(viewport.getWorldWidth(), viewport.getWorldHeight());
        state = GameState.Transition;
        tempBackground = new TransitionSprite(Assets.tempBackground);
        background = new TransitionSprite(Assets.background);
        background.setY(-Game.WORLD_HEIGHT + 480);
        gameOverBox = new GameOverBox(world.getScore());
        if(Gdx.app.getType() == Application.ApplicationType.Android ||
           Gdx.app.getType() == Application.ApplicationType.iOS) {
            game.ads.load();
        }
        logger = new Logger("Life Cycle");
        logger.setLevel(Logger.INFO);
        game.ads.show();
    }

    private void update() {
        //get coordinates
        Vector3 v = new Vector3(game.touch.getX(), game.touch.getY(), 0);
        camera.unproject(v);
        switch (state) {
            case GameOver:
                if(world.getExplosions().doneExploding()) {
                    state = GameState.PlayAgain;
                }
                break;
            case Playing:
                if(!world.getSnake().isAlive() && !world.getExplosions().doneExploding()) {
                    state = GameState.GameOver;
                    updateCamera();
                }
                if(pause.isTouched(v.x, v.y) && game.touch.isTouchedUp()) {
                    game.touch.setTouchedUp(false);
                    state = GameState.Paused;
                    world.pauseSnake(true);
                }
                break;
            case Waiting:
                world.pauseSnake(true);
                if(game.swiped.isSwiped() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
                    world.pauseSnake(false);
                    state = GameState.Playing;
                    game.swiped.setSwiped(false);
                }
                break;
            case Transition:
                world.pauseSnake(true);
                tempBackground.setY(tempBackground.getY() + 15);
                background.setY(background.getY() + 15);
                if(tempBackground.getY() >= 60 * 24) {
                    tempBackground.setShow(false);
                    background.setShow(false);
                    state = GameState.Waiting;
                }
                break;
            case PlayAgain:
                //play again
                if(playAgain.isTouched(v.x, v.y)
                        && game.touch.isTouchedDown()) {
                    playAgain.setPressed(true);
                } else if(playAgain.isTouched(v.x, v.y)
                        && game.touch.isTouchedUp()) {
                    playAgain.setPressed(false);
                    state = GameState.Waiting;
                    world.pauseSnake(true);
                    world.reset();
                    gameOverBox.reset();
                    setCameraToOrigin();
                    game.touch.setTouchedUp(false);
                } else {
                    playAgain.setPressed(false);
                }
                //home
                if(home.isTouched(v.x, v.y)
                        && game.touch.isTouchedDown()) {
                    home.setPressed(true);
                } else if(home.isTouched(v.x, v.y)
                        && game.touch.isTouchedUp()) {
                    setCameraToOrigin();
                    game.touch.setTouchedUp(false);
                    game.setScreen(new MainMenu(game));
                } else {
                    home.setPressed(false);
                }
                break;
            case Paused:
                if(game.touch.isTouchedUp()) {
                    game.touch.setTouchedUp(false);
                    if(resume.isTouched(v.x, v.y)) {
                        state = GameState.Playing;
                        world.pauseSnake(false);
                    }
                    if(back.isTouched(v.x, v.y)) {
                        game.setScreen(new MainMenu(game));
                    }
                }
                break;
        }
    }

    public void drawState() {
        switch (state) {
        case GameOver:
            world.draw(batch);
            game.touch.setTouchedUp(false);
        break;
        case Playing:
            world.draw(batch);
            pause.draw(batch);
        break;
        case Waiting:
            world.draw(batch);
        break;
        case Transition:
            world.draw(batch);
            background.draw(batch);
            tempBackground.draw(batch);
            buttonsFade.draw(batch);
        break;
        case PlayAgain:
            batch.draw(Assets.gameOverScreen, 0, 0);
            batch.draw(Assets.gameOverText, Game.WORLD_WIDTH / 2 -
                    Assets.gameOverText.getWidth() / 2 + 15, Game.WORLD_HEIGHT / 2);
            font.draw(batch, "Score: " + world.getScore().getScore(), 260, 850);
            font.draw(batch, "Best:   " + Scores.getScore(0), 260, 650);
            playAgain.draw(batch);
            home.draw(batch);
            world.getTumbleWeed().draw(batch);
        break;
        case Paused:
            world.drawWorldPaused(batch);
            resume.draw(batch);
            back.draw(batch);
            break;
        }
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawState();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.getCamera().update();
        logger.info("resize");
    }

    @Override
    public void show() {
        logger.info("show");
    }

    @Override
    public void hide() {
        world.pauseSnake(true);
        logger.info("hide");
        if(state == GameState.Playing) {
            state = GameState.Waiting;
        }
    }

    @Override
    public void pause() {
        world.pauseSnake(true);
        logger.info("pause");
        if(state == GameState.Playing) {
            logger.info("state = pause");
            state = GameState.Paused;
        }
    }

    @Override
    public void resume() {
        logger.info("resume");
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        game.dispose();
        game.disposeAssets();
        game.batch.dispose();
        font.dispose();
        logger.info("dispose");
    }

    /***************************************************
     ************* CAMERA METHODS **********************
     ***************************************************/
    public void shakeCamera() {
        float camX = camera.position.x;
        float camY = camera.position.y;
        boolean right = new Random().nextBoolean();
        if(right) {
            //camera horizontal shake
            if (camX == 540) {
                camera.position.set(560, camY, 0);
            } else if (camX == 560) {
                camera.position.set(520, camY, 0);
            } else if (camX == 520) {
                camera.position.set(540, camY, 0);
            }
            camera.update();
        } else {
            //camera vertical shake
            if (camY == 960) {
                camera.position.set(camX, 980, 0);
            } else if (camY == 980) {
                camera.position.set(camX, 940, 0);
            } else if (camY == 940) {
                camera.position.set(camX, 960, 0);
            }
            camera.update();
        }
    }

    public void setCameraToOrigin() {
        camera.position.set(1080 / 2, 1920 / 2, 0);
        camera.update();
    }

    public void updateCamera() {
        if(world.getExplosions().wasRecentExplosion()) {
            shakeCamera();
            Gdx.input.vibrate(250);
        } else {
            setCameraToOrigin();
        }
    }
}
