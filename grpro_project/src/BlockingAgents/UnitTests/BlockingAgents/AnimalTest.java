package BlockingAgents;
import NonblockingAgents.Den;
import NonblockingAgents.Grass;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    World w;
    @BeforeEach
    void setUp() {
        w = new World(3);
    }

    /**
     * Tests the method moveTo, checking whether a rabbit, moves to the specified location.
     *
     */
    @Test
    void TestMoveTo() {
        Rabbit rabbit = new Rabbit(w, false);
        Location location = new Location(0,0);
        Location newLocation = new Location(2,2);
        w.setCurrentLocation(location);
        w.setTile(location,rabbit);
        rabbit.moveTo(newLocation); // 2 times because the distance is two, these are usually called over multiple calls to act.
        rabbit.moveTo(newLocation);

        assertEquals(newLocation, w.getLocation(rabbit));

    }

    /**
     * The same as moveTo, but with sprint.
     */
    @Test
    void TestSprintTo() {
        Rabbit rabbit = new Rabbit(w, false);
        Location location = new Location(0,0);
        Location newLocation = new Location(2,2);
        w.setCurrentLocation(location);
        w.setTile(location,rabbit);
        rabbit.sprintTo(newLocation); // 1 times because the distance is two, and the rabbit is running 2 tiles in each call to act.

        assertEquals(newLocation, w.getLocation(rabbit));

    }

    /**
     * checks whether the method moveAwayFrom makes a rabbit moves in the opposite direction from a given location
     */

    @Test
    void TestMoveAwayFrom() {
        Rabbit rabbit = new Rabbit(w, false);
        Location location = new Location(1,1);
        Location enemyLocation = new Location(0,0);
        Location expectedLocation = new Location(2,2);

        w.setCurrentLocation(location);
        w.setTile(location,rabbit);
        rabbit.moveAwayFrom(enemyLocation);
        assertNull(w.getTile(location)); // the old location
        assertNull(w.getTile(enemyLocation)); // the enemy's location
        assertNotNull(w.getTile(expectedLocation)); // the place it should be



    }

    /**
     * checks whether the rabbit, does in fact move, when move is called, and that it isn't just standing.
     */
    @Test
    void TestMove() {
        Rabbit rabbit = new Rabbit(w, false);
        Location location1 = new Location(0,0);
        w.setCurrentLocation(location1);
        w.setTile(location1,rabbit);
        assertNotNull(w.getTile(location1));
        rabbit.move();
        Location location2 = w.getLocation(rabbit);
        assertNotNull(w.getTile(location2));
        assertNotEquals(location1, location2);
        assertNull(w.getTile(location1));

    }

    /**
     * Test to see if a blockingObject can stand on a nonblocking object.(Rabbit on grass)
     *
     */
    @Test
    void TestStandingOnGrass(){
        Rabbit rabbit = new Rabbit(w, false);
        Location location1 = new Location(0,0);
        Grass grass = new Grass(w);
        w.setCurrentLocation(location1);
        w.setTile(location1,rabbit);
        w.setTile(location1,grass);
        assertEquals(w.getLocation(grass), w.getLocation(rabbit));

    }
    /**
     * Test to see if a blockingObject can stand on a nonblocking object.(Rabbit on Den)
     *
     */
    @Test
    void TestStandingOnDen(){
        Rabbit rabbit = new Rabbit(w, false);
        Location location1 = new Location(0,0);
        Den den = new Den(w, "rabbit");
        w.setCurrentLocation(location1);
        w.setTile(location1,rabbit);
        w.setTile(location1,den);
        assertEquals(w.getLocation(den), w.getLocation(rabbit));

    }

    /**
     * Test to see if an animal can die. and if that results in a carcass(Meat)
     *
     */

    @Test
    void TestDeath(){
        Rabbit rabbit = new Rabbit(w, false);
        Location location1 = new Location(0,0);
        w.setCurrentLocation(location1);
        w.setTile(location1,rabbit);
        while (rabbit.getEnergyLevel() > 0)
         {
            rabbit.act(w);
        }
        rabbit.act(w);

        Set<Rabbit>  rabbits = w.getAll(Rabbit.class, w.getSurroundingTiles(3));
        for (Object entity : w.getEntities().keySet()){

        assertInstanceOf(Meat.class,entity);
        }
        assertEquals(0, rabbits.size());



    }
    @AfterEach
    void tearDown() {

        w = null;

    }
}