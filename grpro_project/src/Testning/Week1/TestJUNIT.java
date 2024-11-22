package Testning.Week1;

import itumulator.executable.Program;
import itumulator.world.World;
import org.junit.Before;
import org.junit.Test;
import itumulator.world.Location;
import BlockingAgents.Rabbit;
import NonblockingAgents.RabbitBurrow;
import NonblockingAgents.Grass;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;


public class TestJUNIT {
    File[] files;
    
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
        Random random;
        Object testObject;

        private void createSimulation(File file) throws FileNotFoundException {
            Scanner scanner = new Scanner(file);

            //Gets world size and initializes world
            worldSize = scanner.nextInt();
            program = new Program(worldSize, 100, 1000);
            program = new Program(worldSize, 900, 1000);
            world = program.getWorld();

            //Initializes objects in world
            String object;
            int amount;
            String object = "";
            int amount = 0;
            random = new Random();
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                //Saves object and amount
                String line = scanner.nextLine();
                String[] lineSplit = line.split(" ");
                String[] lineSplit = line.split("[-\\s]");

                object = lineSplit[0];
                amount = Integer.parseInt(lineSplit[1]);
                //If it is a amount
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

                        if (world.isTileEmpty(location) && !world.containsNonBlocking(location)) {
                            if (object.equals("grass")) world.setTile(location, new Grass());
                            else if (object.equals("rabbit")) world.setTile(location, new Rabbit());
                            else if (object.equals("burrow")) world.setTile(location, new RabbitBurrow(world));
                        }
                        testObject = world.getTile(location);
                    }
                }
            }

            @Before
            public void setUp() throws Exception {
                //Parses in the file directory and saves all files
                File directory = new File("./src/Testning/Week1/TestFiles");
                files = directory.listFiles();
            }
            @Test
            public void test0() {
                try {
                    createSimulation(files[0]);
                    assertTrue(world.contains(testObject));
                } catch (FileNotFoundException e) {
                    System.out.println("Error!:" + e.getMessage());
                }
                program.simulate();
            }
            @Test
            public void test1() {
                try {
                    createSimulation(files[1]);
                    assertTrue(world.contains(testObject));
                } catch (FileNotFoundException e) {
                    System.out.println("Error!:" + e.getMessage());
                }
                program.simulate();
            }
            @Test
            public void test2() {
                try {
                    createSimulation(files[2]);
                    assertTrue(world.contains(testObject));
                } catch (FileNotFoundException e) {
                    System.out.println("Error!:" + e.getMessage());
                }
                program.simulate();
            }
            @Test
            public void test3() {
                try {
                    createSimulation(files[3]);
                    assertTrue(world.contains(testObject));
                } catch (FileNotFoundException e) {
                    System.out.println("Error!:" + e.getMessage());
                }
                program.simulate();
            }
            @Test
            public void test4() {
                try {
                    createSimulation(files[4]);
                    assertTrue(world.contains(testObject));
                } catch (FileNotFoundException e) {
                    System.out.println("Error!:" + e.getMessage());
                }
                program.simulate();
            }
            @Test
            public void test5() {
                try {
                    createSimulation(files[5]);
                    assertTrue(world.contains(testObject));
                } catch (FileNotFoundException e) {
                    System.out.println("Error!:" + e.getMessage());
                }
                program.simulate();
            }
            @Test
            public void test6() {
                try {
                    createSimulation(files[6]);
                    assertTrue(world.contains(testObject));
                } catch (FileNotFoundException e) {
                    System.out.println("Error!:" + e.getMessage());
                }
                program.simulate();
            }
            @Test
            public void test7() {
                try {
                    createSimulation(files[7]);
                    assertTrue(world.contains(testObject));
                } catch (FileNotFoundException e) {
                    System.out.println("Error!:" + e.getMessage());
                }
                program.simulate();
            }
            @Test
            public void test8() {
                try {
                    createSimulation(files[8]);
                    assertTrue(world.contains(testObject));
                } catch (FileNotFoundException e) {
                    System.out.println("Error!:" + e.getMessage());
                }
                program.simulate();
            }
            @Test
            public void test9() {
                try {
                    createSimulation(files[9]);
                    assertTrue(world.contains(testObject));
                } catch (FileNotFoundException e) {
                    System.out.println("Error!:" + e.getMessage());
                }
                program.simulate();
            }
        }