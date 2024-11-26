package NonblockingAgents;

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



    @Test
    void TestRegrowth(){
        Bush bush = new Bush(w);
        w.setTile(new Location(1,1),bush);

        bush.getEaten();
        assertFalse(bush.getHasBerries());
        assertEquals(2, bush.getReGrowDays());
        for (int i = 0; i < 19; i++) {
            bush.act(w);
            //System.out.println(bush.getReGrowDays());
        }
        //assertEquals(1, bush.getReGrowDays());
        System.out.println(bush.getReGrowDays());

        //assertTrue(bush.getHasBerries());


    }
}