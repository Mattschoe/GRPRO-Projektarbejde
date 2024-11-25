package BlockingAgents;

import NonblockingAgents.Territory;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.Set;

public class Predator extends Animal{
    int strength;
    Territory[] territory;
    World world;
    Predator(int strength ){
        this. strength = strength;

        Set<Location> surroundingTiles = world.getSurroundingTiles(world.getLocation(this));
        for (int i = 0; i < surroundingTiles.toArray().length; i++) {
           territory[i] = new Territory(world.getLocation(surroundingTiles.toArray()), world ,this);
            world.setTile(world.getLocation(surroundingTiles.toArray()), territory[i]);
        }
    }
    void hunt (){

    }
    void fight (){}

}
