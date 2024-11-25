package NonblockingAgents;

import BlockingAgents.Predator;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.Set;

public class Territory {
    World world;
    Predator owner;

    public Territory (Location location, World world, Predator owner) {
        this.world = world;
        this.owner = owner;



    }

}
