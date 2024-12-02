package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sun.tools.javac.jvm.Code;
import github.com.Main;

import java.io.Serializable;

public class Wall implements Serializable {
    transient BodyDef bodyDef;
    transient ChainShape shape;
    transient FixtureDef fixtureDef;
    transient Body wall;

    public Wall(World world, int x, int y) {
        bodyDef = new BodyDef();
        shape=new ChainShape();
        shape.createChain(new Vector2[]{new Vector2(0,-500), new Vector2(0,500)});

        fixtureDef= new FixtureDef();

        //slingshot
        bodyDef.type= BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);


        fixtureDef = new FixtureDef();
        fixtureDef.shape=shape;
        fixtureDef.friction=.5f;
        fixtureDef.restitution=0f;

        fixtureDef.filter.categoryBits= Main.BIT_GROUND;

        wall= world.createBody(bodyDef);
        wall.createFixture(fixtureDef);

        wall.setUserData(new userData(null, "wall"));

        shape.dispose();
    }

    public Body getBody(){
        return this.wall;
    }
}
