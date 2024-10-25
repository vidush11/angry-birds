package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Piggy extends PhysicsActor{
    public Piggy(World world, float x, float y, Texture texture, float width, float height) {
        super(world, x, y, texture, width, height);
    }

    public void OnHit(){};
}
