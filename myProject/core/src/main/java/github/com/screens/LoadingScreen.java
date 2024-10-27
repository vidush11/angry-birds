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
    private Texture fadeImageTexture;
    private SpriteBatch batch;
    private BitmapFont blackFont;
    private Sprite fadeImageSprite;
    private float fadeAlpha = 0f;
    private boolean fadingIn = true;

    private float progress = 0f;

    public LoadingScreen(Main game) {
        this.game = game;
        this.stage = new Stage();
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        queueAssets();

        // Load assets for background and logo
        backgroundTexture = new Texture("img/loading_screen.png");  // Resizable background
        fadeImageTexture = new Texture("img/logo.png");          // Fading image

        // Set logo image position dynamically based on screen size
        fadeImageSprite = new Sprite(fadeImageTexture);
        fadeImageSprite.setSize(350,55);
        fadeImageSprite.setPosition((stage.getViewport().getScreenWidth() - fadeImageSprite.getWidth())*0.5f, stage.getViewport().getScreenHeight() * 0.8f);

        // Load font once in constructor
        blackFont = new BitmapFont(Gdx.files.internal("font/black.fnt"), false);
    }

    @Override
    public void show() {}

    private void update(float delta) {
        // If assets are fully loaded, transition to the Splash screen
        updateLoadingBar(delta);
        updateFadeImage(delta);
        if(progress == 1 ) {
            if (game.assetManager.update()) {
                game.setScreen(new MainMenu(game));
                dispose();
            }
        }
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Draw the background
        batch.draw(backgroundTexture, 0, 0, stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());

        // draw the fade animation for the image
        fadeImageSprite.draw(batch);

        blackFont.draw(batch, "LOADING...", 32, (float) stage.getViewport().getScreenHeight()/6 + 64);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(32, (float) stage.getViewport().getScreenHeight()/8 , stage.getViewport().getScreenWidth() - 64, 16);
        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.rect(32, (float) stage.getViewport().getScreenHeight()/8 , progress * (stage.getViewport().getScreenWidth() - 64), 16);

        batch.end();
        shapeRenderer.end();

        // Transition to next screen when progress is complete
        update(delta);
    }

    private void updateFadeImage(float delta) {
        if (fadingIn) {
            fadeAlpha = (float) (progress * 2);
            if (fadeAlpha >= 1f){
                fadingIn = false;
                fadeAlpha = 1f;
            }
        }
        fadeImageSprite.setAlpha(fadeAlpha);
    }

    private void updateLoadingBar(float delta) {
//         Sync the progress bar with the actual asset loading progress
//        progress = Main.assetManager.getProgress();  // Get actual progress (0.0 to 1.0)
        progress += (float) (delta*0.2);
        if (progress > 1f) progress = 1f;
    }

    @Override
    public void resize(int width, int height) {}

    private void queueAssets() {
        // Queue up assets to be loaded in the asset manager
        Main.assetManager.load("img/background.jpg", Texture.class );
        Main.assetManager.load("ui/button.png", Texture.class );
        Main.assetManager.load("font/white.fnt", BitmapFont.class );
        Main.assetManager.load("img/Background_level_1.png", Texture.class );
        Main.assetManager.load("img/RedBird.png", Texture.class );
        Main.assetManager.load("img/basicPig.png", Texture.class );
        Main.assetManager.load("img/SlingShot.png", Texture.class );
        Main.assetManager.load("img/woodenBlock.png", Texture.class );
        Main.assetManager.load("img/ground.png", Texture.class );
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
        fadeImageTexture.dispose();
        shapeRenderer.dispose();
        stage.dispose();
    }
}
