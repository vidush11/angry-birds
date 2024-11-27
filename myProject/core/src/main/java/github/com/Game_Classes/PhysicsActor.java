package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PhysicsActor extends Actor {

    private final Body body;
    private  Texture PhysicsActorTexture; // PhysicsActor's texture (image)
    private int hitPoints;
    private Sprite sprite;

    public PhysicsActor(World world, float x, float y, Texture texture, float width, float height) {
        this.body = createPhysicsActorBody(world, x, y, width, height);
        setPosition(body.getPosition().x, body.getPosition().y);  // Set initial position
        setRotation((float) Math.toDegrees(body.getAngle()));  // Set initial rotation
        // Load the texture (image) for the PhysicsActor
        PhysicsActorTexture = texture;
        this.hitPoints = 0;
        // Set the size of the actor (for rendering)
        this.sprite = new Sprite(PhysicsActorTexture);
        sprite.setSize(width, height);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        body.setUserData(sprite);
    }

    public int getHitPoints() { return hitPoints; }
    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }

    // Method to create the PhysicsActor's body in the Box2D world
    private static Body createPhysicsActorBody(World world, float x, float y, float width, float height) {
        // Define the body type and initial position
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;  // Make it a dynamic body (so it responds to forces)
        bodyDef.position.set(x, y);  // Initial position in the world

        // Define the shape of the PhysicsActor (let's assume it's a box for simplicity)
        PolygonShape PhysicsActorShape = new PolygonShape();
        PhysicsActorShape.setAsBox(width, height);  // Set a 2x2 box shape (you can adjust the size)

        // Define the fixture properties for the PhysicsActor
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = PhysicsActorShape;
        fixtureDef.density = 1f;  // Adjust density (affects mass)
        fixtureDef.friction = 0.5f;  // Friction when in contact with other surfaces
        fixtureDef.restitution = 0.2f;  // Bounciness

        // Create the body in the world
        Body body = world.createBody(bodyDef);

        // Attach the fixture to the body
        body.createFixture(fixtureDef);

        // Dispose of the shape after use
        PhysicsActorShape.dispose();

        // Return the created body
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
