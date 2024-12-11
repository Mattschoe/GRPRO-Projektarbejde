package BlockingAgents;

import NonblockingAgents.Grass;
import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FungiTest {
    World w;
    Fungi fungi;

    @BeforeEach
    void setUp() {
        w = new World(2);
        fungi = new Fungi(w, 20);
    }

    /**
     * Test to see if a fungi increases its lifespan if it can spread to meat around it.
     */
    @Test
    void TestFungiLifespan(){ // spreading infection.

        Rabbit r = new Rabbit(w, false);
        Meat meat = new Meat(w, r, false);
        Location location =new Location(1,1);
        Location location1 = new Location(0,1);
        w.setCurrentLocation(location);
        w.setTile(location, fungi);
        w.setTile(location1, meat);



        for ( int i = 0; i < 40; i++ ) {
            w.step();

            for (Object entity : w.getEntities().keySet()){
                if (entity instanceof Actor actor) {
                    actor.act(w);
                }
            }

            if (i < 10) {
                assertInstanceOf(Fungi.class, w.getTile(location));
            }
            else if (i < 19){
                assertInstanceOf(Fungi.class, w.getTile(location));

            }
            else {
                assertInstanceOf(Grass.class, w.getTile(location));
            }


        }







    }

    /**
     * Test to see if a Fungi dies quicker when there is no meat around it.
     */

    @Test
    void TestFungiLifespanNoMeat(){

        Location location = new Location(1,1);
        w.setTile(location, fungi);

        w.setCurrentLocation(location);


        for ( int i = 0; i < 20; i++ ) {
            fungi.act(w);
            w.step();
        }
        assertInstanceOf(Grass.class, w.getTile(location));







    }

    @AfterEach
    void tearDown() {
        w = null;
        fungi = null;
    }

}