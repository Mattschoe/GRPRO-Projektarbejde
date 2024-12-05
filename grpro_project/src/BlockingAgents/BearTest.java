package BlockingAgents;

import NonblockingAgents.Bush;
import NonblockingAgents.Den;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BearTest {
    World w;
    Bear bear;
    Location location0;
    Location location1;
    @BeforeEach
    void Setup() {
        w = new World(2);
        bear = new Bear(w);
        location0 = new Location(0, 0);
        location1 = new Location(1, 1);
    }


    @Test
    void TestReproduction(){


            Bear bear = new Bear(w, false );
            Location location = new Location(0, 0);

            w.setTile(location, bear);
            w.setCurrentLocation(location);
            Bear bear1 = new Bear(w, false );
            Location location1 = new Location(0, 1);

            w.setTile(location1, bear1);
            w.setCurrentLocation(location1);
            bear.wantsToBreed = true;
            bear1.wantsToBreed = true;
            for (int j = 0; j < 40; j++) {
                w.step();
                bear1.act(w);
                bear.act(w);
            }

            int bears = 0;
            for (Object obj : w.getEntities().keySet()){

                if (obj instanceof Bear) {
                    bears++;
                }}

            assertTrue(bears > 2); // if there are more than 2 bears, the bears have reproduced.





    }

    @Test
    void TestEatingBush(){

        Bush bush = new Bush(w);
        w.setTile(new Location(0,0), bear);
        w.setTile(new Location(1,1), bush);
        w.setCurrentLocation(location0);
        assertTrue(bush.getHasBerries());
        for (int i = 0; i < 20; i++) {
            bear.act(w);
            bush.act(w);
            w.step();
        }
        assertFalse(bush.getHasBerries());
    }
/*
    @Test
    void staysInTerritory(){
        World w = new World(20);
        w.setCurrentLocation(location0);
        w.setTile(location0, bear);
        Set<Location> territoryAndSurroundings = new HashSet<>();
        for (Location loc : bear.getTerritory()){
            territoryAndSurroundings.addAll(w.getSurroundingTiles(loc));

        }
        while (bear.getEnergyLevel() > 0){ // until the bear dies

            bear.act(w);
            w.step();

            System.out.println(w.getLocation(bear));
            System.out.println(bear.getTerritory().toArray().toString());




            assertTrue(territoryAndSurroundings.contains(w.getLocation(bear)));



        }
    }
*/
/*
    @Test
    void TestHunting(){
        World w = new World(10);
        w.setTile(location0, bear);
        w.setCurrentLocation(location1);
        Rabbit rabbit = new Rabbit(w);
        w.setTile(location1, rabbit);

        for (int i = 0; i < 300; i++) {
            bear.act(w);
            rabbit.act(w);
            w.step();
            System.out.println(bear.getEnergyLevel());

        }
        //assertNull(rabbit);


    }
*/
    @AfterEach
    void TearDown() {
        w = null;
        bear = null;
        location0 = null;
        location1 = null;


    }
}