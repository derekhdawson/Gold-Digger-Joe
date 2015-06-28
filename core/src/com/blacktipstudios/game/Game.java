package com.blacktipstudios.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blacktipstudios.resources.Assets;
import com.blacktipstudios.screens.LoadingScreen;
import com.blacktipstudios.tools.Ads;
import com.blacktipstudios.tools.KeyStroke;
import com.blacktipstudios.tools.Swipe;
import com.blacktipstudios.tools.Touch;

public class Game extends com.badlogic.gdx.Game {

    public static final int WORLD_WIDTH = 1080;
    public static final int WORLD_HEIGHT = 1920;

    public SpriteBatch batch;
    public Viewport viewport;
    public Camera camera;
    public Ads ads;
    public Touch touch;
    public Swipe swiped;
    public AssetManager assetManager;

    public Game(Ads ads) {
        this.ads = ads;
        touch = new Touch();
        swiped = new Swipe();
        assetManager = new AssetManager();
    }

    public Game() {
        this.ads = new Ads() {
            @Override
            public void load() {

            }

            @Override
            public void hide() {

            }

            @Override
            public void show() {

            }
        };
        touch = new Touch();
        swiped = new Swipe();
        assetManager = new AssetManager();
    }

    @Override
    public void create() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(swiped));
        multiplexer.addProcessor(new KeyStroke());
        multiplexer.addProcessor(touch);
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        if(Gdx.app.getType() == Application.ApplicationType.Desktop ||
                Gdx.app.getType() == Application.ApplicationType.WebGL) {
            viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        } else {
            viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        }
        this.setScreen(new LoadingScreen(this));
    }

    public void disposeAssets() {
        Assets.background.dispose();
        Assets.coalCart.getTexture().dispose();
        Assets.silverCart.getTexture().dispose();
        Assets.goldCart.getTexture().dispose();
        Assets.backCart.getTexture().dispose();
        Assets.frontCart.getTexture().dispose();
        Assets.coalPiece.getTexture().dispose();
        Assets.silverPiece.getTexture().dispose();
        Assets.goldPiece.getTexture().dispose();
        Assets.coalExplosion.dispose();
        Assets.silverExplosion.dispose();
        Assets.goldExplosion.dispose();
        Assets.frontExplosion.dispose();
        Assets.backExplosion.dispose();
        Assets.explosion1.dispose();
        Assets.explosion2.dispose();
        Assets.explosion3.dispose();
        Assets.itemCollected.dispose();
        Assets.bangerFont_Red.dispose();
        Assets.bangerFont_White.dispose();
        Assets.star_coal.dispose();
        Assets.star_coal.dispose();
        Assets.star_silver.dispose();
        Assets.star_silver.dispose();
        Assets.star_gold.dispose();
        Assets.star_gold.dispose();
        Assets.bomb.dispose();
        Assets.diamond.dispose();
        Assets.highScoresButton.dispose();
        Assets.pauseButton.dispose();
        Assets.playButton.dispose();
        Assets.mainMenu.dispose();
        Assets.tumbleWeed.dispose();
        Assets.buttonHighlight.dispose();
        Assets.gameOver1.dispose();
        Assets.gameOver2.dispose();
        Assets.helveticaWhite.dispose();
        Assets.skullIcon.dispose();
        Assets.speedIcon.dispose();
        Assets.doubleIcon.dispose();
        Assets.shieldIcon.dispose();
        Assets.goldParticleEffect.dispose();
        Assets.goldParticleEffect.dispose();
        Assets.title.dispose();
        Assets.soundOn.dispose();
        Assets.soundOff.dispose();
        Assets.backButton.dispose();
        Assets.scoreScreen.dispose();
        Assets.tempBackground.dispose();
        Assets.soundTrack.dispose();
        Assets.buttonHome.dispose();
        Assets.buttonPlaySmall.dispose();
        Assets.gameOverScreen.dispose();
        Assets.gameOverText.dispose();
        Assets.helveticaWhite.dispose();
    }
}
