package org.metamechanists.metalib.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unused")
public class RandomUtils {

    public static final ThreadLocalRandom randomThread = ThreadLocalRandom.current();

    public static boolean chance(int chance) {
        return chance(chance, 100);
    }

    public static boolean chance(int chance, int in) {
        return randomInteger(1, in + 1) <= chance;
    }

    public static <T> T randomChoice(List<T> list) {
        return list.get(randomInteger(0, list.size()));
    }

    public static int randomInteger(int origin, int bound) {
        return randomThread.nextInt(origin, bound);
    }

    public static double randomDouble(double origin, double bound) {
        return randomThread.nextDouble(origin, bound);
    }

    public static double randomDouble() {
        return randomThread.nextDouble(-1.0, 1.0);
    }
}
