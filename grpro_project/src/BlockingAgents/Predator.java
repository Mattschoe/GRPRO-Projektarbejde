package BlockingAgents;

import NonblockingAgents.Territory;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.List;
import java.util.Set;

//Flyt metoder herop
//Implementer getmetoder til at finde ud af om maden er et dyr eller en plante.
public abstract class Predator extends Animal{
    int strength;
    World world;
    boolean currentlyFighting;
    boolean currentlyHunting;
    Animal opponentAnimal;


    Predator(int strength , World world, int maxEnergy, int maxHealth){
        super(world,0,maxEnergy, maxHealth);
        this.strength = strength;
        this.world = world;
        currentlyFighting = false;
        opponentAnimal = null;
    }
    @Override
    public void act(World world){}


    /**
     * Hunts after prey
     */
    protected void hunt (Animal opponentAnimal){
        this.opponentAnimal = opponentAnimal;
    }

    /**
     * Kills prey if its nearby
     */
    protected void kill() {

    }


    protected void fight () {
        try {
            currentlyFighting = true;
            opponentAnimal.takeDamage(strength);
            if (opponentAnimal.health <= 0) {
                opponentAnimal.die();
                currentlyFighting = false;
            }
        } catch ( IllegalArgumentException e) {
            System.out.println("Fighting Animal is null! Error: " + e.getMessage());
        }


    }

    /**
     * Detects prey in a "huntRadius" radius.
     */
    protected boolean detectPrey(int huntRadius) {
        for (Location location : world.getSurroundingTiles(huntRadius)) {
            if (world.getTile(location) instanceof Prey) {
                return true;
            }
        }
        return false;
    }
}
