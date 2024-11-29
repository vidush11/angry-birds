package github.com.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import github.com.Main;

import static github.com.Main.music;

public class WinLoose implements Screen {

    private Main game;
    private Level_2 lastLevel;
    private Stage stage;
    private Table table;
    private TextureAtlas atlas;
    private Skin skin;
    private List list;
    private ScrollPane scrollPane;
    private TextButton back,l1, l2, l3, l4;
    private BitmapFont white, black;
    private SpriteBatch batch;
    private Sprite background;
    private Sprite starSprite1, starSprite2, starSprite3;

    public WinLoose(Main game, Level_2 lastLevel) {
        this.game = game;
        this.lastLevel = lastLevel;
    }

    @Override
    public void show() {
        batch= new SpriteBatch();
        Texture backgoundTexture = Main.assetManager.get("img/WinLooseBackground.png", Texture.class);
        background = new Sprite(backgoundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage= new Stage();
        Gdx.input.setInputProcessor(stage);

        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("ui/retry_up.png")));
        imageButtonStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture("ui/retry_over.png")));
        Button retry= new ImageButton(imageButtonStyle);
        retry.setSize(65,65);
        retry.setPosition(210,120);

        retry.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level_2(game));
                dispose();
            }
        });

        ImageButton.ImageButtonStyle imageButtonStyle2 = new ImageButton.ImageButtonStyle();
        imageButtonStyle2.up=new TextureRegionDrawable(new TextureRegion(new Texture("ui/next_up.png")));
        imageButtonStyle2.over= new TextureRegionDrawable(new TextureRegion(new Texture("ui/next_over.png")));
        Button next= new ImageButton(imageButtonStyle2);
        next.setSize(65,65);
        next.setPosition(370,120);

        next.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level_2(game));
            }
        });

        ImageButton.ImageButtonStyle imageButtonStyle3 = new ImageButton.ImageButtonStyle();
        imageButtonStyle3.up=new TextureRegionDrawable(new TextureRegion(new Texture("ui/home_up.png")));
        imageButtonStyle3.over= new TextureRegionDrawable(new TextureRegion(new Texture("ui/home_over.png")));
        Button home= new ImageButton(imageButtonStyle3);
        home.setSize(65,65);
        home.setPosition(290,120);

        home.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
                dispose();
            }
        });

        Texture starTexture = Main.assetManager.get("img/star.png", Texture.class);
        starSprite1 = new Sprite(starTexture);
        starSprite2 = new Sprite(starTexture);
        starSprite3 = new Sprite(starTexture);

        starSprite1.setSize(128,128);
        starSprite2.setSize(128,128);
        starSprite3.setSize(128,128);

        starSprite1.setPosition((stage.getWidth()-128) /2,200);
        starSprite2.setPosition((stage.getWidth()-128) /2 - 128,200);
        starSprite3.setPosition((stage.getWidth()-128) /2 + 128,200);

        stage.addActor(next);
        stage.addActor(retry);
        stage.addActor(home);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); //black with 1 alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        starSprite1.draw(batch);
        starSprite2.draw(batch);
        starSprite3.draw(batch);
        batch.end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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
    }
}
