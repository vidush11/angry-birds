package github.com.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import github.com.Main;


public class MainMenu implements Screen {

    private Stage stage; //done
    private Main game;
    private TextureAtlas atlas; //done
    private Table table; //done
    private TextButton buttonPlay, buttonExit;
    private BitmapFont white, black;
    private Label heading1,heading2;
    private Skin skin; //appearance of buttons and other things
    private Sprite splash;
    private SpriteBatch batch;

    public MainMenu(Main game) {
        this.game = game;
    }

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
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu(game));
                dispose();
            }
        });

        buttonPlay.pad(10);
        //heading for our button
        Label.LabelStyle headingstyle = new Label.LabelStyle(white, Color.RED);

        heading1= new Label(Main.NAME, headingstyle);

        table.add(heading1);
        table.getCell(heading1).spaceBottom(10);

        table.getCell(heading1).spaceBottom(50);

        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(10);

        table.row();
        table.add(buttonExit);
        table.getCell(buttonPlay).spaceBottom(10);

        table.row();

        table.setPosition(0,20);
        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); //black with 1 alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        splash.draw(batch);
        batch.end();

        stage.act(delta);
        stage.draw();



    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

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
        splash.getTexture().dispose();
    }
}
