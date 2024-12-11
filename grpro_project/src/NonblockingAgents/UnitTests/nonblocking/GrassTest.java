package nonblocking;

import NonblockingAgents.Grass;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class GrassTest {
    World w;
    @BeforeEach
    void setUp() {
        w = new World(2);
    }


    /**
     * Tests if the grass spreads
     */
    @RepeatedTest(20)

    void TestSpread() {
        Grass grass = new Grass(w);
        Location loc = new Location(0,0);
        w.setCurrentLocation(loc);
        w.setTile(loc, grass);
        assertEquals(1,w.getEntities().size());

        grass.spread(1);

        assertTrue(1 < w.getEntities().size() && w.getEntities().size() <= 4);
        grass.spread(1);
        assertTrue(1 < w.getEntities().size() && w.getEntities().size() <= 4);
    }
}