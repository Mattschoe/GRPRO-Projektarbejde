package NonblockingAgents;

import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;

import itumulator.world.World;

import java.awt.*;

public class Bush extends Plant implements Actor, DynamicDisplayInformationProvider {
    World world;
    boolean hasBerries;
    public Bush(World world) {
        super(world);
        this.world = world;
        hasBerries = false;
    }

    @Override
    public void act(World world) {
        this.world = world;
        if (world.isDay()) {
            spread(new Bush(world), 20 );

        }

        if (world.getCurrentTime() == 19){
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
    }
    void grow(){
        hasBerries = true;

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

