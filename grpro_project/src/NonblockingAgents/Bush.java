package NonblockingAgents;

import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;

import itumulator.world.World;

import java.awt.*;

public class Bush implements Actor, DynamicDisplayInformationProvider {
    World world;
    boolean hasBerries;
    int reGrowDays;
    public Bush(World world) {

        this.world = world;
        hasBerries = true;
        reGrowDays = 2;
    }

    @Override
    public void act(World world) {
        this.world = world;


        if (world.getCurrentTime() == 19 && !hasBerries) {
            reGrowDays -= 1;
        }
        if (reGrowDays <= 0){
            grow();
        }

    }

    @Override
    public DisplayInformation getInformation() {
        if (hasBerries){
            return new DisplayInformation(Color.BLUE, "bush-berries");
        } else {
            return new DisplayInformation(Color.GRAY, "bush");
        }
    }

    /**
     * Berries are eaten, leaving the bush empty.
     */
    public void getEaten() {
        hasBerries = false;
        reGrowDays = 2;
    }

    /**
     * Makes the bushes have berries again
     */
    void grow(){
        hasBerries = true;

    }

    /**
     * Returns whether there are berries on the bush or not
     * @return boolean
     */
    public boolean getHasBerries() {
        return hasBerries;
    }

    /**
     * Returns how many days there are left until berries grow back on the bush
     * @return int
     */
    public int getReGrowDays() {
        return reGrowDays;
    }
}

