package github.com.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import github.com.Main;

public class LevelMenu implements Screen {
    private Main game;
    private Stage stage;
    private Table table;
    private TextureAtlas atlas;
    private Skin skin;
    private List list;
    private ScrollPane scrollPane;
    private TextButton back,l1, l2, l3, l4;
    private BitmapFont white, black;
    private SpriteBatch batch;
    private Sprite splash;

    public LevelMenu(Main game) {
        this.game = game;
    }

    @Override
    public void show() {

        batch= new SpriteBatch();
        Texture splashTexture= new Texture("img/levels_pixelated.jpeg");
        splash= new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage= new Stage();
        Gdx.input.setInputProcessor(stage);
//        stage.setViewport()
        atlas=new TextureAtlas("ui/green/button.atlas");
        skin= new Skin(atlas);

        white=new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
        black=new BitmapFont(Gdx.files.internal("font/black.fnt"), false);


        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up=skin.getDrawable("button_up");
        textButtonStyle.down=skin.getDrawable("button_down");
        textButtonStyle.over=skin.getDrawable("button_pressed");
        textButtonStyle.pressedOffsetX=2;
        textButtonStyle.pressedOffsetY=-2;
        textButtonStyle.font=black;

        table=new Table();
        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        l1=new TextButton("Level 1", textButtonStyle);
        l2=new TextButton("Level 2", textButtonStyle);
        l3=new TextButton("Level 3", textButtonStyle);
        l4=new TextButton("Level 4", textButtonStyle);
        back=new TextButton("back", textButtonStyle);

        l1.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level_1(game));
                dispose();
            }
        });

        l2.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level_1(game));
                dispose();
            }
        });

        l3.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level_Test(game));
                dispose();
            }
        });

        l4.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level_1(game));
                dispose();
            }
        });

        l1.pad(10); l2.pad(10); l3.pad(10); l4.pad(10);
        back.pad(10);

        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
                dispose();
            }
        });

        table.add(l1).expandX().padTop(100).spaceBottom(10).spaceRight(50).right();
        table.add(l2).expandX().padTop(100).spaceBottom(10).left();
        table.row();
        table.add(l3).spaceRight(50).right();
        table.add(l4).left();
        table.row();

        table.add(back).expandX().expandY().bottom().padLeft(40).padBottom(40).left();

        stage.addActor(table);
        table.setFillParent(true);

        // table.debug();
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
        batch.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
        splash.getTexture().dispose();
    }
}
