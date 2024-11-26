package BlockingAgents;

import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WolfTest {
    World w;
    @BeforeEach
    void setUp() {
        w = new World(2);
    }

    @Test
    void fight() {/*
        Wolf wolf1 = new Wolf(w);
        Wolf wolf2 = new Wolf(w);
        Location location1 = new Location(0, 0);
        Location location2 = new Location(0, 1);
        w.setCurrentLocation(location1);
        w.setTile(location1,wolf1);
        w.setTile(location2,wolf2);
        for (int i = 0; i < 10; i++) {
            if (wolf1 != null && wolf2 != null) {
                    wolf1.fight(wolf2);
        }
        }


        int wolfs = 0;
        for (int i = 0; i < w.getEntities().size(); i++) {
            wolfs++;
        }
        assertEquals(1,wolfs);

*/



    }

    @Test
    void hunt() {
    }

    @Test
    void reproduce() {
    }

    @Test
    void flee() {
    }

    @Test
    void currentlyWinning() {
    }

    @Test
    void findDen() {
    }

    @Test
    void digDen() {
    }

    @Test
    void findEatableMeat() {
    }

    @Test
    void getEatableMeatLocation() {
    }

    @Test
    void eatMeat() {


    }
}