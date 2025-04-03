package github.com;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import github.com.Player_classes.Player;
import github.com.screens.*;
import com.badlogic.gdx.assets.AssetManager;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public static final String NAME="chidi hui chidiya", VERSION="0.0.0.new";
    private static Main main= null;
    private static Player owner= null;
    private Sound sound;

    public static AssetManager assetManager = new AssetManager();
    public static Music music;
    public static Sound sound1;
    public static Sound sound2;

    public static ArrayList<Screen> screens= new ArrayList<>();

    public static final short BIT_GROUND=2;
    public static final short BIT_BIRD=4;
    public static final short BIT_PIG=8;

    private Main(){

    }

    public static Main getMain(){
        if (main==null){
            main=new Main();
            owner=new Player("JAAT");
        }
        return main;
    }

    @Override
    public void create() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/angry_birds_theme.mp3"));
        sound1 = Gdx.audio.newSound(Gdx.files.internal("sounds/loading.mp3"));
        sound2 = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));

        setScreen(new LoadingScreen(this));
        music.setVolume(0f);
        music.setLooping(true);
        music.play();
    }

    public void dispose(){
        music.dispose();
    }

}
