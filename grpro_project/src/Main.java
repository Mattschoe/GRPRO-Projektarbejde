
import java.io.File;
import java.util.ArrayList;

import Testning.ParseTest;

public class Main {
    public static void main(String[] args) {
        ShowDropTestHereTests();
    }

    /**
     * Shows the tests that have been put in the folder "DropTestHere" under "./Testning"
     */
    public static void ShowDropTestHereTests() {
        //Program descriptions
        int delay = 50;
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