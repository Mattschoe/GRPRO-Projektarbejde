package Testning.Week1;

import itumulator.executable.Program;
import BlockingAgents.Rabbit;
import NonblockingAgents.Grass;
import NonblockingAgents.RabbitBurrow;
import itumulator.world.World;
import itumulator.world.Location;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


public class TestJUNIT {
    File[] files;
    Scanner scanner;
    int worldSize;
    World world;
    Program program;

    @Before
    public void setUp() throws Exception {
        //Parses in the file directory and saves all files
        File directory = new File("./src/Testning/Week1/TestFiles");
        files = directory.listFiles();
    }

    @Test
    public void test1() {
        try {
            createSimulation(files[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Error!:" + e.getMessage());
        }
        program.simulate();
    }

    private void createSimulation(File file) throws FileNotFoundException {
        scanner = new Scanner(file);

        //Gets world size and initializes world
        worldSize = scanner.nextInt();
        program = new Program(worldSize, 100, 1000);
        world = program.getWorld();

        //Initializes objects in world
        String object;
        int amount;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            //Saves object and amount
            String line = scanner.nextLine();
            String[] lineSplit = line.split(" ");

            object = lineSplit[0];
            amount = Integer.parseInt(lineSplit[1]);

            //Instantiate objects.
            Random random = new Random();
            Location location;
            for (int i = 0; i < amount; i++) {
                location = new Location(random.nextInt(worldSize), random.nextInt(worldSize));
                if (object.equals("grass")) {
                    world.setTile(location, new Grass());
                } else if (object.equals("rabbit")) {
                    world.setTile(location, new Rabbit());
                } else if (object.equals("burrow")) {
                    world.setTile(location, new RabbitBurrow(world));
                }
            }
        }
    }
}
