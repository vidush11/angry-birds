package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import github.com.Main;

public class Ground extends PhysicsActor{

    public Ground(World world, float x, float y, float width, float height) {
        super(world, x, y, Main.assetManager.get("img/ground.png"), width, height);
    }

}
