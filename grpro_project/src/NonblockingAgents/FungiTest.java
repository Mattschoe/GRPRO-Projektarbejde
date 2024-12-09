package NonblockingAgents;

import BlockingAgents.Meat;
import BlockingAgents.Rabbit;
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

    @Test
    void FungiLifespan(){ // spreading infection.

        Rabbit r = new Rabbit(w, false);
        Meat meat = new Meat(w, r, false);
        Location location =new Location(1,1);
        w.setTile(location, fungi);
        w.setTile(new Location(0,1), meat);
        w.setCurrentLocation(location);


        for ( int i = 0; i < 40; i++ ) {
            meat.act(w);
            fungi.act(w);
            w.step();
        }






    }
    @Test
    void FungiLifespanNoMeat(){

        Location location = new Location(1,1);
        w.setTile(location, fungi);

        w.setCurrentLocation(location);


        for ( int i = 0; i < 20; i++ ) {
            fungi.act(w);
            w.step();
        }






    }

    @AfterEach
    void tearDown() {
        w = null;
        fungi = null;
    }

}