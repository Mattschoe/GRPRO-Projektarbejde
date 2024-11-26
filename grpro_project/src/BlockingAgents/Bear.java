package BlockingAgents;

import NonblockingAgents.Bush;
import itumulator.executable.DisplayInformation;

import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Bear extends Predator implements DynamicDisplayInformationProvider, Herbivore {
    World world;
    ArrayList<Location> territory;
    boolean sleeping;
    Location bushLocation = null;
    Boolean wantsToBreed;
    int breedingDelay;



    public Bear(World world ){
        super(20, world);
        this.world = world;
        territory = new ArrayList<>();
        wantsToBreed = false;
        breedingDelay = 5;


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
                protectTerritory();
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
            birthday();
            breedingDelay--;
            if (age > 1 && breedingDelay == 0) wantsToBreed = true;

        }

        if (energyLevel < maxEnergy) { //If hungry
            //moveTo(getEatablePlantLocation());
            findEatablePlant();
            /*for (Location location: territory){
                if (world.getTile(location) instanceof Rabbit){
                    hunt();
                    //moveTo(location);
                }

            }*/

        }

    }

    @Override
    protected void sleep() {

    }

    @Override
    public DisplayInformation getInformation() {
        if (sleeping && age > 1){
            return new DisplayInformation(Color.BLUE, "bear-sleeping");
        } else if (age > 1){
            return new DisplayInformation(Color.GRAY, "bear");
        }

        if (sleeping && age <= 1){
            return new DisplayInformation(Color.GREEN, "bear-small-sleeping");
        } else {
            return new DisplayInformation(Color.RED, "bear-small");
        }


    }
    @Override

    public void eatPlant(){
        Set<Location> surroundingTiles = world.getSurroundingTiles(world.getLocation(this));
        for (Location location : surroundingTiles) {
            if (world.getTile(location) instanceof Bush bush) {
                bush.getEaten();
                energyLevel = energyLevel + 1;
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

    public void eatMeat() {}

    public void findEatableMeat() {}

    public Location getEatableMeatLocation() {

        return null;
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
    void protectTerritory(){
        for (Object entity : world.getEntities().keySet()){

            if (entity instanceof Bear){
                if (territory.contains(world.getLocation(entity))){
                    if (!wantsToBreed){
                    System.out.println("AAAAAAAheurheef");
                    fight();}
                    else if (((Bear) entity).wantsToBreed){
                        if (world.getSurroundingTiles().contains(world.getLocation(entity))){
                            reproduce();
                        }
                    }
                }

            }
        }
    }

    protected void reproduce() {
            Bear cup = new Bear(world);
            //neighbourList = getNeighbours(world);
            Set<Location> neighbours = world.getEmptySurroundingTiles();
            List<Location> neighbourList = new ArrayList<>(neighbours);
            if (!neighbourList.isEmpty()){
                Location birthPlace =  neighbourList.get(0);
                world.setTile(birthPlace, cup);
            }
            wantsToBreed = false;
            breedingDelay = 5;

    }

    public Boolean getWantsToBreed() {
        return wantsToBreed;
    }
}
