package data.BlockingAgents;

import data.NonblockingAgents.Grass;
import itumulator.executable.DisplayInformation;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;


public class Fungi implements Actor {
    private World world;
    private Location location;
    private int energyLevel;
    private int radius;

    public Fungi(World world, int energyLevel) {
        this.world = world;
        this.energyLevel = energyLevel;
        this.radius = 2;
    }

    public void act(World world) {
        this.world = world;
        spread(); // Skal kunne sprede sig til Meat i nærheden
        if (this.energyLevel < 1) {
            die();
        }
    }

    /**
     * Infects nearby Meat
     */
    private void spread() {
        for (Object entity : world.getEntities().keySet()) {
            if (entity instanceof Meat meat) { // Makes sure it infects only meat, which is close
                for (Location nearbyLocation : world.getSurroundingTiles(this.radius)) {
                    if (entity == world.getTile(nearbyLocation)) {
                        meat.setInfected(true);
                    }
                }
            }
        }
        energyLevel--;
    }

    /**
     * Deletes this Fungi from the world. Places a patch of grass at its location, if possible
     */
    private void die() {
        location = world.getLocation(this);
        world.delete(this);
        if (world.getTile(location) == null) {
            world.setTile(location, new Grass(world)); // Spawns grass upon death unless there already is grass
        }
    }

    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.GRAY, "fungi");
    }

}
