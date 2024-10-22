package github.com.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.bullet.collision.GdxCollisionObjectBridge;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import github.com.FirstScreen;
import github.com.Main;

import javax.security.auth.login.CredentialException;
import java.util.function.Predicate;


public class MainMenu implements Screen {

    private Stage stage; //done

    private TextureAtlas atlas; //done
    private Table table; //done
    private TextButton buttonPlay, buttonExit;
    private BitmapFont white, black;
    private Label heading;
    private Skin skin; //appearance of buttons and other things
    private Sprite splash;
    private SpriteBatch batch;
    @Override
    public void show() {
        batch= new SpriteBatch();
        Texture splashTexture= new Texture("img/background.jpg");
        splash= new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage= new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas=new TextureAtlas("ui/button.atlas");
        skin=new Skin(atlas);

        table= new Table(); // not necessary to put argument here
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //fonts
        white=new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
        black=new BitmapFont(Gdx.files.internal("font/black.fnt"), false);

        //buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up=skin.getDrawable("button_up");
        textButtonStyle.down=skin.getDrawable("button_down");
        textButtonStyle.pressedOffsetX=2;
        textButtonStyle.pressedOffsetY=-2;
        textButtonStyle.font=black;

        buttonExit= new TextButton("EXIT", textButtonStyle);
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonExit.pad(10);

        buttonPlay= new TextButton("PLAY", textButtonStyle);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
                dispose();
            }
        });

        buttonPlay.pad(10);
        //heading for our button
        Label.LabelStyle headingstyle = new Label.LabelStyle(white, Color.RED);

        heading= new Label(Main.NAME, headingstyle);
//        heading.setFontScale();
        table.add(heading);
        table.getCell(heading).spaceBottom(10);
        table.row();
        table.add(buttonPlay);

        table.getCell(buttonPlay).spaceBottom(10);
        table.row();

        table.add(buttonExit);


//        table.debug();
        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); //black with 1 alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
//        Table.drawDebug(stage);
        splash.draw(batch);
        stage.act(delta);
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
//        stage.setViewport(new Viewport());
//        table.setClip(true);
//
//        table.setSize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
    }
}
