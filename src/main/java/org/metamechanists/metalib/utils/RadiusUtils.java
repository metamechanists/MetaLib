package org.metamechanists.metalib.utils;

import org.bukkit.block.Block;
import org.metamechanists.metalib.interfaces.BlockRunnable;

@SuppressWarnings("unused")
public class RadiusUtils {

    public static void forEachSquareRadius(Block centerBlock, int radius, BlockRunnable runnable) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
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
        forEachSquareRadius(centerBlock, radius, block -> {
            for (int y = -radius; y <= radius; y++) {
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
