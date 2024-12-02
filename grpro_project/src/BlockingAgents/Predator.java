package BlockingAgents;

import NonblockingAgents.Territory;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.List;
import java.util.Set;

public abstract class Predator extends Animal{
    World world;

    int strength;
    Animal preyAnimal;

    boolean currentlyFighting;
    boolean currentlyHunting;
    boolean hasFoundPrey;



    Predator(int strength , World world, int maxEnergy, int maxHealth){
        super(world,0,maxEnergy, maxHealth);
        this.strength = strength;
        this.world = world;
        currentlyFighting = false;
        preyAnimal = null;
        hasFoundPrey = false;
    }

    /**
     * Hunts after prey. Calls the kill method if its close enough, otherwise it chases it.
     */
    protected void hunt (Animal opponentAnimal){
        this.preyAnimal = opponentAnimal;
        if (isPreyInHuntRadius(1)) { //Kills prey if its in one of the sorrounding tiles
            System.out.println("Im close enough to kill my prey!");
            kill(opponentAnimal);
        } else { //Otherwise it just chases it
            System.out.println("Im chasing a animal!");
            moveTo(world.getLocation(opponentAnimal));
        }
    }

    /**
     * Kills prey if its nearby
     */
    protected void kill(Animal animalToKill) {
        /*world.delete(animalToKill);
        System.out.println("MUMS"); */
    }

    //SKAL ÆNDRES!!

    protected void fight () {
        /*try {
            currentlyFighting = true;
            preyAnimal.takeDamage(strength);
            if (preyAnimal.health <= 0) {
                preyAnimal.die();
                currentlyFighting = false;
            }
        } catch ( Exception e) {
            System.out.println("Fighting Animal is null! Error: " + e.getMessage());
        } */
    }

    /**
     * Detects prey in a "huntRadius" radius.
     */
    protected boolean isPreyInHuntRadius(int huntRadius) {
        for (Location location : world.getSurroundingTiles(huntRadius)) {
            if (world.getTile(location) instanceof Prey) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds prey nearby and returns it. Also saves it so that the class knows it already found a prey
     */
    protected Animal findPrey() {
        if (hasFoundPrey) {
            System.out.println("Already found a prey!");
            return preyAnimal;
        } else {
            for (Object object : world.getEntities().keySet()) {
                if (object instanceof Prey prey) {
                    hasFoundPrey = true;
                    System.out.println("Found prey!");
                    return prey;
                }
            }
        }
        System.out.println("No prey found and none already found!");
        return null;
    }

}
