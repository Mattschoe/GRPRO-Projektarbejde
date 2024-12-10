package BlockingAgents;

import itumulator.executable.DisplayInformation;

import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Bear extends Predator implements DynamicDisplayInformationProvider, Herbivore, Carnivore {
    private ArrayList<Location> territory;
    private boolean wantsToBreed;
    private int breedingDelay;
    private Animal opponent;
    private Bear mate;

    //Skal lige op i den gamle
    public Bear(World world, boolean isInfected){
        super(20, world, 60, 60, isInfected);
        this.world = world;
        territory = new ArrayList<>();
        wantsToBreed = false;
        breedingDelay = 0;
    }

    /**
     * Initialises the world and sets up what actions the Bear should do for every action
     */
    @Override
    public void act(World world){

        if (territory.isEmpty()){
            setTerritory();
        }

        //Daytime activities
        if (world.isDay()) {
            isSleeping = false;
            if (breedingDelay <= 0) {
                wantsToBreed = true;
                findMate();

            }
            System.out.println("energy: " + energyLevel);
            if (world.getCurrentTime() == 10) {
                breedingDelay--;

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
                protectTerritory();
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
        }
    }

    /**
     * Puts the Bear to sleep
     */
    @Override
    protected void sleep() {
        isSleeping = true;
    }

    /**
     * Outputs the view of the Bear depending on whether it is infected and sleeping
     * @return DisplayInformation
     */
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

    /**
     * Sets the territory of the bear to be all tiles within a radius of 4
     */
    private void setTerritory(){

        Set<Location> surroundingTiles = world.getSurroundingTiles(world.getLocation(this), world.getSize()/2);

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


    /**
     * The Bear walks toward it's mate. If it does not have a mate, it does nothing.
     */
    private void findMate(){
        if (mate != null) {
            try {
                System.out.println("mate: " + mate);
                moveTo(world.getLocation(mate));
            }
            catch (IllegalArgumentException e){
                mate = null;
                System.out.println("the mate has died");
            }
        }
    }
    /**
     * Attack other Bears if they come inside the territory and this Bear does not want to breed. If both want to breed, and they are beside each other, they do
     */
    private void protectTerritory(){
        for (Object entity : world.getEntities().keySet()){

            if (entity instanceof Bear && entity != this){
                if (territory.contains(world.getLocation(entity))){
                    if (!wantsToBreed){
                        opponent = (Bear) entity;
                        fight();
                    //System.out.println("Fight fight fight");
                    }
                    else if (((Bear) entity).wantsToBreed && wantsToBreed){
                        System.out.println("baby??");
                        mate = (Bear) entity;
                        //moveTo(world.getLocation(entity));
                        if (world.getSurroundingTiles().contains(world.getLocation(entity))){
                            if (new Random().nextInt(10) == 0 ){
                            reproduce(world.getLocation(this), new Bear(world,isInfected));
                            }
                        }
                    }
                }

            }
        }
    }

    /**
     * Creates a new Bear on the desired location
     * @param location the location where the new Bear spawns
     * @param animal technically any animal, but is intended to be a Bear
     */
    @Override
    protected void reproduce(Location location, Animal animal) {
        super.reproduce(location, animal);

            wantsToBreed = false;
            breedingDelay = 5;
            mate = null;

    }

    /**
     * Checks if the wolf is near its opponent and fight otherwise it moves towards to opponent. If it doesn't have an opponent it finds one
     */
    protected void fight() {
        if (opponent != null) {
            try { //If it killed the opponent last act or the opponent died it stops fighting, else it fights
                currentlyFighting = true;
                System.out.println("fighting: " + opponent);
                if (world.getSurroundingTiles(2).contains(world.getLocation(opponent))) { //If the opponent is close by they fighht
                    opponent.takeDamage(strength);
                } else { //Else it moves towards the opponent
                    moveTo(world.getLocation(opponent));
                }
            } catch (IllegalArgumentException e) { //If the opponentAnimal doesn't exist anymore
                currentlyFighting = false;
                System.out.println("the opponent does not exist anymore");
            }
        } else {
            currentlyFighting = false;
            findOpponent();
        }
    }

    /**
     * Finds a Wolf opponent in the map and saves it to the as opponentWolf. Use getOpponent to see the predators opponent
     */
    private void findOpponent() {
        for (Location location : territory) {
            if (world.getTile(location) != null && world.getTile(location) instanceof Predator predator) {
                opponent = predator;
                return;
            }
        }
    }

    /**
     * Sets whether the Bear wants to breed
     * @param wantsToBreed whether the Bear wants to breed
     */
    public void setWantsToBreed(Boolean wantsToBreed) {
        this.wantsToBreed = wantsToBreed;
    }
    public void setBreedingDelay(Integer breedingDelay) {
        this.breedingDelay = breedingDelay;
    }
}
