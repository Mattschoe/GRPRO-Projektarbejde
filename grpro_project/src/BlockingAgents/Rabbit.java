package BlockingAgents;
import NonblockingAgents.Grass;
import NonblockingAgents.RabbitBurrow;
import itumulator.world.Location;
import itumulator.world.World;
import itumulator.simulator.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Rabbit extends Prey implements DenAnimal, Herbivore {
    RabbitBurrow burrow;
    World world;

    public Rabbit(World world) {
        super(world, 0, 4, 4, 1);
        this.world = world;
    }

    @Override
    public void act(World world) {
        if (detectPredator()) {
            flee();
        } else if (energyLevel < maxEnergy) {
            eatPlant();
        } else {
            move();
        }

        //Loses energy at night
        updateMaxEnergy();
    }

    protected void flee() {
    }

    private void updateMaxEnergy() {
        if (world.getCurrentTime() == 10) {
            maxEnergy = maxEnergy - age;
        }
    }

    /**
     * If there are any RabbitBurrows in the world, this will find them, otherwise the rabbit will dig a new one.
     */
    public void findDen() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof RabbitBurrow ){
                if (world.isTileEmpty(world.getLocation(object))){
                    world.move(this, world.getLocation(object));
                    burrow = (RabbitBurrow) object;
                } else {
                    digDen();
                }
            }
        }
    }

    /**
     * instantiates a new RabbitBurrow on the current location.
     */
    public void digDen() {
        burrow = new RabbitBurrow(world,this);
        burrow.spawnBurrow();
    }




    /***
     * Checks if we are on a
     */
    public void eatPlant() {

            world.delete(world.getNonBlocking(world.getLocation(this)));
            energyLevel = energyLevel + 2;

    }
}
