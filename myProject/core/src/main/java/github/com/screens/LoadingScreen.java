package github.com.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import github.com.Main;

public class LoadingScreen implements Screen {

    private Main game;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private Texture backgroundTexture;
    private Texture loadingBarTexture;
    private Texture fadeImageTexture;
    private SpriteBatch batch;
    private BitmapFont blackFont;
    private Sprite fadeImageSprite;
    private float fadeAlpha = 0f;
    private boolean fadingIn = true;

    private float progress = 0f;

    // Loading bar parameters for anchoring
    private float loadingBarWidth;
    private float loadingBarHeight;
    private float loadingBarY;

    public LoadingScreen(Main game) {
        this.game = game;
        this.stage = new Stage();
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        queueAssets();

        // Load assets for background, fade image, and loading bar
        backgroundTexture = new Texture("img/backgroundLoading.png");  // Resizable background
        loadingBarTexture = new Texture("img/loading_bar.png");        // Loading bar
        fadeImageTexture = new Texture("img/fade_image.png");          // Fading image

        // Set fade image position dynamically based on screen size
        fadeImageSprite = new Sprite(fadeImageTexture);
        fadeImageSprite.setPosition(stage.getViewport().getScreenWidth() * 0.1f, stage.getViewport().getScreenHeight() * 0.5f);

        // Load font once in constructor
        blackFont = new BitmapFont(Gdx.files.internal("font/black.fnt"), false);

        // Dynamically calculate loading bar dimensions and position
        loadingBarWidth = stage.getViewport().getScreenWidth() * 0.5f;
        loadingBarHeight = stage.getViewport().getScreenHeight() * 0.05f;
        loadingBarY = stage.getViewport().getScreenHeight() * 0.1f;
    }

    @Override
    public void show() {}

    private void update(float delta) {
        // If assets are fully loaded, transition to the Splash screen
        updateLoadingBar(delta);
        updateFadeImage(delta);
        if(progress == 1 ) {
            if (game.assetManager.update()) {
                game.setScreen(new Splash(game));
                dispose();
            }
        }
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Start drawing batch
        batch.begin();

        // Draw the resizable background (adjust width and height based on window size)
        batch.draw(backgroundTexture, 0, 0, stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());

        // Update and draw the fade animation for the image
        fadeImageSprite.draw(batch);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(32, (float) stage.getViewport().getScreenHeight()/4 , stage.getViewport().getScreenWidth() - 64, 16);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(32, (float) stage.getViewport().getScreenHeight()/4 , progress * (stage.getViewport().getScreenWidth() - 64), 16);

        batch.end();
        shapeRenderer.end();

        // Transition to next screen when progress is complete
        update(delta);
    }

    private void updateFadeImage(float delta) {
        // Simplified fade-in and fade-out logic using delta time
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
        // Sync the progress bar with the actual asset loading progress
//        progress = game.assetManager.getProgress();  // Get actual progress (0.0 to 1.0)
        progress += (float) (delta*0.2);
        if (progress > 1f) progress = 1f;
    }

    @Override
    public void resize(int width, int height) {}

    private void queueAssets() {
        // Queue up assets to be loaded in the asset manager
        game.assetManager.load("img/background.jpg", Texture.class );
        game.assetManager.load("ui/button.png", Texture.class );
        game.assetManager.load("font/white.fnt", BitmapFont.class );
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Dispose of assets and objects to avoid memory leaks
        batch.dispose();
        blackFont.dispose();
        backgroundTexture.dispose();
        loadingBarTexture.dispose();
        fadeImageTexture.dispose();
    }
}
