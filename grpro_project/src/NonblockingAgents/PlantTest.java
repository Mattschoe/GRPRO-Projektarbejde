package NonblockingAgents;

import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlantTest {
    World w;
    @BeforeEach
    void setUp() {
        w = new World(2);
    }

    @RepeatedTest(20)

    void TestSpread() {
        Grass grass = new Grass(w);
        Location loc = new Location(0,0);
        w.setCurrentLocation(loc);
        w.setTile(loc, grass);
        assertEquals(1,w.getEntities().size());

        grass.spread(new Grass(w), 1);

        assertTrue(1 < w.getEntities().size() && w.getEntities().size() <= 4);
        grass.spread(new Grass(w), 1);
        assertTrue(1 < w.getEntities().size() && w.getEntities().size() <= 4);


    }
}