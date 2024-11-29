package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import github.com.Main;

import java.util.ArrayList;

public class BuildingBlock extends PhysicsActor {
    public static enum types{
        wood,
        metal,
        glass
    }
    private types type;
    public BuildingBlock(World world, float x, float y, float width, float height, types type) {
        super(world, x, y, Main.assetManager.get("img/woodenBlock.png"), width, height, true);
        this.type = type;
        switch (this.type){
            case wood:
                super.setHitPoints(5);
                break;
            case metal:
                super.setHitPoints(10);
                break;
            case glass:
                super.setHitPoints(2);
                break;
        }
        getBody().setUserData(this);
    }

    public void OnHit(ArrayList<BuildingBlock> blockList, PhysicsActor actor){
        this.setHitPoints(this.getHitPoints() - actor.getHitPoints());
        if (this.getHitPoints() <= 0){
            blockList.remove(this);
            this.remove();
            if(getBody() != null && getBody().getWorld().getBodyCount()>0){
                getBody().getWorld().destroyBody(getBody());
            }
        }
    };
}
