package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import sun.jvm.hotspot.debugger.Address;
import sun.jvm.hotspot.opto.Block;

public class BuildingBlock extends PhysicsActor {

    public BuildingBlock(World world, float x, float y, Texture texture, float width, float height) {
        super(world, x, y, texture, width, height);
    }

    public void OnHit(){};
}
