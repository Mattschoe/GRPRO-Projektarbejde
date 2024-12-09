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
import java.util.HashSet;
import java.util.Set;

public class Den implements NonBlocking, DynamicDisplayInformationProvider {
    private World world;
    private String denType;
    private Set<Animal> animalsBelongingToDen;

    public Den(World world, String denType) {
        this.world = world;
        animalsBelongingToDen = new HashSet<>();

        if (!denType.equals("rabbit") && !denType.equals("wolf")) {
            throw new IllegalArgumentException("denType must be \"rabbit\" or \"wolf\"");
        }
        this.denType = denType;
    }

    /**
     * Spawns Den on the location of the animal which has called it.
     * @param animal
     */
    public void spawnDen(DenAnimal animal) {
        //Checks first if there is a nonblocking on the digsite and removes it
        if (world.containsNonBlocking(world.getLocation(animal))) {
            world.delete(world.getNonBlocking(world.getLocation(animal)));
        }
        world.setTile(world.getLocation(animal), this);
    }

    /**
         * Returns whether the owner of the Den is standing on Den. Used to hide and sleep
         * @return
         */
    public boolean isAnimalOnDen (Animal animal) {
            //try {
                //System.out.println("isAnimalOnDen " + world.getLocation(animal));
                if (world.getLocation(this).equals(world.getLocation(animal))) {

                    if (denType.equals("wolf") && animal instanceof Wolf || denType.equals("rabbit") && animal instanceof Rabbit) {
                        return true;
                    }
                }
            //} catch (IllegalArgumentException e) {
              //  System.out.println("Error!: " + e.getMessage() + ". Remember that i need a owner and need to exist before you call this method!");
            //}
            return false;

    }

    /**
     * Adds a animal to the Den
     * @param animal
     */
    public void addAnimalToDen(Animal animal) {
        animalsBelongingToDen.add(animal);
    }

    /**
     * Returns all the animals that belong to the Den
     * @return Set</Animal>
     */
    public Set<Animal> getAnimalsBelongingToDen() {
        return animalsBelongingToDen;
    }

    /**
     * Decides how big the Den should be from either giving it "wolf" (Big) or "rabbit" (small).
     * @return DisplayInformation - Used by Program
     */
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

    public String getDenType(){
        return denType;
    }

}