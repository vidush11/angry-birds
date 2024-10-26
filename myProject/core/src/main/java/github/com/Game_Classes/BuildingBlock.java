package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import github.com.Main;

public class BuildingBlock extends PhysicsActor {

    public BuildingBlock(World world, float x, float y, float width, float height) {
        super(world, x, y, Main.assetManager.get("img/woodenBlock.png"), 2, 8);
        super.setHitPoints(10);
    }

    public void OnHit(){};
}
