package BlockingAgents;

import NonblockingAgents.Bush;
import NonblockingAgents.Grass;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.*;

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
    Object food;
    Location foodLocation;
    boolean isInfected;



    Animal(World world, int age, int maxEnergy, int maxHealth) {
        this.world = world;
        this.age = age;
        this.energyLevel = maxEnergy;
        this.maxEnergy = maxEnergy;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        sleepingLocation = null;
        isSleeping = false;
        hasFoundFood = false;
    }

    Animal(World world, int age, int maxEnergy, int maxHealth, boolean isInfected) {
        this.world = world;
        this.age = age;
        this.energyLevel = maxEnergy;
        this.maxEnergy = maxEnergy;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        sleepingLocation = null;
        isSleeping = false;
        hasFoundFood = false;
        this.isInfected = isInfected;
    }

    @Override
    public void act(World world){
        this.world = world;

    }

    public void takeDamage(int damage) {
        health = health - damage;
    }

    protected void die() {
        if (this.isInfected) {
            int infectionRadius = 2;
            for (Object object : world.getEntities().keySet()) {
                // Checks if animal is close to the dying infection
                if ( (object instanceof Animal) && Math.abs(world.getLocation(this).getX() - world.getLocation(object).getX()) <= infectionRadius && Math.abs(world.getLocation(this).getY() - world.getLocation(object).getY()) <= infectionRadius ) {
                    ((Animal) object).isInfected = true;
                }
            }
            world.delete(this);
        } else {
            try {
                Location tempLocation = world.getLocation(this);
                world.delete(this);
                world.setTile(tempLocation, new Meat(world, this));
            } catch (IllegalArgumentException e) {
                System.out.println("It seems " + this + " didn't have a location upon death");
            }
        }
    }

    protected abstract void sleep();

    protected abstract void reproduce();

    /**
     * Moves randomly around one tile at a time, moves only to empty tiles. Uses up 1 energyLevel
     */
    protected void move() {
        energyLevel--;

        //The Holy Grail. DEN HER STATEMENT MÅ IKKE RØRES, se TF2 Coconut.jpg
        if (sleepingLocation == null && world.getCurrentLocation() != null && world.getEntities().containsKey(this)) {

            //Gets all empty locations
            Set<Location> neighbours = world.getEmptySurroundingTiles();
            List<Location> neighbourList = new ArrayList<>(neighbours);

            //Moves to a random neighbour tile, as long as there is one available
            Random random = new Random();
            if (!neighbourList.isEmpty()) {
                Location location = neighbourList.get(random.nextInt(neighbourList.size()));

                //If the location is takem it finds a new one
                while (!world.isTileEmpty(location)) {
                    location = neighbourList.get(random.nextInt(neighbourList.size()));
                }
                world.move(this, location);
                world.setCurrentLocation(location);


                if (isInfected) {
                    energyLevel--;
                    health--;
                }
            }
        }
    }

    protected Object findClosestInSet(Map<Object, Location> everyAnimalInSet) {
        int closestDistance = Integer.MAX_VALUE;
        Object closestAnimal = null;
        // Searches through entire species to find the closest (not including this)
        for (Object object : everyAnimalInSet.keySet()) {
            if ( !(((Animal) object).isInfected) && Math.abs(world.getLocation(object).getX()) < closestDistance && Math.abs(world.getLocation(object).getY()) < closestDistance ) {
                if (Math.abs(world.getLocation(object).getX()) > Math.abs(world.getLocation(object).getY())) {
                    closestDistance = Math.abs(world.getLocation(object).getX());
                    closestAnimal = object;
                } else {
                    closestDistance = Math.abs(world.getLocation(object).getY());
                    closestAnimal = object;
                }
            }
        }
        return closestAnimal;
    }

    protected Map<Object, Location> findEveryAnimalInSpecies() {
        Map<Object, Location> map = new HashMap<>();
        for (Object object : world.getEntities().keySet()) {
            if (object.getClass() == this.getClass() && object != this) {
                map.put(object, world.getLocation(object));
            }
        }
        return map;
    }

    protected void infectedMove() {
        if (findClosestInSet(findEveryAnimalInSpecies()) != null) {
            moveTo(world.getLocation(findClosestInSet(findEveryAnimalInSpecies())));
        } else {
            move();
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
        if (world.getTile(newLocation) != null) {
            move();
        } else if (sleepingLocation == null && world.getCurrentLocation() != null && world.getEntities().containsKey(this)) {
            world.move(this, newLocation);
            world.setCurrentLocation(newLocation);
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
     * Eats food if standing or close to it (Depending on the food), otherwise it moves towards it.
     */
    protected void eatFood() {
        if (hasFoundFood) { //If the animal has already found food
            if (world.getSurroundingTiles().contains(world.getLocation(food))) {
                if (food instanceof Bush bush) { //If its a bush it just eats the berries
                    bush.getEaten();
                    hasFoundFood = false;
                } else { //If its something else it deletes it and afterwards the animal moves into the food tile, as long as the animal isnt a bear
                    Location tempLocation = world.getLocation(food);
                    world.delete(food);
                    moveTo(tempLocation);
                    hasFoundFood = false;
                }
            } else { //Moves towards the food
                moveTo(foodLocation);
            }
        } else { //If it haven't yet found any food
            findFood();
        }
    }

    /**
     * Finds location of a food spot.
     */
    private void findFood() {
        if (this instanceof Herbivore) { //Animal is Plant eater
            for (Object object : world.getEntities().keySet()) {
                if (object instanceof Bush bush && bush.getHasBerries()) {
                    food = bush;
                    foodLocation = world.getLocation(food);
                    hasFoundFood = true;
                    return;
                } else if (object instanceof Grass grass && !(this instanceof Bear)) {
                    food = grass;
                    foodLocation = world.getLocation(food);
                    hasFoundFood = true;
                    return;
                }
            }
        }
        if (this instanceof Carnivore) { //Animal is Meat eater
            for (Object object : world.getEntities().keySet()) {
                if (object instanceof Meat meat && meat.getAge() == 0) { //Finds meat in the world and goes towards it, as long as it isn't older than a day
                    food = meat;
                    foodLocation = world.getLocation(food);
                    hasFoundFood = true;
                    return;
                }
            }
            food = null;
        }
    }

    /**
     * Returns whether there is any fresh meat in the world that a animal can go after
     * @return boolean
     */
    public boolean isThereFreshMeat() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof Meat meat && meat.getAge() < 2) {
                return true;
            }
        }
        return false;
    }

    public boolean getIsSleeping() {
        return isSleeping;
    }
}
