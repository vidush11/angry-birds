package github.com;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import github.com.screens.*;
import com.badlogic.gdx.assets.AssetManager;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public static final String NAME="chidi hui chidiya", VERSION="0.0.0.new";

    private Sound sound;

    public static AssetManager assetManager = new AssetManager();
    public static Music music;
    public static Sound sound1;
    public static Sound sound2;

    public static ArrayList<Screen> screens= new ArrayList<>();

    public static final short BIT_GROUND=2;
    public static final short BIT_BIRD=4;
    public static final short BIT_PIG=8;


    @Override
    public void create() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/angry_birds_theme.mp3"));
        sound1 = Gdx.audio.newSound(Gdx.files.internal("sounds/loading.mp3"));
        sound2 = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));

//        screens.add(new LoadingScreen(this));
//        screens.add(new MainMenu(this));
//        screens.add(new LevelMenu(this));
//        screens.add(new Level_1(this));
//        screens.add(new Level_2(this));
        setScreen(new LoadingScreen(this));
        music.setVolume(0f);
//        sound.setVolume();
//        sound1.setVolume(1,.1f);
//        sound2.setVolume(1,.1f);

        music.setLooping(true);
        music.play();

//        music2.setVolume(1f);
//        music2.setLooping(true);
//        music2.play();

    }

    public void dispose(){
        music.dispose();
    }

}
