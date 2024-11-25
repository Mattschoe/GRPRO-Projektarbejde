package BlockingAgents;

import itumulator.executable.DisplayInformation;
import itumulator.world.World;

import java.util.List;


public class Wolf extends Predator {

    boolean currentlyFighting;

    public Wolf(World world) {
        super(20, world);
        this.world = world;
        this.currentlyFighting = false;
    }

    public void act() {


        if (currentlyFighting()) {
            if (currentlyWinning()) {
                fight();
            } else {
                flee();
            }
        } else if (this.maxHealth / this.health < 2) {
            // Recover health
            this.energyLevel--;
            this.health++;

        } else if (isHunting()) {

            // call pack

            hunt();


        } else if (calledForHunt()) {
            // call pack

            hunt();

        } else if (isHungry()) {
            // Find pray

            // Start hunt (probably "tell" pack to join)

        }



    }

    private boolean calledForHunt() {

        return false;
    }

    private boolean isHunting() {

        return false; // XXX temp
    }


    protected void reproduce() {}

    protected void sleep() {}

    protected void flee() {

    }

    void fight() {
        startFight();



    }

    public void joinPack() {

    }


    public List<Animal> getPack() {

        return null;
    }

    public List<Animal> getEnemies() {

        return null;
    }

    public boolean isHungry() {

        return (this.maxEnergy / this.energyLevel ) < 2; // Needs tweaking
    }


    // check if currently fighting
    public boolean currentlyFighting() {
        return currentlyFighting;
    }

    public void startFight() {
        this.currentlyFighting = true;
    }

    public void endFight() {
        this.currentlyFighting = false;
    }

    // Get winning chances
    public boolean currentlyWinning() {

        return true; // XXX temp
    }

    public DisplayInformation getInformation() {

        return null;
    }



}
