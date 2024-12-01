package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PhysicsActor extends Actor {

    private Body body;
    private Texture PhysicsActorTexture; // PhysicsActor's texture (image)
    private int hitPoints;
    private Sprite sprite;

    public PhysicsActor(World world, float x, float y, Texture texture, float width, float height, boolean isBlock) {
        this.body = createPhysicsActorBody(world, x, y, width, height, isBlock);
        setPosition(body.getPosition().x, body.getPosition().y);
        setRotation((float) Math.toDegrees(body.getAngle()));

        PhysicsActorTexture = texture;
        this.sprite = new Sprite(PhysicsActorTexture);

        sprite.setSize(width, height);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        body.setUserData(sprite);

        this.hitPoints = 0;
    }

    public int getHitPoints() { return hitPoints; }
    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }
    public Body getBody() { return body; }
    public void setBody(Body body) { this.body = body;}

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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the PhysicsActor's texture at the current position of the actor
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        // Call the parent's act method to update the actor's position and rotation
        super.act(delta);

        // Update the position and rotation based on the Box2D body
        setPosition(body.getPosition().x, body.getPosition().y);
        setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void dispose() {
        PhysicsActorTexture.dispose();
        sprite.getTexture().dispose();
    }

}
