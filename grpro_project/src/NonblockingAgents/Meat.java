package NonblockingAgents;

import BlockingAgents.Animal;
import BlockingAgents.Carnivore;
import itumulator.executable.DisplayInformation;
import itumulator.simulator.Actor;
import itumulator.world.World;

import java.awt.*;

public class Meat implements Actor {
    World world;
    int energyLevel;
    Animal animal;

    public Meat(World world, Animal animal) {
        this.world = world;
        this.animal = animal;

        if (this.animal instanceof Carnivore) {
            this.energyLevel = 50;
        } else { // Herbivore
            this.energyLevel = 20;
        }

    }

    @Override
    public void act(World world) {
        this.world = world;

    }

    @Override
    public DisplayInformation getInformation() {
        if (this.animal instanceof Carnivore) {
            return new DisplayInformation(Color.BLUE, "carcass");
        } else {
            return new DisplayInformation(Color.BLUE, "carcass-small");
        }
    }

}
