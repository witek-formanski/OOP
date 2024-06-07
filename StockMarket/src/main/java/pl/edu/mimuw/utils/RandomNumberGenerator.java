package pl.edu.mimuw.utils;

import java.util.Random;

public class RandomNumberGenerator {
    private static final Random randomNumberGenerator = new Random();
    public static int getRandom(int down, int up) {
        if (down > up) {
            throw new IllegalArgumentException("The lower limit must be less than or equal to the upper limit.");
        }

        return randomNumberGenerator.nextInt(up - down + 1) + down;
    }

    public static boolean flipCoin() {
        return getRandom(0, 1) == 0;
    }
}
