package NonblockingAgents;

import itumulator.executable.DisplayInformation;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;


public class Mushroom implements Actor, NonBlocking {

    World world;
    Location location;
    int energyLevel;
    int radius;

    public Mushroom(World world, int energyLevel) { // energyLevel skal komme an på hvilket dyr det var
        this.world = world;

        this.energyLevel = energyLevel;
        this.radius = 2;
    }

    public void act(World world) {
        this.world = world;
        spread(); // Skal kunne sprede sig til Meat i nærheden
    }

    void spread() {
        for (Object entity : world.getEntities().keySet()) {
            if (entity instanceof Meat) { // Makes sure it infects only meat, which is close
                for (Location nearbyLocation : world.getSurroundingTiles(this.radius)) {
                    if (entity == world.getTile(nearbyLocation)) {
                        ((Meat) entity).infected = true;
                    }
                }
            }
        }
        energyLevel--;
    }

    @Override
    public DisplayInformation getInformation() {
        return null;
    }

}
