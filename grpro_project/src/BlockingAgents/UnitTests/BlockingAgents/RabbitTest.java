package BlockingAgents;
import NonblockingAgents.Den;
import NonblockingAgents.Grass;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RabbitTest {
    World w;
    Rabbit rabbit;
    Location location0;
    Location location01;
    Location location11;
    @BeforeEach
    void setUp() {
        w = new World(2);
        rabbit = new Rabbit(w, false);
        location0 = new Location(0,0);
        location01 = new Location(0,1);
        location11 = new Location(1,1);
    }

    /**
     * Tests if the rabbit finds the den, when flee is called
     */

    @Test
    void TestFleeToBurrow() {

        Den burrow = new Den(w, "rabbit");
        w.setCurrentLocation(location0);
        w.setTile(location0, rabbit);
        w.setTile(location11, burrow);
        rabbit.findDen();
        rabbit.flee();

        assertEquals(location11, w.getLocation(rabbit));

    }

    /**
     * tests if the rabbit can find an already existing den.
     */
    @Test
    void TestFindDen() {

        Den burrow = new Den(w,"rabbit" );
        w.setCurrentLocation(location0);
        w.setTile(location0, rabbit);
        w.setTile(location11, burrow);

        assertEquals(location11, rabbit.findDen());



    }

    /**
     * Test whether the rabbit dig a den if there are no dens in the  world.
     */

    @Test
    void TestDigDen(){

        w.setCurrentLocation(location0);
        w.setTile(location0, rabbit);
        rabbit.findDen();
        assertNotNull(w.getNonBlocking(location0));
    }

    /**
     * Tests if the rabbit is removed from the map if sleeping and there is a burrow close by.
     */
    @RepeatedTest(20)
    void TestSleepingInDens(){

        Rabbit rabbit1 = new Rabbit(w, false); // extra rabbit, that is not used in the other tests.
        Den burrow = new Den(w,"rabbit");

        w.setCurrentLocation(location0);
        w.setTile(location0, rabbit);
        w.setTile(location01, rabbit1);
        w.setTile(new Location(1,1), burrow);

        for (int i = 0; i < 40; i++) {

            if(rabbit.getIsSleeping() && rabbit1.getIsSleeping()){


                assertThrows(IllegalArgumentException.class, () -> {
                    w.getLocation(rabbit);}); // the rabbit is not on the map at night if it is in the burrow;
            assertThrows(IllegalArgumentException.class, () -> {
                w.getLocation(rabbit1);});
            Set<Den> dens = w.getAll(Den.class,w.getSurroundingTiles(location0));

            assertEquals(1, dens.size());
            }
            else if (!rabbit.getIsSleeping() && !rabbit1.getIsSleeping()){
                assertNotNull(w.getLocation(rabbit));
            }
            rabbit.act(w);
            w.step();

        }
    }


    /**
     * tests if the rabbit can eat the grass it is standing on, and if that results in the grass being deleted from the world.
     */
    @Test
    void eatPlant() {

        Grass grass = new Grass(w);

        w.setCurrentLocation(location0);
        w.setTile(location0, grass);
        w.setTile(location0, rabbit);

        assertNotNull(w.getNonBlocking(location0));
        rabbit.eatFood();
        rabbit.act(w);
        rabbit.eatFood();


        assertNull(w.getNonBlocking(location0));


    }


    /**
     * Tests the method getEatablePlantLocation to see if it actually gets the location of an eatable plant in the world.
     */
    /*
    @Test
    void getEatablePlantLocation() {
        Grass grass = new Grass(w);
        Location location = new Location(0,0);
        Location grassLocation = new Location(1,1);
        w.setCurrentLocation(location);
        w.setTile(grassLocation, grass);
        w.setTile(location, rabbit);

        assertEquals(grassLocation, rabbit.findFood());
    } */

    /**
     * test to see if the rabbits reproduce, and if they do it at the right frequency.
     */

    @Test
    void TestReproduce() {
        int iterations = 10000;
        int reproduced = 0;
        for (int i = 0; i < iterations; i++) {


            World world = new World(2);
            Rabbit rabbit = new Rabbit(world, false);
            Location location = new Location(0, 0);

            world.setTile(location, rabbit);
            world.setCurrentLocation(location);
            Rabbit rabbit1 = new Rabbit(world, false);
            Location location1 = new Location(0, 1);

            world.setTile(location1, rabbit1);
            world.setCurrentLocation(location1);
            Den burrow = new Den(world, "rabbit");
            world.setTile(new Location(1,1), burrow);
            for (int j = 0; j < 30; j++) {

                rabbit1.act(world);
                rabbit.act(world);
                world.step();
            }

            int rabbits = 0;
            for (Object obj : world.getEntities().keySet()){

                if (obj instanceof Rabbit) {
                    rabbits++;
                }}
            if (rabbits > 2 ) {
                reproduced++;

            }


        }

        assertTrue(reproduced >= (iterations*0.10 - (iterations* 0.05)) && reproduced <= (iterations*0.1 + (iterations* 0.05))); // 10 % chance, +- 5% because of randomness.

    }

    @AfterEach
    void tearDown() {
        w = null;
        rabbit = null;
        location0 = null;
        location01 = null;
        location11 = null;
    }
}