package BlockingAgents;

import NonblockingAgents.Den;
import NonblockingAgents.Grass;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RabbitTest {
    World w;
    Rabbit rabbit;
    @BeforeEach
    void setUp() {
        w = new World(2);
        rabbit = new Rabbit(w);
    }

    @Test
    void TestFleeToBurrow() {

        Den burrow = new Den(w, "rabbit");
        Location location = new Location(0,0);
        Location burrowLocation = new Location(1,1);

        w.setCurrentLocation(location);
        w.setTile(location, rabbit);
        w.setTile(burrowLocation, burrow);
        rabbit.findDen();
        rabbit.flee();

        assertEquals(burrowLocation, w.getLocation(rabbit));

    }

    /**
     * tests if the rabbit can find an already existing den.
     */
    @Test
    void findDen() {

        Den burrow = new Den(w,"rabbit" );
        Location location = new Location(0,0);
        Location burrowLocation = new Location(1,1);

        w.setCurrentLocation(location);
        w.setTile(location, rabbit);
        w.setTile(burrowLocation, burrow);

       // System.out.println(rabbit.findDen());
        assertEquals(burrowLocation, rabbit.findDen());



    }

    /**
     * Test whether the rabbit dig a den if there are no dens in the  world.
     */

    @Test
    void digDen(){
        Rabbit rabbit = new Rabbit(w);
        Location location = new Location(0,0);
        w.setCurrentLocation(location);
        w.setTile(location, rabbit);
        rabbit.findDen();
        assertNotNull(w.getNonBlocking(location));
    }

    /**
     * Tests if the rabbit is removed from the map if sleeping and there is a burrow close by.
     */
    @RepeatedTest(20)
    void TestSleepingInDens(){
        Rabbit rabbit = new Rabbit(w);
        Rabbit rabbit1 = new Rabbit(w);
        Den burrow = new Den(w,"rabbit");
        Location location = new Location(0,0);
        Location location1 = new Location(1,0);
        w.setCurrentLocation(location);
        w.setTile(location, rabbit);
        w.setTile(location1, rabbit1);
        w.setTile(new Location(1,1), burrow);

        for (int i = 0; i < 40; i++) {

            if(rabbit.getIsSleeping() && rabbit1.getIsSleeping()){


                assertThrows(IllegalArgumentException.class, () -> {
                    w.getLocation(rabbit);}); // the rabbit is not on the map at night if it is in the burrow;
            assertThrows(IllegalArgumentException.class, () -> {
                w.getLocation(rabbit1);});
            Set<Den> dens = w.getAll(Den.class,w.getSurroundingTiles(location));

            assertEquals(1, dens.size());
            }
            else if (!rabbit.getIsSleeping() && !rabbit1.getIsSleeping()){
                assertNotNull(w.getLocation(rabbit));
            }
            rabbit.act(w);
            w.step();

        }
    }



    /* @Test
    void findEatablePlant() {
        Grass grass = new Grass(w);
        Location location = new Location(0,0);
        Location grassLocation = new Location(1,1);
        w.setCurrentLocation(location);
        w.setTile(grassLocation, grass);
        w.setTile(location, rabbit);

        rabbit.findEatablePlant();
        assertEquals(grassLocation, rabbit.getGrassLocation());
        assertTrue(rabbit.getHasFoundGrass());

    } */

    /**
     * tests if the rabbit can eat the grass it is standing on, and if that results in the grass being deleted from the world.
     */
    @Test
    void eatPlant() {

        Grass grass = new Grass(w);
        Location location = new Location(0,0);
        w.setCurrentLocation(location);
        w.setTile(location, grass);
        w.setTile(location, rabbit);

        assertNotNull(w.getNonBlocking(location));
        rabbit.eatFood();
        rabbit.act(w);
        rabbit.eatFood();


        assertNull(w.getNonBlocking(location));


    }


    /**
     * Tests the method getEatablePlantLocation to see if it actually gets the location of an eatable plant in the world.
     */
    /* @Test
    void getEatablePlantLocation() {
        Grass grass = new Grass(w);
        Location location = new Location(0,0);
        Location grassLocation = new Location(1,1);
        w.setCurrentLocation(location);
        w.setTile(grassLocation, grass);
        w.setTile(location, rabbit);

        assertEquals(grassLocation, rabbit.findFood());
    } */

    @Test
    void reproduce() {
        int iterations = 10000;
        int reproduced = 0;
        for (int i = 0; i < iterations; i++) {


            World world = new World(2);
            Rabbit rabbit = new Rabbit(world);
            Location location = new Location(0, 0);

            world.setTile(location, rabbit);
            world.setCurrentLocation(location);
            Rabbit rabbit1 = new Rabbit(world);
            Location location1 = new Location(0, 1);

            world.setTile(location1, rabbit1);
            world.setCurrentLocation(location1);
            Den burrow = new Den(world, "rabbit");
            world.setTile(new Location(1,1), burrow);
            for (int j = 0; j < 40; j++) {
                world.step();
                rabbit1.act(world);
                rabbit.act(world);
            }
           // System.out.println(world.getEntities());
            int rabbits = 0;
            for (Object obj : world.getEntities().keySet()){

                if (obj instanceof Rabbit) {
                    rabbits++;
                }}
            if (rabbits > 2  ) {
                reproduced++;

            }


        }

        assertTrue(reproduced >= (iterations*0.10*2 - (iterations* 0.01)) && reproduced <= (iterations*0.1*2 + (iterations* 0.01))); // 10 % chance *2 rabbits, +- 1% because of randomness.







    }
}