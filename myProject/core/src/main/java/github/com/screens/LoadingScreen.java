package github.com.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen implements Screen {

    private Texture backgroundTexture;
    private Texture loadingBarTexture;
    private Texture fadeImageTexture;
    private SpriteBatch batch;

    private Sprite fadeImageSprite;
    private float fadeAlpha = 0f;
    private boolean fadingIn = true;

    private float progress = 0f;

    // Loading bar parameters for anchoring
    private float loadingBarWidth = 500f;
    private float loadingBarHeight = 20f;
    private float loadingBarY = 50f;  // Distance from bottom

    public LoadingScreen() {
//      this.game = game;
        batch = new SpriteBatch();
        // Load assets for background, fade image, and loading bar
        backgroundTexture = new Texture("img/backgroundLoading.png");  // Resizable background
        loadingBarTexture = new Texture("img/loading_bar.png"); // Loading bar
        fadeImageTexture = new Texture("img/fade_image.png");   // Fading image

        fadeImageSprite = new Sprite(fadeImageTexture);
        fadeImageSprite.setPosition(100, 300); // Position image
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the resizable background (adjust width and height based on window)
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw and animate the fading image
        updateFadeImage(delta);
        fadeImageSprite.draw(batch);

        // Update and draw anchored loading bar
        updateLoadingBar(delta);
        float loadingBarX = (Gdx.graphics.getWidth() - loadingBarWidth) / 2;  // Center horizontally
        batch.draw(loadingBarTexture, loadingBarX, loadingBarY, loadingBarWidth * progress, loadingBarHeight*5);  // Anchored loading bar
//        try {
//            wait(50000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        batch.end();
    }

    private void updateFadeImage(float delta) {
        if (fadingIn) {
            fadeAlpha += delta; // Increase alpha
            if (fadeAlpha >= 1f) fadingIn = false;
        } else {
            fadeAlpha -= delta; // Decrease alpha
            if (fadeAlpha <= 0f) fadingIn = true;
        }
        fadeImageSprite.setAlpha(fadeAlpha);
    }

    private void updateLoadingBar(float delta) {
        // Simulate loading progress
        progress += delta * 0.2f; // Adjust speed as needed
        if (progress > 1f) progress = 1f;
    }

    @Override
    public void resize(int width, int height) {
        // Adjust positions or sizes here if needed based on screen size
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        loadingBarTexture.dispose();
        fadeImageTexture.dispose();
    }

    // Implement other Screen interface methods (resize, show, hide, pause, resume)
}