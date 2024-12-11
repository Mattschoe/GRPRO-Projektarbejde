package BlockingAgents;

import NonblockingAgents.Den;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
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

    /**
     * Test to see if two wolves from different wolf packs fight each other
     */

    @Test
    void TestFight() {
        int iterations = 10000;
        int fought = 0;
        for (int i = 0; i < iterations; i++) {
            World world = new World(2);
            WolfPack wp1 = new WolfPack(world);
            WolfPack wp2 = new WolfPack(world);
            Wolf wolf1 = new Wolf(world, wp1, false);
            Wolf wolf2 = new Wolf(world, wp2, false);
            world.setTile(location0, wolf1);
            world.setTile(location01, wolf2);



            //while (wolf1.getHealth() > 4 && wolf2.getHealth() > 4 && wolf1.getEnergyLevel() > 0 && wolf2.getEnergyLevel() > 0) {
            for (int j = 0; j < 4; j++) {
                world.setCurrentLocation(location01);


                world.step();
                wolf1.act(world);
                wolf2.act(world);
            }

            int wolfs = 0;
                for (Object obj : world.getEntities().keySet()) {

                    if (obj instanceof Wolf) {
                        wolfs++;
                    }
                }
            if (wolf1.getHealth() < wolf1.getMaxHealth() || wolf2.getHealth() < wolf2.getMaxHealth()){//wolf1.getWolfpack() == wolf2.getWolfpack()){
                fought++;
            }


            //assertEquals(1, wolfs);
            //assertEquals(wolf1.getWolfpack(), wolf2.getWolfpack());

        }
        assertTrue(fought >= (iterations*0.25 - (iterations* 0.02)) && fought <= (iterations*0.25 + (iterations* 0.02)));
    }

    /**
     * Tests if the wolf hunts prey, by placing a rabbit and a wolf in a small world, and seeing if the rabbit disappears.
     */
    @Test
    void TestHunt() {
        Rabbit rabbit = new Rabbit(w, false);
        w.setTile(location0,wolf);
        w.setTile(location01,rabbit);
        w.setCurrentLocation(location0);

        int rabbits = 0;

        while (rabbits > 0) {
            rabbits = 0;
            for (Object obj : w.getEntities().keySet()){
                if (obj instanceof Rabbit) {
                    rabbits++;
                }
            }
            assertEquals(1,rabbits);
            w.step();
            rabbit.act(w);
            wolf.act(w);
        }
        assertEquals(0, rabbits);
    }


    /**
     * Testing if the wolves reproduce in the right frequency. By counting the amount of wolves there are after a night where two wolves shared a den
     * to see in how many of the cases it results in baby wolfs
     *
     */
    @Test
    void TestReproduce() {
        int iterations = 10000;
        int reproduced = 0;
        for (int i = 0; i < iterations; i++) {


            World world = new World(2);
            WolfPack wolfPack= new WolfPack(world);
            Wolf wolf = new Wolf(world, wolfPack, false );
            Location location = new Location(0, 0);

            world.setTile(location, wolf);
            world.setCurrentLocation(location);
            Wolf wolf1 = new Wolf(world, wolfPack, false );
            Location location1 = new Location(0, 1);

            world.setTile(location1, wolf1);

            Den den = new Den(world, "wolf");
            world.setTile(location, den);
            world.setCurrentLocation(location1);


            wolf.setDen(den);
            wolf1.setDen(den);
            for (int j = 0; j < 20; j++) {

                world.step();
                wolf1.act(world);
                wolf.act(world);
            }

            int wolfs = 0;
            for (Object obj : world.getEntities().keySet()){

                if (obj instanceof Wolf) {
                    wolfs++;
                }}
            if (wolfs > 2 ) {
                reproduced++;

            }


        }

        assertTrue(reproduced >= (iterations*0.10*2 - (iterations* 0.03)) && reproduced <= (iterations*0.1*2 + (iterations* 0.03))); // 10 % chance *2 wolfs, +- 1% because of randomness.



    }

    /**
     * Testing if a wolf stays in its pack, by checking if the distancee between two wolves in the same pack, ever gets bigger than two tiles.
     */
    @Test
    void TestStaysInPack(){
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




    @AfterEach
    void tearDown() {
        w = null;
        wolf = null;
        location0 = null;
        location01 = null;
        location11 = null;


    }
}