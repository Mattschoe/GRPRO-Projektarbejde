package BlockingAgents;

import itumulator.world.Location;

public interface Carnivore {
    public void eatMeat();
    public void findEatableMeat();
    public Location getEatableMeatLocation();
}
