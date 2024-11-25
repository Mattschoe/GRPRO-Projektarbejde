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

    public Rabbit(World world) {
        super(world, 1, 40, 40, 1);
        this.world = world;
    }

    @Override
    public void act(World world) {
        eatPlant();

        if (detectPredator()) { //If predator nearby
            flee();
        } else if (energyLevel + 9 < maxEnergy) { //If hungry
            moveTo(getEatablePlantLocation());
            System.out.println("Græss: " + grassLocation);
            System.out.println("Kanin: " + world.getLocation(this));
        } else { //Else moves randomly
            move();
        }

        //Loses energy at night
        System.out.println("Energi Level: " + energyLevel);
        updateMaxEnergy();
    }

    @Override
    public DisplayInformation getInformation() {
        return null;
    }

    protected void flee() {
    }

    private void updateMaxEnergy() {
        if (world.getCurrentTime() == 10) {
            maxEnergy = maxEnergy - age;
        }
    }

    /**
     * If there are any RabbitBurrows in the world, this will find them, otherwise the rabbit will dig a new one.
     */
    public void findDen() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof RabbitBurrow ){
                if (world.isTileEmpty(world.getLocation(object))){
                    world.move(this, world.getLocation(object));
                    burrow = (RabbitBurrow) object;
                } else {
                    digDen();
                }
            }
        }
    }

    /**
     * instantiates a new RabbitBurrow on the current location.
     */
    public void digDen() {
        burrow = new RabbitBurrow(world,this);
        burrow.spawnBurrow();
    }


    /***
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

    /***
     * Checks if we are on a grass tile and eats it if so
     */
    public void eatPlant() {
        if (world.getNonBlocking(world.getLocation(this)) instanceof Grass grass) {
            world.delete(grass);
            energyLevel = energyLevel + 5;
        }
    }

    public Location getEatablePlantLocation() {
        if (grassLocation == null) {
            findEatablePlant();
            return grassLocation;
        }
        return grassLocation;
    }
}
