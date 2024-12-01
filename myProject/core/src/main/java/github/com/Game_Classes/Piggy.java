package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import github.com.Main;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Piggy extends PhysicsActor implements Serializable {
    String texturePath;
    public Piggy(World world, float x, float y, float width, float height, String s) {
        super(world, x, y, s, width, height, false);
        texturePath = s;

        Filter filter = super.getBody().getFixtureList().get(0).getFilterData();
        filter.categoryBits=Main.BIT_PIG;
        super.getBody().getFixtureList().get(0).setFilterData(filter);
        Object curr_user_data= super.getBody().getUserData();
        super.getBody().setUserData(new userData((Sprite)curr_user_data, "piggy"));
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        bodyWrapper = SerializableBodyWrapper.wrap(getBody());
        out.defaultWriteObject();
    }

    private void remakeBody(World world){
        setWorld(world);
        setBody(bodyWrapper.recreateBody(world));
        addSpriteToBody(texturePath);
        Filter filter=super.getBody().getFixtureList().get(0).getFilterData();
        filter.categoryBits=Main.BIT_PIG;
        super.getBody().getFixtureList().get(0).setFilterData(filter);
        Object curr_user_data= super.getBody().getUserData();
        super.getBody().setUserData(new userData((Sprite)curr_user_data, "piggy"));
    }
}
