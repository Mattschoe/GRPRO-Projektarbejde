package BlockingAgents;

import NonblockingAgents.WolfDen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class wolfPack {

    Wolf wolf;
    Set<Wolf> pack;
    WolfDen den;

    wolfPack(Wolf wolf) {
        pack = new HashSet<Wolf>();
        this.wolf = wolf;
        pack.add(this.wolf);

    }

    public void addWolf(Wolf wolf) {
        pack.add(wolf);
        wolf.den = this.den;
    }

    public void callPack() {
        for (Wolf wolf : pack) {
            wolf.calledForHunt = true;
        }

    }

}
