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
    boolean hasFoundGrass;
    Location hidingLocation;

    public Rabbit(World world) {
        super(world,1,40, 1);
        fleeRadius = 2;
        hasFoundGrass = false;
    }

    public Rabbit(World world, boolean isInfected) {
        super(world,1,40, 1, isInfected);
        fleeRadius = 2;
        hasFoundGrass = false;
    }

    @Override
    public void act(World world) {
        if (!isHiding) {
            //Daytime activities:
            if (world.isDay()) {
                isSleeping = false;
                if (sleepingLocation != null) { //Adds rabbit back to world after sleeping
                    world.setTile(sleepingLocation, this);
                    sleepingLocation = null;
                } else if (energyLevel <= 0) {
                    die();
                } else if (isInfected) {
                    // Makes sure it doesn't do wolf things when infected
                    try {
                        System.out.println("The " + this + " at " + world.getLocation(this) + " is infected");
                        moveTo(world.getLocation(findClosestInSet(findEveryAnimalInSpecies())));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Seems infected animals lose their location when reaching their prey");
                    }
                }else if (detectPredator(2)) { //If predator nearby
                    System.out.println("FLEEING!");
                } else if (detectPredator(2)) { //If predator nearby
                    flee();
                    hide();
                } else if (energyLevel + 5 < maxEnergy) { //If hungry
                    eatFood();
                } else if (!isInfected) { //Else moves randomly
                    move();
                }
            }

            //Nighttime activities:
            if (world.isNight() && !isInfected) {
                if (world.getCurrentTime() == 10) {
                    findDen();
                    updateMaxEnergy();
                }

                //Moves towards burrow until it's the middle of the night
                if (world.getCurrentTime() < 15) {
                    //If it reaches the burrow it goes to sleep otherwise it tries to move towards it
                    if (!isSleeping && burrow.isAnimalOnDen(this)) {
                        world.remove(this);
                        sleepingLocation = world.getLocation(burrow);
                        isSleeping = true;
                    } else if (!isSleeping) {
                        moveTo(world.getLocation(burrow));
                    }
                } else if (world.getCurrentTime() == 15 && !isSleeping && !burrow.isAnimalOnDen(this)) { //Didnt reach the burrow


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
     * Hides from predators and continues to hide in its burrow until it doesn't detect predators
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
        if (burrow != null && burrow.isAnimalOnDen(this)) {
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
            if (object instanceof Den burrow ){//&& this.burrow == object) {//burrow.getOwner() == this){
                //if (burrow == this.burrow) {
                    if (world.isTileEmpty(world.getLocation(burrow))) {
                        this.burrow = burrow;
                        System.out.println("Burrow found " + burrow);
                        return world.getLocation(burrow);
                    }
                /*} else {
                    System.out.println("new den " + burrow);
                    this.burrow = burrow;
                    return world.getLocation(burrow);
                }*/

            }
        }
        return digDen(); //Makes a new Den if the rabbit cant find any
    }

    /**
     * instantiates a new burrow (Den) on the current location. Only makes a burrow if there isnt a predator nearby
     */
    public Location digDen() {
<<<<<<< HEAD
        burrow = new Den(world, this, false);
=======
        burrow = new Den(world,  "rabbit");
>>>>>>> Reroll-Back
        burrow.spawnDen(this);

        return world.getLocation(burrow);
    }

    /**
     * Returns location of a grass spot
     */
    public void findEatablePlant() {
        //Finds a spot of grass if the rabbit hasn't found it
        if (!hasFoundGrass) {
            for (Object object : world.getEntities().keySet()) {
                if (object instanceof Grass grass) {
                    foodLocation = world.getLocation(grass);
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
     * gives the location of the plant chosen to be eaten by the rabbit. If it hasn't chosen a location the method first finds a location
     * @return
     */
    public Location getEatablePlantLocation() {
        if (foodLocation == null) {
            findEatablePlant();
            return foodLocation;
        }
        return foodLocation;
    }


    protected void reproduce() {}

    public boolean getHasFoundGrass() {
        return hasFoundGrass;
    }
}
