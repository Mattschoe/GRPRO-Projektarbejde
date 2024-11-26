package NonblockingAgents;

import BlockingAgents.Rabbit;
import BlockingAgents.Wolf;
import itumulator.world.NonBlocking;
import itumulator.world.World;

public class WolfDen implements NonBlocking {
    private World world;
    private Wolf wolf;
    //private Pack pack;



    // spawned by Wolf
    public WolfDen(World world, Wolf wolf) {
        this.world = world;
        this.wolf = wolf;
        //this.pack = pack;
    }

    // Spawns the den
    public void spawnDen() {

        if (world.containsNonBlocking(world.getLocation(wolf))) { // if there is grass on the tile
            world.delete(world.getNonBlocking(world.getLocation(wolf)));
        }
        world.setTile(world.getLocation(wolf), this);
    }

    //public Pack getPack() { return this.pack; }

    //public void setPack(Pack pack) { this.pack = pack; }
}

