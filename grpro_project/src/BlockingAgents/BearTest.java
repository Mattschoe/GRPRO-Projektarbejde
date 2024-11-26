package BlockingAgents;

import NonblockingAgents.Bush;
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

        Bear bear1 = new Bear(w);
        Bear bear2 = new Bear(w);
        Location loc1 = new Location(0, 0);
        w.setTile(loc1, bear1);
        w.setTile(new Location(1,1), bear2);
        w.setCurrentLocation(loc1);
        for (int i = 0; i < 40; i++) {
            int bears = 0;
            bear1.act(w);
            bear2.act(w);
        if (bear1.getWantsToBreed() && bear2.getWantsToBreed()){

            for (Object object :  w.getEntities().keySet()) {
                System.out.println(object.toString());
                if (object instanceof Bear) bears++;
                assertInstanceOf(Bear.class, object);
            }
            assertEquals(4, bears );
        }
        else {
            for (Object object :  w.getEntities().keySet()) {
                System.out.println(bears);
                if (object instanceof Bear){
                    bears++;
                }

            }
            assertEquals(2, bears );

        }
        }
        //Look at this again when fights are working.



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