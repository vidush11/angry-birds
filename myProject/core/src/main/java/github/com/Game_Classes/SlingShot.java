package github.com.Game_Classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import github.com.Main;

public class SlingShot extends Actor {
    private Texture slingshotTexture;   // Base of the slingshot (stationary)
    private Sprite sprite;
    private Body body;
    private float width = 8, height = 16;
    private Vector2 anchorPoint;   // Stationary point for the slingshot
    private Bird loadedBird;      // The bird to be launched
    private boolean isLoaded;  // Whether the bird is being dragged
    private Vector2 trijectory;     // Position where the bird is being launched

    // Constructor for slingshot
    public SlingShot(World world, float x, float y) {
        slingshotTexture = Main.assetManager.get("img/SlingShot.png");
        createBody(world, x, y);
        isLoaded = false;
        loadedBird = null;
        trijectory = new Vector2(0,0);
        anchorPoint = new Vector2(x,y);
        sprite = new Sprite(slingshotTexture);
        sprite.setSize(width, height);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        body.setUserData(sprite);
    }

    private void createBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    // Method to load a new bird into the slingshot
    public void loadBird(Bird bird) {
        loadedBird = bird;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the base of the slingshot
        sprite.setPosition(getX(), getY());
        sprite.draw(batch, parentAlpha);
//        batch.draw(slingshotTexture, anchorPoint.x, anchorPoint.y, 64, 64);
    }

    @Override
    public void act(float delta) {}


    // Method to start dragging the bird
    public void startDragging() {}

    // Method to launch the bird
    private void launchBird() {}

    public void dispose() {
        slingshotTexture.dispose();
    }
}

