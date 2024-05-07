package pl.edu.mimuw.simulation;

import java.util.Random;

public class Losowanie {
    private static final Random randomNumberGenerator = new Random();
    public static int losuj(int down, int up) {
        if (down > up) {
            throw new IllegalArgumentException("The lower limit must be less than or equal to the upper limit.");
        }

        return randomNumberGenerator.nextInt(up - down + 1) + down;
    }
}
