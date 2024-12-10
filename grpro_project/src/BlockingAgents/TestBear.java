package BlockingAgents;
import static org.junit.jupiter.api.Assertions.*;

import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBear {
    World w;
    @BeforeEach
    void Setup() {
        w = new World(2);

    }


    @Test
    void TestReproduction(){
        Bear bear1 = new Bear(w, false);
        Bear bear2 = new Bear(w, false);
        w.setTile(new Location(0,0), bear1);
        w.setTile(new Location(1,1), bear2);
        if (bear1.getAge() > 1 && bear2.getAge() > 1 ){

           Object object1 = w.getTile(new Location(1,0));
            Object object2 = w.getTile(new Location(0,1));
            assertInstanceOf(Bear.class, object1);
            assertInstanceOf(Bear.class, object2);
        }

    }





}
