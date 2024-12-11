package BlockingAgents;

import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MeatTest {
    World w;

    @BeforeEach
    void setUp() {
        w = new World(2);

    }

    /**
     * Test to see if meat disappears over time.
     */
    @Test
    void TestDecayingProcess() {

        Wolf wolf = new Wolf(w, false);
        Meat meat = new Meat(w, wolf, false);
        Location location = new Location(0, 0);
        w.setCurrentLocation(location);
        w.setTile(location,meat);

        while (meat.getEnergyLevel() > 1){
            meat.act(w);
        }

        Set<Meat> meatSet = w.getAll(Meat.class, w.getSurroundingTiles(2));

        assertEquals(0, meatSet.size());


    }

    /**
     * Test to see if Meat becomes Fungi if it was infected.
     */
    @Test
    void TestDecayingProcessIfInfected() {

        Wolf wolf = new Wolf(w, true);

        Meat meat = new Meat(w, wolf, false);
        Location location = new Location(0, 0);
        w.setCurrentLocation(location);
        w.setTile(location,meat);

        meat.setInfected(true);


        while (meat.getEnergyLevel() > 0){
            meat.act(w);
        }

        assertInstanceOf(Fungi.class, w.getTile(location));

    }

    /**
     * Test to see if a wolf will eat meat, standing next to it.
     */
    @Test
    void TestMeatGetsEaten (){
        Wolf wolf = new Wolf(w, false);
        Rabbit rabbit = new Rabbit(w, false);
        Meat meat = new Meat(w, rabbit, false);
        Location location = new Location(0, 0);
        Location location2 = new Location(0, 1);
        w.setCurrentLocation(location);
        w.setTile(location,wolf);
        w.setTile(location2,meat);
        int wolfEnergyBeforeEating = wolf.getEnergyLevel();
        while (!w.getAll(Meat.class, w.getSurroundingTiles(2)).isEmpty()) {
            wolfEnergyBeforeEating = wolf.getEnergyLevel();


            meat.act(w);
            wolf.act(w);
            w.step();
        }
        assertTrue(wolfEnergyBeforeEating < wolf.getEnergyLevel());

        assertTrue(w.getAll(Meat.class, w.getSurroundingTiles(2)).isEmpty());
    }

    @AfterEach
    void tearDown() {
        w = null;

    }

}