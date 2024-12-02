package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import java.io.IOException;
import java.io.Serializable;

public class PhysicsActor extends Actor implements Serializable {

    private transient Body body;
    private transient World world;
    private transient Texture PhysicsActorTexture; // PhysicsActor's texture (image)
    private transient Sprite sprite;


    public PhysicsActor(World world, float x, float y, String texturePath, float width, float height, boolean isBlock) {
        this.body = createPhysicsActorBody(world, x, y, width, height, isBlock);
        setPosition(body.getPosition().x, body.getPosition().y);
        setRotation((float) Math.toDegrees(body.getAngle()));

        this.world = world;
        PhysicsActorTexture = new Texture(texturePath);
        this.sprite = new Sprite(PhysicsActorTexture);

        sprite.setSize(width, height);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        body.setUserData(sprite);
//        bodyWrapper = SerializableBodyWrapper.wrap(body);
    }

    public Body getBody() { return body; }
    public void setBody(Body body) { this.body = body;}
    public World getWorld() { return world; }
    public void setWorld(World world) { this.world = world; }

    // Method to create the PhysicsActor's body in the Box2D world
    private static Body createPhysicsActorBody(World world, float x, float y, float width, float height, boolean isBlock) {

        // Define the body type and initial position
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        Shape PhysicsActorShape;
        if (isBlock) {
            PolygonShape temp = new PolygonShape();
            temp.setAsBox(width, height);
            PhysicsActorShape = temp;
        }
        else{
            CircleShape temp = new CircleShape();
            temp.setRadius(width/2);
            PhysicsActorShape = temp;
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = PhysicsActorShape;
        fixtureDef.friction=.5f;
        fixtureDef.restitution=0.5f;
        fixtureDef.density=0.5f;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        PhysicsActorShape.dispose();

        return body;
    }

    public void addSpriteToBody(String texturePath, SerializableBodyWrapper bodyWrapper) {
        PhysicsActorTexture = new Texture(texturePath);
        this.sprite = new Sprite(PhysicsActorTexture);

        sprite.setSize(bodyWrapper.getWidth(), bodyWrapper.getHeight());
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        userData temp = (userData)body.getUserData();
        temp.sprite = sprite;
        body.setUserData(temp);
        System.out.println("Sprite added");
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(body.getPosition().x, body.getPosition().y);
        setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void dispose() {
        PhysicsActorTexture.dispose();
        sprite.getTexture().dispose();
    }
}
