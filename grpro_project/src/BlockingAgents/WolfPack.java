package BlockingAgents;

import NonblockingAgents.Den;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class WolfPack {

    Wolf wolf;
    Set<Wolf> pack;
    Den den;

    WolfPack(Wolf wolf) {
        pack = new HashSet<Wolf>();
        this.wolf = wolf;
        pack.add(this.wolf);

    }

    public void addWolf(Wolf wolf) {
        pack.add(wolf);
        wolf.den = this.den;
    }

    public boolean wolfIsInPack(Wolf wolf) {
        for (Wolf packmember : getWolves() ) {
            if (wolf == packmember) {
                return true;
            }
        }
        return false;
    }

    public void removeWolf(Wolf wolf) {
        pack.remove(wolf);
    }

    public Set<Wolf> getWolves() {
        return pack;
    }

}
