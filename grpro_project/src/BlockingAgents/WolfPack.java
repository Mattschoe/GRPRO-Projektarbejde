package BlockingAgents;

import NonblockingAgents.Den;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.HashSet;
import java.util.Set;

public class WolfPack {
    private Wolf alphaWolf;
    private HashSet<Wolf> pack;
    private Den packDen;
    private Location location;
    private World world;

    public WolfPack(World world) {
        pack = new HashSet<Wolf>();
        this.world = world;
    }

    /**
     * Adds a new wolf to the Alpha Wolf's pack, also adds it to the AlphaWolf Den if it has a Den
     * @param newWolfToPack
     */
    public void addWolfToPack(Wolf newWolfToPack) {
        pack.add(newWolfToPack);
        newWolfToPack.setWolfpack(this);

        //Sets the Den of the new Wolf in the pack to the Alpha Wolf's
        /*if (alphaWolf.getDen() != null) {
            newWolfToPack.setDen(alphaWolf.getDen());
        }*/
    }

    /**
     * Checks if a certain wolf is in pack.
     * @param wolf
     * @return
     */
    public boolean isWolfInPack(Wolf wolf) {
        for (Wolf packWolfs : getWolvesInPack() ) {
            if (wolf == packWolfs) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes wolf from pack
     * @param wolf
     */
    public void removeWolfFromPack(Wolf wolf) {
        if (isWolfInPack(wolf)) {
            pack.remove(wolf);
        }
    }

    /**
     * Returns all wolf's in the pack
     * @return
     */
    public Set<Wolf> getWolvesInPack() {
        return pack;
    }

    /**
     * Changes the Alpha wolf of the pack to "newAlphaWolf". Also adds it to the pack if it isn't already in it
     * @param newAlphaWolf
     */
    public void setAlphaWolf(Wolf newAlphaWolf) {
        alphaWolf = newAlphaWolf;

        //Adds the Alphawolf to the pack if it isn't in it
        if (!getWolvesInPack().contains(alphaWolf)) {
            addWolfToPack(alphaWolf);
        }
    }

    public Wolf getAlphaWolf() {
        return alphaWolf;
    }

    public void setDen(Den newDen) {
        packDen = newDen;
    }

    public Den getPackDen() {
        return packDen;
    }

    public Location getPackLocation() {
        for (Location location : world.getEmptySurroundingTiles(world.getLocation(alphaWolf))) {
            this.location = location;
            return location;
        }
        return location;
    }

    /**
     * Returns whether one of the wolf's in the pack are fighting. If they are then all get wolfs in the pack get activated
     * @return boolean
     */
    public boolean isWolfPackFighting() {
        for (Wolf wolf : getWolvesInPack()) {
            if (wolf.getCurrentlyFighting()) {
                return true;
            }
        }
        return false;
    }
}
