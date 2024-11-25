package NonblockingAgents;

import BlockingAgents.Predator;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.awt.*;
import java.util.Set;

public class Territory implements NonBlocking {
    World world;
    Predator owner;

    public Territory (Location location, World world, Predator owner) {
        //world.setTile(location, this);
        this.world = world;
        this.owner = owner;



    }

}
