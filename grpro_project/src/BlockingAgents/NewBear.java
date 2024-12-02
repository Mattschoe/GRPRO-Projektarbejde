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

    public NewBear(World world) {
        super(20, world, 50, 30);
    }

    public void act(World world) {

    }

    @Override
    public DisplayInformation getInformation() {
        return null;
    }

    @Override
    protected void sleep() {

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
