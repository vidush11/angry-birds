package github.com.Game_Classes;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.concurrent.atomic.AtomicBoolean;

public class userData {
    public Sprite sprite;
    public String id;
    public AtomicBoolean dead=new AtomicBoolean(false);
    public int contacts;
    public userData(Sprite s, String i){
        sprite=s;
        id=i;
        contacts=0;
    }

    public int increaseContact(){
        return contacts++;

    }
}
