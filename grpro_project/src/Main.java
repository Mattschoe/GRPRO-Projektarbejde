import java.awt.Color;
import java.util.Random;

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
        int delay = 1500;
        int displaySize = 800;

        //Initializes the program and world
        Program program = new Program(size, displaySize, delay);
        World world = program.getWorld();

        //Display Information
        program.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.white, "rabbit-small"));
        program.setDisplayInformation(RabbitBurrow.class, new DisplayInformation(Color.black, "hole"));
        program.setDisplayInformation(Grass.class, new DisplayInformation(Color.green, "grass" ));

        //Shows the demo for the week
        demoWeek1(program);
    }

    private static void demoWeek1(Program program) {
        World world = program.getWorld();
        Random random = new Random();

        //Adds rabbit
        world.setTile(new Location(random.nextInt(world.getSize()-1), random.nextInt(world.getSize()-1)), new Rabbit());
        world.setTile(new Location(random.nextInt(world.getSize()-1), random.nextInt(world.getSize()-1)), new Rabbit());
        world.setTile(new Location(random.nextInt(world.getSize()-1), random.nextInt(world.getSize()-1)), new Rabbit());

        //Adds grass
        world.setTile(new Location(random.nextInt(world.getSize()-1), random.nextInt(world.getSize()-1)), new Grass());
        world.setTile(new Location(random.nextInt(world.getSize()-1), random.nextInt(world.getSize()-1)), new Grass());

        //Adds RabbitBurrows
        world.setTile(new Location(random.nextInt(world.getSize()-1), random.nextInt(world.getSize()-1)), new RabbitBurrow(world));

        //Shows and runs simulation
        program.show();
        for (int i = 0; i < 200; i++) {
            program.simulate();
        }
    }
}