package github.com;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import github.com.screens.LoadingScreen;
import github.com.screens.MainMenu;
import github.com.screens.Splash;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public static final String NAME="chidi hui chidiya", VERSION="0.0.0.new";

    public static AssetManager assetManager = new AssetManager();
    @Override
    public void create() {
        setScreen(new LoadingScreen(this));
    }

      Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/angry_birds_theme.mp3"));

        music.setVolume(200f);
        music.setLooping(true);
        music.play();
}
