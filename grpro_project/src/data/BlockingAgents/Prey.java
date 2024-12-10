package data.BlockingAgents;

import itumulator.world.World;
import itumulator.world.Location;

public abstract class Prey extends Animal {
    World world;
    boolean isHiding;
    Predator predator;
    int fleeRadius;

    Prey(World world, int age, int maxEnergy, int health, boolean isInfected) {
        super(world, age, maxEnergy, health, isInfected);
        this.world = world;
        isHiding = false;
        predator = null;
    }

    /**
     * Detects predators in a "fleeRadius" radius.
     */
    protected boolean detectPredator(int fleeRadius) {
        this.fleeRadius = fleeRadius;
        if (isHiding) {
            world.setCurrentLocation(sleepingLocation);
        } else world.setCurrentLocation(world.getLocation(this));


        for (Location location : world.getSurroundingTiles(fleeRadius)) {
            if (world.getTile(location) instanceof Predator predator) {
                this.predator = predator;
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether it's safe to come back out of hiding
     * @return
     */
    protected boolean itIsSafeToComeBack(Location hidingLocation) {
        this.sleepingLocation = hidingLocation;
        world.setCurrentLocation(hidingLocation);
        if (!detectPredator(fleeRadius) && world.isTileEmpty(hidingLocation)) {
            return true;
        }
        return false;
    }

}
