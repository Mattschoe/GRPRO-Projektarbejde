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
            System.out.println("regrow " + reGrowDays);
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

    public void getEaten() {
        hasBerries = false;
        reGrowDays = 2;
    }
    void grow(){
        hasBerries = true;

    }

    public boolean getHasBerries() {
        return hasBerries;
    }

    public int getReGrowDays() {
        return reGrowDays;
    }
}
/*
    @Override
    public DisplayInformation getInformation() {
     if (hasBerries){
        return new DisplayInformation(Color.BLUE, "bush-berries");
        } else {
        return new DisplayInformation(Color.GRAY, "bush");
        }
       }

*/

