package NonblockingAgents;

import BlockingAgents.Animal;
import BlockingAgents.DenAnimal;
import BlockingAgents.Rabbit;
import BlockingAgents.Wolf;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.awt.*;
import java.util.ArrayList;

public class Den implements NonBlocking, DynamicDisplayInformationProvider {
    private World world;
    private ArrayList<DenAnimal> owners;
    private boolean denSize;
    private String denType;

    /**
     * @param world the current world
     * @param owner the owner of the den
     * @param denSize the size of den. True = big, False = small
     */
    public Den(World world, String denType) {
        this.world = world;
        //this.owner = owner;
       // this.denSize = denSize;
        this.owners = new ArrayList<>();
        if (!denType.equals("rabbit") && !denType.equals("wolf")) {
            throw new IllegalArgumentException("denType must be \"rabbit\" or \"wolf\"");
        }
            this.denType = denType;

    }

    public void spawnDen(DenAnimal animal) {
        //if (owner != null) {
            owners.add(animal);
            //Checks first if there is a nonblocking on the digsite and removes it
            if (world.containsNonBlocking(world.getLocation(animal))) {
                world.delete(world.getNonBlocking(world.getLocation(animal)));
            }
            world.setTile(world.getLocation(animal), this);
       // }
    }

    @Override
    public DisplayInformation getInformation() {
        //Makes a big den
        if (denType.equals("wolf")) {
            return new DisplayInformation(Color.white, "hole");
        }
        //Makes a small den
        else if (denType.equals("rabbit")) {
            return new DisplayInformation(Color.black, "hole-small");
        }
        return null;
    }


    /*public DenAnimal getOwner() {
        return owner;
    }*/

        /**
         * Returns whether the owner of the Den is standing on Den. Used to hide and sleep
         * @return
         */
        public boolean isAnimalOnDen (Animal animal) {
            try {
                System.out.println(world.getLocation(this));
                if (world.getLocation(this).equals(world.getLocation(animal))) {

                    if (denType.equals("wolf") && animal instanceof Wolf || denType.equals("rabbit") && animal instanceof Rabbit) {
                        System.out.println(animal.toString() + " is on den " + this.toString());
                        return true;
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error!: " + e.getMessage() + ". Remember that i need a owner and need to exist before you call this method!");
            }
            return false;

        }
        public void addOwner(DenAnimal animal){
            owners.add(animal);

        }

}