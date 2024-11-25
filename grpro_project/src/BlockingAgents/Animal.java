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
    protected int maxHealth;

    Animal(World world, int age, int energyLevel, int maxEnergy, int health) {
        this.world = world;
        this.age = age;
        this.energyLevel = energyLevel;
        this.maxEnergy = maxEnergy;
        this.health = health;
    }

    @Override
    public void act(World world){
        this.world = world;
    }

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

    /***
     * Moves to a chosen location one tile. Call this method in "act" to move repeatable towards a location
     */
    public void moveTo(Location moveToLocation) {
        energyLevel--;

        int x = 0;
        int y = 0;

        //Calculates x
        if (world.getLocation(this).getX() == moveToLocation.getX()) {
            x = moveToLocation.getX();
        } else if (world.getLocation(this).getX() < moveToLocation.getX()) {
            x = world.getLocation(this).getX() + 1;
        } else if (world.getLocation(this).getX() > moveToLocation.getX()) {
            x = world.getLocation(this).getX() - 1;
        }

        //Calculates y
        if (world.getLocation(this).getY() == moveToLocation.getY()) {
            y = moveToLocation.getY();
        } else if (world.getLocation(this).getY() < moveToLocation.getY()) {
            y = world.getLocation(this).getY() + 1;
        } else if (world.getLocation(this).getY() > moveToLocation.getY()) {
            y = world.getLocation(this).getY() - 1;
        }

        //Moves
        Location newLocation = new Location(x, y);
        world.move(this, newLocation);
    }

    /***
     * Moves twice towards chosen location
     * @param moveToLocation
     */
    protected void sprintTo(Location moveToLocation) {
        moveTo(moveToLocation);
        moveTo(moveToLocation);
    }

    protected void flee() {};

    protected void move() {
        energyLevel--;

        Random random = new Random();
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        List<Location> neighbourList = new ArrayList<>(neighbours);

        if (!neighbourList.isEmpty()){
            Location location = neighbourList.get(random.nextInt(neighbourList.size()));
            world.move(this, location);
        }
    }
}
