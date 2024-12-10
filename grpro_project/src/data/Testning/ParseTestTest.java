package data.Testning;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

class ParseTestTest {
   int displaySize;
   int delay;
   File[]  files;
   ArrayList<ParseTest> tests;
    @BeforeEach
    void setUp() {
        displaySize = 5;
        delay = 5;
        File directory = new File ("./src/data.Testning/Week1");
        files = directory.listFiles();
        tests = new ArrayList<>();

    }

    @Test
    void TestFileInput() {



        for (File file : files) {
            ParseTest test = new ParseTest(file, displaySize, delay);
            tests.add(test);
        }

        for (ParseTest test : tests) {
            test.simulateTest();
        }


    }

    @Test
    void simulateTest() {
    }
}