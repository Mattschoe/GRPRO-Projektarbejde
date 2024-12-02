package BlockingAgents;

import NonblockingAgents.Fungi;
import itumulator.executable.DisplayInformation;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.awt.*;

public class Meat implements Actor {
    World world;
    int energyLevel;
    Animal animal;
    public boolean infected = false;
    int age;

    public Meat(World world, Animal animal) {
        this.world = world;
        this.animal = animal;
        age = 0;

        if (this.animal instanceof Carnivore) {
            this.energyLevel = 50;
        } else { // Herbivore
            this.energyLevel = 20;
        }

    }

    @Override
    public void act(World world) {
        this.world = world;
        this.energyLevel--;
        if (this.infected) {
            this.energyLevel--;
        }
        if (this.energyLevel < 1) { //can die without being eaten
            if (this.infected) { // spawns mushroom if infected, otherwise just dies
                Location tempLocation = world.getCurrentLocation();
                spawnMushroom(tempLocation);
            }
            else {
                world.delete(this);
            }
        }

        if (world.getCurrentTime() == 10) {
            age++; //Makes the meat older.
        }
    }

    @Override
    public DisplayInformation getInformation() {
        if (this.animal instanceof Carnivore) {
            return new DisplayInformation(Color.BLUE, "carcass");
        } else {
            return new DisplayInformation(Color.BLUE, "carcass-small");
        }
    }

    void spawnMushroom(Location location) {
        if (this.animal instanceof Carnivore) {
            world.setTile(location, new Fungi(world,50));
        } else {
            world.setTile(location, new Fungi(world,20));
        }
    }

    /**
     * returns the energyLevel which is dependent on which type of animal was killed.
     * @return
     */
    public int getEnergyLevel() {
        return this.energyLevel;
    }

    public int getAge() {
        return this.age;
    }

    public boolean isInfected() {
        return infected;
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
    }
}
