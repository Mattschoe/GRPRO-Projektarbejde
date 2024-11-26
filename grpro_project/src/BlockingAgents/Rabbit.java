package BlockingAgents;
import NonblockingAgents.Grass;
import NonblockingAgents.RabbitBurrow;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;
import itumulator.simulator.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Rabbit extends Prey implements DenAnimal, Herbivore {
    RabbitBurrow burrow;
    World world;
    boolean hasFoundGrass = false;
    Location grassLocation = null;
    Location sleepingLocation = null;
    boolean isSleeping = false;

    public Rabbit(World world) {
        super(world,1,40, 1);
        this.world = world;
    }

    @Override
    public void act(World world) {
        //Daytime activities:
        if (world.isDay()) {
            if (sleepingLocation != null) { //Adds rabbit back to world after sleeping
                world.setTile(sleepingLocation, this);
                sleepingLocation = null;
                isSleeping = false;
            } else if (energyLevel == 0) {
                die();
            } else if (detectPredator()) { //If predator nearby
                flee();
            } else if (energyLevel + 10 < maxEnergy) { //If hungry
                eatPlant();
                try {
                    moveTo(getEatablePlantLocation());
                } catch (NullPointerException e) { //No more grass. Rabbit just moves instead
                    move();
                }
            } else { //Else moves randomly
                move();
            }
        }
        //Nighttime activities:
        if (world.isNight()) {
            if (world.getCurrentTime() == 10) {
                findDen();
                //Loses energy at night
                updateMaxEnergy();
            }

            //Moves towards burrow until its the middle of the night
            if (world.getCurrentTime() < 15) {
                //If it reaches the burrow it goes to sleep otherwise it tries to move towards it
                if (!isSleeping && isOnBurrow()) {
                    sleepingLocation = world.getLocation(burrow);
                    world.remove(this);
                    isSleeping = true;
                } else if (!isSleeping) {
                    moveTo(world.getLocation(burrow));
                }
            } else if (world.getCurrentTime() == 15 && !isSleeping && !isOnBurrow()) { //Didnt reach the burrow
                System.out.println("am sleeping on the ground tonight :(");
            }
        }
    }

    protected void sleep() {
        world.remove(this);
    }

    @Override
    public DisplayInformation getInformation() {
        return null;
    }

    protected void flee() {
    }

    /**
     * If there are any RabbitBurrows in the world, this will find them, otherwise the rabbit will dig a new one.
     */
    public Location findDen() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof RabbitBurrow burrow){
                if (world.isTileEmpty(world.getLocation(burrow))){
                    this.burrow = burrow;
                    return world.getLocation(burrow);
                }
            }
        }
        return digDen(); //Makes a new Den if the rabbit cant find any
    }
    /**
     * instantiates a new RabbitDens on the current location.
     */
    public Location digDen() {
        burrow = new RabbitBurrow(world,this);
        burrow.spawnBurrow();
        return world.getLocation(burrow);
    }

    /**
     * Checks if rabbit is standing on burrow
     */
    private boolean isOnBurrow() {
        if (burrow != null) {
            if (world.getLocation(this).getX() == world.getLocation(burrow).getX() && world.getLocation(this).getY() == world.getLocation(burrow).getY()) {
                return true;
            }
        }
        return false;
    }
    /**
     * Returns location of a grass spot
     * @return
     */
    public void findEatablePlant() {
        //Finds a spot of grass if the rabbit hasn't found it
        if (!hasFoundGrass) {
            for (Object object : world.getEntities().keySet()) {
                if (object instanceof Grass grass) {
                    grassLocation = world.getLocation(grass);
                    hasFoundGrass = true;
                    break;
                }
            }
        }
    }
    /**
     * Checks if we are on a grass tile and eats it if so
     */
    public void eatPlant() {
        if (world.getNonBlocking(world.getLocation(this)) instanceof Grass grass) {
            world.delete(grass);
            energyLevel = energyLevel + 5;
        }
    }
    /**
     * gives the location of the plant chosen to be eaten by the rabbit. If it hasnt chosen a location the method first finds a location
     * @return
     */
    public Location getEatablePlantLocation() {
        if (grassLocation == null) {
            findEatablePlant();
            return grassLocation;
        }
        return grassLocation;
    }
    /**
     * Updates the daily energy cost
     */
    private void updateMaxEnergy() {
        maxEnergy = maxEnergy - age;
    }

    protected void reproduce() {}
}
