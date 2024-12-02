package NonblockingAgents;

import BlockingAgents.Meat;
import itumulator.executable.DisplayInformation;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;


public class Fungi implements Actor, NonBlocking {

    World world;
    Location location;
    int energyLevel;
    int radius;

    public Fungi(World world, int energyLevel) {
        this.world = world;

        if (energyLevel == 0) { // Sætter energyLevel in case den bliver spawnet uden tilknyttet dyr
            this.energyLevel = 10;
        } else {
            this.energyLevel = energyLevel;
        }
        this.radius = 2;
    }

    public void act(World world) {
        this.world = world;
        spread(); // Skal kunne sprede sig til Meat i nærheden
        if (this.energyLevel < 1) {
            die(world.getCurrentLocation());
        }
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

    void die(Location location) {
        world.delete(this);
        world.setTile(location, new Grass(world)); // Spawns grass upon death
    }

    @Override
    public DisplayInformation getInformation() {
        return null;
    }

}
