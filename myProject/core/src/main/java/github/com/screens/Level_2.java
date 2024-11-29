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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import github.com.Game_Classes.*;
//import github.com.Game_Classes.InputController;
import github.com.Main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import github.com.Game_Classes.Projectile;
import github.com.Game_Classes.SlingShot;

import github.com.Main;
public class Level_2 implements Screen {

    private boolean options=false;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera oCamera;
    private Main game;
    private Body box;
    private BodyDef bird_body= new BodyDef();

    private Body currBird;

    private Sprite boxSprite;
    private SpriteBatch batch;
    private Array<Body> bodies= new Array<>();
    private Texture background;
    private Stage stage;
    private Viewport viewport;
    private Vector2 movement;
    private ShapeRenderer shape;

    private LinkedList<Bird> BirdQueue;
    private ArrayList<Piggy> PigList;
    private ArrayList<BuildingBlock> Blocks;

    private Vector2 initial=new Vector2(-20.65f,-3.5f);
    private Vector2 final_pos=new Vector2(-20.65f,-3.5f);
    private boolean shoot=false;
    private Projectile projectile= new Projectile(-10,0,0,0,0);

    private final float TIMESTEP=1/60f;
    private final int VELOCITYITERATIONS=8;
    private final int POSITIONITERATIONS=3;

    public Level_2(Main game){
        this.game=game;
        this.background = Main.assetManager.get("img/Background_level_1.png");
        this.stage = new Stage();
//        this.viewport= new StretchViewport(1920,1080, this.oCamera);
        this.movement=new Vector2(0,0);
        BirdQueue = new LinkedList<>();
        PigList = new ArrayList<>();
        Blocks = new ArrayList<>();

    }



    @Override
    public void show() {
        world = new World(new Vector2(0,-10f), true); //vector 2 for x,y acc //10 newton/kg down
        debugRenderer= new Box2DDebugRenderer();
        batch= new SpriteBatch();
        oCamera = new OrthographicCamera();
        shape = new ShapeRenderer();

        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean keyDown(int keycode) {
                if (keycode== Input.Keys.ESCAPE) {
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new LevelMenu(game));
//                    dispose();
                }

                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float x= (float) (screenX - Gdx.graphics.getWidth() / 2) /10;
                float y= (float) (-screenY + Gdx.graphics.getHeight() / 2) /10;

                if (-23<=x && x<=-20 && -8<=y && y<=-3){
                    if (!(BirdQueue.isEmpty()) && currBird==null && !shoot){
                        Bird bird = BirdQueue.poll();
                        bird.loadOnSlingShot();
                        currBird = bird.getBody();
                        shoot = false;
                    }
                    else if (currBird != null){
                        shoot=true;
                    }
                }
                else if (24<=x && x<=31.5 && 15.5<=y &&y<=23.5){
                    options=true;
                }
                return true;

            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                float x= (float) (screenX - Gdx.graphics.getWidth() / 2) /10;
                float y= (float) (-screenY + Gdx.graphics.getHeight() / 2) /10;
//                System.out.println(shoot);

                if (shoot){
                    final_pos.set(x,y);
                    projectile=new Projectile(-10, 5*(initial.x-x), 5*(initial.y-y), -20.65f,-3.5f);

                }

                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if(currBird != null && shoot){
                    Vector2 diff= initial.sub(final_pos);
                    currBird.setTransform(-20.65f,-3.5f, currBird.getAngle());
                    currBird.setLinearVelocity(5*diff.x + 5, 5*diff.y);
                    currBird = null;
                    shoot=false;
                    initial.set(-20.65f,-3.5f);
                }
                else if (options){
                    game.setScreen(new OptionsMenu(game));
                    options=false;
//                    ((Game)Gdx.app.getApplicationListener()).setScreen(new OptionsMenu(game, screen));
//                    dispose();
                }
                return true;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
//                System.out.println("x: "+(screenX-Gdx.graphics.getWidth()/2)+", y:"+(-screenY+Gdx.graphics.getHeight()/2));
                return true;
            }

        });

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Object objA = contact.getFixtureA().getBody().getUserData();
                Object objB = contact.getFixtureB().getBody().getUserData();
                if (objA instanceof Bird && objB instanceof Piggy){
                    Bird bird = (Bird)objA;
                    Piggy piggy = (Piggy)objB;
                    bird.OnHit(piggy);
                    piggy.OnHit(PigList, bird);
                }
                else if (objA instanceof Piggy && objB instanceof Bird) {
                    Bird bird = (Bird)objB;
                    Piggy piggy = (Piggy)objA;
                    bird.OnHit(piggy);
                    piggy.OnHit(PigList, bird);
                }
                else if (objA instanceof Ground && objB instanceof Bird){
                    Bird bird = (Bird)objB;
                    bird.OnHit((Ground)objA);
                }
                else if (objA instanceof Bird && objB instanceof Ground){
                    Bird bird = (Bird)objA;
                    bird.OnHit((Ground)objB);
                }
                else if (objA instanceof BuildingBlock && objB instanceof Bird){
                    Bird bird = (Bird)objB;
                    BuildingBlock block = (BuildingBlock)objA;
                    bird.OnHit(block);
                    block.OnHit(Blocks, bird);
                }
                else if (objA instanceof Bird && objB instanceof BuildingBlock){
                    Bird bird = (Bird)objA;
                    BuildingBlock block = (BuildingBlock)objB;
                    bird.OnHit(block);
                    block.OnHit(Blocks, bird);
                }
                else if (objA instanceof Piggy && objB instanceof BuildingBlock){
                    BuildingBlock block = (BuildingBlock)objB;
                    Piggy piggy = (Piggy)objA;
                    piggy.OnHit(PigList, block);
                    block.OnHit(Blocks, piggy);
                }
                else if (objA instanceof BuildingBlock && objB instanceof Piggy){
                    BuildingBlock block = (BuildingBlock)objA;
                    Piggy piggy = (Piggy)objB;
                    piggy.OnHit(PigList, block);
                    block.OnHit(Blocks, piggy);
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });
        //Ground declaration
        Ground ground = new Ground(world);

        //Bird
        BirdQueue.add(new Bird(world, -23.65f, -13.5f, 3f, 3f));
        BirdQueue.add(new Bird(world, -26.65f, -13.5f, 3f, 3f));
        BirdQueue.add(new Bird(world, -29.65f, -13.5f, 3f, 3f));

        PigList.add(new Piggy(world, 23.65f, -12.5f, 3f, 3f));

        Blocks.add(new BuildingBlock(world, 16.65f, -12.5f, 3f, 3f));

        BodyDef bodydef = new BodyDef();
        //slingshot
        bodydef.type= BodyDef.BodyType.StaticBody;
        bodydef.position.set(-20,-8);

        PolygonShape boxShape= new PolygonShape();
        boxShape.setAsBox(5,1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0.5f;
        fixtureDef.density = 1f;
        Body slingShot= world.createBody(bodydef);
        slingShot.createFixture(fixtureDef);
        boxSprite= new Sprite(new Texture("./img/SlingShot.png"));
        boxSprite.setSize(20,10);
        boxSprite.setOrigin(boxSprite.getWidth()/2, boxSprite.getHeight()/2);
        slingShot.setUserData(boxSprite);

        boxShape.dispose();

        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up=new TextureRegionDrawable(new TextureRegion(new Texture("ui/pause_up.png")));
        imageButtonStyle.over= new TextureRegionDrawable(new TextureRegion(new Texture("ui/pause_over.png")));
        Button pause= new ImageButton(imageButtonStyle);
        pause.setSize(65,65);
        pause.setPosition(Gdx.graphics.getWidth()-75, Gdx.graphics.getHeight()-75);
        stage.addActor(pause);
    }



    @Override
    public void render(float delta)
    {
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
            if (body.getUserData() != null && body.getUserData() instanceof PhysicsActor) {
                Sprite sprite = (Sprite) ((PhysicsActor) body.getUserData()).getSprite();
                sprite.setPosition(body.getPosition().x-sprite.getWidth()/2, body.getPosition().y- sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
            else if(body.getUserData() != null && body.getUserData() instanceof Sprite){
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x-sprite.getWidth()/2, body.getPosition().y- sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }

        }
        batch.end();
        if (shoot){
            drawTrajectory();
        }

        if (PigList.size() <= 0){
            game.setScreen(new WinLoose(game, new Level_2(game)));
        }
        Iterator<Piggy> iterator = PigList.iterator(); // Use an iterator for safe removal
        while (iterator.hasNext()) {
            Piggy pig = iterator.next();
            if (pig.getBody().getPosition().x > 35 || pig.getBody().getPosition().x < -30) {
                // Clean up resources related to the pig
                pig.dispose();
                pig.remove();
                if(pig.getBody() != null && pig.getBody().getWorld().getBodyCount()>0){
                    pig.getBody().getWorld().destroyBody(pig.getBody());
                }
                // Remove the pig from the list
                iterator.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
//        viewport.update(width, height, true);
        oCamera.viewportWidth= width/10;
        oCamera.viewportHeight= height/10;
        oCamera.update();
        stage.getViewport().update(width,height);

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
        boxSprite.getTexture().dispose();
        stage.dispose();
    }

    public void drawTrajectory(){
        float deltaT= 0.11f;
        float x_;
        float y_;
        for (int i=0; i<12; i++){
            x_=projectile.getX(i*deltaT);
            y_=projectile.getY(i*deltaT);

            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(0,0,0,1-.08f*i);
            shape.circle(x_, y_, 0.3f-0.015f*i,100);
            shape.end();
        }

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.BROWN);
        shape.rectLine(-21.5f, initial.y, final_pos.x, final_pos.y,0.5f);
        shape.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.BROWN);
        shape.rectLine(-19.5f, initial.y, final_pos.x, final_pos.y,0.5f);
        shape.end();

        currBird.setTransform(final_pos.x, final_pos.y,currBird.getAngle());

    }
}
