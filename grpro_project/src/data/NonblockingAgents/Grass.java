package data.NonblockingAgents;

import itumulator.executable.DisplayInformation;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Grass implements Actor, NonBlocking {
    World world;

    public Grass(World world) {
        this.world = world;
    }

    /**
     * Initializes the world and runs Grass.spread() for each simulation
     */
    @Override
    public void act(World world) {
        this.world = world;
        if (world.isDay()) {
            spread(10);
        }
    }

    @Override
    public DisplayInformation getInformation() {
        return null;
    }

    /**
     * Spawns grass with a certain chance in each of the empty surrounding tiles nearby. The higher the chance the lower chance of grass spawning
     */
    public void spread(int chance) {
        Random random = new Random();

        for (int i = 0; i < world.getEmptySurroundingTiles().size(); i++) {
            //Spawns grass in an empty surrounding tile with a chance of doing so
            if (random.nextInt(chance) == 0) {
                Set<Location> emptySurrounding = world.getSurroundingTiles();
                List<Location> list = new ArrayList<>(emptySurrounding);

                Location newGrassLocation = list.get(random.nextInt(list.size()));
                //Only instantiates the object if the location DOESNT contain a non-blocking
                if (!world.containsNonBlocking(newGrassLocation) && world.isTileEmpty(newGrassLocation) ) {
                    world.setTile(newGrassLocation, new Grass(world));
                }
            }
        }
    }

}
