package NonblockingAgents;

import itumulator.executable.DisplayInformation;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Grass extends Plant implements Actor, NonBlocking {
    World world;




    public Grass(World world) {
        super(world);
        this.world = world;
    }

    /**
     * Initializes the world and runs Grass.spread() for each simulation
     */
    @Override
    public void act(World world) {
        this.world = world;
        if (world.isDay()) {
            spread(new Grass(world), 10);
        }
    }

    @Override
    public DisplayInformation getInformation() {
        return null;
    }


}
