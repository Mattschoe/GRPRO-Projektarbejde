package BlockingAgents;

import itumulator.executable.DisplayInformation;

import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.ArrayList;
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

    //Skal lige op i den gamle
    public Bear(World world, boolean isInfected){
        super(20, world, 50, 50, isInfected);
        this.world = world;
        territory = new ArrayList<>();
        wantsToBreed = false;
        breedingDelay = 5;

        //world.setTile(location, this );

    }


    //MANGLER: At hunte efter kaniner
    @Override
    public void act(World world){
        try {
        if (territory.isEmpty()){
            setTerritory();
        }

        //Daytime activities
        if (world.isDay()) {
            isSleeping = false;
            protectTerritory();
            if (world.getCurrentTime() == 10) {
                energyLevel--;
                breedingDelay--;
                if (breedingDelay <= 0) {
                    wantsToBreed = true;
                }
            }
            if (energyLevel <= 0) { //Dies when out of energy
                System.out.println("BAIII");
                die();
                return;
            }
            else if (isInfected) {
                // Makes sure it doesn't do bear things when infected
                infectedMove();
            } else if (currentlyFighting) { //Fighting. Bear fights to death.
                fight();
            } else if (isInTerritory()) { //Moves around in territory and protects it
                //protectTerritory();
            } else if (!isInTerritory()) { //If it's not in its territory it moves towards it
                moveTo(territory.getFirst());
            }

            if (isHungry() && !isInfected) { //Eats food if it's hungry
                eatFood();
            }
            if (!isInfected) {
                move();
            }
        }

        //Nighttime activities
        if (world.isNight() && !isInfected) {
            updateMaxEnergy();
            sleep();
        }} catch (IllegalArgumentException e) {

        }
    }

    @Override
    protected void sleep() {
        isSleeping = true;
    }

    @Override
    public DisplayInformation getInformation() {
        if (isSleeping) {
            if (isInfected) {
            return new DisplayInformation(Color.red, "bear-fungi-sleeping");
            } else {
            return new DisplayInformation(Color.red, "bear-sleeping");
            }
        } else {
            if (isInfected) {
                return new DisplayInformation(Color.blue, "bear-fungi");
            } else {
            return new DisplayInformation(Color.blue, "bear");
            }
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
                            reproduce(world.getLocation(this), new Bear(world,isInfected));
                        }
                    }
                }

            }
        }
    }
    @Override
    protected void reproduce(Location location, Animal animal) {
        super.reproduce(location, animal);

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
