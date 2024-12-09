import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import BlockingAgents.*;
import NonblockingAgents.*;
import Testning.ParseTest;
import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

public class Main {
    public static void main(String[] args) {
        //ShowDropTestHereTests();
        //MattTempTest();
        //JosvaTempTest();
        Week2Test();
    }
    //OBS: Virker ikke da Den skal renames da klassen er blevet mere general
    public static void ShowDropTestHereTests() {
        //Program descriptions
        int delay = 200;
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
        int size = 20;
        int delay = 200;
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
            WolfPack wp = new WolfPack(world);
            //world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Fungi(world, 20));
            //world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Meat( world, new Rabbit(world)));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Rabbit(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Rabbit(world));
            Wolf wolf = new Wolf(world, wp, false);
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), wolf);

            world.setTile(new Location(0,0), new Wolf(world, wp,  false));
            //world.setTile(new Location(0,1), new Wolf(world,  false));
            //world.setTile(new Location(1, 1), new Wolf(world,  false));
            //world.setTile(new Location(1,0), new Wolf(world,  false));

            //wp.setAlphaWolf(wolf);
            //world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Grass(world));
            //world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bush(world));//world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bear(world));
            //world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bush(world));
            //world.setTile(new Location(0,1), new Meat(world, new Rabbit(world)));
            //world.setTile(new Location(1,1), new Fungi(world, 20));
            //world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bear(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Den(world, "wolf"));
        } catch (IllegalArgumentException e) {
            Random random = new Random();
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Rabbit(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bear(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Grass(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bush(world));
            world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Wolf(world, false));
        }

        //Shows world

        program.show();
    }

    static void JosvaTempTest() {
        //Program descriptions
        int size = 10;
        int delay = 200;
        int displaySize = 1000;

        //Sets up world
        Program program = new Program(size, displaySize, delay);
        World world = program.getWorld();

        //Display Information
        program.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.gray, "rabbit"));
        program.setDisplayInformation(Grass.class, new DisplayInformation(Color.gray, "grass"));
        program.setDisplayInformation(Wolf.class, new DisplayInformation(Color.gray, "wolf"));
        program.setDisplayInformation(Meat.class, new DisplayInformation(Color.gray, "carcass"));
        program.setDisplayInformation(Fungi.class, new DisplayInformation(Color.gray, "fungi"));


        //Adds agents
        Random random = new Random();
        world.setTile(new Location(2,0), new Rabbit(world, false));
        /*world.setTile(new Location(0,0), new Wolf(world, false));
        world.setTile(new Location(3,0), new Rabbit(world, true));
        world.setTile(new Location(2,2), new Wolf(world, false));
        world.setTile(new Location(5,5), new Meat(world, new Wolf(world, false), false));
        world.setTile(new Location(7,7), new Meat(world, new Rabbit(world, false), true));*/

        //Shows world
        program.show();
    }


    static void MattTempTest() {
        simulation();
    }

    static void simulation() {
        //Program descriptions
        int size = 7;
        int delay = 200;
        int displaySize = 1000;

        //Sets up world
        Program program = new Program(size, displaySize, delay);
        World world = program.getWorld();
        Random random = new Random();

        //Display Information
        program.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.gray, "rabbit"));
        program.setDisplayInformation(Grass.class, new DisplayInformation(Color.gray, "grass"));
        program.setDisplayInformation(Wolf.class, new DisplayInformation(Color.gray, "wolf"));
        program.setDisplayInformation(Meat.class, new DisplayInformation(Color.gray, "carcass"));


        //Adds agents
        world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bear(world));
        world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bear(world));
        // world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Rabbit(world));
        // world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Rabbit(world));
        // world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Grass(world));
        // world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Meat(world, new Wolf(world)));
        // world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Meat(world, new Wolf(world)));
        // world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Meat(world, new Wolf(world)));
        // world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Bush(world));

        /* WOLF FIGHTING IN PACKS TEST
        Wolf wolf = new Wolf(world, false);
        Location location = new Location(random.nextInt(size), random.nextInt(size));
        WolfPack wolfPack = new WolfPack(world);
        wolfPack.setAlphaWolf(wolf);
        world.setTile(location, wolf);
        world.setCurrentLocation(location);
        wolfPack.getAlphaWolf().digDen();
        wolfPack.setDen((wolfPack.getAlphaWolf().getDen()));


        wolf = new Wolf(world, false);
        location = new Location(random.nextInt(size), random.nextInt(size));
        wolfPack = new WolfPack(world);
        wolfPack.setAlphaWolf(wolf);
        world.setTile(location, wolf);
        world.setCurrentLocation(location);
        wolfPack.getAlphaWolf().digDen();
        wolfPack.setDen((wolfPack.getAlphaWolf().getDen()));
        /* WOLF FIGHTING IN PACKS TEST */


        //Shows world
        program.show();
    }

}