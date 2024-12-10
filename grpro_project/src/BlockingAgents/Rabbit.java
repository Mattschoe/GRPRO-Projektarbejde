package BlockingAgents;

import NonblockingAgents.Den;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.Random;


public class Rabbit extends Prey implements DenAnimal, Herbivore, DynamicDisplayInformationProvider {
    private Den burrow;

    public Rabbit(World world, boolean isInfected) {
        super(world,1,40, 1, isInfected);
        fleeRadius = 2;
    }

    /**
     * Initialises the world and sets up what actions the Rabbit should do for every action.
     */
    @Override
    public void act(World world) {
            if (!isHiding) {
                //Daytime activities:
                if (world.isDay()) {
                    isSleeping = false;

                    if (sleepingLocation != null) { //Adds rabbit back to world after sleeping

                        try {
                            world.setTile(sleepingLocation, this);
                            world.setCurrentLocation(sleepingLocation);
                            sleepingLocation = null;
                        } catch (IllegalArgumentException e) {
                        }
                        for (Object obj : world.getEntities().keySet()){
                            if (obj instanceof Rabbit rabbit){
                                if ((rabbit.getBurrow() == this.getBurrow() && rabbit != this)){
                                    if (new Random().nextInt(10) == 0){reproduce(world.getLocation(burrow), new Rabbit(world,false));}
                                }
                            }
                        }



                    } else if (energyLevel <= 0) {
                        die();
                    } else if (isInfected) {
                        // Makes sure it doesn't do wolf things when infected
                        infectedMove();
                    } else if (detectPredator(2)) { //If predator nearby
                        flee();
                        hide();
                    } else if (energyLevel + 5 < maxEnergy) { //If hungry
                        if (food == null) {
                            move();
                        } else {
                            eatFood();
                        }
                    } else if (!isInfected) { //Else moves randomly
                        move();
                    }
                }

                //Nighttime activities:
                if (world.isNight() && !isInfected) {
                    if (world.getCurrentTime() == 10) {
                        if (burrow == null) {
                            findDen();
                        }
                        updateMaxEnergy();
                    }

                    //Moves towards burrow until it's the middle of the night
                    if (world.getCurrentTime() < 15) {
                        //If it reaches the burrow it goes to sleep otherwise it tries to move towards it
                        if (!isSleeping && burrow.isAnimalOnDen(this)) {
                            world.remove(this);
                            sleepingLocation = world.getLocation(burrow);
                        } else if (!isSleeping && burrow != null && world.contains(burrow)) {
                            moveTo(world.getLocation(burrow));
                        }
                    } else if (world.getCurrentTime() == 15 && !isSleeping && !burrow.isAnimalOnDen(this)) { //Didnt reach the burrow
                        isSleeping = true;
                    }
                }
            }

            if (isHiding) {
                if (itIsSafeToComeBack(sleepingLocation)) {
                    isHiding = false;
                    world.setTile(sleepingLocation, this);
                    world.setCurrentLocation(sleepingLocation);
                    sleepingLocation = null;
                }
            }

    }

    /**
     * Hides from predators and continues to hide in its burrow until it doesn't detect predators
     */
    protected void flee() {
        //Moves towards its burrow if it's close by, otherwise it runs closer to the burrow
        if (burrow != null) {
            if (world.getSurroundingTiles(world.getLocation(this)).contains(burrow)) {
                moveTo(world.getLocation(burrow));
            } else {
                moveTo(world.getLocation(burrow));
            }
        } else { //Runs the opposite direction of the predator if it doesn't have a burrow
            moveAwayFrom(world.getLocation(predator));
        }
    }

    /**
     * Hides in burrow if its on it
     */
    private void hide() {
        if (burrow != null && burrow.isAnimalOnDen(this)) {
            sleepingLocation  = world.getLocation(burrow);
            isHiding = true;
            world.remove(this);
        }
    }

    /**
     * Puts the Rabbit to sleep, by removing it from the world (Because it goes into its burrow)
     */
    protected void sleep() {
        world.remove(this);
    }

    /**
     * Makes the Rabbit have a different look depending on whether it is infected or sleeping
     */
    @Override
    public DisplayInformation getInformation() {
        if (isSleeping){
            if (isInfected) {
                return new DisplayInformation(Color.GRAY, "rabbit-fungi-small-sleeping");
            } else {
            return new DisplayInformation(Color.BLUE, "rabbit-small-sleeping");
            }
        } else {
            if (isInfected) {
                return new DisplayInformation(Color.GRAY, "rabbit-fungi-small");
            } else {
            return new DisplayInformation(Color.GRAY, "rabbit-small");
            }
        }
    }

    /**
     * If there are any RabbitBurrows in the world, this will find them, otherwise the rabbit will dig a new one.
     */
    public Location findDen() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof Den burrow) {//&& this.burrow == object) {//burrow.getOwner() == this){
                if (burrow.getDenType() == "rabbit") {
                    if (world.isTileEmpty(world.getLocation(burrow))) {
                        this.burrow = burrow;
                        return world.getLocation(burrow);
                    }
                }
            }
        }
        return digDen(); //Makes a new Den if the rabbit cant find any
    }

    /**
     * instantiates a new burrow (Den) on the current location. Only makes a burrow if there isnt a predator nearby
     */
    public Location digDen() {
        burrow = new Den(world,  "rabbit");
        burrow.spawnDen(this);
        return world.getLocation(burrow);
    }

    /**
     * Returns the burrow
     * @return Den
     */
    public Den getBurrow() {
        return burrow;
    }
}
