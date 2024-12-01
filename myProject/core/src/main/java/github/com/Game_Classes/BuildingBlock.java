package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import github.com.Main;

public class BuildingBlock {
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
        fixtureDef.density = 1f;
        block= world.createBody(bodyDef);
        block.createFixture(fixtureDef);

        boxSprite.setSize(width,height);
        boxSprite.setOrigin(boxSprite.getWidth()/2, boxSprite.getHeight()/2);
        block.setUserData(new userData(boxSprite,"block"));

        this.myType = type;

        shape.dispose();
    }

    private void remakeBody(World world){
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

    public void dispose(){

    }
}
