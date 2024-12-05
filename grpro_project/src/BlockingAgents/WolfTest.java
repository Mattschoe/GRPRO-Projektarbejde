package BlockingAgents;

import NonblockingAgents.Den;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.AfterEach;
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
        int iterations = 10000;
        int reproduced = 0;
        for (int i = 0; i < iterations; i++) {

            WolfPack wolfPack= new WolfPack();
            World world = new World(2);
            Wolf wolf = new Wolf(world, wolfPack, false );
            Location location = new Location(0, 0);

            world.setTile(location, wolf);
            world.setCurrentLocation(location);
            Wolf wolf1 = new Wolf(world, wolfPack, false );
            Location location1 = new Location(0, 1);

            world.setTile(location1, wolf1);
            world.setCurrentLocation(location1);
            Den den = new Den(world, "wolf");
            world.setTile(new Location(1,1), den);
            for (int j = 0; j < 40; j++) {
                world.step();
                wolf1.act(world);
                wolf.act(world);
            }
            // System.out.println(world.getEntities());
            int wolfs = 0;
            for (Object obj : world.getEntities().keySet()){

                if (obj instanceof Wolf) {
                    wolfs++;
                }}
            if (wolfs > 2 ) {
                reproduced++;

            }


        }
        System.out.println("reproduced   " + reproduced);

        assertTrue(reproduced >= (iterations*0.10*2 - (iterations* 0.01)) && reproduced <= (iterations*0.1*2 + (iterations* 0.01))); // 10 % chance *2 wolfs, +- 1% because of randomness.



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
    @AfterEach
    void tearDown() {

    }
}