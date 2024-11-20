import java.awt.Color;

import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.simulator.BlockingAgents.Rabbit;
import itumulator.simulator.NonblockingAgents.Grass;
import itumulator.simulator.NonblockingAgents.RabbitBurrow;
import itumulator.world.Location;
import itumulator.world.World;

public class Main {
    public static void main(String[] args) {
        int size = 15;
        int delay = 1000;
        int displaySize = 800;

        Program program = new Program(size, displaySize, delay);
        World world = program.getWorld();


        //Display Information
        program.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.white, "rabbit-small"));
        program.setDisplayInformation(RabbitBurrow.class, new DisplayInformation(Color.black, "hole"));
        program.setDisplayInformation(Grass.class, new DisplayInformation(Color.green, "grass" ));
        
        program.show();
        program.simulate();
    }
}