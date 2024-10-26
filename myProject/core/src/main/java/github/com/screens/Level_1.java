package github.com.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import github.com.Game_Classes.*;
import github.com.Main;

import java.util.ArrayList;

public class Level_1 implements Screen {

    private final Main game;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Texture background;
    private ArrayList<Bird> birds;
    private ArrayList<Piggy> pigs;
    private ArrayList<BuildingBlock> buildingBlocks;
    private SlingShot slingShot;
    private Ground ground;
    private World world;

    public Level_1(Main game) {
        this.game = game;
        this.stage = new Stage();
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.birds = new ArrayList<>();
        this.pigs = new ArrayList<>();
        this.buildingBlocks = new ArrayList<>();
        this.world = new World(new Vector2(0, 0), true);
        this.ground = new Ground(world, 0, 0, stage.getWidth(), 32);
        this.slingShot = new SlingShot(world, 21, 40);
        this.background = Main.assetManager.get("img/Background_level_1.png");
    }
    @Override
    public void show() {
        birds.add(new Bird(world, 32, 32, 40, 40 ));
        pigs.add(new Piggy(world, stage.getWidth() - 64, 32, 40, 40 ));
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(background, 0, 0, stage.getWidth(), stage.getHeight());

        for (Bird bird : birds) {
            bird.draw(batch, 1);
        }

        for (Piggy piggy : pigs) {
            piggy.draw(batch, 1);
        }

        for (BuildingBlock buildingBlock : buildingBlocks) {
            buildingBlock.draw(batch, 1);
        }

        ground.draw(batch, 1);
        slingShot.draw(batch, 1);
        batch.end();

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
