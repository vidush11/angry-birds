package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Box2DActor extends Actor {
    private final Body body;  // Box2D body for physics simulation
    private Texture texture;

    public Box2DActor(Body body) {
        this.body = body;  // Set the body for the actor
        this.texture = texture; //Set texture for the actor
        setPosition(body.getPosition().x, body.getPosition().y);  // Set initial position
        setRotation((float) Math.toDegrees(body.getAngle()));  // Set initial rotation
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Update the position and rotation based on the Box2D body
        setPosition(body.getPosition().x, body.getPosition().y);
        setRotation((float) Math.toDegrees(body.getAngle()));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public Body getBody() {
        return body;
    }

    public abstract void dispose();
}
