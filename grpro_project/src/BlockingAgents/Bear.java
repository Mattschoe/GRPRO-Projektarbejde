package BlockingAgents;

import NonblockingAgents.Territory;
import itumulator.world.Location;
import itumulator.world.World;
import java.util.Set;

public class Bear extends Predator{
    World world;

    public Bear(World world ){
        super(20, world);
        this.world = world;
        //world.setTile(location, this );

    }

    protected void sleep() {}

    protected void reproduce() {}

}
