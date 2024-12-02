package BlockingAgents;

import NonblockingAgents.Den;
import NonblockingAgents.Grass;
import NonblockingAgents.Meat;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import BlockingAgents.WolfPack;
import itumulator.world.World;

import java.awt.*;

public class Wolf extends Predator implements DenAnimal, Carnivore {
    Den den;
    WolfPack wolfpack;
    int huntRadius;
    boolean hasFoundMeat = false;

    /**
     * Wolf without being a Alpha in a wolfpack
     * @param world
     */
    public Wolf(World world) {
        super(20, world, 30, 20);
        huntRadius = 2;
    }

    /**
     * Wolf with a Wolfpack where its the alpha
     * @param world
     * @param wolfpack
     */
    public Wolf(World world, WolfPack wolfpack) {
        super(29, world, 30, 20);
        this.wolfpack = wolfpack;
    }


    //MANGLER: At få en wolfpack

    public void act(World world) {
        System.out.println("Wolf energy: " + energyLevel + ", is hungry: " + isHungry());
        //Daytime activities:
        if (world.isDay()) {
            isSleeping = false;
            if (sleepingLocation != null) {
                world.setTile(sleepingLocation, this);
                sleepingLocation = null;
            } else if (energyLevel <= 0) {
                die();
            } else if (currentlyFighting) { //Fighting. Fight while its not critically low on health, else runs away.
                if (health > 5) {
                    fight();
                } else {
                    flee();
                }
            } else if (isPreyInHuntRadius(huntRadius) && isHungry()) {
                hunt(findPrey());
            } else {
                move();
            }
        }

        //Nighttime activites
        if (world.isNight()) {
            if (world.getCurrentTime() == 10) {
                findDen();
                updateMaxEnergy();
            }

            //Moves towards den until its the middle of the night'
            if (world.getCurrentTime() < 15) {
                //If it reaches the burrow it goes to sleep otherwise it tries to move towards it
                if (!isSleeping && den.isOwnerOnDen()) {
                    world.remove(this);
                    sleepingLocation = world.getLocation(den);
                    isSleeping = true;
                } else if (!isSleeping) {
                    moveTo(world.getLocation(den));
                }
            } else if (world.getCurrentTime() == 15 && !isSleeping && !den.isOwnerOnDen()) { //Didnt reach the burrow
                isSleeping = true;
            }
        }
    }

    @Override
    public DisplayInformation getInformation() {
        return null;
    }

    @Override
    protected void sleep() {
        world.remove(this);
    }

    @Override
    protected void reproduce() {} //MANGLER

    /**
     * Eats meat if standing on it
     */
    @Override
    public void eatMeat() {
        if (world.getNonBlocking(world.getLocation(this)) instanceof Meat meat) {
            energyLevel = energyLevel + meat.getEnergyLevel();
            world.delete(meat);
        }
    }

    /**
     * Finds location of a meat spot
     */
    @Override
    public void findEatableMeat() {
        //Finds a spot of meat if the wolf hasn't found it
        if (!hasFoundMeat) {
            for (Object object : world.getEntities().keySet()) {
                if (object instanceof Grass grass) {
                    foodLocation = world.getLocation(grass);
                    hasFoundMeat = true;
                    break;
                }
            }
        }
    }

    /**
     * Returns the location of the plant chosen to be eaten by the wolf. If it hasnt chosen a location the method first finds a location
     * @return
     */
    @Override
    public Location getEatableMeatLocation() {
        if (foodLocation == null) {
            findEatableMeat();
            return foodLocation;
        }
        return foodLocation;
    }

    /**
     * If there are any Den's in the world and the Wolf is the owner of it, this will find them, otherwise the wolf will dig a new one.
     */
    @Override
    public Location findDen() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof Den den && den.getOwner() == this){
                if (world.isTileEmpty(world.getLocation(den))){
                    this.den = den;
                    return world.getLocation(den);
                }
            }
        }
        return digDen(); //Makes a new Den if the rabbit cant find any
    }

    /**
     * instantiates a new RabbitDens on the current location.
     */
    @Override
    public Location digDen() {
        den = new Den(world, this, false);
        den.spawnDen();
        return world.getLocation(den);
    }

    /**
     * Sets the Den for the wolf. Used for WolfPack so the pack has the same Den to sleep in
     */
    public void setDen(Den den) {
        this.den = den;
    }

    /**
     * Returns the wolfs den
     * @return
     */
    public Den getDen() {
        return den;
    }

    /**
     * Returns the wolfs pack
     * @return
     */
    public WolfPack getWolfpack() {
        if (wolfpack != null) {
            return wolfpack;
        }
        throw new IllegalStateException("Wolfpack is null!");
    }
}
