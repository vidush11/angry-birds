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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import github.com.Main;

import java.util.ArrayList;

import static github.com.Main.music;
import static java.lang.Thread.sleep;

public class WinLoose implements Screen {
    private int i=0;
    private Main game;
    private Level_1 lastLevel;
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
    private int points;
    private int max_points;
    private boolean win;
    private int stars=0;
    private ArrayList<Sprite> starSprites;
    private Label.LabelStyle headingstyle;
    private Label message1;
    private Label message2;

    public WinLoose(Main game, int points, int max_points, boolean win) {
        this.game = game;
        this.points=points;
        this.max_points=max_points;
        this.win=win;
//        this.lastLevel = sc;
        this.starSprites=new ArrayList<>();
        white=new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
        headingstyle = new Label.LabelStyle(white, Color.BLACK);

    }


    @Override
    public void show() {
        stage= new Stage();
        Gdx.input.setInputProcessor(stage);
        message2=new Label(String.valueOf(points)+" Points", headingstyle);

        if (win) {
            message1= new Label("WIN",headingstyle);
            float score = 1.0f * points / max_points;
            if (score < 0.5) {
                stars = 1;
            } else if (score<0.75){
                stars=2;
            } else{
                stars=3;
            }
        }
        else{
            message1= new Label("LOSE",headingstyle);

        }
        message1.setPosition(stage.getWidth()/2-message1.getWidth()/2, stage.getHeight()-80);
        message2.setPosition(stage.getWidth()/2-message2.getWidth()/2,stage.getHeight()/2-50);
        batch= new SpriteBatch();
        Texture backgoundTexture = new Texture("img/bg1.jpg");
        background = new Sprite(backgoundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("ui/retry_up.png")));
        imageButtonStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture("ui/retry_over.png")));
        Button retry= new ImageButton(imageButtonStyle);
        retry.setSize(65,65);
        retry.setPosition(210,120);

        retry.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level_1(game));
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
                game.setScreen(new Level_1(game));
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

        Texture starTexture = new Texture("img/star.png");

        starSprite1 = new Sprite(starTexture);
        starSprite2 = new Sprite(starTexture);
        starSprite3 = new Sprite(starTexture);

        starSprite1.setSize(0,0);
        starSprite2.setSize(0,0);
        starSprite3.setSize(0,0);

        starSprite1.setPosition(stage.getWidth()/2-200,200);
        starSprite2.setPosition(stage.getWidth()/2-75,250);
        starSprite3.setPosition(stage.getWidth()/2+ 50,200);

        starSprites.add(starSprite1);
        starSprites.add(starSprite2);
        starSprites.add(starSprite3);


        stage.addActor(next);
        stage.addActor(retry);
        stage.addActor(home);

    }

    @Override
    public void render(float delta) {
        if (i<stars*100) i++;
        Gdx.gl.glClearColor(0,0,0,1); //black with 1 alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);

        if (win) {

            if (i <= 100) {
                starSprite1.setSize(100 + 0.5f * i, 100 + 0.5f * i);
            }
            else if (i<=200){
                starSprite2.setSize(100 + 0.5f * (i-100), 100 + .5f * (i-100));
            }
            else if (i<=300){
                starSprite3.setSize(100 + 0.5f * (i-200), 100 + .5f * (i-200));

            }
            System.out.println("STARS: "+stars);
            for (int i=0; i<stars; i++){
                starSprites.get(i).draw(batch);
            }

        }
        message1.draw(batch,1f);
        message2.draw(batch, 1f);
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
