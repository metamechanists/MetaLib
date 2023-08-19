package org.metamechanists.metalib.utils;

import org.bukkit.block.Block;
import org.metamechanists.metalib.interfaces.BlockRunnable;

@SuppressWarnings("unused")
public class RadiusUtils {

    public static void forEachSquareRadius(Block centerBlock, int radius, BlockRunnable runnable) {
        forEachRectangularRadius(centerBlock, radius, radius, runnable);
    }

    public static void forEachRectangularRadius(Block centerBlock, int xRadius, int zRadius, BlockRunnable runnable) {
        for (int x = -xRadius; x <= xRadius; x++) {
            for (int z = -zRadius; z <= zRadius; z++) {
                if (runnable.run(centerBlock.getRelative(x, 0, z))) {
                    return;
                }
            }
        }
    }

    public static void forEachCircleRadius(Block centerBlock, int radius, BlockRunnable runnable) {
        forEachSquareRadius(centerBlock, radius, block -> {
            if (centerBlock.getLocation().distanceSquared(block.getLocation()) > Math.pow(radius, 2)) {
                return false;
            }

            return runnable.run(block);
        });
    }

    public static void forEachCubeRadius(Block centerBlock, int radius, BlockRunnable runnable) {
        forEachRectangularPrismRadius(centerBlock, radius, radius, radius, runnable);
    }

    public static void forEachRectangularPrismRadius(Block centerBlock, int xRadius, int yRadius, int zRadius, BlockRunnable runnable) {
        forEachRectangularRadius(centerBlock, xRadius, zRadius, block -> {
            for (int y = -yRadius; y <= yRadius; y++) {
                if (runnable.run(block.getRelative(0, y, 0))) {
                    return true;
                }
            }
            return false;
        });
    }

    public static void forEachSphereRadius(Block centerBlock, int radius, BlockRunnable runnable) {
        forEachCubeRadius(centerBlock, radius, block -> {
            if (centerBlock.getLocation().distanceSquared(block.getLocation()) > Math.pow(radius, 2)) {
                return false;
            }

            return runnable.run(block);
        });
    }
}
