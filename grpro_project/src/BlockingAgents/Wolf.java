package BlockingAgents;

import NonblockingAgents.Den;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;

import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.util.Random;

public class Wolf extends Predator implements DenAnimal, Carnivore, DynamicDisplayInformationProvider {
    private Den den;
    private WolfPack wolfpack;
    private int huntRadius;
    private Wolf opponentWolf;

    /**
     * Wolf without being a Alpha in a wolfpack
     * @param world The world the wolf exists in
     * @param isInfected whether the wolf is infected with fungi or not
     */
    public Wolf(World world , boolean isInfected) {
        super(2, world, 30, 10, isInfected);
        huntRadius = 3;
    }
    public Wolf(World world ,WolfPack wolfpack, boolean isInfected) {
        super(2, world, 30, 10, isInfected);
        huntRadius = 3;
        this.wolfpack = wolfpack;
    }

    /**
     * Initialises the world and sets up what actions the Wolf should do for every action
     */
    public void act(World world) {
        //If the wolf got damaged last act it goes into fighting mode. Else it makes sure that it's still not in fight mode
        if (tookDamage) {
            currentlyFighting = true;
            tookDamage = false;
        } else {
            currentlyFighting = false;
        }

        //Daytime activities:
        if (world.isDay()) {
            isSleeping = false;
            if (sleepingLocation != null) {
                try { //Tries waking up
                    world.setTile(sleepingLocation, this);
                    world.setCurrentLocation(sleepingLocation);
                    sleepingLocation = null;
                } catch (IllegalArgumentException e) { //If there is already somebody above the hole it waits a step
                }
                for (Object obj : world.getEntities().keySet()){ //Reproduces wolf's with a 10% chance, adds it to the world and into the parents pack
                    if (obj instanceof Wolf wolf){
                        if ((wolf.getDen() == this.getDen() && wolf != this)){
                            Wolf babyWolf = new Wolf(world, false);
                            if (wolfpack == null){
                                wolfpack = new WolfPack(world);
                                wolfpack.addWolfToPack(this);
                            }
                            if (wolfpack == wolf.getWolfpack()) {
                                System.out.println("Wolfpack is the same as the wolfpack");
                                wolfpack.addWolfToPack(babyWolf);
                                if (new Random().nextInt(10) == 0) {
                                    reproduce(world.getLocation(den), babyWolf);
                                }
                            }
                        }
                    }
                }
            } else if (energyLevel <= 0 || health <= 0) {
                die();
            } else if (isInfected) {
                // Makes sure it doesn't do wolf things when infected
                infectedMove();
            } else if (currentlyFighting || new Random().nextInt(4) == 1 ||( wolfpack != null && wolfpack.isWolfPackFighting())) { //Fighting, keeps on fighting or starts fighting with a 1/4% chance. Wolfs fight if their pack fights. Fight while it's not critically low on health, else runs away.;
                if (health > 5) {

                    fight();
                } else if (wolfpack != null && health <=5 ) { //Changes pack when its too low HP
                    currentlyFighting = false;
                    System.out.println("i am changing pack ");
                    changePack(opponentWolf.getWolfpack());
                } else { //If it doesn't have a pack if just moves away from the opponent
                    try {
                        moveAwayFrom(world.getLocation(opponentWolf));
                    } catch (IllegalArgumentException e) {
                        move();
                    }
                }
            } else if (wolfpack != null) { //Tries to move towards the alpha wolf as long as it's in a pack else it just moves randomly
                try {
                    moveTo(wolfpack.getPackLocation());
                } catch (IllegalArgumentException e) {
                    move();
                }
            } else if (!isInfected) { //Else moves randomly
                move();
            }

            //Praise the Holy Grail of If-Statements
            if (isHungry() && sleepingLocation == null && world.getCurrentLocation() != null && world.getEntities().containsKey(this)) {
                //First checks if there is any easy meat close, otherwise it starts hunting. If neither it just moves.
                if (isThereFreshMeat()) {
                    eatFood();
                } else if (isPreyInHuntRadius(huntRadius)) {
                    hunt(findPrey(huntRadius));
                }
            }
        }

        //Nighttime activites
        if (world.isNight() && !isInfected) {
            if (world.getCurrentTime() == 10) {
                if ( den == null ){
                    if ( wolfpack == null  || wolfpack.getPackDen() == null) {//wolfpack.getAlphaWolf() == this){
                        System.out.println("findDen");
                        findDen();
                    } else if (wolfpack != null){
                        System.out.println("get pack den ");
                    den = wolfpack.getPackDen();
                    }

                }
                updateMaxEnergy();

            }
            if (world.getCurrentTime() == 12) {
                if (den == null){
                    den = wolfpack.getPackDen();
                }}

            //Moves towards den until it's the middle of the night'
            if (world.getCurrentTime() < 15) {
                //If it reaches the burrow it goes to sleep otherwise it tries to move towards it
                if (den.getDenType().equals("rabbit")) {
                    System.out.println("Seems we lost our den, guys...");
                }
                if (!isSleeping && den.isAnimalOnDen(this)) {
                    world.remove(this);
                    sleepingLocation = world.getLocation(den);
                    isSleeping = true;

                } else if (!isSleeping && den != null) {
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

    /**
     * Makes the Wolf look different depending on whether it is infected or sleeping
     */
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

    /**
     * Puts the Wolf to sleep by removing it from the world (Because it is in a Den)
     */
    @Override
    protected void sleep() {
        world.remove(this);
    }

    /**
     * If there are any Dens in the world and the Wolf is the owner of it, this will find them, otherwise the wolf will dig a new one.
     */
    @Override
    public Location findDen() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof Den den){
                if (den.getDenType() == "wolf")  {
                    if (world.isTileEmpty(world.getLocation(den))){
                        this.den = den;
                        if (wolfpack != null) {
                            wolfpack.setDen(den);
                        }
                        return world.getLocation(den);
                    }
                }
            }
        }
        return digDen(); //Makes a new Den if the wolf cant find any
    }

    /**
     * instantiates a new Dens on the current location.
     */
    @Override
    public Location digDen() {
        //if (world.getNonBlocking(world.getLocation(this)) == null) {
        den = new Den(world, "wolf");
        if (wolfpack != null) {
            wolfpack.setDen(den);
        }
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
     * @return Den
     */
    public Den getDen() {
        return den;
    }

    /**
     * Returns the wolf's
     * @return wolfpack
     *
     */
    public WolfPack getWolfpack() {
        if (wolfpack != null) {
            return wolfpack;
        }
        else {
     return null;
    }}

    /**
     * Makes the Wolf part of a specified pack.
     * @param wolfpack The WolfPack this Wolf should join
     */
    public void setWolfpack(WolfPack wolfpack) {
        this.wolfpack = wolfpack;
        /*if (!this.wolfpack.isWolfInPack(this)) {
            this.wolfpack.addWolfToPack(this);
        }*/
    }

    /**
     * Finds a Wolf opponent in the map and saves it to the as opponentWolf. Use getOpponent to see the predators opponent
     */
    private void findOpponent() {
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof Wolf wolf && wolf != this) {
                opponentWolf = wolf;
            }
        }
    }

    /**
     * Checks if the wolf is near its opponent and fight otherwise it moves towards to opponent. If it doesn't have an opponent it finds one
     */
    protected void fight() {
        if (opponentWolf != null) {
            if (wolfpack != null && wolfpack != opponentWolf.getWolfpack()){//!wolfpack.getWolvesInPack().contains(opponentWolf)) { //Makes sure it doesnt fight a wolf in the same pack
                //If it killed the opponent last act or the opponent died it stops fighting, else it fights
                try {

                    currentlyFighting = true;
                    if (world.getSurroundingTiles(2).contains(world.getLocation(opponentWolf))) { //If the opponent is close by they fight
                        opponentWolf.takeDamage(strength);
                        System.out.println("fighting");
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

    /**
     * @return boolean currentlyFighting
     */
    public boolean getCurrentlyFighting() {
        return currentlyFighting;
    }

    /**
     * Returns how much health the Wolf has left
     * @return int health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns how much health the Wolf can have in total
     * @return int maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }
}
