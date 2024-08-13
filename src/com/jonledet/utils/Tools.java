package com.jonledet.utils;

import java.util.Random;
import java.util.Scanner;

public class Tools {
    public static Random random = new Random();
    public static Scanner input = new Scanner(System.in);

    public static int nextIntAvoid(int origin, int bound, int avoid) {
        int result;

        do {
            result = random.nextInt(origin, bound);
        } while (result == avoid);

        return result;
    }
}
