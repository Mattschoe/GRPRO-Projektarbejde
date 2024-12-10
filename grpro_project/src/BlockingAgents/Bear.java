package BlockingAgents;

import itumulator.executable.DisplayInformation;

import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class Bear extends Predator implements DynamicDisplayInformationProvider, Herbivore, Carnivore {
    private ArrayList<Location> territory;
    private boolean wantsToBreed;
    private int breedingDelay;
    private Animal opponent;

    //Skal lige op i den gamle
    public Bear(World world, boolean isInfected){
        super(20, world, 50, 50, isInfected);
        this.world = world;
        territory = new ArrayList<>();
        wantsToBreed = false;
        breedingDelay = 5;
    }

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

    private void setTerritory(){

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

    private void protectTerritory(){
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

    /**
     * Checks if the wolf is near its opponent and fight otherwise it moves towards to opponent. If it doesn't have an opponent it finds one
     */
    protected void fight() {
        if (opponent != null) { //Makes sure it doesnt fight a wolf in the same pack
            try { //If it killed the opponent last act or the opponent died it stops fighting, else it fights
                currentlyFighting = true;
                if (world.getSurroundingTiles(2).contains(world.getLocation(opponent))) { //If the opponent is close by they fighht
                    opponent.takeDamage(strength);
                } else { //Else it moves towards the opponent
                    moveTo(world.getLocation(opponent));
                }
            } catch (IllegalArgumentException e) { //If the opponentAnimal doesn't exist anymore
                currentlyFighting = false;
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

    public void setWantsToBreed(Boolean wantsToBreed) {
        this.wantsToBreed = wantsToBreed;
    }
}
