
//public class Level_1 implements Screen {
//
//    private Main game;
//    private Stage stage;
//    private SpriteBatch batch;
//    private Texture background;
//    private ArrayList<Bird> birds;
//    private ArrayList<Piggy> pigs;
//    private ArrayList<BuildingBlock> buildingBlocks;
//    private SlingShot slingShot;
//    private Ground ground;
//    private World world;
//    private Screen screen=this;
//
//    public Level_1(Main game) {
//        this.game = game;
//        this.stage = new Stage();
//        Gdx.input.setInputProcessor(stage);
//        this.batch = new SpriteBatch();
//        this.birds = new ArrayList<>();
//        this.pigs = new ArrayList<>();
//        this.buildingBlocks = new ArrayList<>();
//        this.world = new World(new Vector2(0, 0), true);
////        this.ground = new Ground(world, 0, 0, stage.getWidth(), 32);
//        this.slingShot = new SlingShot(world, 21, 40);
//        this.background = Main.assetManager.get("img/Background_level_1.png");
//    }
//    @Override
//    public void show() {
//        birds.add(new Bird(world, 32, 32, 40, 40 ));
////        pigs.add(new Piggy(world, stage.getWidth() - 64, 32, 40, 40 ));
//
//        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
//        imageButtonStyle.up=new TextureRegionDrawable(new TextureRegion(new Texture("ui/pause_up.png")));
//        imageButtonStyle.over= new TextureRegionDrawable(new TextureRegion(new Texture("ui/pause_over.png")));
//        Button pause= new ImageButton(imageButtonStyle);
//        pause.setSize(65,65);
//        pause.setPosition(stage.getWidth() - 64, stage.getHeight() - 64);
//
//        pause.addListener(new ClickListener(){
//            public void clicked(InputEvent event, float x, float y) {
//
//                ((Game)Gdx.app.getApplicationListener()).setScreen(new OptionsMenu(game, screen));
////                dispose();
//            }
//        });
//
//        ImageButton.ImageButtonStyle imageButtonStyle2 = new ImageButton.ImageButtonStyle();
//        imageButtonStyle2.up=new TextureRegionDrawable(new TextureRegion(new Texture("ui/winLoose_up.png")));
//        imageButtonStyle2.over= new TextureRegionDrawable(new TextureRegion(new Texture("ui/winLoose_over.png")));
//        Button winLoose= new ImageButton(imageButtonStyle2);
//        winLoose.setSize(65,65);
//        winLoose.setPosition(stage.getWidth() - 64, 128);
//
//        winLoose.addListener(new ClickListener(){
//            public void clicked(InputEvent event, float x, float y) {
//
//                ((Game)Gdx.app.getApplicationListener()).setScreen(new WinLoose(game, Level_1.this));
//                dispose();
//            }
//        });
//
//        stage.addActor(winLoose);
//        stage.addActor(pause);
//    }
//
//    @Override
//    public void render(float delta) {
//
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        batch.begin();
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
////        for (BuildingBlock buildingBlock : buildingBlocks) {
////            buildingBlock.draw(batch, 1);
////        }
//
////        ground.draw(batch, 1);
//        slingShot.draw(batch, 1);
//
//
//        batch.end();
//
//        stage.act(delta);
//        stage.draw();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//    @Override
//    public void dispose() {
//        stage.dispose();
//        batch.dispose();
//    }
//}


package github.com.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import github.com.Game_Classes.*;
import github.com.Game_Classes.InputController;
import github.com.Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import github.com.Game_Classes.Projectile;
import github.com.Game_Classes.SlingShot;

import github.com.Main;
import static java.lang.Thread.sleep;

public class Level_1 implements Screen, Serializable {

    private static final String SAVE_FILE_PATH = "level_1_save.ser";
    private transient World world;
    private transient Box2DDebugRenderer debugRenderer;
    private transient OrthographicCamera oCamera;
    private transient Main game;
    private transient Body box;
    private transient BodyDef bird_body = new BodyDef();

    private transient Body currBird;
    private transient Body prevBird;
    private Bird currBIRD;
    private Bird prevBIRD;
    private transient Stage stage;
    private transient Sprite boxSprite;
    private transient SpriteBatch batch;
    private transient Array<Body> bodies = new Array<>();
    private transient Texture background;
    private transient Viewport viewport;
    private Vector2 movement;
    private transient ShapeRenderer shape;

    private LinkedList<Bird> BirdQueue;
    private ArrayList<Piggy> PigList;
    private ArrayList<BuildingBlock> blocks;

    private Vector2 initial = new Vector2(-20.65f, -3.5f);
    private Vector2 final_pos = new Vector2(-20.65f, -3.5f);
    private boolean shoot = false;
    private boolean shootSound = false;
    private boolean powerUp=false;
    private AtomicBoolean worldEnd= new AtomicBoolean(false);
    private boolean end=false;
    private AtomicBoolean delay= new AtomicBoolean(false);
    private boolean delayOnce=false;
    private boolean options = false;
    private transient Screen screen = this;

    private Projectile projectile = new Projectile(-10, 0, 0, 0, 0);
    private int score;

    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8;
    private final int POSITIONITERATIONS = 3;
    private float MAX_SPEED=6;

    public int x=1;
    public Level_1(Main game) {
        this.game = game;
        this.background = new Texture("img/bg6.jpg");
        this.stage = new Stage();
        this.movement = new Vector2(0, 0);
        BirdQueue = new LinkedList<>();
        PigList = new ArrayList<>();
        blocks= new ArrayList<>();
        world = new World(new Vector2(0, -10f), true); //vector 2 for x,y acc //10 newton/kg down
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        oCamera = new OrthographicCamera();
        shape = new ShapeRenderer();
        initialize();

    }


    @Override
    public void show() {

        world.setContactListener(new MyContactListener());
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu(game));
                }

                // Save game state when 'S' is pressed
                if (keycode == Input.Keys.S) {
                    saveGameState();
                    return true;

                }

                // Load game state when 'L' is pressed
                if (keycode == Input.Keys.L) {
                    loadGameState();
                    return true;

                }

                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float x = (float) (screenX - Gdx.graphics.getWidth() / 2) / 10;
                float y = (float) (-screenY + Gdx.graphics.getHeight() / 2) / 10;

                if (-23 <= x && x <= -20 && -8 <= y && y <= -3) {
                    if (!(BirdQueue.isEmpty()) && currBird==null &&!shoot) {
                        shootSound=false;
                        currBIRD = BirdQueue.poll();
                        currBIRD.loadOnSlingShot();
                        currBird = currBIRD.getBody();
                        shoot = false;
                    } else if (currBird != null) {
                        shoot = true;
                    }
                }
                else if (24<=x && x<=31.5 && 15.5<=y &&y<=23.5){
                    options=true;
                }
                else if(prevBird!=null && prevBird.getUserData()!=null && !((userData)prevBird.getUserData()).dead.get() ){
                    powerUp=true;
                }
                return true;

            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                float x = (float) (screenX - Gdx.graphics.getWidth() / 2) / 10;
                float y = (float) (-screenY + Gdx.graphics.getHeight() / 2) / 10;
                System.out.println(shoot);

                if (shoot) {
                    if (!shootSound){
                        Main.sound1.play(0f);
                        shootSound=true;
                    }
                    final_pos.set(x, y);
                    float diffx=initial.x-x;
                    float diffy=initial.y-y;
                    float speed= (float) Math.sqrt(Math.pow(initial.x-x,2)+ Math.pow(initial.y-y, 2));
                    if (speed>6){
                        diffx=diffx*6/speed;
                        diffy=diffy*6/speed;
                    }
                    projectile = new Projectile(-10, 5 * diffx, 5 * diffy, -20.65f, -3.5f);
                }

                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (currBird != null && shoot) {
                    currBird.setTransform(initial.x, initial.y, currBird.getAngle());

                    Vector2 diff = initial.sub(final_pos);
                    Main.sound2.play(0f);

                    float speed= (float) Math.sqrt(Math.pow(diff.x,2)+ Math.pow(diff.y, 2));
                    if (speed>6){
                        diff.set(diff.x*6/speed, diff.y*6/speed);
                    }

                    currBird.setLinearVelocity(5 * diff.x + 5, 5 * diff.y);
                    prevBird=currBird;
                    prevBIRD=currBIRD;
                    currBIRD=null;
                    currBird = null;
                    shoot = false;
                    initial.set(-20.65f, -3.5f);
                }

                else if (options){
                    game.setScreen(new OptionsMenu(game, screen));
                    options=false;
//                    ((Game)Gdx.app.getApplicationListener()).setScreen(new OptionsMenu(game, screen));
//                    dispose();
                }

                else if(prevBird!=null && ((userData)prevBird.getUserData() != null) && powerUp){
                    (prevBIRD).powerUp();
                    prevBird=null;
                    prevBIRD=null;
                    powerUp=false;
                }
                return true;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
//                System.out.println("x: " + (screenX - Gdx.graphics.getWidth() / 2) + ", y:" + (-screenY + Gdx.graphics.getHeight() / 2));
                return true;
            }

        });
    }
    public void initialize() {


        //Ground declaration
        Ground ground = new Ground(world,0,-15);
        ground= new Ground(world, 0,25);

        Wall wall= new Wall(world,30,0);
        wall=new Wall(world,-30,0);
        //Bird
        BirdQueue.add(new Bird(world, -21.65f, -15f, 3f, 3f,"./img/pigs/RedBird.png"));

        BirdQueue.add(new Bird(world, -24.65f, -15f, 2f, 2f,"./img/pigs/blue.png"));
        BirdQueue.add(new Bird(world, -27.65f, -15f, 2f, 2f,"./img/pigs/yellow.png"));


        BodyDef bodydef = new BodyDef();
        //slingshot
        bodydef.type = BodyDef.BodyType.StaticBody;
        bodydef.position.set(-20, -8);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(5, 1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0.5f;
        fixtureDef.density = 1f;
        Body slingShot = world.createBody(bodydef);
        slingShot.createFixture(fixtureDef);
        boxSprite = new Sprite(new Texture("./img/SlingShot.png"));
        boxSprite.setSize(20, 10);
        boxSprite.setOrigin(boxSprite.getWidth() / 2, boxSprite.getHeight() / 2);
        slingShot.setUserData(new userData(boxSprite,"slingshot"));

        boxShape.dispose();

        PigList.add(new Piggy(world, 6.5f, -7.8f, 3f, 3f,"img/pigs/green.png"));
        PigList.add(new Piggy(world, 12.5f, -7.8f, 3f, 3f,"img/pigs/green.png"));
        PigList.add(new Piggy(world, 18.5f, -7.8f, 3f, 3f,"img/pigs/green.png"));
        PigList.add(new Piggy(world, 26.5f, -2.5f, 3f, 3f,"img/pigs/green.png"));


        blocks.add(new BuildingBlock(world, 6.5f, -10.5f, 1f, 4.5f, BuildingBlock.Type.metal,"img/Stone elements/elementStone025.png"));
        blocks.add(new BuildingBlock(world, 12.5f, -10.5f, 1f, 4.5f, BuildingBlock.Type.metal,"img/Stone elements/elementStone025.png"));
        blocks.add(new BuildingBlock(world, 18.5f, -10.5f, 1f, 4.5f, BuildingBlock.Type.metal,"img/Stone elements/elementStone025.png"));
        blocks.add(new BuildingBlock(world, 26.5f, -8.5f, 1f, 9f, BuildingBlock.Type.metal,"img/Stone elements/elementStone025.png"));

        blocks.add(new BuildingBlock(world, 6.5f, -8f, 4.5f, 1f, BuildingBlock.Type.wood,"img/Wood elements/elementWood015.png"));
        blocks.add(new BuildingBlock(world, 12.5f, -8f, 4.5f, 1f, BuildingBlock.Type.wood,"img/Wood elements/elementWood015.png"));
        blocks.add(new BuildingBlock(world, 18.5f, -8f, 4.5f, 1f, BuildingBlock.Type.wood,"img/Wood elements/elementWood015.png"));
        blocks.add(new BuildingBlock(world, 26.5f, -6f, 4.5f, 1f, BuildingBlock.Type.wood,"img/Wood elements/elementWood015.png"));


        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up=new TextureRegionDrawable(new TextureRegion(new Texture("ui/pause_up.png")));
        imageButtonStyle.over= new TextureRegionDrawable(new TextureRegion(new Texture("ui/pause_over.png")));
        Button pause= new ImageButton(imageButtonStyle);
        pause.setSize(65,65);
        pause.setPosition(Gdx.graphics.getWidth()-75, Gdx.graphics.getHeight()-75);

//        pause.addListener(new ClickListener(){
//            public void clicked(InputEvent event, float x, float y) {
//
//
//            }
//        });

        stage.addActor(pause);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, oCamera.combined);

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
        shape.setProjectionMatrix(oCamera.combined);
        batch.setProjectionMatrix(oCamera.combined);


//        box.applyForceToCenter(movement, true);
        batch.begin();

        batch.draw(background, (float) -stage.getViewport().getScreenWidth() /20, (float) -stage.getViewport().getScreenHeight() /20, (float) stage.getViewport().getScreenWidth() /10, (float) stage.getViewport().getScreenHeight() /10);
//        background.setSize
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getUserData() != null && body.getUserData() instanceof userData) {
                Sprite sprite = ((userData) body.getUserData()).sprite;
                if (sprite==null) continue;
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }

        }
        batch.end();

        stage.act(delta);
        stage.draw();

        if (shoot) {
            drawTrajectory();
        }
        if (!world.isLocked()){
            for (Body body : bodies) {
                if (body.getUserData() != null && body.getUserData() instanceof userData) {
                    userData data = ((userData) body.getUserData());
                    if (data.dead.get()){
                        if (!data.id.equals("bird")){
                            score+=5000;
                        }
                        world.destroyBody(body);
                    }

                }
            }
        }
        boolean dead=true;
        for (Piggy pig: PigList){
            if (pig.getBody().getUserData()!=null){dead=false; break;}
        }
        if (dead){
            if (! end){
                Thread t1 = new Thread(new Level_1.Dhagga(worldEnd));
                t1.start();
                end = true;
            }
            if (worldEnd.get()) {
                for (int i=0; i<BirdQueue.size(); i++){
                    score+=5000;
                }
                if (currBird!=null) score+=5000;

                System.out.println("SCORE: "+score);
                ((Game) Gdx.app.getApplicationListener()).setScreen(new WinLoose(game, score, 70000,true,"level_1"));
                dispose();
            }
        }
        else {
            if (BirdQueue.isEmpty() && currBird==null){ //no birds left and curr bird also dead
                if (!delayOnce){
                    Thread t1 = new Thread(new Level_1.Dhagga(delay));
                    t1.start();
                    delayOnce=true;
                }
                if (delay.get()) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new WinLoose(game, score, 70000, false, "level 1"));
                    dispose();
                }
            }

        }
    }

    @Override
    public void resize(int width, int height) {
//        viewport.update(width, height, true);
        oCamera.viewportWidth = width / 10;
        oCamera.viewportHeight = height / 10;
        oCamera.update();
        stage.getViewport().update(width, height);

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
        world.dispose();
        debugRenderer.dispose();
        boxSprite.getTexture().dispose();
        stage.dispose();
    }

    public void drawTrajectory() {
        float deltaT = 0.11f;
        float x_;
        float y_;
        for (int i = 0; i < 12; i++) {
            x_ = projectile.getX(i * deltaT);
            y_ = projectile.getY(i * deltaT);

            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(255, 255, 255, 1 - .08f * i);
            shape.circle(x_, y_, 0.3f - 0.015f * i, 100);
            shape.end();
        }

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.BROWN);
        shape.rectLine(-21.5f, initial.y, final_pos.x, final_pos.y, 0.5f);
        shape.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.BROWN);
        shape.rectLine(-19.5f, initial.y, final_pos.x, final_pos.y, 0.5f);
        shape.end();

        currBird.setTransform(final_pos.x, final_pos.y, currBird.getAngle());

    }
    public class Dhagga implements Runnable{
        private AtomicBoolean flag;

        public Dhagga(AtomicBoolean x){
            this.flag=x;
        }
        public void run() {
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("error");
            }
            flag.set(true);
        }
    }

    // Method to save the game state
    private void saveGameState() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(SAVE_FILE_PATH)));
            // Serialize core game state
            out.writeObject(this);
            System.out.println("Game state saved successfully!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Method to load the game state
    private void loadGameState() {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(SAVE_FILE_PATH)))) {

            // Deserialize the saved game state
            Level_1 savedGame = (Level_1) in.readObject();

            world.dispose();
            world = new World(new Vector2(0, -10f), true);

            Ground ground= new Ground(world,0,-15);
            ground= new Ground(world, 0,25);

            Wall wall= new Wall(world,30,0);
            wall=new Wall(world,-30,0);
            makeSlingShot();

            this.BirdQueue = savedGame.BirdQueue;
            this.PigList = savedGame.PigList;
            this.blocks = savedGame.blocks;

            this.bodies.clear();
            world.getBodies(bodies);
            this.currBIRD = savedGame.currBIRD;
            this.prevBIRD = savedGame.prevBIRD;
            for(Bird bird: BirdQueue){
                bird.remakeBody(world);
            }
            for(Piggy pig: PigList){
                pig.remakeBody(world);
            }
            for(BuildingBlock block: blocks){
                block.remakeBody(world);
            }
            if (currBIRD != null){
                currBIRD.remakeBody(world);
                this.currBird = currBIRD.getBody();
            }
            if (prevBIRD != null){
                prevBIRD.remakeBody(world);
                this.prevBird = prevBIRD.getBody();
            }

            this.shoot = savedGame.shoot;
            this.shootSound = savedGame.shootSound;
            this.movement = savedGame.movement;

            this.powerUp = savedGame.powerUp;
            this.worldEnd.set(savedGame.worldEnd.get());
            this.end = savedGame.end;
            this.options = savedGame.options;
            this.score = savedGame.score;
            screen = this;

            this.initial = savedGame.initial;
            this.final_pos = savedGame.final_pos;

            show();
            System.out.println("Game state loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game state: " + e.getMessage());
        }
    }
    private void makeSlingShot(){
        BodyDef bodydef = new BodyDef();
        //slingshot
        bodydef.type = BodyDef.BodyType.StaticBody;
        bodydef.position.set(-20, -8);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(5, 1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0.5f;
        fixtureDef.density = 1f;
        Body slingShot = world.createBody(bodydef);
        slingShot.createFixture(fixtureDef);
        boxSprite = new Sprite(new Texture("./img/SlingShot.png"));
        boxSprite.setSize(20, 10);
        boxSprite.setOrigin(boxSprite.getWidth() / 2, boxSprite.getHeight() / 2);
        slingShot.setUserData(new userData(boxSprite,"slingshot"));

        boxShape.dispose();
    }
}
