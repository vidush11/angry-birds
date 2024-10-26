package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import github.com.Main;

public class Bird extends PhysicsActor{

    public Bird(World world, float x, float y, float width, float height) {
        super(world, x, y, Main.assetManager.get("img/RedBird.png"), width, height);
        super.setHitPoints(5);
    }

    public void OnHit(){};
    public void onClick(){};
}
