package github.com.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import jdk.jfr.internal.consumer.StringParser;
//import com.sun.org.apache.xpath.internal.operations.String;

//import java.util.ArrayList;
//import java.util.List;

public class LevelMenu implements Screen {
    private Stage stage;
    private Table table;
    private TextureAtlas atlas;
    private Skin skin;
    private List list;
    private ScrollPane scrollPane;
    private TextButton play, back;
    private BitmapFont white, black;


    @Override
    public void show() {
//        stage= new Stage();
//        Gdx.input.setInputProcessor(stage);
//
//        atlas=new TextureAtlas("ui/button.atlas");
//        skin= new Skin(atlas);
//
//        white=new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
//        black=new BitmapFont(Gdx.files.internal("font/black.fnt"), false);
//
//        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.up=skin.getDrawable("button_up");
//        textButtonStyle.down=skin.getDrawable("button_down");
//        textButtonStyle.pressedOffsetX=2;
//        textButtonStyle.pressedOffsetY=-2;
//        textButtonStyle.font=black;
//
//        table=new Table();
//        table.debug();
//
////        list= new List(skin);
////        list.setItems("one", "two","three");
//
////        scrollPane= new ScrollPane(list, skin);
////
//        play = new TextButton("PLAY", skin);
//        play.pad(10);
//
//
//        back=  new TextButton("BACK", sk2in);
//        back.pad(10);
////        table.add("SELECT LEVEL");
    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(0,0,0,1); //black with 1 alpha
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        stage.act(delta);
//        stage.draw();

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

    }
}
