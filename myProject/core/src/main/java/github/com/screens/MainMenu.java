package github.com.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.bullet.collision.GdxCollisionObjectBridge;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
import github.com.FirstScreen;
import github.com.Main;
import org.w3c.dom.Text;

import javax.security.auth.login.CredentialException;
import java.util.function.Predicate;


public class MainMenu implements Screen {

    private Stage stage; //done

    private TextureAtlas atlas; //done
    private Table table; //done
    private TextButton buttonPlay, buttonExit;
    private BitmapFont white, black;
    private Label heading1,heading2;
    private Skin skin; //appearance of buttons and other things
    private Sprite splash;
    private SpriteBatch batch;
    private Main game;

//    public MainMenu(Main game) {
//        this.game = game;
//    }

    public void show() {

        batch= new SpriteBatch();
        Texture splashTexture= new Texture("img/pixelated_menu.png");
        splash= new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage= new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas=new TextureAtlas("ui/button.atlas");
        skin=new Skin(atlas);

        table= new Table(); // not necessary to put argument here
        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //fonts
        white=new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
        black=new BitmapFont(Gdx.files.internal("font/black.fnt"), false);

        //buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up=skin.getDrawable("button_up");
        textButtonStyle.down=skin.getDrawable("button_down");
        textButtonStyle.over=skin.getDrawable("button_pressed");
        textButtonStyle.pressedOffsetX=2;
        textButtonStyle.pressedOffsetY=-2;
        textButtonStyle.font=black;
//        textButtonStyle.font.setColor(Color.BLUE);
//        textButtonStyle.overFontColor = Color.WHITE;
//        Texture buttonTexture = new Texture(Gdx.files.internal("img/background.jpg"));

//        Skin skin1= new Skin(buttonTexture);
//        ImageButton button =  new ImageButton(new TextureRegionDrawable(new TextureRegion(buttonTexture)));
//        button.setTransform(true);
//        button.setPosition(100,-10000);
//        button.setHeight(10);
//        button.setWidth(10);
//        button.setPreferredSize();
//        button.setSize(10,10);
//        button.setPosition();

//        button.addListener(new ClickListener(){
//
//            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                button.setScale(0.5f);
//
//            }
//
//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                button.setScale(1f);
//            }
//        });
//                skin.add("honey", new Texture("img/birds.jpeg"));
//        TextButton button =  new TextButton("honey",skin1);
//        ImageButton button =  new ImageButton("img/birds.jpeg");

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

        heading1= new Label(Main.NAME, headingstyle);
//        heading2= new Label("(ANGRY BIRDS)",headingstyle);
//        heading.setFontScale();
        table.add(heading1);
        table.getCell(heading1).spaceBottom(10);
//        table.row();
//        table.add(heading2);
        table.getCell(heading1).spaceBottom(50);

        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(10);

        table.row();
        table.add(buttonExit);
        table.getCell(buttonPlay).spaceBottom(10);

        table.row();
//        table.add(button);

        table.setPosition(0,20);
//        table.debug();
        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); //black with 1 alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        splash.draw(batch);
        batch.end();

//        Table.drawDebug(stage);
        stage.act(delta);
        stage.draw();



    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

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
