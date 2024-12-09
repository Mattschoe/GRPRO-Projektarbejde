package BlockingAgents;

import itumulator.world.Location;
import itumulator.world.World;

public abstract class Predator extends Animal{
    int strength;
    private Animal preyAnimal;

    boolean currentlyFighting;
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
    protected void hunt (Animal preyAnimal){
        this.preyAnimal = preyAnimal;
        if (world.getSurroundingTiles().contains(world.getLocation(preyAnimal))) { //Kills prey if its in one of the surrounding tiles
            kill(preyAnimal);
            System.out.println("Killed the " + preyAnimal);
        } else { //Otherwise it just chases it
            moveTo(world.getLocation(preyAnimal));
            System.out.println("Moving towards " + preyAnimal);
        }
    }

    /**
     * Kills prey if it's nearby.
     */
    protected void kill(Animal animalToKill) {
        System.out.println("Killing " + animalToKill);
        animalToKill.die();
    }

    /**
     * Fights the Opponent close to it, if not close it moves towards it. If the predator doesn't have an opponent it finds one
     */
    protected abstract void fight ();

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
