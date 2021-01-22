package com.daleondeveloper.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.I18NBundle;
import com.daleondeveloper.Assets.Audio.music.AssetMusic;
import com.daleondeveloper.Assets.Audio.sound.AssetSounds;
import com.daleondeveloper.Assets.fonts.AssetFonts;
import com.daleondeveloper.Assets.game.AssetBackground;
import com.daleondeveloper.Assets.game.AssetBlock;
import com.daleondeveloper.Assets.game.AssetGates;
import com.daleondeveloper.Assets.game.AssetHero;
import com.daleondeveloper.Assets.guiI.AssetGUI;
import com.daleondeveloper.Assets.help.AssetHelp;
import com.daleondeveloper.Assets.i18n.AssetI18NElementMaster;

public class Assets implements Disposable,AssetErrorListener {
    private static final String TAG = Assets.class.getName();

    // Sound FXs
    public static final String FX_FILE_JUMP_A = "audio/sounds/jumpA.ogg";
    public static final String FX_FILE_JUMP_B = "audio/sounds/jumpB.ogg";
    public static final String FX_FILE_JUMP_C = "audio/sounds/jumpC.ogg";
    public static final String FX_FILE_JUMP_D = "audio/sounds/jumpD.ogg";
    public static final String FX_FILE_JUMP_E = "audio/sounds/jumpE.ogg";
    public static final String FX_FILE_JUMP_F = "audio/sounds/jumpF.ogg";
    public static final String FX_FILE_JUMP_G = "audio/sounds/jumpG.ogg";
    public static final String FX_FILE_JUMP_H = "audio/sounds/jumpH.ogg";
    public static final String FX_FILE_JUMP_I = "audio/sounds/jumpI.ogg";
    public static final String FX_FILE_JUMP_J = "audio/sounds/jumpJ.ogg";
    public static final String FX_FILE_JUMP_K = "audio/sounds/jumpK.ogg";
    public static final String FX_FILE_HIT = "audio/sounds/hit.ogg";
    public static final String FX_FILE_BODY_IMPACT = "audio/sounds/bodyImpact.ogg";
    public static final String FX_FILE_PUM = "audio/sounds/pum.ogg";
    public static final String FX_FILE_CLICK = "audio/sounds/click.ogg";
    public static final String FX_FILE_NEW_ACHIEVEMENT = "audio/sounds/newAchievement.ogg";
    public static final String FX_FILE_BLOOD_SPLASH = "audio/sounds/bloodSplash.ogg";
    public static final String FX_FILE_VOICE = "audio/sounds/voice.ogg";
    public static final String FX_FILE_THROW = "audio/sounds/throw.ogg";
    public static final String FX_FILE_PERFECT = "audio/sounds/perfect.ogg";

    // Music
    public static final String MUSIC_FILE_MAIN_MENU = "audio/music/songMainMenu.ogg";
    public static final String MUSIC_FILE_CREDITS = "audio/music/songCredits.ogg";
    public static final String MUSIC_FILE_GAME = "audio/music/songGame.ogg";

    // Texture atlas
    private static final String TEXTURE_ATLAS_GUI = "atlas/gui/gui.atlas";
    private static final String TEXTURE_ATLAS_BLOCK = "atlas/blocks/blocks.atlas";
    private static final String TEXTURE_ATLAS_HERO = "atlas/hero/hero.atlas";
    private static final String TEXTURE_ATLAS_GATES = "atlas/gates/gates.atlas";
    private static final String TEXTURE_ATLAS_BACKGROUND = "atlas/background/background.atlas";
    private static final String TEXTURE_ATLAS_HELP = "atlas/help/help.atlas";

    private static final String EFFECT_FIRE = "effect/fire/fireeffect.p";
    private static final String EFFECT_FIRE_SECOND = "effect/fire/fireeffectsecond";


    private static Assets instance;
    private AssetManager assetManager;
    private AssetI18NElementMaster i18NElementMaster;
    private AssetGUI assetGUI;
    private AssetBlock assetBlock;
    private AssetHero assetHero;
    private AssetGates assetGates;
    private AssetBackground assetBackground;
    private AssetHelp assetHelp;

    private AssetFonts assetFonts;
    private AssetMusic assetMusic;
    private AssetSounds assetSounds;

    private Assets(){
    }

    public static Assets getInstance(){
        if(instance == null){
            instance = new Assets();
        }
        return instance;
    }

    public void init(AssetManager assetManager){
        this.assetManager = assetManager;

        assetManager.setErrorListener(this);

        loadI18NElementMaster();

        loadTextureAtlas();

        loadParticleEffects();


        //loadSounds();

        //loadMusic();
    }

    public void finishLoading() {
        Gdx.app.debug(TAG,"*********************************");
        Gdx.app.debug(TAG, "**** Number of assets loaded: " + assetManager.getAssetNames().size);
        for(String assetName : assetManager.getAssetNames()){
            Gdx.app.debug(TAG,"**** Asset: " + assetName);
        }
        Gdx.app.debug(TAG, "********************************");

        TextureAtlas atlasGUI = assetManager.get(TEXTURE_ATLAS_GUI);
        TextureAtlas atlasBlock = assetManager.get(TEXTURE_ATLAS_BLOCK);
        TextureAtlas atlasHero = assetManager.get(TEXTURE_ATLAS_HERO);
        TextureAtlas atlasGates = assetManager.get(TEXTURE_ATLAS_GATES);
        TextureAtlas atlasBackground = assetManager.get(TEXTURE_ATLAS_BACKGROUND);
        TextureAtlas atlasHelp = assetManager.get(TEXTURE_ATLAS_HELP);

        for(Texture texture : atlasGUI.getTextures()){
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        for(Texture texture : atlasBlock.getTextures()){
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        for(Texture texture : atlasHero.getTextures()){
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        for(Texture texture : atlasGates.getTextures()){
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        for(Texture texture : atlasBackground.getTextures()){
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        for(Texture texture : atlasHelp.getTextures()){
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }


        i18NElementMaster = new AssetI18NElementMaster(assetManager);
        assetGUI = new AssetGUI(atlasGUI);
        assetBlock = new AssetBlock(atlasBlock);
        assetHero = new AssetHero(atlasHero);
        assetGates = new AssetGates(atlasGates);
        assetBackground = new AssetBackground(atlasBackground);
        assetHelp = new AssetHelp(atlasHelp);

        assetSounds = new AssetSounds(assetManager);
        assetMusic = new AssetMusic(assetManager);
        assetFonts = new AssetFonts();

    }


    private void loadI18NElementMaster(){
        assetManager.load("i18n/I18NGameFourBundle", I18NBundle.class);
    }

    private void loadTextureAtlas(){
        assetManager.load(TEXTURE_ATLAS_GUI, TextureAtlas.class);
        assetManager.load(TEXTURE_ATLAS_BLOCK, TextureAtlas.class);
        assetManager.load(TEXTURE_ATLAS_HERO, TextureAtlas.class);
        assetManager.load(TEXTURE_ATLAS_GATES, TextureAtlas.class);
        assetManager.load(TEXTURE_ATLAS_BACKGROUND, TextureAtlas.class);
        assetManager.load(TEXTURE_ATLAS_HELP, TextureAtlas.class);
    }

    private void loadParticleEffects(){
        ParticleEffectLoader.ParticleEffectParameter pep = new ParticleEffectLoader.ParticleEffectParameter();

        pep.atlasFile = "effect/fire/particle.png";

        assetManager.load(EFFECT_FIRE, ParticleEffect.class);

        assetManager.load(EFFECT_FIRE_SECOND, ParticleEffect.class);
    }
    private void loadSounds(){
        assetManager.load(FX_FILE_JUMP_A, Sound.class);
        assetManager.load(FX_FILE_JUMP_B, Sound.class);
        assetManager.load(FX_FILE_JUMP_C, Sound.class);
        assetManager.load(FX_FILE_JUMP_D, Sound.class);
        assetManager.load(FX_FILE_JUMP_E, Sound.class);
        assetManager.load(FX_FILE_JUMP_F, Sound.class);
        assetManager.load(FX_FILE_JUMP_G, Sound.class);
        assetManager.load(FX_FILE_JUMP_H, Sound.class);
        assetManager.load(FX_FILE_JUMP_I, Sound.class);
        assetManager.load(FX_FILE_JUMP_J, Sound.class);
        assetManager.load(FX_FILE_JUMP_K, Sound.class);
        assetManager.load(FX_FILE_HIT, Sound.class);
        assetManager.load(FX_FILE_BODY_IMPACT, Sound.class);
        assetManager.load(FX_FILE_PUM, Sound.class);
        assetManager.load(FX_FILE_CLICK, Sound.class);
        assetManager.load(FX_FILE_NEW_ACHIEVEMENT, Sound.class);
        assetManager.load(FX_FILE_BLOOD_SPLASH, Sound.class);
        assetManager.load(FX_FILE_VOICE, Sound.class);
        assetManager.load(FX_FILE_THROW, Sound.class);
        assetManager.load(FX_FILE_PERFECT, Sound.class);
}

    private void loadMusic() {
        assetManager.load(MUSIC_FILE_MAIN_MENU, Music.class);
        assetManager.load(MUSIC_FILE_CREDITS, Music.class);
        assetManager.load(MUSIC_FILE_GAME, Music.class);
    }

    public AssetBlock getAssetBlock() {
        return assetBlock;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public AssetHero getAssetHero() {
        return assetHero;
    }

    public AssetGates getAssetGates() {
        return assetGates;
    }

    public AssetBackground getAssetBackground() {
        return assetBackground;
    }

    public AssetHelp getAssetHelp() {
        return assetHelp;
    }

    public AssetGUI getAssetGUI() {
        return assetGUI;
    }

    public AssetFonts getAssetFonts() {
        return assetFonts;
    }

    public AssetMusic getAssetMusic() {
        return assetMusic;
    }

    public AssetSounds getAssetSounds() {
        return assetSounds;
    }

    public AssetI18NElementMaster getI18NElementMaster() {
        return i18NElementMaster;
    }

    @Override
    public void error(AssetDescriptor assetDescriptor, Throwable throwable){
        Gdx.app.error(TAG, "Error loading asset: '" + assetDescriptor.fileName +"'" , throwable );
    }

    @Override
    public void dispose(){
        assetManager.dispose();
        assetFonts.dispose();
        assetManager = null;
        assetFonts = null;
    }
}
