package github.com.Game_Classes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.*;

public class SerializableBodyWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    // Key body properties to serialize
    private Vector2 position;
    private float angle;
    private Vector2 linearVelocity;
    private float angularVelocity;
    private BodyDef.BodyType bodyType;
    private boolean isActive;
    private userData userData;

    // Fixture properties
    private float friction;
    private float restitution;
    private float density;
    private float width;
    private float height;
    private boolean isCircle;

    // Constructor to capture body properties
    private SerializableBodyWrapper(Body body) {
        this.position = body.getPosition().cpy();
        this.angle = body.getAngle();
        this.linearVelocity = body.getLinearVelocity().cpy();
        this.angularVelocity = body.getAngularVelocity();
        this.bodyType = body.getType();
        this.isActive = body.isActive();
        this.userData = (userData) body.getUserData();

        // Capture first fixture properties (assumes single fixture)
        Fixture firstFixture = body.getFixtureList().first();
        if (firstFixture != null) {
            this.friction = firstFixture.getFriction();
            this.restitution = firstFixture.getRestitution();
            this.density = firstFixture.getDensity();

            // Determine shape type and dimensions
            Shape shape = firstFixture.getShape();
            if (shape instanceof PolygonShape) {
                PolygonShape polygonShape = (PolygonShape) shape;
                Vector2 dimensions = new Vector2();
                polygonShape.getVertex(1, dimensions);
                this.width = Math.abs(dimensions.x * 2);
                this.height = Math.abs(dimensions.y * 2);
                this.isCircle = false;
            } else if (shape instanceof CircleShape) {
                CircleShape circleShape = (CircleShape) shape;
                this.width = circleShape.getRadius() * 2;
                this.height = this.width;
                this.isCircle = true;
            }
        }
    }

    // Static method to create wrapper from Body
    public static SerializableBodyWrapper wrap(Body body) {
        if(body.getUserData() == null) {
            return null;
        }
        return new SerializableBodyWrapper(body);
    }

    // Method to recreate Body in a Box2D World
    public Body recreateBody(World world) {
        // Create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.angle = angle;
        bodyDef.type = bodyType;

        // Create body
        Body body = world.createBody(bodyDef);

        // Create fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.density = density;

        // Create appropriate shape
        Shape shape;
        if (isCircle) {
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(width / 2);
            shape = circleShape;
        } else {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(width / 2, height / 2);
            shape = polygonShape;
        }
        fixtureDef.shape = shape;

        // Create fixture
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setActive(isActive);
        body.setLinearVelocity(linearVelocity);
        body.setAngularVelocity(angularVelocity);
        body.setUserData(userData);
        return body;
    }

    public float getHeight() {return height;}
    public float getWidth() {return width;}
}
