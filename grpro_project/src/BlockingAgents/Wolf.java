package BlockingAgents;

import NonblockingAgents.Den;
import NonblockingAgents.Meat;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Wolf extends Predator implements DenAnimal, Carnivore{

    Den den;
    World world;
    public WolfPack pack;

    boolean currentlyFighting = false;
    boolean isCurrentlyHunting = false;
    boolean calledForHunt = false;
    boolean hasMoved = false;
    boolean hasFoundMeat = false;
    Location meatLocation = null;

    public Wolf(World world) {
        super(20, world, 30, 20);
        this.world = world;
        }

    public void act(World world) {
        this.world = world;
        hasMoved = false;

        // Daytime activities
        if (world.isDay()) {
            if (this.pack == null) {
                this.pack = new WolfPack(this) {};
            }

            // Fighting actions
            if (this.currentlyFighting) {
                if (currentlyWinning()) {
                    fight();
                } else {
                    flee();
                    hasMoved = true;
                }
            }
            // Fight non-pack neighbours - eat Meat
            for (Object object : world.getEntities().keySet()) {
                try {
                    if (object instanceof Animal && !isSleeping) {
                        if ( !(object == this) && ((world.getLocation(object).getX() + 2) > world.getLocation(this).getX()) && ((world.getLocation(object).getY() + 2) > world.getLocation(this).getY()) && ((world.getLocation(object).getX() - 1) <= world.getCurrentLocation().getX()) && ((world.getLocation(object).getY() - 1) <= world.getCurrentLocation().getY())) {
                            if (object instanceof Wolf) {
                                if (!(this.pack == ((Wolf) object).pack)) { // Check if same pack
                                    fight((Animal) object);
                                }
                            } else {
                                fight((Animal) object);
                            }
                        }
                    } else if (object instanceof Meat && !isSleeping) {

                    }
                } catch (IllegalArgumentException e) { //object has no location. Let's delete it
                    if (object instanceof Animal && ((Animal) object).isSleeping) {
                        // A sleeping animal may not have a location, but that's okay - We don't always feel like we have a place in the world
                    } else {
                        System.out.println("Something doesn't have a location - been deleted by the big bad wolf");
                        world.delete(object);
                    }
                }
            }
            // Wakes up
            isSleeping = false;
            if (sleepingLocation != null) { //Adds wolf back to world after sleeping
                world.setTile(sleepingLocation, this);
                world.setCurrentLocation(sleepingLocation);
                sleepingLocation = null;
            } else if (energyLevel == 0 || health <= 0 ) {
                die();
            } else if (isCurrentlyHunting || calledForHunt) { //Hunting actions
                hunt();
            } else if (this.energyLevel + 9 < maxEnergy) {
                getEatableMeatLocation();

            } else if (!hasMoved) { // Moving actions
                // Move toward other pack members
                if (this.pack != null && pack.getWolves().size() > 1) {
                    int packDistanceX = 0;
                    int packDistanceY = 0;
                    for (Wolf wolf : pack.getWolves()) {
                        try {
                            packDistanceX += world.getLocation(wolf).getX();
                            packDistanceY += world.getLocation(wolf).getY();
                        } catch (IllegalArgumentException e) {
                            System.out.println("Other wolf has no location");
                        }
                    }
                    moveTo(new Location(packDistanceX, packDistanceY));

                } else {
                    move();
                }
                hasMoved = true;
            } else if (this.energyLevel > 12) { // Healing actions
                recoverHealth();
            } else if (this.energyLevel * 2 > maxEnergy) {
                recoverHealth();
            }
        }

        // Nighttime activities
        if (world.isNight()) {
            if (world.getCurrentTime() == 10) {
                findDen();
                //Loses energy at night
                updateMaxEnergy();
                updateBreeding();

            }
            //Moves towards den until its the middle of the night
            if (world.getCurrentTime() < 15 && !currentlyFighting) {
                //If it reaches the burrow it goes to sleep otherwise it tries to move towards it
                if (!isSleeping && isOnDen()) {
                    world.remove(this);
                    sleepingLocation = world.getLocation(den);
                    isSleeping = true;
                    reproduce();
                } else if (!isSleeping) {
                    moveTo(world.getLocation(den));
                }
            }
        }
    }


    void fight(Animal animal) {
        currentlyFighting = true;
        int attackPower = new Random().nextInt(strength);
        animal.takeDamage(attackPower);
        if (animal.health <= 0) {
            animal.die();
            eatMeat();
            currentlyFighting = false;
        }
        hasMoved = true;
    }

    void hunt(Animal animal) {
        // walk toward prey if far - run if close
        for (Object object : world.getSurroundingTiles(world.getLocation(this), 5).toArray()) {
            if (object == animal) {
                moveTo(world.getLocation(animal));
            }
        }
        moveTo(world.getLocation(animal));
        pack.callPack();
        this.hasMoved = true;
    }

    private void calledForHunt() {
        if (this.health > 5) {
            this.calledForHunt = false;
        } else {
            hunt();
        }
    }

    protected void reproduce() {
        // Checks if other pack member is sleeping in same den
        for (Wolf wolf : this.pack.getWolves()) {
            if (wolf.isSleeping && (wolf.findDen() == this.findDen()) && wolf.pack != null) {
                System.out.println("Two wolves chilling in a wolf den - two feet apart, cuz they're not gay");
                System.out.println(pack.getWolves().size());
                // Makes new (sleeping) wolf if there's room around the den and both are willing
                Set<Location> neighbours = world.getEmptySurroundingTiles(this.findDen());
                List<Location> neighbourList = new ArrayList<>(neighbours);

                if (!neighbourList.isEmpty() && wantsToBreed && wolf.wantsToBreed && this.pack == wolf.pack && pack.getWolves().size() > 1) {
                    Wolf pup = new Wolf(world);
                    pup.isSleeping = true;
                    pup.sleepingLocation = neighbourList.get(new Random().nextInt(neighbourList.size()));
                    pup.world.setCurrentLocation(pup.sleepingLocation);
                    this.pack.addWolf(pup);
                    System.out.println("Okay, they made a child...maybe a little gay?");
                    System.out.println(pack.getWolves().size());
                    // Makes it so they don't breed for a while
                    wantsToBreed = false;
                    breedingDelay = 3;
                    wolf.wantsToBreed = false;
                    wolf.breedingDelay = 3;
                    pup.wantsToBreed = false;
                    pup.breedingDelay = 3;
                    world.setTile(pup.sleepingLocation, pup);
                    world.remove(pup);

                }
            }
        }
    }

    @Override
    protected void sleep() {}

    protected void flee() {}

    // Get winning chances
    public boolean currentlyWinning() {

        return true; // XXX temp
    }

    public List<Animal> getEnemies() { // to tell pack members whom you're fighting/hunting

        return null;
    }


    public Location findDen() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof Den den && den.getOwner() == this){
                if (world.isTileEmpty(world.getLocation(den))){
                    this.den = den;
                    return world.getLocation(den);
                }
            }
        }
        return digDen(); //Makes a new Den if the wolf cant find any
    }

    public Location digDen() {
        den = new Den(world,this, true);
        den.spawnDen();
        return world.getLocation(den);
    }

    private boolean isOnDen() {
        if (den != null && world.getLocation(den) != null) {
            if (world.getLocation(this).getX() == world.getLocation(den).getX() && world.getLocation(this).getY() == world.getLocation(den).getY()) {
                return true;
            }
        }
        return false;
    }

    private void updateMaxEnergy() {
        maxEnergy = maxEnergy - age;
    }

    private void updateBreeding() {
        if (!this.wantsToBreed) {
            this.breedingDelay--;
            if (breedingDelay == 0) {
                this.wantsToBreed = true;
            }
        }
    }

    public void findEatableMeat() {
        //Finds a spot of grass if the rabbit hasn't found it
        if (!hasFoundMeat) {
            for (Object object : world.getEntities().keySet()) {
                if (object instanceof Meat meat) {
                    meatLocation = world.getLocation(meat);
                    hasFoundMeat = true;
                    break;
                }
            }
        }
    }

    public Location getEatableMeatLocation() {
        if (meatLocation == null) {
            findEatableMeat();
            return meatLocation;
        }
        return meatLocation;
    }

    public void eatMeat() {
        for (Location location : world.getSurroundingTiles()) {
            if (world.getTile(location) instanceof Meat) {
                Meat meat = (Meat) world.getTile(location);
                int extraEnergy = maxEnergy - energyLevel;
                int extraHealth = maxHealth - health;
                if (extraEnergy >= meat.energyLevel) {
                    energyLevel += meat.energyLevel;
                    world.delete(meat);
                } else { // meat energy is higher
                    energyLevel = maxEnergy;
                    meat.energyLevel -= extraEnergy;
                    if (extraHealth >= meat.energyLevel) {
                        energyLevel += meat.energyLevel;
                        world.delete(meat);
                    } else { // meat energy is higher
                        health = maxHealth;
                        meat.energyLevel -= extraHealth;
                    }
                    System.out.println("Energy left in meat: " + meat.energyLevel);
                }
            }
        }
    }



    @Override
    public DisplayInformation getInformation() {
        if (isSleeping){
            return new DisplayInformation(Color.BLUE, "wolf-sleeping");
        } else {
            return new DisplayInformation(Color.GRAY, "wolf");
        }
    }
}
