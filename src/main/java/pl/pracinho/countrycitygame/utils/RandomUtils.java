package pl.pracinho.countrycitygame.utils;

import java.util.Random;

public class RandomUtils {

    public static int getRandomInt(int size) {
        return new Random()
                .nextInt(size);
    }
}
