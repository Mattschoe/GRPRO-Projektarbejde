package itumulator.simulator.NonblockingAgents;

import itumulator.simulator.BlockingAgents.Rabbit;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

public class RabbitBurrow implements NonBlocking {
    Rabbit rabbit;
    Location spawnLocation;
    World world;

    //Spawned by Rabbit
    RabbitBurrow(World world, Rabbit rabbit) {
        this.world = world;
        this.rabbit = rabbit;
    }

    //Spawned by File
    RabbitBurrow(World world, Location spawnLocation) {
        this.world = world;
        this.spawnLocation = spawnLocation;
    }

    /*
     * Spawns the burrow either from the rabbits location or the location given in file
     */
    public void spawn() {
        //Spawns under rabbit
        if (rabbit != null) {
            spawnLocation = world.getLocation(rabbit);
            world.setTile(spawnLocation, this);
        }
        //Spawns from File location
        else if (spawnLocation != null) {
            world.setTile(spawnLocation, this);
        }
    }
}
