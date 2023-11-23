package com.example.epidemic.utils;

import java.util.Random;

public class RandomGenerator {

    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min + 1);
    }
}
