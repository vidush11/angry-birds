package github.com;

import com.badlogic.gdx.Game;
import github.com.screens.Splash;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public static final String NAME="chidi hui chidiya", VERSION="0.0.0.new";
    @Override
    public void create() {
        setScreen(new Splash());
    }
}
