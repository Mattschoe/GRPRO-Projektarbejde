package BlockingAgents;

import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MeatTest {
    World w;

    @BeforeEach
    void setUp() {
        w = new World(2);

    }


    @Test
    void decayingProcess() {

        Wolf wolf = new Wolf(w, false);
        Meat meat = new Meat(w, wolf);
        Location location = new Location(0, 0);
        w.setCurrentLocation(location);
        w.setTile(location,wolf);

        while (meat.getEnergyLevel() > 1){
            meat.act(w);
        }

        Set<Meat> meatSet = w.getAll(Meat.class, w.getSurroundingTiles(2));

        assertEquals(0, meatSet.size());


    }
    @Test
    void decayingProcessIfInfected() {
        //Random r = new Random();
        //boolean infected = r.nextBoolean();
        Wolf wolf = new Wolf(w, false);
        //Random r = new Random();
        //boolean infected = r.nextBoolean();
        //Wolf wolf = new Wolf(w, false);
        Meat meat = new Meat(w, wolf);
        Location location = new Location(0, 0);
        w.setCurrentLocation(location);
        w.setTile(location,wolf);
        //if (infected){
            meat.setInfected(true);
        //}

        while (meat.getEnergyLevel() > 1){
            meat.act(w);
        }
        //if (infected)
        assertInstanceOf(Fungi.class, w.getNonBlocking(location));
        //else assertNull(w.getNonBlocking(location));
    }

    @Test
    void meatGetsEaten (){
        Wolf wolf = new Wolf(w, false);
        Rabbit rabbit = new Rabbit(w);
        Meat meat = new Meat(w, rabbit);
        Location location = new Location(0, 0);
        Location location2 = new Location(0, 1);
        w.setCurrentLocation(location);
        w.setTile(location,wolf);
        w.setTile(location2,meat);

        while (!w.getAll(Meat.class, w.getSurroundingTiles(2)).isEmpty()) {
            meat.act(w);
            wolf.act(w);
            w.step();
        }

        assertTrue(w.getAll(Meat.class, w.getSurroundingTiles(2)).isEmpty());





    }

}