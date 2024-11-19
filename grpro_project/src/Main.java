import java.awt.Color;

import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

public class Main {
    public static void main(String[] args) {
        int size = 15;
        Program program = new Program(size, 800, 75);
        World w = program.getWorld();

        program.show();
    }
}