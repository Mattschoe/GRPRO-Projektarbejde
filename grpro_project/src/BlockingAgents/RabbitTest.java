package BlockingAgents;

import NonblockingAgents.Den;
import NonblockingAgents.Grass;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        Den burrow = new Den(w, rabbit, false);
        Location location = new Location(0,0);
        Location burrowLocation = new Location(1,1);

        w.setCurrentLocation(location);
        w.setTile(location, rabbit);
        w.setTile(burrowLocation, burrow);
        rabbit.findDen();
        rabbit.flee();
        assertEquals(burrowLocation, w.getLocation(rabbit));

    }

    @Test
    void findDen() {

        Den burrow = new Den(w,rabbit,false );
        Location location = new Location(0,0);
        Location burrowLocation = new Location(1,1);

        w.setCurrentLocation(location);
        w.setTile(location, rabbit);
        w.setTile(burrowLocation, burrow);

        System.out.println(rabbit.findDen());

        assertEquals(burrowLocation, rabbit.findDen());
        assertNotNull(burrow.getOwner());


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

    @Test
    void eatPlant() {

        Grass grass = new Grass(w);
        Location location = new Location(0,0);
        w.setCurrentLocation(location);
        w.setTile(location, grass);
        w.setTile(location, rabbit);

        assertNotNull(w.getNonBlocking(location));
        rabbit.eatPlant();
        assertNull(w.getNonBlocking(location));


    }

    @Test
    void getEatablePlantLocation() {
        Grass grass = new Grass(w);
        Location location = new Location(0,0);
        Location grassLocation = new Location(1,1);
        w.setCurrentLocation(location);
        w.setTile(grassLocation, grass);
        w.setTile(location, rabbit);

        assertEquals(grassLocation, rabbit.getEatablePlantLocation());
    }

    @Test
    void reproduce() {




    }
}