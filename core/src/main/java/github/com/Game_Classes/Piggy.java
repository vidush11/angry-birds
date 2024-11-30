package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import github.com.Main;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Piggy extends PhysicsActor{
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public Piggy(World world, float x, float y, float width, float height, String s) {
        super(world, x, y, new Texture(s), width, height, false);
//        super.getUser
        super.setHitPoints(5);
        Filter filter=super.getBody().getFixtureList().get(0).getFilterData();
        filter.categoryBits=Main.BIT_PIG;
        super.getBody().getFixtureList().get(0).setFilterData(filter);
        Object curr_user_data= super.getBody().getUserData();
        super.getBody().setUserData(new userData((Sprite)curr_user_data, "piggy"));
    }

    public void OnHit(ArrayList<Piggy> pigList, Bird bird)
    {
//        setHitPoints(getHitPoints() - bird.getHitPoints());
//        if (getHitPoints() <= 0){
//            scheduler.schedule(() -> {
//                synchronized (this) {
//                    pigList.remove(this);
//                    this.dispose();
//                    this.remove();
//                }
//            }, 1, TimeUnit.SECONDS);
//        };
    }

}
