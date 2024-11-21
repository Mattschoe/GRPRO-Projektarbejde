package BlockingAgents;
import NonblockingAgents.RabbitBurrow;
import itumulator.world.World;
import itumulator.simulator.Actor;


public class Rabbit implements Actor {
    int age;
    int energyLevel;
    RabbitBurrow burrow;
    Rabbit (){}

    @Override
    public void act(World world ){


    }
    void movement(){

    }
    void eat(){}
    void findBurrow(){}
    void digBurrow(){}
    void die(World world){
        world.delete(this);
    }
    void sleep(){}




}
