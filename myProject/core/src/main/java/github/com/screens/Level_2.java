package github.com.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import github.com.Game_Classes.InputController;
import github.com.Game_Classes.Projectile;
import github.com.Game_Classes.SlingShot;
//import github.com.Game_Classes.SlingShotMouse;
import github.com.Main;

public class Level_2 implements Screen {

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera oCamera;
    private Main game;
    private Body box;
    private Body bird;

    private Sprite boxSprite;
    private SpriteBatch batch;
    private Array<Body> bodies= new Array<>();
    private Texture background;
    private Stage stage;
    private Viewport viewport;
    private Vector2 movement;

    private final float TIMESTEP=1/60f;
    private final int VELOCITYITERATIONS=8;
    private final int POSITIONITERATIONS=3;

    public Level_2(Main game){
        this.game=game;
        this.background = Main.assetManager.get("img/Background_level_1.png");
        this.stage = new Stage();
//        this.viewport= new StretchViewport(1920,1080, this.oCamera);
        this.movement=new Vector2(0,0);

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


        Gdx.input.setInputProcessor(new InputController(){
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
                float x=(screenX-Gdx.graphics.getWidth()/2)/10;
                float y=(-screenY+Gdx.graphics.getHeight()/2)/10;

                if (-24<=x && x<=17 && -8<=y && y<=-3){
                    shoot=true;
                }
                return true;

            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                float x=(screenX-Gdx.graphics.getWidth()/2)/10;
                float y=(-screenY+Gdx.graphics.getHeight()/2)/10;

                if (shoot){
                    final_pos.set(x,y);
                }

                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                shoot=false;
                Vector2 diff= initial.sub(final_pos);
                System.out.println(diff.x+ "diff "+ diff.y);
                Projectile curr_projectile=new Projectile(-10, 5*diff.x, 5*diff.y, -20.65f,-5.5f);
                float deltaT=1;
                bird.setLinearVelocity(5*diff.x, 5*diff.y);
                for (int i=0; i<10; i++){
                    System.out.println(curr_projectile.getX(i*deltaT)+" "+ curr_projectile.getY(i*deltaT));
                }

                return true;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                System.out.println("x: "+(screenX-Gdx.graphics.getWidth()/2)+", y:"+(-screenY+Gdx.graphics.getHeight()/2));
                return true;
            }



        });
        // body definition
        BodyDef bodydef= new BodyDef();
        bodydef.type= BodyDef.BodyType.DynamicBody;
        bodydef.position.set(0,20); // 1m up

        CircleShape shape =  new CircleShape();
        shape.setRadius(5f);

        // fixture defintion
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape= shape;
        fixtureDef.density= 2.5f;
        fixtureDef.friction= 0.7f; //u
        fixtureDef.restitution=0.8f;

        Body ball= world.createBody(bodydef);
        ball.createFixture(fixtureDef);

        //GROUND Definition
        bodydef.type= BodyDef.BodyType.StaticBody;
        bodydef.position.set(0,-10);

        //Ground shape
        ChainShape groundShape= new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(-500,0), new Vector2(500,0)});
        //fixture definition
        fixtureDef.shape=groundShape;
        fixtureDef.friction=.5f;
        fixtureDef.restitution=0f;

        world.createBody(bodydef).createFixture(fixtureDef);

        //BOX
        bodydef.type= BodyDef.BodyType.DynamicBody;
        bodydef.position.set(-20.65f,-5.5f);

        PolygonShape boxShape= new PolygonShape();
        boxShape.setAsBox(1.5f,1.5f);

        fixtureDef.shape=boxShape;
        fixtureDef.friction=.5f;
        fixtureDef.restitution=0.5f;
        fixtureDef.density=0.5f;
        bird= world.createBody(bodydef);
        bird.createFixture(fixtureDef);
        boxSprite= new Sprite(new Texture("./img/RedBird.png"));
        boxSprite.setSize(3,3);
        boxSprite.setOrigin(boxSprite.getWidth()/2, boxSprite.getHeight()/2);
        bird.setUserData(boxSprite);

        //slingshot
        bodydef.type= BodyDef.BodyType.StaticBody;
        bodydef.position.set(-20,-10);

        PolygonShape boxShape_= new PolygonShape();
        boxShape_.setAsBox(10,3);

        fixtureDef.shape=boxShape_;
        fixtureDef.friction=.5f;
        fixtureDef.restitution=0.5f;
        fixtureDef.density=1f;
        Body slingShot= world.createBody(bodydef);
        slingShot.createFixture(fixtureDef);
        boxSprite= new Sprite(new Texture("./img/SlingShot.png"));
        boxSprite.setSize(20,10);
        boxSprite.setOrigin(boxSprite.getWidth()/2, boxSprite.getHeight()/2);
        slingShot.setUserData(boxSprite);
//        box.applyForceToCenter(1000,0,true);

//        SlingShotMouse slingShot= new SlingShotMouse(world, oCamera,0,0);
//        slingShot.show();
        shape.dispose();
        groundShape.dispose();
        boxShape.dispose();


    }



    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(world, oCamera.combined);

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        batch.setProjectionMatrix(oCamera.combined);

//        box.applyForceToCenter(movement, true);
        batch.begin();

//        batch.draw(background, -stage.getViewport().getScreenWidth()/20, -stage.getViewport().getScreenHeight()/20, stage.getViewport().getScreenWidth()/10, stage.getViewport().getScreenHeight()/10);
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
