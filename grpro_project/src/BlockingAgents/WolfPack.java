package BlockingAgents;

import NonblockingAgents.Den;

import java.util.HashSet;
import java.util.Set;

public class WolfPack {
    Wolf alphaWolf;
    HashSet<Wolf> pack;
    Den packDen;

    public WolfPack() {
        pack = new HashSet<Wolf>();
    }

    /**
     * Adds a new wolf to the Alpha Wolf's pack, also adds it to the AlphaWolf Den if it has a Den
     * @param newWolfToPack
     */
    public void addWolfToPack(Wolf newWolfToPack) {
        pack.add(newWolfToPack);

        //Sets the Den of the new Wolf in the pack to the Alpha Wolf's
        if (alphaWolf.getDen() != null) {
            newWolfToPack.setDen(alphaWolf.getDen());
        }
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
     * Changes the Alpha wolf of the pack to "newAlphaWolf". Also adds it to the pack if it isnt already in it
     * @param newAlphaWolf
     */
    public void setAlphaWolf(Wolf newAlphaWolf) {
        alphaWolf = newAlphaWolf;

        //Adds the Alphawolf to the pack if it isnt in it
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
}
