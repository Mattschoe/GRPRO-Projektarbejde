package BlockingAgents;

import NonblockingAgents.Bush;
import NonblockingAgents.Den;
import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BearTest {
    World w;
    @BeforeEach
    void Setup() {
        w = new World(2);

    }


    @Test
    void TestReproduction(){

            World world = new World(2);
            Bear bear = new Bear(world, false );
            Location location = new Location(0, 0);

            world.setTile(location, bear);
            world.setCurrentLocation(location);
            Bear bear1 = new Bear(world, false );
            Location location1 = new Location(0, 1);

            world.setTile(location1, bear1);
            world.setCurrentLocation(location1);
            bear.wantsToBreed = true;
            bear1.wantsToBreed = true;
            for (int j = 0; j < 40; j++) {
                world.step();
                bear1.act(world);
                bear.act(world);
            }

            int bears = 0;
            for (Object obj : world.getEntities().keySet()){

                if (obj instanceof Bear) {
                    bears++;
                }}

            assertTrue(bears > 2); // if there are more than 2 bears, the bears have reproduced.





    }
/*
    @Test
    void TestEatingBush(){
        Bear bear1 = new Bear(w);
        Bush bush1 = new Bush(w);
        w.setTile(new Location(0,0), bear1);
        w.setTile(new Location(1,1), bush1);
        int EnergybeforeEating = bear1.getEnergyLevel();
        if (bear1.getEnergyLevel() < bear1.getMaxEnergy()){


        }

    }
*/

}