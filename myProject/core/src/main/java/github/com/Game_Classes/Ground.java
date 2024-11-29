package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import github.com.Main;

public class Ground {

    Body body;
    public Ground(World world) {
        body = createBody(world);
    }

    public Body createBody(World world){
        //GROUND Definition
        BodyDef bodydef= new BodyDef();
        bodydef.type= BodyDef.BodyType.StaticBody;
        bodydef.position.set(0,-10);

        //Ground shape
        ChainShape groundShape= new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(-500,-5), new Vector2(500,-5)});
        //fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape=groundShape;
        fixtureDef.friction=.5f;
        fixtureDef.restitution=0f;

        body = world.createBody(bodydef);
        body.createFixture(fixtureDef);
        groundShape.dispose();

        return body;
    }

    public Body getBody(){ return body;}
}
