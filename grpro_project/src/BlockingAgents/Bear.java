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

public class Bear extends Predator implements DynamicDisplayInformationProvider, Herbivore, Carnivore {
    World world;
    ArrayList<Location> territory;
    boolean sleeping;
    Location bushLocation = null;
    boolean hasFoundMeat = false;
    Location meatLocation = null;
    Boolean wantsToBreed;
    int breedingDelay;



    public Bear(World world ){
        super(20, world, 50, 50);
        this.world = world;
        territory = new ArrayList<>();
        wantsToBreed = false;
        breedingDelay = 5;

        //world.setTile(location, this );

    }
    //MANGLER: At hunte efter kaniner
    @Override
    public void act(World world){
        if (territory.isEmpty()){
            setTerritory();
        }

        //Daytime activities
        if (world.isDay()) {
            isSleeping = false;
            if (energyLevel <= 0) { //Dies when out of energy
                System.out.println("BAIII");
                die();
                return;
            } else if (currentlyFighting) { //Fighting. Bear fights to death.
                fight();
            } else if (isInTerritory()) { //Moves around in territory and protects it
                //protectTerritory();
            } else if (!isInTerritory()) { //If it's not in its territory it moves towards it
                moveTo(territory.getFirst());
            }

            if (isHungry()) { //Eats food if it's hungry
                eatFood();
            } else {
                move();
            }

        }

        //Nighttime activities
        if (world.isNight()) {
            updateMaxEnergy();
            sleep();
        }
    }

    @Override
    protected void sleep() {
        isSleeping = true;
    }

    @Override
    public DisplayInformation getInformation() {
        if (isSleeping) {
            return new DisplayInformation(Color.red, "bear-sleeping");
        } else {
            return new DisplayInformation(Color.blue, "bear");
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

    /**
     * Returns whether the bear is in its territory or not
     * @return boolean
     */
    private boolean isInTerritory() {
        return territory.contains(world.getLocation(this));
    }

    void protectTerritory(){
        for (Object entity : world.getEntities().keySet()){

            if (entity instanceof Bear){
                if (territory.contains(world.getLocation(entity))){
                    if (!wantsToBreed){ fight(); }
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
                Location birthPlace =  neighbourList.getFirst();
                world.setTile(birthPlace, cup);
            }
            wantsToBreed = false;
            breedingDelay = 5;

    }

    public ArrayList<Location> getTerritory(){
        return territory;
    }

    public Boolean getWantsToBreed() {
        return wantsToBreed;
    }
}
