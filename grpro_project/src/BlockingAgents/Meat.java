package BlockingAgents;

import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;

public class Meat implements Actor, DynamicDisplayInformationProvider {
    private World world;
    private int energyLevel;
    private Animal animal;
    private boolean isInfected;
    private int age;
    private int fungiLife;

    public Meat(World world, Animal animal) {
        this.world = world;
        this.animal = animal;
        this.isInfected = false;
        this.fungiLife = 0;
        age = 0;

        if (this.animal instanceof Carnivore) {
            this.energyLevel = 50;
        } else { //Prey
            this.energyLevel = 20;
        }

    }

    public Meat(World world, Animal animal, boolean isInfected) {
        this.world = world;
        this.animal = animal;
        this.isInfected = isInfected;
        this.fungiLife = 0;
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
        if (this.isInfected) {
            this.fungiLife++;
            this.energyLevel--;
        }
        if (this.energyLevel < 1) { //can die without being eaten
            if (this.isInfected) { // spawns mushroom if infected, otherwise just dies
                Location tempLocation = world.getLocation(this);
                world.delete(this);
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

    /**
     * Creates a new Fungi on a designated spot with health based on how long the Fungi has lived.
     */
    private void spawnMushroom(Location location) {
        world.setTile(location, new Fungi(world,this.fungiLife));
    }

    /**
     * returns the energyLevel which is dependent on which type of animal was killed.
     * @return
     */
    public int getEnergyLevel() {
        return this.energyLevel;
    }

    /**
     * @return int
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Sets whether the Meat should be infected
     * @param infected whether the meat should be infected
     */
    public void setInfected(boolean infected) {
        this.isInfected = infected;
    }
}
