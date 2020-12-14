package com.daleondeveloper.Assets.Audio.music;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

public class AssetMusic {
    private static final String TAG = AssetMusic.class.getName();

    private Music songMainMenu;
    private Music songCredits;
    private Music songGame;

    public AssetMusic(AssetManager am) {
//        songMainMenu = am.get(Assets.MUSIC_FILE_MAIN_MENU, Music.class);
//        songCredits = am.get(Assets.MUSIC_FILE_CREDITS, Music.class);
//        songGame = am.get(Assets.MUSIC_FILE_GAME, Music.class);
    }

    public Music getSongMainMenu() {
        return songMainMenu;
    }

    public Music getSongCredits() {
        return songCredits;
    }

    public Music getSongGame() {
        return songGame;
    }
}