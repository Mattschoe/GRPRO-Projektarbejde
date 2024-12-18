package BlockingAgents;

import NonblockingAgents.Bush;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BearTest {
    World w;
    Bear bear;
    Location location0;
    Location location1;
    @BeforeEach
    void Setup() {
        w = new World(2);
        bear = new Bear(w, false);
        location0 = new Location(0, 0);
        location1 = new Location(1, 1);
    }

    /**
     * Tests if two bears reproduce, and if they do it at the right frequency.
     */
    @Test
    void TestReproduction(){

        int iterations = 10000;
        int reproduced = 0;
        for (int i = 0; i < iterations; i++) {


            World world = new World(2);
            Bear bear = new Bear(world, false);
            Location location = new Location(0, 0);

            world.setTile(location, bear);
            world.setCurrentLocation(location);
            Bear bear1 = new Bear(world, false);
            Location location1 = new Location(0, 1);

            world.setTile(location1, bear1);
            world.setCurrentLocation(location1);


            for (int j = 0; j < 2; j++) {
                world.step();
                bear1.act(world);
                bear.act(world);
            }

            int bears = 0;
            for (Object obj : world.getEntities().keySet()){

                if (obj instanceof Bear) {
                    bears++;
                }}
            if (bears > 2 ) {
                reproduced++;

            }



        }

        assertTrue(reproduced >= (iterations*0.10 - (iterations* 0.01)) && reproduced <= (iterations*0.1 + (iterations* 0.01))); // 10 % chance *2 rabbits, +- 1% because of randomness.






    }

    /**
     * Tests if the bear eats a bush, by checking if the bush still has berries after a bear has acted around it.
     */

    @Test
    void TestEatingBush(){

        Bush bush = new Bush(w);
        w.setTile(new Location(0,0), bear);
        w.setTile(new Location(1,1), bush);
        w.setCurrentLocation(location0);
        assertTrue(bush.getHasBerries());
        int energyBefore = bear.getEnergyLevel();

        while (bear.getEnergyLevel() <= energyBefore) {
            energyBefore = bear.getEnergyLevel();

            bear.act(w);
            bush.act(w);
            w.step();
        }

        assertTrue(bear.getEnergyLevel() > energyBefore);
        assertFalse(bush.getHasBerries());
    }

    /**
     * Tests if two bears fight if none of them want babies, and they are in eachothers territory.
     */
    @Test
    void TestFighting(){
        Bear bear1 = new Bear(w, false);
        w.setTile(location0, bear);

        w.setCurrentLocation(location0);
        w.setTile(location1, bear1);

        int bears = 2;
        while (bears > 1){
            bears = 0;

            bear.setWantsToBreed(false);
            bear1.setWantsToBreed(false);
            bear.setBreedingDelay(5);
            bear1.setBreedingDelay(5);

            for (Object obj : w.getEntities().keySet()){

                if (obj instanceof Bear b) {
                    b.act(w);
                    bears++;
                }
            }

        }


        assertTrue(bears == 1 || bears == 0 );



    }

    /**
     * Test to see if the bear ever moves more than one tile away from its territory.
     */

    @Test
    void TestStaysInTerritory(){
        World world = new World(20);
        Location location = new Location(0, 0);
        world.setCurrentLocation(location);
        Bear b = new Bear(world, false);
        world.setTile(location, b);


        while (b.getEnergyLevel() > 1) {


            world.step();
            b.act(world);

            // location is the center of the bears territory, the territory is the tiles around the bear with a radius of half the world, this is quite big, but otherwise there is no action.We are checking if the  bear gets further away than one tile from its territory.
            assertTrue(Math.abs(world.getLocation(b).getX() - location.getX()) < (world.getSize()/2+1) && Math.abs(world.getLocation(b).getX() - location.getX()) < (world.getSize()/2+1));




        }
    }

    /**
     * test to see if a bear hunts a rabbit, and if that results in the rabbit disappearing
     */

    @Test
    void TestHunting(){

            World world = new World(6);
            Rabbit rabbit = new Rabbit(world, false);
            Bear bear1 = new Bear(world, false);
            world.setTile(location0,bear1);
            world.setTile(location1,rabbit);
            world.setCurrentLocation(location0);

            int rabbits = 1;

            while (rabbits > 0) {

                rabbits = 0;
                for (Object obj : world.getEntities().keySet()){
                    if (obj instanceof Rabbit) {
                        rabbits++;
                    }
                    if (obj instanceof Actor actor) {
                        actor.act(world);
                    }

                }
               if (bear1.getEnergyLevel() >= bear1.getMaxEnergy()){ // if it is not hungry
                    assertEquals(1,rabbits);
               }

               world.step();

            }

            assertEquals(0, rabbits);

    }

    @AfterEach
    void TearDown() {
        w = null;
        bear = null;
        location0 = null;
        location1 = null;


    }
}