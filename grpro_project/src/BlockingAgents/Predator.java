package BlockingAgents;

import itumulator.world.Location;
import itumulator.world.World;

public abstract class Predator extends Animal{
    protected int strength;
    private Animal preyAnimal;

    protected boolean currentlyFighting;
    protected boolean hasFoundPrey;


    Predator(int strength , World world, int maxEnergy, int maxHealth, boolean isInfected){
        super(world,0,maxEnergy, maxHealth, isInfected);
        this.strength = strength;
        this.world = world;
        currentlyFighting = false;
        preyAnimal = null;
        hasFoundPrey = false;
    }

    /**
     * Hunts prey. Calls the kill method if it's close enough, otherwise it chases it.
     */
    protected void hunt (Animal preyAnimal) {
        this.preyAnimal = preyAnimal;
        try {
            if (preyAnimal != null && preyAnimal.sleepingLocation == null && !preyAnimal.isSleeping && world.getSurroundingTiles().contains(world.getLocation(preyAnimal))) { //Kills prey if its in one of the surrounding tiles
                kill(preyAnimal);
            } else if (preyAnimal != null && preyAnimal.sleepingLocation == null) { //Otherwise it just chases it
                moveTo(world.getLocation(preyAnimal));
            }
        } catch (IllegalArgumentException e) {
            // Prey has died
        }
    }

    /**
     * Kills prey if it's nearby.
     */
    protected void kill(Animal animalToKill) {
        animalToKill.die();
    }

    /**
     * Fights the Opponent close to it, if not close it moves towards it. If the predator doesn't have an opponent it finds one
     */
    protected abstract void fight();

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
    protected Animal findPrey(int huntRadius) {
        if (hasFoundPrey && preyAnimal != null) {
            return preyAnimal;
        } else {
            for (Location location : world.getSurroundingTiles(huntRadius)) {
                Object object = world.getTile(location);
                if (object instanceof Prey prey) {
                    hasFoundPrey = true;
                    this.preyAnimal = prey;
                    return prey;
                }
            }
        }
        return null;
    }
}
