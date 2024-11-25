package BlockingAgents;

import itumulator.world.World;

import java.util.List;


public class Wolf extends Predator{

    public Wolf(World world) {
        super(20, world);
        this.world = world;
    }

    public void act() {

        // Currently fighting
        if (currentlyFighting()) {
            System.out.println("Wolf Fighting");

            // check fighting chances
            if (currentlyWinning()) {
                System.out.println("Wolf Winning");


            } else {

                flee();


            }

        }


    }

    protected void flee() {

    }

    public void joinPack() {

    }

    /*
    public List<Wolf> getPack() {


    }*/

    public boolean currentlyFighting() {
        // Check if currently in  a fight

        return true; // XXX temp
    }

    public boolean currentlyWinning() {

        return true; // XXX temp
    }

    public int getClosestPackDistance() {

        return 5;
    }


}
