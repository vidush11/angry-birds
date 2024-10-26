package github.com;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import github.com.screens.LoadingScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public static final String NAME="chidi hui chidiya", VERSION="0.0.0.new";

    public static AssetManager assetManager = new AssetManager();
    public static Music music;
//    public static Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/angry_birds_theme.mp3"));

    @Override
    public void create() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/angry_birds_theme.mp3"));
        setScreen(new LoadingScreen(this));
        music.setVolume(0.1f);
        music.setLooping(true);
        music.play();
    }



}
