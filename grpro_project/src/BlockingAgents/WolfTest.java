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
    Wolf wolf;
    Location location0;
    Location location01;
    Location location11;
    @BeforeEach
    void setUp() {
        w = new World(2);

        wolf = new Wolf(w, false);
        location0 = new Location(0,0);
        location01 = new Location(0,1);
        location11 = new Location(1,1);
    }

    /* @Test
    void fight() {
        WolfPack wp1 = new WolfPack();
        WolfPack wp2 = new WolfPack();
        Wolf wolf1 = new Wolf(w, wp1);
        Wolf wolf2 = new Wolf(w, wp2);
        int wolfs =  0;
        while (wolfs > 0) {
            wolfs = 0;
            for (Object obj : w.getEntities().keySet()){

                if (obj instanceof Rabbit) {
                    wolfs++;
                }}
            assertEquals(1, wolfs);
            wolf1.act(w);
            wolf2.act(w);
            w.step();


        }
        assertEquals(0, wolfs);

    } */

    @Test
    void TestHunt() {
        Rabbit rabbit = new Rabbit(w);
        w.setTile(location0,wolf);
        w.setTile(location01,rabbit);
        w.setCurrentLocation(location0);

        int rabbits = 0;

        while (rabbits > 0) {
            rabbits = 0;
            for (Object obj : w.getEntities().keySet()){

                if (obj instanceof Rabbit) {
                    rabbits++;
                }}
            System.out.println(w.getEntities());
            assertEquals(1,rabbits);
            w.step();
            rabbit.act(w);
            wolf.act(w);


        }
        assertEquals(0, rabbits);




    }

    /*@Test
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



    } */

    @Test
    void StaysInPack(){
        World world = new World(20);
        WolfPack wp = new WolfPack(world);
        Wolf wolf = new Wolf(world, false );
        Wolf wolf1 = new Wolf(world, false );
        world.setTile(location0,wolf);
        world.setTile(location01,wolf1);
        world.setCurrentLocation(location0);
        for (int i = 0; i < 120; i++) {
            assertTrue( Math.abs(world.getLocation(wolf).getX() -  world.getLocation(wolf1).getX()) < 2 && Math.abs(world.getLocation(wolf).getY() -  world.getLocation(wolf1).getY()) < 2); // distance is less than two tiles.
            
        }






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
        w = null;
        wolf = null;
        location0 = null;
        location01 = null;
        location11 = null;


    }
}