package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.Disposable;
import github.com.Main;

import java.io.IOException;
import java.io.Serializable;

public class BuildingBlock implements Serializable {
    transient BodyDef bodyDef;
    transient PolygonShape shape;
    transient FixtureDef fixtureDef;
    transient Body block;
    transient Sprite boxSprite;

    SerializableBodyWrapper bodyWrapper;
    String texturePath;
    public static enum Type{
        wood,
        metal,
        glass
    }
    private Type myType;
    public BuildingBlock(World world, float x, float y, float width, float height, Type type, String s) {
        bodyDef = new BodyDef();
        shape = new PolygonShape();
        fixtureDef= new FixtureDef();
        texturePath=s;
        boxSprite= new Sprite(new Texture(s));

        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);

        shape.setAsBox(width/2.0f,height/2.0f);

        fixtureDef.shape = shape;
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0.5f;
        if (type==Type.glass){
            fixtureDef.density = 1f;

        } else if (type==Type.metal){
            fixtureDef.density = 1f;

        } else if (type==Type.wood){
            fixtureDef.density = 1f;

        }
        block= world.createBody(bodyDef);
        block.createFixture(fixtureDef);

        boxSprite.setSize(width,height);
        boxSprite.setOrigin(boxSprite.getWidth()/2, boxSprite.getHeight()/2);
        block.setUserData(new userData(boxSprite,"block"));

        this.myType = type;

        shape.dispose();
    }

    public void remakeBody(World world){
        if (bodyWrapper == null){
            BuildingBlock temp = new BuildingBlock(world,1, 1, 1, 1, myType, texturePath);
            block = temp.block;
            block.setUserData(null);
            return;
        }
        block = bodyWrapper.recreateBody(world);
        reAddSprite(texturePath);
    }

    private void reAddSprite(String texturePath){
        boxSprite = new Sprite(new Texture(texturePath));
        boxSprite.setSize(bodyWrapper.getWidth(), bodyWrapper.getHeight());
        boxSprite.setOrigin(boxSprite.getWidth() / 2, boxSprite.getHeight() / 2);
        userData data = (userData) block.getUserData();
        data.sprite = boxSprite;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        bodyWrapper = SerializableBodyWrapper.wrap(block);
        out.defaultWriteObject();
    }

    public void dispose(){

    }

    public Body getBody(){
        return block;
    }
}
