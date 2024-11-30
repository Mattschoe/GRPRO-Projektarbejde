package BlockingAgents;
import NonblockingAgents.Den;
import NonblockingAgents.Grass;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;


public class Rabbit extends Prey implements DenAnimal, Herbivore, DynamicDisplayInformationProvider {
    Den burrow;
    World world;
    boolean hasFoundGrass;
    Location grassLocation;
    Location sleepingLocation;
    Location hidingLocation;
    boolean isSleeping;

    public Rabbit(World world) {
        super(world,1,40, 1);
        this.world = world;
        fleeRadius = 2;
        hasFoundGrass = false;
        grassLocation = null;
        sleepingLocation = null;
        isSleeping = false;
    }

    @Override
    public void act(World world) {
        if (!isHiding) {
            //Daytime activities:
            if (world.isDay()) {
                isSleeping = false;
                if (sleepingLocation != null) { //Adds rabbit back to world after sleeping
                    world.setTile(sleepingLocation, this);
                    world.setCurrentLocation(sleepingLocation);
                    sleepingLocation = null;
                } else if (energyLevel <= 0) {
                    die();
                } else if (detectPredator(2)) { //If predator nearby
                        flee();
                        hide();
                } else if (energyLevel + 10 < maxEnergy) { //If hungry
                    eatPlant();
                    try {
                        moveTo(getEatablePlantLocation());
                    } catch (Exception e) { //No more grass. Rabbit just moves instead
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
                    if (!isSleeping && burrow.isOwnerOnDen()) {
                        world.remove(this);
                        sleepingLocation = world.getLocation(burrow);
                        isSleeping = true;
                    } else if (!isSleeping) {
                        moveTo(world.getLocation(burrow));
                    }
                } else if (world.getCurrentTime() == 15 && !isSleeping && !burrow.isOwnerOnDen()) { //Didnt reach the burrow
                    isSleeping = true;
                }
            }
        }

        if (isHiding) {
            if (itIsSafeToComeBack(hidingLocation)) {
                isHiding = false;
                world.setTile(hidingLocation, this);
            }
        }
    }

    /**
     * Hides from predators and continues to hide in its burrow until it doesnt detect predators
     */
    protected void flee() {
        //Moves towards its burrow if it's close by, otherwise it runs closer to the burrow
        if (burrow != null) {
            if (world.getSurroundingTiles(world.getLocation(this)).contains(burrow)) {
                moveTo(world.getLocation(burrow));
            } else {
                sprintTo(world.getLocation(burrow));
            }
        } else { //Runs the opposite direction of the predator if it doesn't have a burrow
            moveAwayFrom(world.getLocation(predator));
        }
    }

    /**
     * Hides in burrow if its on it
     */
    private void hide() {
        if (burrow != null && burrow.isOwnerOnDen()) {
            hidingLocation  = world.getLocation(burrow);
            isHiding = true;
            world.remove(this);
        }
    }

    protected void sleep() {
        world.remove(this);
    }

    @Override
    public DisplayInformation getInformation() {
        if (isSleeping){
            return new DisplayInformation(Color.BLUE, "rabbit-small-sleeping");
        } else {
            return new DisplayInformation(Color.GRAY, "rabbit-small");
        }
    }



    /**
     * If there are any RabbitBurrows in the world, this will find them, otherwise the rabbit will dig a new one.
     */
    public Location findDen() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof Den burrow && burrow.getOwner() == this){
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
        burrow = new Den(world, this, false);
        burrow.spawnDen();
        return world.getLocation(burrow);
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

    public boolean getHasFoundGrass() {
        return hasFoundGrass;
    }

    public Location getGrassLocation() {
        return grassLocation;
    }
}
