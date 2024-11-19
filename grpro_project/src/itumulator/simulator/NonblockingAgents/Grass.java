package itumulator.simulator.NonblockingAgents;

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

    /*
     * Initializes the world and runs Grass.spread() for each simulation
     */
    @Override
    public void act(World world) {
        this.world = world;
        spread();
    }

    /*
     * Spawns grass with a 25% chance in each of the empty surrounding tiles nearby.
     */
    private void spread() {
        Random random = new Random();
        for (int i = 0; i < world.getEmptySurroundingTiles().size(); i++) {
            //Spawns grass in a empty surrounding tile with a (25%) chance of doing so
            if (random.nextInt(4) == 0) {
                Set<Location> emptySurrounding = world.getSurroundingTiles();
                List<Location> list = new ArrayList<>(emptySurrounding);
                world.setTile(list.get(emptySurrounding.size()), this);
            }
        }
    }


}
