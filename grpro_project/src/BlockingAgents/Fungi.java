package BlockingAgents;

import NonblockingAgents.Grass;
import itumulator.executable.DisplayInformation;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;

import java.awt.*;


public class Fungi implements Actor {

    World world;
    Location location;
    int energyLevel;
    int radius;

    public Fungi(World world, int energyLevel) {
        this.world = world;
        this.energyLevel = energyLevel;
        this.radius = 2;
    }

    public void act(World world) {
        this.world = world;
        spread(); // Skal kunne sprede sig til Meat i nærheden
        if (this.energyLevel < 1) {
            die(world.getLocation(this));
        }
    }

    void spread() {
        for (Object entity : world.getEntities().keySet()) {
            if (entity instanceof Meat) { // Makes sure it infects only meat, which is close
                for (Location nearbyLocation : world.getSurroundingTiles(this.radius)) {
                    if (entity == world.getTile(nearbyLocation)) {
                        ((Meat) entity).isInfected = true;
                    }
                }
            }
        }
        energyLevel--;
    }

    void die(Location location) {
        world.delete(this);
        world.setTile(location, new Grass(world)); // Spawns grass upon death
    }

    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.GRAY, "fungi");
    }

}
