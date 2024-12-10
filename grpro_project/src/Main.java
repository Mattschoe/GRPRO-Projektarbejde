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
        ShowDropTestHereTests();
    }
    //OBS: Virker ikke da Den skal renames da klassen er blevet mere general
    public static void ShowDropTestHereTests() {
        //Program descriptions
        int delay = 5;
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
}