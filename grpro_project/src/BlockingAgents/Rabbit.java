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
    private int age;
    private int energyLevel;
    private int maxEnergy;
    private RabbitBurrow burrow;

    public Rabbit(){}

    @Override
    public void act(World world ){
       /* if (energyLevel <= 0){
            die(world);
        }
        if (world.isNight()){
            sleep(world);

        } else {
*/
            movement(world);
            /*
            eat(world);
        }
*/



    }
    void movement(World world){
        Random r = new Random();

        Set<Location> neighbours = world.getEmptySurroundingTiles();
        List<Location> neighbourList = new ArrayList<>(neighbours);


        if (neighbourList.isEmpty()){
            Location l = neighbourList.get(r.nextInt(neighbourList.size()));
            world.move(this, l);
        }
    }
    void eat(World world){
        if (energyLevel < maxEnergy){
            // get tile
            Location location = world.getLocation(this);
            if (world.containsNonBlocking(location)){

                Object nonBlocking = world.getNonBlocking(location);
                if (nonBlocking instanceof Grass){ // maybe not this
                    world.delete(nonBlocking);
                    energyLevel++;
                }

            }


        }
    }
    void findBurrow(){}
    void digBurrow(){}
    void die(World world){
        world.delete(this);
    }
    void sleep(World world){
        if (burrow != null){
            findBurrow();
        }else {
            digBurrow();
        }
        world.remove(this);


    }




}
