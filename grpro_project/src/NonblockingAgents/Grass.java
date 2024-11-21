package NonblockingAgents;

import itumulator.simulator.Actor;
import itumulator.world.NonBlocking;
import itumulator.world.World;

public class Grass implements Actor, NonBlocking {
    World world;

    Grass() {

    }

    @Override
    public void act(World world) {
        this.world = world;
        spread();
    }

    private void spread() {
        for (int i = 0; i < world.getEmptySurroundingTiles().size(); i++) {

        }
    }


}
