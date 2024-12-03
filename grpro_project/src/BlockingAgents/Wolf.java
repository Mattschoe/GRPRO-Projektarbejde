package BlockingAgents;

import NonblockingAgents.Den;
import NonblockingAgents.Grass;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;

public class Wolf extends Predator implements DenAnimal, Carnivore, DynamicDisplayInformationProvider {
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
        huntRadius = 3;
    }

    public Wolf(World world, boolean isInfected) {
        super(20, world, 30, 20, isInfected);
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

    public Wolf(World world, WolfPack wolfpack, boolean isInfected) {
        super(29, world, 30, 20, isInfected);
        this.wolfpack = wolfpack;
    }


    //MANGLER: At få en wolfpack
    public void act(World world) {
        //Daytime activities:
        if (world.isDay()) {
            isSleeping = false;
            if (sleepingLocation != null) {
                try {
                    world.setTile(sleepingLocation, this);
                    sleepingLocation = null;
                } catch (IllegalArgumentException e) {} //If there is already somebody above the hole it waits a step
            } else if (energyLevel <= 0) {
                die();
            } else if (isInfected) {
                // Makes sure it doesn't do wolf things when infected
                infectedMove();
            } else if (currentlyFighting) { //Fighting. Fight while its not critically low on health, else runs away.
                if (health > 5) {
                    fight();
                } else {
                    flee();
                }
            } else if (!isInfected) {
                move();
            }

            //Praise the Holy Grail of If-Statements
            if (isHungry() && sleepingLocation == null && world.getCurrentLocation() != null && world.getEntities().containsKey(this)) {
                //First checks if there is any easy meat close, otherwise it starts hunting. If neither it just moves.
                if (isThereFreshMeat()) {
                    eatFood();
                } else if (isPreyInHuntRadius(huntRadius)) {
                    hunt(findPrey());
                }
            }
        }

        if (world.isNight() && !isInfected) {
            if (world.getCurrentTime() == 10) {
                findDen();
                updateMaxEnergy();
            }

            //Moves towards den until its the middle of the night'
            if (world.getCurrentTime() < 17) {
                //If it reaches the burrow it goes to sleep otherwise it tries to move towards it
                if (!isSleeping && den.isAnimalOnDen(this)) {
                    sleepingLocation = world.getLocation(den);
                    isSleeping = true;
                    world.remove(this);
                } else if (!isSleeping) {
                    moveTo(world.getLocation(den));
                }
            } else if (!isSleeping) { //Didnt reach the burrow
                isSleeping = true;
            }
        }
    }

    @Override
    public DisplayInformation getInformation() {
        if (isSleeping) {
            if (isInfected) {
                return new DisplayInformation(Color.red, "wolf-fungi-sleeping");
            } else {
                return new DisplayInformation(Color.red, "wolf-sleeping");
            }
        } else {
            if (isInfected) {
                return new DisplayInformation(Color.red, "wolf-fungi");
            } else {
                return new DisplayInformation(Color.blue, "wolf");
            }
        }
    }

    @Override
    protected void sleep() {
        world.remove(this);
    }

    @Override
    protected void reproduce() {} //MANGLER


    /**
     * If there are any Den's in the world and the Wolf is the owner of it, this will find them, otherwise the wolf will dig a new one.
     */
    @Override
    public Location findDen() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof Den den ){
                if (den == this.den) {
                    return world.getLocation(den);
                }
            }
        }
        return digDen(); //Makes a new Den if the wolf cant find any
    }

    /**
     * instantiates a new RabbitDens on the current location.
     */
    @Override
    public Location digDen() {
        den = new Den(world, "wolf");
        world.setTile(world.getLocation(this), den);
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
     * Returns the wolf's
     * @return
     */
    public WolfPack getWolfpack() {
        if (wolfpack != null) {
            return wolfpack;
        }
        throw new IllegalStateException("Wolfpack is null!");
    }


}
