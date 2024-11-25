package BlockingAgents;

import NonblockingAgents.Territory;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.List;
import java.util.Set;

public abstract class Predator extends Animal{
    int strength;
    List<Territory> territory;
    World world;
    Predator(int strength , World world){
        super(world,0,10, 10, 5 );
        this.strength = strength;
        this.world = world;
        //setTerritory();


    }
    void setTerritory(){
        Set<Location> surroundingTiles = world.getSurroundingTiles(world.getLocation(this));
        for (int i = 0; i < surroundingTiles.toArray().length; i++) {
            territory.add(new Territory(world.getLocation(surroundingTiles.toArray()), world ,this));
            world.setTile(world.getLocation(surroundingTiles.toArray()), territory.get(i));
        }


    }
    void hunt (){


    }
    void fight (){}


}
