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

    Predator(int strength , World world, int maxEnergy, int maxHealth, boolean isInfected){
        super(world,0,maxEnergy, maxHealth, isInfected);
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
        preyAnimal = opponentAnimal;
        if (world.getSurroundingTiles().contains(world.getLocation(preyAnimal))) { //Kills prey if its in one of the sourrounding tiles
            kill(opponentAnimal);
            System.out.println("Killed the " + opponentAnimal);
        } else { //Otherwise it just chases it
            moveTo(world.getLocation(opponentAnimal));
            System.out.println("Moving towards " + opponentAnimal);
        }
    }

    /**
     * Kills prey if its nearby.
     */
    protected void kill(Animal animalToKill) {
        animalToKill.die();
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
            return preyAnimal;
        } else {
            for (Object object : world.getEntities().keySet()) {
                if (object instanceof Prey prey) {
                    hasFoundPrey = true;
                    return prey;
                }
            }
        }
        return null;
    }

}
