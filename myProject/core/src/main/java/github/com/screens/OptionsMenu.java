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

public class OptionsMenu implements Screen {
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

    public OptionsMenu(Main game) {
        this.game = game;
    }

    public void show() {
        batch= new SpriteBatch();
        Texture splashTexture= new Texture("img/pause_menu.png");
        splash= new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage= new Stage();
        Gdx.input.setInputProcessor(stage);


        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up=new TextureRegionDrawable(new TextureRegion(new Texture("ui/play_up.png")));
        imageButtonStyle.over= new TextureRegionDrawable(new TextureRegion(new Texture("ui/play_over.png")));
        Button play= new ImageButton(imageButtonStyle);
        play.setSize(65,65);
        play.setPosition(290,260);

        play.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level_1(game));
                dispose();
//                splash.setRegion(new Texture("img/color_birds.png"));
//                stage.getViewport().update(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight());

            }

        });

        ImageButton.ImageButtonStyle imageButtonStyle2 = new ImageButton.ImageButtonStyle();
        imageButtonStyle2.up=new TextureRegionDrawable(new TextureRegion(new Texture("ui/volume_up.png")));
        imageButtonStyle2.over= new TextureRegionDrawable(new TextureRegion(new Texture("ui/volume_over.png")));
        imageButtonStyle2.checked=new TextureRegionDrawable(new TextureRegion(new Texture("ui/volume_off_up.png")));
        imageButtonStyle2.checkedOver=new TextureRegionDrawable(new TextureRegion(new Texture("ui/volume_off_over.png")));
        Button volume= new ImageButton(imageButtonStyle2);
        volume.setSize(65,65);
        volume.setPosition(290,190);

        volume.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {

                music.setVolume(0f);
<<<<<<< Updated upstream
                // ((Game)Gdx.app.getApplicationListener()).setScreen(new OptionsMenu());
                // dispose();
=======
                ((Game)Gdx.app.getApplicationListener()).setScreen(new OptionsMenu(game));
                dispose();
>>>>>>> Stashed changes
//                splash.setRegion(new Texture("img/color_birds.png"));
//                stage.getViewport().update(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight());

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
//                splash.setRegion(new Texture("img/color_birds.png"));
//                stage.getViewport().update(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight());

            }

        });
        stage.addActor(play);
        stage.addActor(volume);
        stage.addActor(home);

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
