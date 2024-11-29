package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import github.com.Main;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bird extends PhysicsActor{
    boolean abilityActive = true;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Bird(World world, float x, float y, float width, float height) {
        super(world, x, y, Main.assetManager.get("img/RedBird.png"), width, height, false);
        super.setHitPoints(5);
        getBody().setActive(false);
        getBody().setUserData(this);
    }

    public void OnHit(Object collidedWith){
        if (collidedWith instanceof Ground){
            Ground g = (Ground)collidedWith;
            abilityActive = false;
        }

        scheduler.schedule(() -> {
            synchronized (this) {  // Use synchronization to prevent race conditions
//                this.dispose();
                this.remove();
                getBody().getWorld().destroyBody(getBody());
            }
        }, 5, TimeUnit.SECONDS);
    };

    public void loadOnSlingShot(){
//        System.out.println("hii");
        getBody().setActive(true);
        getBody().setTransform(-20.65f, -4.5f, getBody().getAngle());
    }
    public void onClick(){
        if (abilityActive){
            abilityActive = false;
        }
    };
}
