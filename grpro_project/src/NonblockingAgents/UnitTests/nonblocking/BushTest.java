package nonblocking;
import NonblockingAgents.Bush;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BushTest {
    World w;

    @BeforeEach
    void setUp() {
        w = new World(2);
    }


    /**
     * Tests if the berries grow back, after being eaten.
     */
    @Test
    void TestRegrowth(){
        Bush bush = new Bush(w);
        w.setTile(new Location(1,1),bush);

        bush.getEaten();
        assertFalse(bush.getHasBerries());
        assertEquals(2, bush.getReGrowDays());
        for (int i = 0; i < 40; i++) {
            bush.act(w);
            w.step();
        }

        assertEquals(0, bush.getReGrowDays());
        assertTrue(bush.getHasBerries());
    }

    /**
     * Tests if being eaten results in the berries disappearing.
     */
    @Test
    void TestGetEaten(){
        Bush bush = new Bush(w);
        w.setTile(new Location(1,1),bush);

        assertTrue(bush.getHasBerries());

        bush.getEaten();
        assertFalse(bush.getHasBerries());

        for (int i = 0; i < 40; i++) {
            bush.act(w);
            w.step();
        }
        assertTrue(bush.getHasBerries());



    }
}