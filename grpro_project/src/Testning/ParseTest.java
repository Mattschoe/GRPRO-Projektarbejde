package Testning;

import BlockingAgents.*;
import NonblockingAgents.*;
import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class ParseTest {
    File file;

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

    /**
     * Reads the file and inserts everything into the world according to what is written in the file
     */
    public void readFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        Random random = new Random();

        //Gets world size and initializes world
        worldSize = scanner.nextInt();
        program = new Program(worldSize, displaySize, delay);
        world = program.getWorld();

        //Display Information
        program.setDisplayInformation(Grass.class, new DisplayInformation(Color.green, "grass" ));
        program.setDisplayInformation(Wolf.class, new DisplayInformation(Color.yellow, "wolf" ));
        program.setDisplayInformation(Bush.class, new DisplayInformation(Color.green, "bush-berries"));
        program.setDisplayInformation(Den.class, new DisplayInformation(Color.black, "hole"));
        program.setDisplayInformation(Meat.class, new DisplayInformation(Color.green, "carcass" ));
        program.setDisplayInformation(Fungi.class, new DisplayInformation(Color.green, "fungi" ));

        //Initializes objects in world
        String object = "";
        int amount = 0;
        Location bearTerritoryCentrum;


        scanner.nextLine();
        while (scanner.hasNextLine()) {
            int infectedAnimalCounter = 0;
            int infectedObjectCounter = 0;
            boolean isInfected = false;
            //Saves object and amount
            String line = scanner.nextLine();
            String[] lineSplit = line.split("[-\\s(),]+");

            if (lineSplit.length > 1 && (lineSplit[0].length() == 9)) { // 9 is cordyceps
                infectedObjectCounter = 1;
                infectedAnimalCounter = 1;
                isInfected = true;
            } else if (lineSplit.length > 1 && (lineSplit[1].length() == 5)) { // 5 is fungi
                infectedObjectCounter = 0;
                infectedAnimalCounter = 1;
                isInfected = true;
            }


            if (lineSplit.length == 2 + infectedAnimalCounter) {
                object = lineSplit[0 + infectedObjectCounter];
                amount = Integer.parseInt(lineSplit[1 + infectedAnimalCounter]);
            } else if (lineSplit.length == 3 + infectedAnimalCounter) { //If it is a min max
                object = lineSplit[0 + infectedObjectCounter];
                int min = Integer.parseInt(lineSplit[1 + infectedAnimalCounter]);
                int max = Integer.parseInt(lineSplit[2 + infectedAnimalCounter]);
                amount = random.nextInt(max - min + 1) + min;
            } else if (lineSplit.length == 4 + infectedAnimalCounter) { //Object with a territory
                object = lineSplit[0 + infectedObjectCounter];
                amount = Integer.parseInt(lineSplit[1 + infectedAnimalCounter]);
                bearTerritoryCentrum = new Location(Integer.parseInt(lineSplit[2 + infectedAnimalCounter]), Integer.parseInt(lineSplit[3 + infectedAnimalCounter]));
            }

            // In case of wolf
            if (object.equals("wolf")) {
                WolfPack wolfPack = new WolfPack(world);

                //Adding Wolves to pack
                for (int i = 0; i < amount; i++) {
                    Location location = new Location(random.nextInt(world.getSize()), random.nextInt(world.getSize()));
                    while (world.getTile(location) != null) {
                        location = new Location(random.nextInt(world.getSize()), random.nextInt(world.getSize()));
                    }
                    Wolf wolf = new Wolf(world, isInfected);
                    world.setTile(location, wolf);
                    wolfPack.addWolfToPack(wolf);
                    if (wolfPack.getAlphaWolf() == null) {
                        wolfPack.setAlphaWolf(wolf);
                        wolf.digDen();
                        wolfPack.setDen(wolfPack.getAlphaWolf().getDen());

                    }
                }


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

                if (world.isTileEmpty(location) && !world.containsNonBlocking(location)) {
                    if (object.equals("grass")) world.setTile(location, new Grass(world));
                    else if (object.equals("rabbit")) world.setTile(location, new Rabbit(world, isInfected));
                    else if (object.equals("burrow")) world.setTile(location, new Den(world, "rabbit"));
                    else if (object.equals("bear")) world.setTile(location, new Bear(world, isInfected));
                    else if (object.equals("berry")) world.setTile(location, new Bush(world));
                    else if (object.equals("Carcass")) world.setTile(location, new Meat(world, new Rabbit(world, false), isInfected)); // Der findes 1 test hvor carcass er med stort. Der giver ikke mening
                    else if (object.equals("carcass")) world.setTile(location, new Meat(world, new Rabbit(world, false), isInfected));
                }
                testObject = world.getTile(location);
            }
        }
    }
    /**
     * Simulates the test
     */
    public void simulateTest() {
        program.show();
    }
}
