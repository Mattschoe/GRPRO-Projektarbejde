package BlockingAgents;

import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Animal implements Actor {
    protected int age;
    protected int energyLevel;
    protected int maxEnergy;
    protected int health;

    Animal(World world, int age, int energyLevel, int maxEnergy, int health) {
        this.age = age;
        this.energyLevel = energyLevel;
        this.maxEnergy = maxEnergy;
        this.health = health;
    }

    @Override
    public void act(World world){}

    protected void die() {}

    protected void sleep() {}

    protected void reproduce() {}

    protected void sprint() {}

    protected void move() {}
}
