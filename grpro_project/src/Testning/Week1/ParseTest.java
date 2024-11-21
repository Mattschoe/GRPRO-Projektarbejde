package Testning.Week1;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ParseTest {
    public static void main(String[] args) {
        //Parses in the file directory and saves all files
        File directory = new File("./src/Testning/Week1/TestFiles");
        File[] files = directory.listFiles();

        //Edits every file and prepares them for testing
        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName() + ":");
                try {
                    CreateTest(file);
                } catch (FileNotFoundException e) {
                    System.out.println("Error!: " + e.getMessage());
                }
            }
        }
    }

    private static void CreateTest(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        //Size of world
        String stringSize = scanner.nextLine();
        int size = Integer.parseInt(stringSize);

        //Reads each line and parses it to xxx
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineSplit = line.split("[-\\s]");
        }
    }
}
