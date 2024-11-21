import java.awt.Color;

import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import BlockingAgents.Rabbit;
import NonblockingAgents.Grass;
import NonblockingAgents.RabbitBurrow;
import itumulator.world.Location;
import itumulator.world.World;

public class Main {
    public static void main(String[] args) {
        //Sets up world
        int size = 15;
        int delay = 1000;
        int displaySize = 800;

        Program program = new Program(size, displaySize, delay);
        World world = program.getWorld();

        Rabbit rabbit = new Rabbit();
        world.setTile(new Location(1,1), rabbit);
        //Display Information
        program.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.white, "rabbit-small"));
        program.setDisplayInformation(RabbitBurrow.class, new DisplayInformation(Color.black, "hole"));
        program.setDisplayInformation(Grass.class, new DisplayInformation(Color.green, "grass" ));

        //Adds agents
        world.setTile(new Location(0, 1), new RabbitBurrow(world));

        program.show();
        program.simulate();
    }
}