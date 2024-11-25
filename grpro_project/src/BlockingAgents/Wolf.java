package BlockingAgents;

import itumulator.executable.DisplayInformation;
import itumulator.world.World;

import java.util.List;


public class Wolf extends Predator{

    boolean currentlyFighting;
    boolean iscurrentlyHunting;
    boolean calledForHunt;

    public Wolf(World world) {
        super(20, world);
        this.world = world;

        this.currentlyFighting = false;
        this.iscurrentlyHunting = false;
        this.calledForHunt = false;
    }

    public void act() {


        if (this.currentlyFighting) {
            if (currentlyWinning()) {
                fight();
            } else {
                flee();
            }
        } else if (haslowHealth() && energyLevel > 5) {
            // Recover health
            recoverHealth();

        }

        if (this.iscurrentlyHunting) {

            // call pack

            hunt();

        } else if (this.calledForHunt) {
            // call pack

            hunt();

        }

        if (this.energyLevel + 9 < maxEnergy) {
            // call pack

            hunt();

        }

        if (this.energyLevel > 12) {
            recoverHealth();
        } else if (this.energyLevel + 9 > maxEnergy) {
            recoverHealth();
        }

    }

    private void calledForHunt() {

        if (this.calledForHunt) {
            if (haslowHealth()) {
                this.calledForHunt = false;
            } else {

                hunt();
            }

        }
    }


    protected void flee() {

    }

    protected void createDen() {


    }

    void fight() {




    }

    public List<Animal> getPack() {

        return null;
    }

    public List<Animal> getEnemies() { // to tell pack members whom you're fighting/hunting

        return null;
    }


    // Get winning chances
    public boolean currentlyWinning() {

        return true; // XXX temp
    }

    public boolean haslowHealth() {

        return maxHealth / this.health < 4;
    }


    @Override
    public DisplayInformation getInformation() {

        return null;
    }
}
