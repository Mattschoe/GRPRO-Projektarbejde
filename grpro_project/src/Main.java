import java.io.File;
import java.util.ArrayList;

import Testning.ParseTest;

public class Main {
    public static void main(String[] args) {
        Week1TestCases();
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
}