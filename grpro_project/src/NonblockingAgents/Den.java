package NonblockingAgents;

import BlockingAgents.DenAnimal;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.awt.*;

public class Den implements NonBlocking, DynamicDisplayInformationProvider {
    private World world;
    private DenAnimal owner;
    private boolean denSize;

    /**
     * @param world the current world
     * @param owner the owner of the den
     * @param denSize the size of den. True = big, False = small
     */
    public Den(World world, DenAnimal owner, boolean denSize) {
        this.world = world;
        this.owner = owner;
        this.denSize = denSize;
    }

    public void spawnDen() {
        if (owner != null) {
            //Checks first if there is a nonblocking on the digsite and removes it
            if (world.containsNonBlocking(world.getLocation(owner))) {
                world.delete(world.getNonBlocking(world.getLocation(owner)));
            }
            world.setTile(world.getLocation(owner), this);
        }
    }

    @Override
    public DisplayInformation getInformation() {
        //Makes a big den
        if (denSize) {
            return new DisplayInformation(Color.white, "hole");
        }
        //Makes a small den
        return new DisplayInformation(Color.black, "hole-small");
    }

    public DenAnimal getOwner() {
        return owner;
    }
}
