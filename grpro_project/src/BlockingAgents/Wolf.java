package BlockingAgents;

import NonblockingAgents.Den;
import itumulator.executable.DisplayInformation;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.List;
import java.util.Set;


public class Wolf extends Predator implements DenAnimal, Carnivore{

    Den den;
    World world;
    WolfPack pack;

    boolean currentlyFighting = false;
    boolean iscurrentlyHunting = false;
    boolean calledForHunt = false;
    boolean isSleeping = false;
    Location sleepingLocation = null;
    boolean hasMoved = false;

    public Wolf(World world) {
        super(20, world);
        this.world = world;

    }

    public void act(World world) {
        this.world = world;
        hasMoved = false;



        // Daytime activities
        if (world.isDay()) {
            // Fighting actions
            if (this.currentlyFighting) {
                if (currentlyWinning()) {
                    fight();
                    hasMoved = true;
                } else {
                    flee();
                    hasMoved = true;
                }
            }
            // Wakes up
            isSleeping = false;
            if (sleepingLocation != null) { //Adds wolf back to world after sleeping
                world.setTile(sleepingLocation, this);
                sleepingLocation = null;
            } else if (energyLevel == 0) {
                die();
            } else if (iscurrentlyHunting || calledForHunt || this.energyLevel + 9 < maxEnergy) { //Hunting actions
                hunt();
            } else if (!hasMoved) { // Moving actions
                move();
                hasMoved = true;
            } else if (this.energyLevel > 12) { // Healing actions
                recoverHealth();
            } else if (this.energyLevel + 9 > maxEnergy) {
                recoverHealth();
            }
        }

        // Nighttime activities
        if (world.isNight()) {
            if (world.getCurrentTime() == 10) {
                findDen();
                //Loses energy at night
                updateMaxEnergy();
            }
            //Moves towards den until its the middle of the night
            if (world.getCurrentTime() < 15 && !currentlyFighting) {
                //If it reaches the burrow it goes to sleep otherwise it tries to move towards it
                if (!isSleeping && isOnDen()) {
                    world.remove(this);
                    sleepingLocation = world.getLocation(den);
                    isSleeping = true;
                    System.out.println(sleepingLocation);
                } else if (!isSleeping) {
                    moveTo(world.getLocation(den));
                }
            }
        }
    }


    void hunt(Prey prey) {
        // walk toward prey if far - run if close
        for (Object object : world.getSurroundingTiles(world.getLocation(this), 5).toArray()) {
            if (object == prey) {
                moveTo(world.getLocation(prey));
            }
        }
        moveTo(world.getLocation(prey));
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

    protected void reproduce() {}

    @Override
    protected void sleep() {}

    protected void flee() {}


    void fight() {}

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
        if (den != null) {
            if (world.getLocation(this).getX() == world.getLocation(den).getX() && world.getLocation(this).getY() == world.getLocation(den).getY()) {
                return true;
            }
        }
        return false;
    }

    private void updateMaxEnergy() {
        maxEnergy = maxEnergy - age;
    }

    public void eatMeat() {}

    public void findEatableMeat() {}

    public Location getEatableMeatLocation() {

        return null;
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
