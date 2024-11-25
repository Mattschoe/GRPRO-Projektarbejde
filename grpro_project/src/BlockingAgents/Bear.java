package BlockingAgents;

import NonblockingAgents.Grass;
import NonblockingAgents.Territory;
import itumulator.executable.DisplayInformation;

import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Bear extends Predator implements DynamicDisplayInformationProvider {
    World world;
    ArrayList<Territory> territory;
    boolean sleeping ;

    public Bear(World world ){
        super(20, world);
        this.world = world;
        setTerritory();
        //world.setTile(location, this );

    }
    @Override
    public void act(World world){
        this.world = world;
        if (world.isDay()){
            move();
            sleeping = false;
        }
        else {
            sleeping = world.isNight();
        }
    }

    @Override
    public DisplayInformation getInformation() {
        if (sleeping){
            return new DisplayInformation(Color.BLUE, "bear-sleeping");
        } else {
            return new DisplayInformation(Color.GRAY, "bear");
        }
    }
    @Override
    protected void sleep(){


    }

    void setTerritory(){

        //world.setTile(world.getLocation(this), new Grass(world));//world.getLocation(this), new Territory(world.getLocation(this), world,this));

        /*Set<Location> surroundingTiles = world.getSurroundingTiles(world.getLocation(this));
        for (int i = 0; i < surroundingTiles.toArray().length; i++) {
            territory.add(new Territory(world.getLocation(surroundingTiles.toArray()[i]), world ,this));
            world.setTile(world.getLocation(surroundingTiles.toArray()), territory.get(i));
        }*/


    }




}
