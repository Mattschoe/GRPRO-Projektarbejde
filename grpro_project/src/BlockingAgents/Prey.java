package BlockingAgents;

import itumulator.world.World;

public abstract class Prey extends Animal {
    World world;

    Prey(World world, int age, int maxEnergy, int health) {
        super(world, age, maxEnergy, health);
        this.world = world;
    }

    /***
     * Detects predators in a "fleeRadius" radius.
     */
    protected boolean detectPredator() {
        int fleeRadius = 2;
        for (Object object : world.getSurroundingTiles(fleeRadius)) {
            if (object instanceof Predator) {
                System.out.println(this.getClass() + " i detected a predator!");
                return true;
            }
        }
        return false;
    }

}
