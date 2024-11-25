import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import BlockingAgents.Rabbit;
import NonblockingAgents.Grass;
import NonblockingAgents.RabbitBurrow;
import Testning.ParseTest;
import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

public class Main {
    public static void main(String[] args) {
        Week2Test();
    }

    public static void Week1TestCases() {
        //Program descriptions
        int delay = 1000;
        int displaySize = 400;

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

    //Skal slettes længere
    public static void Week2Test() {
        //Program descriptions
        int size = 20;
        int delay = 1000;
        int displaySize = 400;

        //Sets up world
        Program program = new Program(size, delay, displaySize);
        World world = program.getWorld();

        //Display Information
        program.setDisplayInformation(Rabbit.class, new DisplayInformation(Color.white, "rabbit-small"));
        program.setDisplayInformation(RabbitBurrow.class, new DisplayInformation(Color.black, "hole"));
        program.setDisplayInformation(Grass.class, new DisplayInformation(Color.green, "grass" ));

        //Adds agents
        Random random = new Random();
        world.setTile(new Location(random.nextInt(size), random.nextInt(size)), new Rabbit(world));

        //Shows world
        program.show();
    }
}