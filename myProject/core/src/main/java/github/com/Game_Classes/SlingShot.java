package github.com.Game_Classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SlingShot extends Actor {
    private Texture baseTexture;   // Base of the slingshot (stationary)
    private Texture rubberTexture; // Rubber band texture for stretching
    private Vector2 anchorPoint;   // Stationary point for the slingshot
    private Bird loadedBird;      // The bird to be launched
    private boolean isLoaded;  // Whether the bird is being dragged
    private Vector2 trijectory;     // Position where the bird is being pulled

    // Constructor for slingshot
    public SlingShot() {
        isLoaded = false;
        baseTexture = new Texture(Gdx.files.internal("slingshot_base.png"));
        rubberTexture = new Texture(Gdx.files.internal("slingshot_rubber.png"));
        anchorPoint = new Vector2(5, 2);  // Fixed position of the slingshot (you can adjust this)
    }

    // Method to load a new bird into the slingshot
    public void loadBird(Bird bird) {
        loadedBird = bird;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the base of the slingshot
        batch.draw(baseTexture, anchorPoint.x - 1, anchorPoint.y - 1, 2, 4);
    }

    @Override
    public void act(float delta) {}


    // Method to start dragging the bird
    public void startDragging() {}

    // Method to launch the bird
    private void launchBird() {}

    public void dispose() {
        baseTexture.dispose();
        rubberTexture.dispose();
    }
}

