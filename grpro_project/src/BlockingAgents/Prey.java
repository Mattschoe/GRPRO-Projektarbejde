package BlockingAgents;

import itumulator.world.World;

public abstract class Prey extends Animal {
    World world;

    Prey(World world, int age, int energyLevel, int maxEnergy, int health) {
        super(world, age, energyLevel, maxEnergy, health);
        this.world = world;
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
                System.out.println(this.getClass() + " i am fleeing!");
            }
        }
    }
}
