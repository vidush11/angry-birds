package github.com.Game_Classes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import github.com.Main;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bird extends PhysicsActor implements Serializable {
    boolean abilityActive = true;
    private String type;
    private SerializableBodyWrapper bodyWrapper;

    public Bird(World world, float x, float y, float width, float height, String s) {
        super(world, x, y, s, width, height, false);
        type=s;
        Filter filter=super.getBody().getFixtureList().get(0).getFilterData();
        filter.categoryBits=Main.BIT_BIRD;
        filter.maskBits=Main.BIT_GROUND|Main.BIT_PIG|1;
        Object curr_user_data= super.getBody().getUserData();
        super.getBody().setUserData(new userData((Sprite)curr_user_data, "bird"));
        super.getBody().setActive(false);
    }

    public void OnHit(ArrayList<Bird> birdList, Object collidedWith){
        if (collidedWith instanceof Ground){
            Ground g = (Ground)collidedWith;
            abilityActive = false;
        }
    };

    public void loadOnSlingShot(){
        getBody().setActive(true);
        getBody().setTransform(-20.65f, -4.5f, getBody().getAngle());
    }

    public void powerUp(){
        if (type.equals("./img/pigs/blue.png")){
            float x=this.getBody().getPosition().x;
            float y=this.getBody().getPosition().y;
            Vector2 velocity=this.getBody().getLinearVelocity();
            double angle=(float) Math.atan(velocity.y/velocity.x);

            double speed=Math.sqrt(Math.pow(velocity.x, 2) + Math.pow(velocity.y, 2));

            Bird b1=new Bird(getWorld(), x,y, 2f, 2f,"./img/pigs/blue.png");
            b1.getBody().setActive(true);
            b1.getBody().setLinearVelocity((float) (speed*Math.cos(angle+Math.toRadians(15))), (float) (speed*Math.sin(angle+Math.toRadians(15))));


            Bird b2=new Bird(getWorld(), x,y, 2f, 2f,"./img/pigs/blue.png");
            b2.getBody().setActive(true);
            b2.getBody().setLinearVelocity((float) (speed*Math.cos(angle-Math.toRadians(15))), (float) (speed*Math.sin(angle-Math.toRadians(15))));


        }
        else if (type.equals("./img/pigs/yellow.png")){
            float x=this.getBody().getLinearVelocity().x;
            float y=this.getBody().getLinearVelocity().y;
            this.getBody().setLinearVelocity(2*x, 2*y);
        }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        bodyWrapper = SerializableBodyWrapper.wrap(getBody());
        out.defaultWriteObject();
    }

    public void remakeBody(World world){
        System.out.println("making body");
        if (bodyWrapper == null){
            Bird bird = new Bird(world, getX(), getY(), getWidth(), getHeight(), type);
            bird.getBody().setUserData(null);
            setBody(bird.getBody());
            System.out.println("null data");
            return;
        }
        setWorld(world);
        setBody(bodyWrapper.recreateBody(world));
        addSpriteToBody(type, bodyWrapper);
        Filter filter=super.getBody().getFixtureList().get(0).getFilterData();
        filter.categoryBits=Main.BIT_BIRD;
        filter.maskBits=Main.BIT_GROUND|Main.BIT_PIG|1;
    }

}
