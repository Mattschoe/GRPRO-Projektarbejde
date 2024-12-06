package BlockingAgents;

import NonblockingAgents.Den;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;

import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.Random;

public class Wolf extends Predator implements DenAnimal, Carnivore, DynamicDisplayInformationProvider {
    Den den;
    WolfPack wolfpack;
    int huntRadius;
    boolean hasFoundMeat = false;
    Wolf opponentWolf;

    /**
     * Wolf without being a Alpha in a wolfpack
     * @param world
     */
    public Wolf(World world, boolean isInfected) {
        super(2, world, 30, 10, isInfected);
        huntRadius = 3;
    }

    //MANGLER: At få en wolfpack
    public void act(World world) {
        //If the wolf got damaged last act it goes into fighting mode
        if (health < maxHealth) {
            currentlyFighting = true;
        }

        //Daytime activities:
        if (world.isDay()) {
            isSleeping = false;
            if (sleepingLocation != null) {
                try { //Tries waking up
                    world.setTile(sleepingLocation, this);
                    sleepingLocation = null;
                } catch (IllegalArgumentException e) { //If there is already somebody above the hole it waits a step
                    System.out.println("Someone is standing on my den. - " + this);
                }
                for (Object obj : world.getEntities().keySet()){ //Reproduces wolf's with a 10% chance, adds it to the world and into the parents pack
                    if (obj instanceof Wolf wolf){
                        if ((wolf.getDen() == this.getDen() && wolf != this)){
                            Wolf babyWolf = new Wolf(world, false);
                            getWolfpack().addWolfToPack(babyWolf);
                            if (new Random().nextInt(10) == 0){reproduce(world.getLocation(den), babyWolf);}
                        }
                    }
                }
            } else if (energyLevel <= 0 || health <= 0) {
                die();
            } else if (isInfected) {
                // Makes sure it doesn't do wolf things when infected
                infectedMove();
            } else if (currentlyFighting || new Random().nextInt(4) == 1 || wolfpack != null && wolfpack.isWolfPackFighting()) { //Fighting, keeps on fighting or starts fighting with a 1/4% chance. Wolfs fight if their pack fights. Fight while it's not critically low on health, else runs away.
                System.out.println(health);
                if (health > 5) {
                    fight();
                } else if (wolfpack != null) { //Changes pack when its too low HP
                    System.out.print("Im changing pack! From: " + wolfpack);
                    changePack(opponentWolf.getWolfpack());
                    System.out.println("To: " + wolfpack);
                } else { //If it doesn't have a pack if just moves away from the opponent
                    System.out.println("Moving away from my opponent!");
                    moveAwayFrom(world.getLocation(opponentWolf));
                }
            } else if (wolfpack != null) { //Move towards the alpha wolf as long as its in a pack
                moveTo(wolfpack.getPackLocation());
            } else if (!isInfected) { //Else moves randomly
                move();
            }

            //Praise the Holy Grail of If-Statements
            if (isHungry() && sleepingLocation == null && world.getCurrentLocation() != null && world.getEntities().containsKey(this)) {
                //First checks if there is any easy meat close, otherwise it starts hunting. If neither it just moves.
                if (isThereFreshMeat()) {
                    eatFood();
                } else if (isPreyInHuntRadius(huntRadius)) {
                    hunt(findPrey());
                }
            }
        }

        //Nighttime activites
        if (world.isNight() && !isInfected) {
            if (world.getCurrentTime() == 10) {
                if (den == null) {
                    findDen();
                }
                updateMaxEnergy();
            }

            //Moves towards den until it's the middle of the night'
            if (world.getCurrentTime() < 15) {
                //If it reaches the burrow it goes to sleep otherwise it tries to move towards it
                if (!isSleeping && den.isAnimalOnDen(this)) {
                    world.remove(this);
                    sleepingLocation = world.getLocation(den);
                    isSleeping = true;

                } else if (!isSleeping) {
                    moveTo(world.getLocation(den));
                }
            } else if (world.getCurrentTime() == 15 && !isSleeping && !den.isAnimalOnDen(this)) { //Didnt reach the burrow
                isSleeping = true;
            }
        }
    }

    /**
     * Removes wolf from old pack and puts the wolf in the new pack
     * @param newPack WolfPack
     */
    private void changePack(WolfPack newPack) {
        wolfpack.removeWolfFromPack(this);
        newPack.addWolfToPack(this);
        wolfpack = newPack;
    }

    @Override
    public DisplayInformation getInformation() {
        if (isSleeping) {
            if (isInfected) {
                return new DisplayInformation(Color.red, "wolf-fungi-sleeping");
            } else {
                return new DisplayInformation(Color.red, "wolf-sleeping");
            }
        } else {
            if (isInfected) {
                return new DisplayInformation(Color.red, "wolf-fungi");
            } else {
                return new DisplayInformation(Color.blue, "wolf");
            }
        }
    }

    @Override
    protected void sleep() {
        world.remove(this);
    }

    /**
     * If there are any Den's in the world and the Wolf is the owner of it, this will find them, otherwise the wolf will dig a new one.
     */
    @Override
    public Location findDen() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof Den den){
                if (den.getAnimalsBelongingToDen().contains(this)) {
                    if (world.isTileEmpty(world.getLocation(den))){
                        this.den = den;
                        return world.getLocation(den);
                    }
                }
            }
        }
        return digDen(); //Makes a new Den if the wolf cant find any
    }

    /**
     * instantiates a new RabbitDens on the current location.
     */
    @Override
    public Location digDen() {
        //if (world.getNonBlocking(world.getLocation(this)) == null) {
        den = new Den(world, "wolf");
        den.spawnDen(this);//world.setTile(world.getLocation(this), den);
        return world.getLocation(den);
   /* }   else {
        return null;//findDen();
    }*/
    }

    /**
     * Sets the Den for the wolf. Used for WolfPack so the pack has the same Den to sleep in
     */
    public void setDen(Den den) {
        this.den = den;
    }

    /**
     * Returns the wolfs den
     * @return
     */
    public Den getDen() {
        return den;
    }

    /**
     * Returns the wolf's
     * @return
     */
    public WolfPack getWolfpack() {
        if (wolfpack != null) {
            return wolfpack;
        }
        throw new IllegalStateException("Wolfpack is null!");
    }

    /**
     * Finds a Wolf opponent in the map and saves it to the as opponentWolf. Use getOpponent to see the predators opponent
     */
    private void findOpponent() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof Wolf wolf) {
                opponentWolf = wolf;
            }
        }
    }

    /**
     * Returns the wolf that is the opponent
     * @return Wolf
     */
    public Wolf getOpponentWolf() {
        return opponentWolf;
    }

    /**
     * Checks if the wolf is near its opponent and fight otherwise it moves towards to opponent. If it doesn't have an opponent it finds one
     */
    protected void fight() {
        if (opponentWolf != null) { //Makes sure it doesnt fight a wolf in the same pack
            if (wolfpack != null && !wolfpack.getWolvesInPack().contains(opponentWolf)) {
                //If it killed the opponent last act or the opponent died it stops fighting, else it fights
                try {
                    currentlyFighting = true;
                    if (world.getSurroundingTiles(2).contains(world.getLocation(opponentWolf))) { //If the opponent is close by they fighht
                        opponentWolf.takeDamage(strength);
                    } else { //Else it moves towards the opponent
                        moveTo(world.getLocation(opponentWolf));
                    }
                } catch (IllegalArgumentException e) { //If the opponentAnimal doesn't exist anymore
                    currentlyFighting = false;
                }
            } else if (wolfpack == null) { //If the Wolf just doesnt have a pack
                try { //If it killed the opponent last act or the opponent died it stops fighting, else it fights
                    currentlyFighting = true;
                    if (world.getSurroundingTiles(2).contains(world.getLocation(opponentWolf))) { //If the opponent is close by they fighht
                        opponentWolf.takeDamage(strength);
                    } else { //Else it moves towards the opponent
                        moveTo(world.getLocation(opponentWolf));
                    }
                } catch (IllegalArgumentException e) { //If the opponentAnimal doesn't exist anymore
                    currentlyFighting = false;
                }
            }
        } else {
            currentlyFighting = false;
            findOpponent();
        }
    }

    public boolean getCurrentlyFighting() {
        return currentlyFighting;
    }
}
