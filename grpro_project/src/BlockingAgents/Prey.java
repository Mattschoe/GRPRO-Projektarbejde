package BlockingAgents;

import itumulator.world.World;
import itumulator.world.Location;

public abstract class Prey extends Animal {
    World world;
    boolean isHiding;
    Predator predator;

    Prey(World world, int age, int maxEnergy, int health) {
        super(world, age, maxEnergy, health);
        this.world = world;
    }

    /**
     * Detects predators in a "fleeRadius" radius.
     */
    protected boolean detectPredator(int fleeRadius) {
        for (Location location : world.getSurroundingTiles(fleeRadius)) {
            if (world.getTile(location) instanceof Predator predator) {
                this.predator = predator;
                return true;
            }
        }
        return false;
    }

    protected Predator getPredator() {
        return predator;
    }
}
