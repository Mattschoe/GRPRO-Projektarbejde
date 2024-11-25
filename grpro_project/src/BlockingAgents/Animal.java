package BlockingAgents;

import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Animal implements Actor {
    World world;
    int age;
    int energyLevel;
    int maxEnergy;
    int health;

    @Override
    public void act(World world){}



    protected World getWorld() {
        return world;
    }

    protected void die() {

    }

    protected void sleep() {

    }

    protected void reproduce() {

    }

    protected void sprint() {

    }

    protected void move() {
        //Moves the object to a random empty neighbour next to its location
        Set<Location> emptyNeighbours = world.getEmptySurroundingTiles();
        List<Location> list = new ArrayList<>(emptyNeighbours);
        world.move(this, list.get(new Random().nextInt(list.size())));
    }
}
