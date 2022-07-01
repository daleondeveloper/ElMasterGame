package com.daleondeveloper.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
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

import java.util.Locale;

public class Assets implements Disposable,AssetErrorListener {
    private static final String TAG = Assets.class.getName();

    // Texture atlas
    private static final String TEXTURE_ATLAS_GUI = "atlas/gui/gui.atlas";
    private static final String TEXTURE_ATLAS_BLOCK = "atlas/blocks/blocks.atlas";
    private static final String TEXTURE_ATLAS_HERO = "atlas/hero/hero.atlas";
    private static final String TEXTURE_ATLAS_GATES = "atlas/gates/gates.atlas";
    private static final String TEXTURE_ATLAS_BACKGROUND = "atlas/background/background.atlas";
    private static final String TEXTURE_ATLAS_HELP = "atlas/help/help.atlas";

    private static final String EFFECT_FIRE = "effect/fire/fireeffect.p";
    private static final String EFFECT_FALL = "effect/fire/fall_effect";
    private static final String EFFECT_FIRE_SECOND = "effect/fire/cold_effect_under_block";


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
        String localeParam = Locale.getDefault().getLanguage().toString();
        I18NBundleLoader.I18NBundleParameter parameter = null;
        if(localeParam.equals("ru")){
            parameter =  new I18NBundleLoader.I18NBundleParameter(new Locale("ru","RU"),"UTF-8");
        }else if(localeParam.equals("uk")){
            parameter =  new I18NBundleLoader.I18NBundleParameter(new Locale("uk"),"UTF-8");
        }else{
            parameter =  new I18NBundleLoader.I18NBundleParameter(new Locale(""),"UTF-8");

        }
        assetManager.load("i18n/I18NElMasterBundle", I18NBundle.class, parameter);

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

        assetManager.load(EFFECT_FALL, ParticleEffect.class);
    }
    private void loadSounds(){

}

    private void loadMusic() {

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
