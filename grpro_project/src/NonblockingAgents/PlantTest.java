package NonblockingAgents;

import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlantTest {
    World w;
    @BeforeEach
    void setUp() {
        w = new World(2);
    }

    @Test
    void TestSpread() {
        Grass grass = new Grass(w);
        Location loc = new Location(0,0);
        w.setCurrentLocation(loc);
        w.setTile(loc, grass);
        assertEquals(1,w.getEntities().keySet().size());
        grass.spread(new Grass(w), 1); //100 % chance
        assertEquals(3,w.getEntities().keySet().size());
        grass.spread(new Grass(w), 1);
        assertEquals(4,w.getEntities().keySet().size());

    }
}