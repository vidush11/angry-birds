//package github.com.Game_Classes;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.InputAdapter;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.Camera;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.math.Vector3;
//import com.badlogic.gdx.physics.box2d.*;
//import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
//import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
//import com.sun.org.apache.xerces.internal.xs.ItemPSVI;
//import github.com.Main;
//import sun.java2d.CRenderer;
//
//public class SlingShotMouse extends InputAdapter implements Screen {
//    private MouseJointDef jointDef;
//    private MouseJoint joint;
//    // Base of the slingshot (stationary)
//    private Texture slingshotTexture;   // Base of the slingshot (stationary)
//
//    private Sprite sprite;
//    private Body body;
//    private float anchorx;
//    private float anchory;
//
//    private int width=1;
//    private int height=2;
//
//    private World world;
//    private OrthographicCamera camera;
//
//    public SlingShotMouse(World w, OrthographicCamera c, float x, float y){
//        world=w;
//        camera=c;
//
//        anchorx=x;
//        anchory = y;
//
//        slingshotTexture = Main.assetManager.get("img/SlingShot.png");
//
//    }
//    @Override
//    public void show() {
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        bodyDef.position.set(anchorx, anchory);
//
//        Gdx.input.setInputProcessor(this);
//
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(width / 2f, height / 2f);
//
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape= shape;
////        fixtureDef.density= 2.5f;
////        fixtureDef.friction= 0.7f; //u
////        fixtureDef.restitution=0.8f;
//
//        body= world.createBody(bodyDef);
//        body.createFixture(fixtureDef);
//
//        jointDef = new MouseJointDef();
//        jointDef.bodyA= body;
////        jointDef.bodyB??/
//        jointDef.collideConnected=true;
//        jointDef.maxForce=300;
////        jointDef.
//
//        shape.dispose();
//    }
//
//    @Override
//    public void render(float v) {
//
//    }
//
//    @Override
//    public void resize(int i, int i1) {
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
//        world.dispose();
//    }
//
////    private Vector3 temp= new Vector3();
////    private Vector2 temp2= new Vector2();
//
////    private QueryCallback queryCallBack =  new QueryCallback() {
////        @Override
////        public boolean reportFixture(Fixture fixture) {
////            try {
////                if (!fixture.testPoint(temp.x, temp.y)) return false;
////
////                jointDef.bodyB = fixture.getBody();
////                jointDef.target.set(temp.x, temp.y);
////                joint = (MouseJoint) world.createJoint(jointDef);
////                return false;
////            }
////            catch (Exception e){
////                return false;
////            }
////        }
////    };
//
//    @Override
////    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
////        camera.unproject(temp.set(screenX, screenY,0));
////        world.QueryAABB(queryCallBack, temp.x, temp.y, temp.x, temp.y);
////
////        return true;
////
////    }
////
////    @Override
////    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
////
////        return true;
////    }
////
////    @Override
////    public boolean touchDragged(int screenX, int screenY, int pointer) {
////        if (joint== null) return false;
////
////        camera.unproject(temp.set(screenX, screenY,0 ));
////        joint.setTarget(temp2.set(temp.x, temp.y));
//////        joint.setTarget();
////        return true;
////    }
//}
