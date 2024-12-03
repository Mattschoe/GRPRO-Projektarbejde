import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import BlockingAgents.Bear;
import BlockingAgents.Meat;
import BlockingAgents.Wolf;
import BlockingAgents.Rabbit;
import NonblockingAgents.*;
import Testning.ParseTest;
import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

public class Main {
    public static void main(String[] args) {
        Week2Test();
    }
    //OBS: Virker ikke da Den skal renames da klassen er blevet mere general
    public static void ShowDropTestHereTests() {
        //Program descriptions
        int delay = 1000;
        int displaySize = 1000;

        //File setup, saves all files dropped in folder
        File[] files;
        File directory = new File ("./src/Testning/DropTestHere");
        files = directory.listFiles();

        ArrayList<ParseTest> tests = new ArrayList<>();
        for (File file : files) {
            ParseTest test = new ParseTest(file, displaySize, delay);
            tests.add(test);
        }

        for (ParseTest test : tests) {
            test.simulateTest();
        }
    }

    //Skal slettes senere
    public static void Week2Test() {
        //Program descriptions
        int size = 5;
        int delay = 500;
        int displaySize = 1000;

        //Sets up world
        Program program = new Program(size, displaySize, delay);
        World world = program.getWorld();

        //Display Information
        program.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.white, "rabbit-small"));
        program.setDisplayInformation(Wolf.class, new DisplayInformation(Color.red, "wolf" ));
        program.setDisplayInformation(Bear.class, new DisplayInformation(Color.red, "bear" ));
        program.setDisplayInformation(Grass.class, new DisplayInformation(Color.green, "grass" ));
        program.setDisplayInformation(Territory.class, new DisplayInformation(Color.red));
        program.setDisplayInformation(Bush.class, new DisplayInformation(Color.green, "bush-berries"));
        program.setDisplayInformation(Den.class, new DisplayInformation(Color.black, "hole"));
        program.setDisplayInformation(Meat.class, new DisplayInformation(Color.green, "carcass" ));
        program.setDisplayInformation(Fungi.class, new DisplayInformation(Color.green, "fungi" ));

        //Adds agents
        try {
            Random random = new Random();
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Rabbit(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Rabbit(world));
            //world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bear(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Grass(world));
            //world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bush(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Wolf(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Den(world, "rabbit"));
        } catch (IllegalArgumentException e) {
            Random random = new Random();
            /*world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Rabbit(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bear(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Grass(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bush(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Wolf(world));
        */}

        //Shows world

        program.show();
    }


    static void MattTempTest() {
        //Program descriptions
        int size = 5;
        int delay = 800;
        int displaySize = 1000;

        //Sets up world
        Program program = new Program(size, displaySize, delay);
        World world = program.getWorld();

        //Display Information
        program.setDisplayInformation(Wolf.class, new DisplayInformation(Color.gray, "wolf"));
        program.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.black, "rabbit" ));
        program.setDisplayInformation(Meat.class, new DisplayInformation(Color.red, "carcass" ));

        //Adds agents
        Random random = new Random();
        world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Rabbit(world));
        world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Wolf(world));

        //Shows world
        program.show();
    }
}