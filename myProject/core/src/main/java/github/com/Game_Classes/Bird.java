package github.com.Game_Classes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import github.com.Main;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bird extends PhysicsActor{
    boolean abilityActive = true;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Bird(World world, float x, float y, float width, float height, String s) {
        super(world, x, y, new Texture(s), width, height, false);
        super.setHitPoints(5);

//        super.setUserData
        Filter filter=super.getBody().getFixtureList().get(0).getFilterData();
        filter.categoryBits=Main.BIT_BIRD;
//        filter.maskBits=M
        Object curr_user_data= super.getBody().getUserData();
        super.getBody().setUserData(new userData((Sprite)curr_user_data, "bird"));
        super.getBody().setActive(false);
    }

    public void OnHit(ArrayList<Bird> birdList, Object collidedWith){
        if (collidedWith instanceof Ground){
            Ground g = (Ground)collidedWith;
            abilityActive = false;
        }

//        scheduler.schedule(() -> {
//            synchronized (this) {  // Use synchronization to prevent race conditions
//                birdList.remove(this);
//                this.dispose();
//                this.remove();
//            }
//        }, 5, TimeUnit.SECONDS);
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
