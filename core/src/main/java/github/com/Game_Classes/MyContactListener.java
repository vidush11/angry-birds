package github.com.Game_Classes;

import com.badlogic.gdx.physics.box2d.*;

import static java.lang.Thread.sleep;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyContactListener implements ContactListener {
    private Set<Object> to_remove = new HashSet<>();

    public class Dhagga implements Runnable{
        private AtomicBoolean flag;

        public Dhagga(AtomicBoolean x){
            this.flag=x;
        }
        public void run() {
            System.out.println("JAAT code");
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("ERRROR");
            }
            flag.set(true);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Object o1 = contact.getFixtureA().getBody();
        Object o2 = contact.getFixtureB().getBody();

        String id1 = ((userData) (((Body) o1).getUserData())).id;
        String id2 = ((userData) (((Body) o2).getUserData())).id;

        if (id1.equals("ground")) {
            Object o3 = o1;
            o1 = o2;
            o2 = o1;
            String id3 = id1;
            id1 = id2;
            id2 = id3;
        }

//        if (id1.equals("piggy")){
        Body b1 = (Body) o1;
        userData data1 = ((userData) b1.getUserData());
        data1.increaseContact();

//            System.out.println("Contacts"+ data.contacts);
        if (data1.id.equals("piggy")) {
            if (data1.contacts > 10) {
                data1.dead.set(true);
            }
        }
        else if (data1.id.equals("block")) {
            if (data1.contacts > 25) {
                data1.dead.set(true);
            }
        }
//        }
//        if (id2.equals("piggy")){
        Body b2 = (Body) o2;
        userData data2 = ((userData) b2.getUserData());

        data2.increaseContact();
//            System.out.println("Contacts"+ data.contacts);
        if (data2.id.equals("piggy")) {
            if (data2.contacts > 10) {
                data2.dead.set(true);
            }
        }
        else if (data2.id.equals("block")){
            if (data2.contacts > 25) {
                data2.dead.set(true);
            }
        }
//        }


        if (id1.equals("bird") && id2.equals("ground") && !to_remove.contains(o1)) {
            to_remove.add(o1);
            Thread t1 = new Thread(new Dhagga(((userData) ((Body) o1).getUserData()).dead));
            t1.start();
        }
//        System.out.println(id1+", "+ id2);
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
//        Object o1=contact.getFixtureA().getBody();
//        Object o2=contact.getFixtureB().getBody();
//
//        String id1=(   (userData)(((Body) o1).getUserData())   ).id;
//        String id2=(   (userData)(((Body) o2).getUserData())   ).id;
//
//        if (id1.equals("ground")){
//            Object o3=o1;
//            o1=o2;
//            o2=o1;
//            String id3=id1;
//            id1=id2;
//            id2=id3;
//        }
//
//        if (id1.equals("piggy")){
//            System.out.println("Impulse: "+contactImpulse.getNormalImpulses());
//        }
//        if (id2.equals("piggy")){
//            System.out.println("Impulse: "+contactImpulse.getNormalImpulses()[0]+", "+contactImpulse.getNormalImpulses()[1]);
//        }
    }
}
