package NonblockingAgents;

import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Plant implements Actor {
    World world;
    Plant(World world) {
        this.world = world;

    }
    @Override
    public void act(World world) {
        this.world = world;
    }

    /**
     * Spawns plant with a certain chance in each of the empty surrounding tiles nearby.
     */
    protected void spread(Plant type, int chance) {
        Random random = new Random();

        for (int i = 0; i < world.getEmptySurroundingTiles().size(); i++) {
            //Spawns grass in a empty surrounding tile with a (25%) chance of doing so
            if (random.nextInt(chance) == 0) {
                Set<Location> emptySurrounding = world.getSurroundingTiles();
                List<Location> list = new ArrayList<>(emptySurrounding);

                Location newGrassLocation = list.get(random.nextInt(list.size()));
                //Only instantiates the object if the location DOESNT contain a non-blocking
                if (!world.containsNonBlocking(newGrassLocation) && world.isTileEmpty(newGrassLocation) ) {
                    if (type instanceof Grass){
                        world.setTile(newGrassLocation, new Grass(world));
                    }

                }
            }
        }
    }

}
