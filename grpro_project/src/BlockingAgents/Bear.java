package BlockingAgents;

import NonblockingAgents.Bush;
import NonblockingAgents.Meat;
import itumulator.executable.DisplayInformation;

import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class Bear extends Predator implements DynamicDisplayInformationProvider, Herbivore {
    World world;
    ArrayList<Location> territory;
    boolean sleeping;
    Location bushLocation = null;
    boolean hasFoundMeat = false;
    Location meatLocation = null;



    public Bear(World world ){
        super(20, world);
        this.world = world;
        territory = new ArrayList<>();

        //world.setTile(location, this );

    }
    @Override
    public void act(World world){
        this.world = world;
        if (territory.isEmpty()){
            setTerritory();
        }

        if (world.isDay()){
            if (territory.contains(world.getLocation(this))){
                move();
            } else {
                moveTo(territory.get(0));
            }
            sleeping = false;
        }
        else {
            sleeping = world.isNight();
        }
        if (world.getCurrentTime() == 10) {
            energyLevel--;
        }
        if (energyLevel < maxEnergy) { //If hungry
            //moveTo(getEatablePlantLocation());
            // findEatableMeat();
            findEatablePlant();
            for (Location location: territory){
                if (world.getTile(location) instanceof Rabbit){
                    hunt();
                    //moveTo(location);
                }

            }


        }
    }

    @Override
    protected void sleep() {

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

    public void eatPlant(){
        Set<Location> surroundingTiles = world.getSurroundingTiles(world.getLocation(this));
        for (Location location : surroundingTiles) {
            if (world.getTile(location) instanceof Bush bush) {
                bush.getEaten();
                energyLevel = energyLevel + 5;
                break;
            }
        }
    }

    @Override
    public void findEatablePlant() {
        //if (!hasFoundBush) {
            Set<Location>  surroundingTiles = world.getSurroundingTiles(world.getLocation(this));
            for (Location location : surroundingTiles) {
                if (world.getTile(location) instanceof Bush bush) {
                    bushLocation = location;
                    bush.getEaten();
                    break;
                }
            }
            /*Set<Location> emptysurroundingTiles = world.getEmptySurroundingTiles(bushLocation);
            System.out.println(emptysurroundingTiles.toArray());
            moveTo((Location )emptysurroundingTiles.toArray()[0]);

            /*
            for (Object object : world.getEntities().keySet()) {
                if (object instanceof Bush bush) {
                    Set<Location>  bushNeighbours = world.getEmptySurroundingTiles(world.getLocation(bush));
                    bushLocation = (Location) bushNeighbours.toArray()[0];
                    System.out.println(bushLocation.toString());
                    hasFoundBush = true;
                    break;
                }
            }
            for (int i = 0; i < territory.size(); i++) {
                if (world.getTile(territory.get(i)) instanceof Bush) {
                    bushLocation = territory.get(i);
                    hasFoundBush = true;
                    break;
                }

            }*/

        //}
    }

    @Override
    public Location getEatablePlantLocation() {

        if (bushLocation == null){
            findEatablePlant();
            return bushLocation;
        }
        return bushLocation;
    }


    public void findEatableMeat() {
        //Finds a spot of grass if the rabbit hasn't found it
        if (!hasFoundMeat) {
            for (Object object : world.getEntities().keySet()) {
                if (object instanceof Meat meat) {
                    meatLocation = world.getLocation(meat);
                    hasFoundMeat = true;
                    break;
                }
            }
        }
    }

    public Location getEatableMeatLocation() {
        if (meatLocation == null) {
            findEatableMeat();
            return meatLocation;
        }
        return meatLocation;
    }

    public void eatMeat() {
        if (world.getNonBlocking(world.getLocation(this)) instanceof Meat meat) {
            energyLevel = meat.energyLevel;
            world.delete(meat);
        }
    }

    void setTerritory(){

        Set<Location> surroundingTiles = world.getSurroundingTiles(world.getLocation(this), 4);

        territory.add(world.getLocation(this));

        for (int i = 0; i < surroundingTiles.toArray().length; i++) {
            territory.add((Location) surroundingTiles.toArray()[i]);
            //world.setTile(territory.get(i), new Territory(territory.get(i), world, this));
        }


        //world.setTile(world.getLocation(this), new Grass(world));//world.getLocation(this), new Territory(world.getLocation(this), world,this));

        /*Set<Location> surroundingTiles = world.getSurroundingTiles(world.getLocation(this));
        for (int i = 0; i < surroundingTiles.toArray().length; i++) {
            territory.add(new Territory(world.getLocation(surroundingTiles.toArray()[i]), world ,this));
            world.setTile(world.getLocation(surroundingTiles.toArray()), territory.get(i));
        }*/


    }

    public ArrayList<Location> getTerritory(){
        return territory;
    }

    protected void reproduce() {}

}
