package github.com.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import github.com.Game_Classes.*;
import github.com.Main;

import java.util.ArrayList;

public class Level_Test implements Screen {

    private Main game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;
    private ArrayList<Bird> birds;
    private ArrayList<Piggy> pigs;
    private ArrayList<BuildingBlock> buildingBlocks;
    private SlingShot slingShot;
    private Ground ground;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private Array<Body> temp;

    private final float TIMESTEP = 1/60f;
    private final int VELOCITY_ITERATIONS = 8;
    private final int POSITION_ITERATIONS = 3;

    public Level_Test(Main game) {
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.batch = new SpriteBatch();
        this.birds = new ArrayList<>();
        this.pigs = new ArrayList<>();
        this.buildingBlocks = new ArrayList<>();
        this.world = new World(new Vector2(0, -20), true);
        this.debugRenderer = new Box2DDebugRenderer();
        this.camera = new OrthographicCamera();
        temp = new Array<>();

        this.background = Main.assetManager.get("img/Background_level_1.png");
    }
    @Override
    public void show() {
//        ground = new Ground(world, 0, -100, stage.getWidth(), 8);
        slingShot = new SlingShot(world, -10, -84);
        birds.add(new Bird(world, -20, 16, 8, 8 ));
        pigs.add(new Piggy(world, 20, 16, 8, 8 ));

        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up=new TextureRegionDrawable(new TextureRegion(new Texture("ui/pause_up.png")));
        imageButtonStyle.over= new TextureRegionDrawable(new TextureRegion(new Texture("ui/pause_over.png")));
        Button pause= new ImageButton(imageButtonStyle);
        pause.setSize(65,65);
        pause.setPosition(stage.getWidth() - 64, stage.getHeight() - 64);

        pause.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new OptionsMenu(game));
//                dispose();
            }
        });

        ImageButton.ImageButtonStyle imageButtonStyle2 = new ImageButton.ImageButtonStyle();
        imageButtonStyle2.up=new TextureRegionDrawable(new TextureRegion(new Texture("ui/winLoose_up.png")));
        imageButtonStyle2.over= new TextureRegionDrawable(new TextureRegion(new Texture("ui/winLoose_over.png")));
        Button winLoose= new ImageButton(imageButtonStyle2);
        winLoose.setSize(65,65);
        winLoose.setPosition(stage.getWidth() - 64, 128);

        winLoose.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new WinLoose(game, new Level_2(game)));
//                dispose();
            }
        });

        stage.addActor(winLoose);
        stage.addActor(pause);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode){
                System.out.println("hi");
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                camera.zoom += amountY/25f;
                camera.zoom = camera.zoom < 0.5 ? 0.5f : camera.zoom > 2 ? 2 : camera.zoom;
                camera.update();
                System.out.println(camera.zoom);
                return true;
            }
        });

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.getBodies(temp);
        for (Body body : temp){
            if (body.getUserData()!=null && body.getUserData() instanceof Sprite){
                Sprite sprite = (Sprite)body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
        }
//
//        batch.draw(background, 0, 0, stage.getWidth(), stage.getHeight());
//
//        for (Bird bird : birds) {
//            bird.draw(batch, 1);
//        }
//
//        for (Piggy piggy : pigs) {
//            piggy.draw(batch, 1);
//        }
//
//        for (BuildingBlock buildingBlock : buildingBlocks) {
//            buildingBlock.draw(batch, 1);
//        }
//
//        ground.draw(batch, 1);
//        slingShot.draw(batch, 1);
//
//
        batch.end();
//
//        stage.act(delta);
//        stage.draw();
        debugRenderer.render(world, camera.combined);
        world.step(TIMESTEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width/2;
        camera.viewportHeight = height/2;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        stage.dispose();
        batch.dispose();
    }
}
