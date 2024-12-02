package BlockingAgents;

import NonblockingAgents.Bush;
import NonblockingAgents.Meat;
import itumulator.executable.DisplayInformation;

import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NewBear extends Predator implements Carnivore, Herbivore {
    ArrayList<Location> territory;


    public NewBear(World world) {
        super(20, world, 50, 30);
        territory = new ArrayList<>();
    }

    public void act(World world) {
        //Daytime activities
        if (world.isDay()) {
            isSleeping = false;
            if (sleepingLocation != null) { //Spawns back into the world after night
                world.setTile(sleepingLocation, this);
                sleepingLocation = null;
            } else if (energyLevel <= 0) { //Dies when out of energy
                die();
            } else if (currentlyFighting) { //Fighting. Bear fights to death.
                fight();
            } else if (isInTerritory()) { //Moves around in territory and protects it
                move();
                protectTerritory();
            } else { //If its not in its territory it moves towards it
                moveTo(territory.get(0));
            }
        }

        //Nighttime activities
        if (world.isNight()) {
            updateMaxEnergy();
            sleep();
        }
    }

    /**
     * Returns whether the bear is in its territory or not
     * @return
     */
    private boolean isInTerritory() {
        if (territory.contains(world.getLocation(this))) {
            return true;
        }
        return false;
    }

    private void protectTerritory() {

    }

    @Override
    public DisplayInformation getInformation() {
        if (isSleeping) {
            return new DisplayInformation(Color.red, "bear-sleeping");
        } else {
            return new DisplayInformation(Color.blue, "bear");
        }
    }

    @Override
    protected void sleep() {
        isSleeping = true;
    }

    @Override
    protected void reproduce() {

    }

    @Override
    public void eatMeat() {

    }

    @Override
    public void findEatableMeat() {

    }

    @Override
    public Location getEatableMeatLocation() {
        return null;
    }

    @Override
    public void eatPlant() {

    }

    @Override
    public void findEatablePlant() {

    }

    @Override
    public Location getEatablePlantLocation() {
        return null;
    }
}
