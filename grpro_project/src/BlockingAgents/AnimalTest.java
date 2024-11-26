package BlockingAgents;

import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    World w;
    @BeforeEach
    void setUp() {
        w = new World(3);
    }

    @Test
    void TestMoveTo() {
        Rabbit rabbit = new Rabbit(w);
        Location location = new Location(0,0);
        Location newLocation = new Location(2,2);
        w.setCurrentLocation(location);
        w.setTile(location,rabbit);
        rabbit.moveTo(newLocation); // 2 times because the distance is two, these are called over multiple calls to act.
        rabbit.moveTo(newLocation);

        assertEquals(newLocation, w.getLocation(rabbit));

    }

    @Test
    void sprintTo() {
        Rabbit rabbit = new Rabbit(w);
        Location location = new Location(0,0);
        Location newLocation = new Location(2,2);
        w.setCurrentLocation(location);
        w.setTile(location,rabbit);
        rabbit.sprintTo(newLocation); // 1 times because the distance is two, and the rabbit is running

        assertEquals(newLocation, w.getLocation(rabbit));

    }

    @Test
    void moveAwayFrom() {
        Rabbit rabbit = new Rabbit(w);
        Location location = new Location(1,1);
        Location enemyLocation = new Location(0,0);
        Location expectedLocation = new Location(2,2);

        w.setCurrentLocation(location);
        w.setTile(location,rabbit);
        rabbit.moveAwayFrom(enemyLocation);
        assertNull(w.getTile(location));
        assertNull(w.getTile(enemyLocation));
        assertNotNull(w.getTile(expectedLocation));



    }

    @Test
    void move() {
        Rabbit rabbit = new Rabbit(w);
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
}