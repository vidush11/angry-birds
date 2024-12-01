package github.com.Game_Classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import github.com.Main;

public class BuildingBlock {
    BodyDef bodyDef;
    PolygonShape shape;
    FixtureDef fixtureDef;
    Body block;
    Sprite boxSprite;
    public static enum Type{
        wood,
        metal,
        glass
    }
    private Type myType;
    public BuildingBlock(World world, float x, float y, float width, float height, Type type, String s) {
        bodyDef = new BodyDef();
        shape=new PolygonShape();
        fixtureDef= new FixtureDef();
        boxSprite= new Sprite(new Texture(s));

        //slingshot
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
//        super(world, x, y, new Texture(s), width, height, true);

        this.myType = type;
//        switch (this.myType){
//            case wood:
//                super.setHitPoints(5);
//                break;
//            case metal:
//                super.setHitPoints(10);
//                break;
//            case glass:
//                super.setHitPoints(2);
//                break;
//        }

        shape.dispose();
    }

    public void OnHit(PhysicsActor actor){
//        this.setHitPoints(this.getHitPoints() - actor.getHitPoints());
//        if (this.getHitPoints() <= 0){
//            this.dispose();
//            this.remove();
//        }
    };

    public void dispose(){

    }

    public Body getBody(){
        return block;
    }
}
