package com.blacktipstudios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.blacktipstudios.game.Game;
import com.blacktipstudios.resources.Assets;
import com.blacktipstudios.resources.AssetsLoader;
import com.blacktipstudios.resources.Scores;


public class LoadingScreen implements Screen {

    private Game game;
    private SpriteBatch batch;
    private AssetsLoader assetsLoader;
    private AssetManager assetManager;
    public Texture splashScreen = new Texture("img/spash.png");

    public LoadingScreen(Game game) {
        this.game = game;
        batch = game.batch;
        assetsLoader = new AssetsLoader(game);
        assetsLoader.load();
        assetManager = assetsLoader.assetManager;
        game.camera.position.set(Game.WORLD_WIDTH / 2, Game.WORLD_HEIGHT / 2, 0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(game.viewport.getCamera().combined);
        batch.begin();
        batch.draw(splashScreen, 0, 0);
        batch.end();
        if(!assetsLoader.assetManager.update()) {
            System.out.println(assetsLoader.assetManager.getProgress() * 100 + "");
        } else {
            loadAssets();
            game.setScreen(new MainMenu(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
        game.viewport.getCamera().update();
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
        batch.dispose();
        game.dispose();
        game.disposeAssets();
        game.batch.dispose();
    }

    private void loadAssets() {
        TextureAtlas atlas = assetManager.get("img/snake.pack", TextureAtlas.class);
        Assets.background = assetManager.get("img/background.png", Texture.class);
        Assets.coalCart = atlas.findRegion("snake_coal");
        Assets.silverCart = atlas.findRegion("snake_silver");
        Assets.goldCart = atlas.findRegion("snake_gold");
        Assets.backCart = atlas.findRegion("snake_back");
        Assets.frontCart = atlas.findRegion("snake_front");
        Assets.coalPiece = atlas.findRegion("item_coal");
        Assets.silverPiece = atlas.findRegion("item_silver");
        Assets.goldPiece = atlas.findRegion("item_gold");
        Assets.coalExplosion = assetManager.get("img/explosion_coal.png", Texture.class);
        Assets.silverExplosion = assetManager.get("img/explosion_silver.png", Texture.class);
        Assets.goldExplosion = assetManager.get("img/explosion_gold.png", Texture.class);
        Assets.frontExplosion = assetManager.get("img/explosion_front.png", Texture.class);
        Assets.backExplosion = assetManager.get("img/explosion_back.png", Texture.class);
        Assets.explosion1 = assetManager.get("sounds/explosion_1.mp3", Sound.class);
        Assets.explosion2 = assetManager.get("sounds/explosion_2.mp3", Sound.class);
        Assets.explosion3 = assetManager.get("sounds/explosion_3.mp3", Sound.class);
        Assets.itemCollected = assetManager.get("sounds/gold_sound.mp3", Sound.class);
        Assets.bangerFont_Red = assetManager.get("fonts/bangers_red.fnt", BitmapFont.class);
        Assets.bangerFont_White = assetManager.get("fonts/banger_white.fnt", BitmapFont.class);
        Assets.star_coal = assetManager.get("effects/star_coal.p", ParticleEffect.class);
        Assets.star_silver = assetManager.get("effects/star_silver.p", ParticleEffect.class);
        Assets.star_gold = assetManager.get("effects/star_gold.p", ParticleEffect.class);
        Assets.bomb = assetManager.get("img/skull.png", Texture.class);
        Assets.diamond = assetManager.get("img/diamonds.png", Texture.class);
        Assets.highScoresButton = assetManager.get("img/button_highscores.png", Texture.class);
        Assets.pauseButton = assetManager.get("img/button_pause.png", Texture.class);
        Assets.playButton = assetManager.get("img/button_play.png", Texture.class);
        Assets.mainMenu = assetManager.get("img/menu_screen.png", Texture.class);
        Assets.tumbleWeed = assetManager.get("img/tumbleweed.png", Texture.class);
        Assets.buttonHighlight = assetManager.get("img/button_highlight.png", Texture.class);
        Assets.gameOver1 = assetManager.get("img/game_over1.png", Texture.class);
        Assets.gameOver2 = assetManager.get("img/game_over2.png", Texture.class); //test
        Assets.helveticaWhite = assetManager.get("fonts/helvetica_white.fnt", BitmapFont.class);
        Assets.skullIcon = assetManager.get("img/icon_skull.png", Texture.class);
        Assets.speedIcon = assetManager.get("img/icon_speed.png", Texture.class);
        Assets.doubleIcon = assetManager.get("img/icon_double.png", Texture.class);
        Assets.shieldIcon = assetManager.get("img/icon_shield.png", Texture.class);
        Assets.goldParticleEffect = assetManager.get("effects/gold.p", ParticleEffect.class);
        Assets.title = assetManager.get("img/title.png", Texture.class);
        Assets.soundOn = assetManager.get("img/button_sound2.png", Texture.class);
        Assets.soundOff = assetManager.get("img/button_sound1.png", Texture.class);
        Assets.backButton = assetManager.get("img/button_home.png", Texture.class);
        Assets.scoreScreen = assetManager.get("img/highscore_screen.png", Texture.class);
        Assets.tempBackground = assetManager.get("img/menu_screen.png", Texture.class);
        Assets.soundTrack = assetManager.get("sounds/Gold Digger Joe Soundtrack.mp3", Music.class);
        Assets.buttonHome = assetManager.get("img/button_home.png", Texture.class);
        Assets.buttonPlaySmall = assetManager.get("img/button_play_small.png", Texture.class);
        Assets.gameOverScreen = assetManager.get("img/game_over_screen.png", Texture.class);
        Assets.gameOverText = assetManager.get("img/game_over_text.png", Texture.class);
        Assets.helveticaWhite = assetManager.get("fonts/helvetica_white.fnt", BitmapFont.class);
        Scores.load();

        //Make it look pretty
        Assets.helveticaWhite.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.buttonHome.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.buttonPlaySmall.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.gameOverScreen.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.gameOverText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.bangerFont_Red.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.bangerFont_White.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.coalCart.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.silverCart.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.goldCart.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.goldPiece.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.silverPiece.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.coalPiece.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.coalExplosion.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.silverExplosion.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.goldExplosion.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.frontExplosion.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.backExplosion.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.bomb.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.diamond.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.highScoresButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.pauseButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.playButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.mainMenu.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.tumbleWeed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.buttonHighlight.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.gameOver1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.gameOver2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.skullIcon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.speedIcon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.doubleIcon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.shieldIcon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.title.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.soundOn.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.soundOff.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.backButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.scoreScreen.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Assets.tempBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

}
