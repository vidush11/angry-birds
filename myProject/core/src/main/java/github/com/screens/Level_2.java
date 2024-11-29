package github.com.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import github.com.Game_Classes.Bird;
import github.com.Game_Classes.Ground;
import github.com.Game_Classes.Projectile;
import github.com.Game_Classes.SlingShot;
//import github.com.Game_Classes.SlingShotMouse;
import github.com.Main;

import java.util.LinkedList;
import java.util.Vector;

public class Level_2 implements Screen {

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera oCamera;
    private Main game;
//    private Body box;
    private Body currBird;

    private Sprite boxSprite;
    private SpriteBatch batch;
    private Array<Body> bodies= new Array<>();
    private Texture background;
    private Stage stage;
    private Viewport viewport;
    private Vector2 movement;

    private LinkedList<Bird> BirdQueue;

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

    }

    private Vector2 initial=new Vector2(-20.65f,-5.5f);
    private Vector2 final_pos=new Vector2(-20.65f,-5.5f);
    private boolean shoot=false;

    @Override
    public void show() {
        world = new World(new Vector2(0,-10f), true); //vector 2 for x,y acc //10 newton/kg down
        debugRenderer= new Box2DDebugRenderer();
        batch= new SpriteBatch();
        oCamera = new OrthographicCamera();


        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean keyDown(int keycode) {
                if (keycode== Input.Keys.ESCAPE) {
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new LevelMenu(game));
//                    dispose();
                }
                else if (keycode== Input.Keys.RIGHT){
                    movement.x=30;
                }
                else if (keycode== Input.Keys.LEFT){
                    movement.x=-30;
                }
                else if (keycode== Input.Keys.UP){
                    movement.y=50;
                }
                else if (keycode== Input.Keys.DOWN){
                    movement.y=-10;
                }

                return true;
            }
            @Override
            public boolean keyUp(int keycode) {
                if (keycode== Input.Keys.RIGHT){
                    movement.x=0;
                }
                else if (keycode== Input.Keys.LEFT){
                    movement.x=0;
                }
                else if (keycode== Input.Keys.UP){
                    movement.y=0;
                }
                else if (keycode== Input.Keys.DOWN){
                    movement.y=0;
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
                return true;

            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                float x= (float) (screenX - Gdx.graphics.getWidth() / 2) /10;
                float y= (float) (-screenY + Gdx.graphics.getHeight() / 2) /10;
                System.out.println(shoot);

                if (shoot){
                    final_pos.set(x,y);
                }

                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if(currBird != null && shoot){
                    Vector2 diff= initial.sub(final_pos);
                    Projectile curr_projectile=new Projectile(-10, 5*diff.x, 5*diff.y, -20.65f,-5.5f);
                    float deltaT=1;
                    currBird.setLinearVelocity(5*diff.x + 5, 5*diff.y);
                    currBird = null;
                    shoot=false;
                    initial.set(-20.65f,-5.5f);

                    for (int i=0; i<10; i++){
                        System.out.println(curr_projectile.getX(i*deltaT)+" "+ curr_projectile.getY(i*deltaT));
                    }

                }
                return true;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                System.out.println("x: "+(screenX-Gdx.graphics.getWidth()/2)+", y:"+(-screenY+Gdx.graphics.getHeight()/2));
                return true;
            }

        });

        //Ground declaration
        Ground ground = new Ground(world);

        //Bird
        BirdQueue.add(new Bird(world, -23.65f, -9.5f, 3f, 3f));
        BirdQueue.add(new Bird(world, -24.65f, -9.5f, 3f, 3f));
        BirdQueue.add(new Bird(world, -25.65f, -9.5f, 3f, 3f));


        BodyDef bodydef = new BodyDef();
        //slingshot
        bodydef.type= BodyDef.BodyType.StaticBody;
        bodydef.position.set(-20,-8);

        PolygonShape boxShape= new PolygonShape();
        boxShape.setAsBox(10,1);

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

//        box.applyForceToCenter(1000,0,true);

//        SlingShotMouse slingShot= new SlingShotMouse(world, oCamera,0,0);
//        slingShot.show();
//        shape.dispose();
        boxShape.dispose();


    }



    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        batch.setProjectionMatrix(oCamera.combined);

//        box.applyForceToCenter(movement, true);
        batch.begin();

//        batch.draw(background, (float) -stage.getViewport().getScreenWidth() /20, (float) -stage.getViewport().getScreenHeight() /20, (float) stage.getViewport().getScreenWidth() /10, (float) stage.getViewport().getScreenHeight() /10);
//        background.setSize
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x-sprite.getWidth()/2, body.getPosition().y- sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }

        }
        batch.end();
        debugRenderer.render(world, oCamera.combined);

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
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
}
