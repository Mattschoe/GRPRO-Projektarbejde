package BlockingAgents;

import itumulator.world.Location;

public interface Herbivore {
    public void eatPlant();
    public void findEatablePlant();
    public Location getEatablePlantLocation();
}
