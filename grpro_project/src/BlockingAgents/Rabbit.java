package BlockingAgents;
import NonblockingAgents.Grass;
import NonblockingAgents.RabbitBurrow;
import itumulator.world.Location;
import itumulator.world.World;
import itumulator.simulator.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Rabbit implements Actor {
    int age;
    int energyLevel;
    int maxEnergy;
    RabbitBurrow burrow;
    World world;

    public Rabbit(){
        this.age = 0;
        this.maxEnergy = 10 - age;
        this.energyLevel = maxEnergy;


    }

    @Override
    public void act(World world ){
        this.world = world;
        if (world.getCurrentTime() == 19){ // morning
            if (burrow != null) {
                energyLevel -= 8;
                world.setTile(world.getLocation(burrow), this);
                world.setCurrentLocation(world.getLocation(burrow));
            }
        }
       if (energyLevel <= 0) die();
       //Finds or digs burrow when nightfall
       if (world.getCurrentTime() == 10 ) {
           findBurrow();
       }
       if (world.getCurrentTime() == 11) {
           sleep();
       }

        if (world.isDay()){
            movement();

            if (energyLevel < maxEnergy) eat();

            Set<Location> neighbours = world.getSurroundingTiles();
            if (world.getAll(Rabbit.class,neighbours ).size() > 1) {
                if (new Random().nextInt(12) == 0) {
                reproduce();
                }
            }

        }

    }

    /**
     * Gives the rabbit random movement.
     *
     */
    void movement(){
        Random r = new Random();

        Set<Location> neighbours = world.getEmptySurroundingTiles();
        List<Location> neighbourList = new ArrayList<>(neighbours);


        if (!neighbourList.isEmpty()){
            Location l = neighbourList.get(r.nextInt(neighbourList.size()));
            world.move(this, l);
        }
    }

    /**
     * Is used by the rabbit to eat the tile of grass, that it is standing on.
     * The rabbits energyLevel increments by 1, when a tile of grass is eaten
     * If there is no grass, the rabbit can't eat it. If the grass is eaten, it disappears.
     */
    void eat(){
            Location location = world.getLocation(this);
            if (world.containsNonBlocking(location)){

                Object nonBlocking = world.getNonBlocking(location);
                if (nonBlocking instanceof Grass){ // maybe not this
                    world.delete(nonBlocking);
                    energyLevel++;
                }

            }
        }


    /**
     * Goes to random rabbitBurrow in the world, if the rabbitBurrow doesn't have a rabbit
      */
    void findBurrow(){
        for (Object object : world.getEntities().keySet()) {
            if (object instanceof RabbitBurrow rabbitBurrow){
                if (world.isTileEmpty(world.getLocation(rabbitBurrow))){
                    world.move(this, world.getLocation(rabbitBurrow));
                    burrow = rabbitBurrow;
                } else {
                    digBurrow();
                }
            }
        }
    }
    void digBurrow(){
        burrow = new RabbitBurrow(world,this);
        burrow.spawnBurrow();
    }
    void die(){
        System.out.println("Dying....:(");
        world.delete(this);
    }
    void sleep(){
        world.remove(this);

    }
    void reproduce(){
        Rabbit kid = new Rabbit();
        //neighbourList = getNeighbours(world);
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        List<Location> neighbourList = new ArrayList<>(neighbours);
        if (!neighbourList.isEmpty()){
        Location birthPlace =  neighbourList.get(0);

        world.setTile(birthPlace, kid);
    }}
}
