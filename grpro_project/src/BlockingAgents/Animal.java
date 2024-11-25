package BlockingAgents;

import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Animal implements Actor {
    protected World world;
    protected int age;
    protected int energyLevel;
    protected int maxEnergy;
    protected int health;

    Animal(World world, int age, int energyLevel, int maxEnergy, int health) {
        this.world = world;
        this.age = age;
        this.energyLevel = energyLevel;
        this.maxEnergy = maxEnergy;
        this.health = health;
    }

    @Override
    public void act(World world){}

    protected void die() {
        world.delete(this);
    }

    protected void sleep() {
        world.remove(this);
    }

    protected void reproduce() {
        Rabbit kid = new Rabbit(world);
        //neighbourList = getNeighbours(world);
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        List<Location> neighbourList = new ArrayList<>(neighbours);
        if (!neighbourList.isEmpty()) {
            Location birthPlace = neighbourList.get(0);

            world.setTile(birthPlace, kid);
        }
    }

    protected void sprint() {}

    protected void move() {
        Random random = new Random();
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        List<Location> neighbourList = new ArrayList<>(neighbours);

        if (!neighbourList.isEmpty()){
            Location location = neighbourList.get(random.nextInt(neighbourList.size()));
            world.move(this, location);
        }
    }
}
