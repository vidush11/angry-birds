package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import github.com.Main;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bird extends PhysicsActor{
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public Bird(World world, float x, float y, float width, float height) {
        super(world, x, y, Main.assetManager.get("img/RedBird.png"), width, height);
        super.setHitPoints(5);

    }

    public void OnHit(ArrayList<Bird> birdList){
        scheduler.schedule(() -> {
            synchronized (this) {  // Use synchronization to prevent race conditions
                birdList.remove(this);
                this.dispose();
                this.remove();
            }
        }, 5, TimeUnit.SECONDS);
    };
    public void onClick(){};
}
