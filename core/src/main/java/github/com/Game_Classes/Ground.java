package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import github.com.Main;

public class Ground {

    Body body;
    public Ground(World world, int x, int y) {
        body = createBody(world,x,y);

    }

    public Body createBody(World world, int x, int y){
        //GROUND Definition
        BodyDef bodydef= new BodyDef();
        bodydef.type= BodyDef.BodyType.StaticBody;
        bodydef.position.set(x,y);

        //Ground shape
        ChainShape groundShape= new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(-500,0), new Vector2(500,0)});
        //fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape=groundShape;
        fixtureDef.friction=.5f;
        fixtureDef.restitution=0f;

        fixtureDef.filter.categoryBits=Main.BIT_GROUND;
//        fixtureDef.filter.maskBits=Main.BIT_BIRD;
        body = world.createBody(bodydef);
        body.createFixture(fixtureDef);
        body.setUserData(new userData(null, "ground"));
        groundShape.dispose();

        return body;
    }

    public Body getBody(){ return body;}
}
