package github.com.Game_Classes;

public class Projectile {
    public float gravity;
    public float speedx;
    public float speedy;

    public float startx;
    public float starty;

    public Projectile(float g, float sx, float sy, float px, float py){
        this.gravity=g;
        this.speedx=sx;
        this.speedy=sy;
        this.startx=px;
        this.starty=py;
    }

    public float getX(float time){
        return startx+speedx*time;
    }

    public float getY(float time){
        return starty+ speedy*time+0.5f*gravity*time*time;
    }

}
