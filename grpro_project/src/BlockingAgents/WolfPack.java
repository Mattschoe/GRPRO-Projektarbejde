package BlockingAgents;

import NonblockingAgents.Den;

import java.util.HashSet;
import java.util.Set;

public class WolfPack {
    Wolf AlphaWolf;
    Set<Wolf> pack;
    Den den;

    public WolfPack() {
        pack = new HashSet<Wolf>();
    }

    /**
     * Adds a new wolf to the Alpha Wolf's pack
     * @param newWolfToPack
     */
    public void addWolfToPack(Wolf newWolfToPack) {
        pack.add(newWolfToPack);
        newWolfToPack.setDen(AlphaWolf.getDen());
    }

    /**
     * Checks if a certain wolf is in pack.
     * @param wolf
     * @return
     */
    public boolean wolfIsInPack(Wolf wolf) {
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
        if (wolfIsInPack(wolf)) {
            pack.remove(wolf);
        }
    }

    /**
     * Returns all wolfs in the pack
     * @return
     */
    public Set<Wolf> getWolvesInPack() {
        return pack;
    }

    /**
     * Changes the Alpha wolf of the pack to "newAlphaWolf".
     * @param newAlphaWolf
     */
    public void setAlphaWolf(Wolf newAlphaWolf) {
        AlphaWolf = newAlphaWolf;
    }
}
