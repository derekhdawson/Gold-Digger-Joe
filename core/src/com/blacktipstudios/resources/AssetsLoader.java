package com.blacktipstudios.resources;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.blacktipstudios.game.Game;

/**
 * Created by derekdawson on 6/26/15.
 */
public class AssetsLoader {


    public AssetManager assetManager;
    TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
    BitmapFontLoader.BitmapFontParameter bitmapFontParameter = new BitmapFontLoader.BitmapFontParameter();
    com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter particleEffectParameter =
            new com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter();

    public AssetsLoader(Game game) {
        assetManager = game.assetManager;
        textureParameter.minFilter = Texture.TextureFilter.Linear;
        textureParameter.genMipMaps = true;
        bitmapFontParameter.minFilter = Texture.TextureFilter.Linear;
        bitmapFontParameter.genMipMaps = true;
        particleEffectParameter.imagesDir = Gdx.files.internal("effects");


    }

    public void load() {
        assetManager.load("img/background.png", Texture.class, textureParameter);
        assetManager.load("img/snake.pack", TextureAtlas.class);
        assetManager.load("img/explosion_coal.png", Texture.class, textureParameter);
        assetManager.load("img/explosion_silver.png", Texture.class, textureParameter);
        assetManager.load("img/explosion_gold.png", Texture.class, textureParameter);
        assetManager.load("img/explosion_front.png", Texture.class, textureParameter);
        assetManager.load("img/explosion_back.png", Texture.class, textureParameter);
        assetManager.load("sounds/explosion_1.mp3", Sound.class);
        assetManager.load("sounds/explosion_2.mp3", Sound.class);
        assetManager.load("sounds/explosion_3.mp3", Sound.class);
        assetManager.load("sounds/gold_sound.mp3", Sound.class);
        assetManager.load("fonts/bangers_red.fnt", BitmapFont.class, bitmapFontParameter);
        assetManager.load("fonts/banger_white.fnt", BitmapFont.class, bitmapFontParameter);
        assetManager.load("effects/star_coal.p", ParticleEffect.class, particleEffectParameter);
        assetManager.load("effects/star_silver.p", ParticleEffect.class, particleEffectParameter);
        assetManager.load("effects/star_gold.p", ParticleEffect.class, particleEffectParameter);
        assetManager.load("img/skull.png", Texture.class, textureParameter);
        assetManager.load("img/diamonds.png", Texture.class, textureParameter);
        assetManager.load("img/button_highscores.png", Texture.class, textureParameter);
        assetManager.load("img/button_pause.png", Texture.class, textureParameter);
        assetManager.load("img/button_play.png", Texture.class, textureParameter);
        assetManager.load("img/menu_screen.png", Texture.class, textureParameter);
        assetManager.load("img/tumbleweed.png", Texture.class, textureParameter);
        assetManager.load("img/button_highlight.png", Texture.class, textureParameter);
        assetManager.load("img/game_over1.png", Texture.class, textureParameter);
        assetManager.load("img/game_over2.png", Texture.class, textureParameter); //test later
        assetManager.load("fonts/helvetica_white.fnt", BitmapFont.class, bitmapFontParameter);
        assetManager.load("img/icon_skull.png", Texture.class, textureParameter);
        assetManager.load("img/icon_speed.png", Texture.class, textureParameter);
        assetManager.load("img/icon_double.png", Texture.class, textureParameter);
        assetManager.load("img/icon_shield.png", Texture.class, textureParameter);
        assetManager.load("effects/gold.p", ParticleEffect.class, particleEffectParameter);
        assetManager.load("img/title.png", Texture.class, textureParameter);
        assetManager.load("img/button_sound2.png", Texture.class, textureParameter);
        assetManager.load("img/button_sound1.png", Texture.class, textureParameter);
        assetManager.load("img/button_home.png", Texture.class, textureParameter);
        assetManager.load("img/highscore_screen.png", Texture.class, textureParameter);
        assetManager.load("img/menu_screen.png", Texture.class, textureParameter);
        assetManager.load("sounds/Gold Digger Joe Soundtrack.mp3", Music.class);
        assetManager.load("img/button_home.png", Texture.class, textureParameter);
        assetManager.load("img/button_play_small.png", Texture.class, textureParameter);
        assetManager.load("img/game_over_screen.png", Texture.class, textureParameter);
        assetManager.load("img/game_over_text.png", Texture.class, textureParameter);
        assetManager.load("fonts/helvetica_white.fnt", BitmapFont.class, bitmapFontParameter);
    }
}

