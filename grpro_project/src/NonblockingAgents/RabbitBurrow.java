package NonblockingAgents;

import BlockingAgents.Rabbit;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

public class RabbitBurrow implements NonBlocking {
    private Rabbit rabbit;
    private World world;

    //Spawned by Rabbit
    public RabbitBurrow(World world, Rabbit rabbit) {
        this.world = world;
        this.rabbit = rabbit;
    }

    //Spawned by anything other than rabbit
    public RabbitBurrow(World world) {
        this.world = world;
    }

    /*
     * Spawns the burrow either from the rabbits location or the location given in file
     */
    public void spawnBurrow() {
        //Spawns under rabbit
        if (rabbit != null) {
            world.setTile(world.getLocation(rabbit), this);
        }
    }
}
