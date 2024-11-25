package BlockingAgents;

import itumulator.world.World;

public abstract class Prey extends Animal {
    World world;

    Prey() {
        super();
        world = super.getWorld();
    }

    /***
     * If Prey detects predator in detectPredator(), it flees.
     */
    protected void flee() {
        move();
    }

    /***
     * Detects predators in a "fleeRadius" radius.
     */
    private void detectPredator() {
        int fleeRadius = 2;
        for (Object object : world.getSurroundingTiles(fleeRadius)) {
            if (object instanceof Predator) {
                flee();
            }
        }
    }


}
