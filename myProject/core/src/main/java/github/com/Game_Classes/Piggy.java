package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import github.com.Main;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Piggy extends PhysicsActor{
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public Piggy(World world, float x, float y, float width, float height) {
        super(world, x, y, Main.assetManager.get("img/basicPig.png"), width, height, false);
        super.setHitPoints(5);
        getBody().setUserData(this);
    }

    public void OnHit(ArrayList<Piggy> pigList, PhysicsActor obj){
        setHitPoints(getHitPoints() - obj.getHitPoints());
        System.out.println(getHitPoints());
        if (getHitPoints() <= 0){
            scheduler.schedule(() -> {
                synchronized (this) {
                    kill(pigList);
                }
            }, 1, TimeUnit.SECONDS);
        };
    }

    public void kill(ArrayList<Piggy> pigList){
        pigList.remove(this);
//        this.dispose();
        this.remove();
        if(getBody() != null && getBody().getWorld().getBodyCount()>0){
            getBody().getWorld().destroyBody(getBody());
        }
    }
}

