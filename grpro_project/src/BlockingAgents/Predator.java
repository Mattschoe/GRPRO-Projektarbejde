package BlockingAgents;

import NonblockingAgents.Territory;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.List;
import java.util.Set;

public abstract class Predator extends Animal{
    int strength;

    World world;
    Predator(int strength , World world){
        super(world,0,10, 10);
        this.strength = strength;
        this.world = world;

    }
    @Override
    public void act(World world){}


    void hunt (){


    }
    void fight (){}


}
