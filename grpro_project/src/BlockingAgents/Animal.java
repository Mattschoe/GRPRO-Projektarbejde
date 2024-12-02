package BlockingAgents;

import NonblockingAgents.Bush;
import NonblockingAgents.Grass;
import NonblockingAgents.Meat;
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
    Location sleepingLocation;
    boolean isSleeping;
    boolean hasFoundFood;
    Location foodLocation;



    Animal(World world, int age, int maxEnergy, int maxHealth) {
        this.world = world;
        this.age = age;
        this.energyLevel = maxEnergy;
        this.maxEnergy = maxEnergy;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        sleepingLocation = null;
        isSleeping = false;
        foodLocation = null;
        hasFoundFood = false;
    }

    @Override
    public void act(World world){
        this.world = world;
    }

    public void takeDamage(int damage) {
        health = health - damage;
    }

    protected void die() {
        Location tempLocation = world.getLocation(this);
        world.delete(this);
        world.setTile(tempLocation, new Meat(world,this));
    }

    protected abstract void sleep();

    protected abstract void reproduce();

    /**
     * Moves randomly around one tile at a time, moves only to empty tiles. Uses up 1 energyLevel
     */
    protected void move() {
        energyLevel--;


        if (world.isOnTile(this)) {
            //Gets all empty locations
            Set<Location> neighbours = world.getEmptySurroundingTiles();
            List<Location> neighbourList = new ArrayList<>(neighbours);

            //Moves to a random neighbour tile, as long as there is one available
            Random random = new Random();
            if (!neighbourList.isEmpty()){
                Location location = neighbourList.get(random.nextInt(neighbourList.size()));
                world.move(this, location);
                world.setCurrentLocation(location);
            }
        }
    }

    /***
     * Moves to a chosen location one tile. Call this method in "act" to move repeatable towards a location
     */
    protected void moveTo(Location moveToLocation) {
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
        //Tries to move unless there is a object in the way
        try {
            world.move(this, newLocation);
            world.setCurrentLocation(newLocation);
        } catch (IllegalArgumentException e) {
            move();
        }

    }

    /**
     * Moves twice towards chosen location
     * @param moveToLocation
     */
    protected void sprintTo(Location moveToLocation) {
        moveTo(moveToLocation);
        moveTo(moveToLocation);
    }

    protected void moveAwayFrom(Location moveAwayFromLocation) {
        energyLevel--;

        int x = 0;
        int y = 0;

        //Calculates x
        if (world.getLocation(this).getX() < moveAwayFromLocation.getX()) {
            x = world.getLocation(this).getX() - 1;
        } else if (world.getLocation(this).getX() > moveAwayFromLocation.getX()) {
            x = world.getLocation(this).getX() + 1;
        } else {
            x = world.getLocation(this).getX();
        }

        //Calculates y
        if (world.getLocation(this).getY() < moveAwayFromLocation.getY()) {
            y = world.getLocation(this).getY() - 1;
        } else if (world.getLocation(this).getY() > moveAwayFromLocation.getY()) {
            y = world.getLocation(this).getY() + 1;
        } else {
            y = world.getLocation(this).getY();
        }

        //Tries to move unless there is a object in the way
        Location newLocation = new Location(x, y);
        try {
            world.move(this, newLocation);
            world.setCurrentLocation(newLocation);
        } catch (IllegalArgumentException e) {
            move();
        }
    }

    protected void flee() {};

    protected void recoverHealth() {}

    void birthday() {
        age += 1;

    }

    public int getAge(){
        return age;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    /**
     * Returns whether the animal is hungry or not
     * @return boolean
     */
    protected boolean isHungry() {
        return energyLevel + 5 < maxEnergy;
    }

    /**
     * Updates the amount of max energy the animal daily has. The method is called each night.
     */
    protected void updateMaxEnergy() {
        maxEnergy = maxEnergy - age;
    }

    /**
     * Eats food if standing on it
     */
    protected void eatFood() {
        if (hasFoundFood) { //If the animal has already found food
            if (world.getSurroundingTiles().contains(foodLocation)) {
                if (world.getTile(foodLocation) instanceof Bush bush ) { //If its a bush it just eats the berries
                    bush.getEaten();
                    hasFoundFood = false;
                } else {
                    world.delete(world.getTile(foodLocation));
                    hasFoundFood = false;
                }
            } else {
                System.out.println("Moving to food");
                moveTo(getFoodLocation());
            }
        } else { //If it haven't yet found any food
            System.out.println("Finding food");
            findFood();
        }
    }

    /**
     * Finds location of a food spot.
     */
    private void findFood() {
        if (this instanceof Herbivore) { //Animal is Plant eater
            for (Object object : world.getEntities().keySet()) {
                if (object instanceof Grass grass) {
                    foodLocation = world.getLocation(grass);
                    System.out.println("Found some grass at: " + world.getLocation(grass));
                    hasFoundFood = true;
                    break;
                } else if (object instanceof Bush bush && bush.getHasBerries()) {
                    foodLocation = world.getLocation(bush);
                    hasFoundFood = true;
                    break;
                }
            }
        }
        if (this instanceof Carnivore) { //Animal is Meat eater
            for (Object object : world.getEntities().keySet()) {
                if (object instanceof Meat meat) {
                    foodLocation = world.getLocation(meat);
                    hasFoundFood = true;
                    break;
                }
            }
        }
    }

    /**
     * Returns the location of the plant chosen to be eaten by the wolf. If it hasnt chosen a location the method first finds a location
     * @return Location
     */
    private Location getFoodLocation() {
        return foodLocation;
    }
}
