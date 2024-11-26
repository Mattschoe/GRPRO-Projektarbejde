package Testning;

import BlockingAgents.Bear;
import BlockingAgents.Rabbit;
import NonblockingAgents.Grass;
import NonblockingAgents.Territory;
import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class ParseTest {
    /* File file;

    //Program fields
    int worldSize;
    int displaySize;
    int delay;

    Program program;
    World world;
    Object testObject;


    public ParseTest(File testFile, int displaySize, int delay) {
        this.file = testFile;
        this.delay = delay;
        this.displaySize = displaySize;

        try {
            readFile();
        } catch (FileNotFoundException e) {
            System.out.println("Error!:" + e.getMessage());
        }

    }

    public void readFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        //Gets world size and initializes world
        worldSize = scanner.nextInt();
        program = new Program(worldSize, displaySize, delay);
        world = program.getWorld();

        //Display Information
        program.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.white, "rabbit-small"));
        program.setDisplayInformation(Territory.class, new DisplayInformation(Color.red));
        program.setDisplayInformation(RabbitBurrow.class, new DisplayInformation(Color.black, "hole"));
        program.setDisplayInformation(Grass.class, new DisplayInformation(Color.green, "grass" ));

        //Initializes objects in world
        String object = "";
        int amount = 0;
        Random random = new Random();
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            //Saves object and amount
            String line = scanner.nextLine();
            String[] lineSplit = line.split("[-\\s]");

            //If it is a amount
            System.out.println(lineSplit.length);
            if (lineSplit.length == 2) {
                object = lineSplit[0];
                amount = Integer.parseInt(lineSplit[1]);
            } else if (lineSplit.length == 3) { //If it is a min max
                object = lineSplit[0];
                int min = Integer.parseInt(lineSplit[1]);
                int max = Integer.parseInt(lineSplit[2]);
                amount = random.nextInt(max - min + 1) + min;
            }


            //Instantiate objects.
            random = new Random();
            Location location;
            for (int i = 0; i < amount; i++) {
                location = new Location(random.nextInt(worldSize), random.nextInt(worldSize));

                //Finds new location if location is not empty
                while (!world.isTileEmpty(location) || world.containsNonBlocking(location)) {
                    location = new Location(random.nextInt(worldSize), random.nextInt(worldSize));
                }

                if (object.equals("grass")) {
                    world.setTile(location, new Grass(world));
                } else if (object.equals("rabbit")) {
                    world.setTile(location, new Rabbit(world));
                } else if (object.equals("burrow")) {
                    world.setTile(location, new RabbitBurrow(world));
                } else if (object.equals("bear")) {
                    world.setTile(location, new Bear(world));
                    if (world.isTileEmpty(location) && !world.containsNonBlocking(location)) {
                        if (object.equals("grass")) world.setTile(location, new Grass(world));
                        else if (object.equals("rabbit")) world.setTile(location, new Rabbit(world));
                        else if (object.equals("burrow")) world.setTile(location, new RabbitBurrow(world));
                    }
                    testObject = world.getTile(location);
                }
            }

        }
    }

    public void simulateTest() {
        program.show();
    } */
}
