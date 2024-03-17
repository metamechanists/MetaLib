package org.metamechanists.metalib.utils;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unused")
public class ParticleUtils {
    @Getter
    private static final double[][] sphere = new double[7 * 6 * 2][];

    static {
        int sphereLoc = 0;
        for (double i = 0; i <= Math.PI; i += Math.PI / 6) {
            final double radius = Math.sin(i);
            final double y = Math.cos(i);
            for (double a = 0; a < Math.PI * 2; a+= Math.PI / 6) {
                final double x = Math.cos(a) * radius;
                final double z = Math.sin(a) * radius;
                sphere[sphereLoc] = new double[] { x, y, z };
                sphereLoc++;
            }
        }
    }

    private static Vector generateRandomOffset(boolean setRandom) {
        return setRandom ? new Vector(RandomUtils.randomDouble(), RandomUtils.randomDouble(), RandomUtils.randomDouble()) : new Vector();
    }

    public static void randomParticle(Location location, Particle particle, double radius, int count) {
        randomParticle(location, particle, radius, radius, radius, count);
    }

    public static void randomParticle(Location location, Particle particle, double xRadius, double yRadius, double zRadius, int count) {
        for (int i = 0; i < count; ++i) {
            randomParticle(location, particle, xRadius, yRadius, zRadius);
        }
    }

    public static void randomParticle(Location location, Particle particle, double radius) {
        randomParticle(location, particle, radius, radius, radius);
    }

    public static void randomParticle(Location location, Particle particle, double xRadius, double yRadius, double zRadius) {
        double x = ThreadLocalRandom.current().nextDouble(-xRadius, xRadius + 0.1);
        double y = ThreadLocalRandom.current().nextDouble(-yRadius, yRadius + 0.1);
        double z = ThreadLocalRandom.current().nextDouble(-zRadius, zRadius + 0.1);
        location.getWorld().spawnParticle(particle, location.clone().add(x, y, z), 0, 0, 0, 0, 0);
    }

    @ParametersAreNonnullByDefault
    public static void outlineBlock(Player player, Block block, double space, Particle particle, @Nullable Particle.DustOptions dustOptions) {
        outlineBox(player, block.getX(), block.getY(), block.getZ(), block.getX() + 1D, block.getY() + 1D, block.getZ() + 1D, space, particle, dustOptions);
    }

    @ParametersAreNonnullByDefault
    public static void outlineBox(Player player, BoundingBox box, double space, Particle particle, @Nullable Particle.DustOptions dustOptions) {
        outlineBox(player, (int) box.getMinX(), (int) box.getMinY(), (int) box.getMinZ(), (int) box.getMaxX(), (int) box.getMaxY(), (int) box.getMaxZ(), space, particle, dustOptions);
    }

    @ParametersAreNonnullByDefault
    public static void outlineBox(Player player, double lowerX, double lowerY, double lowerZ, double upperX, double upperY, double upperZ, double space, Particle particle, @Nullable Particle.DustOptions dustOptions) {
        // Define all the points
        final Vector bottomBackLeft = new Vector(lowerX, lowerY, lowerZ);
        final Vector bottomBackRight = new Vector(lowerX, lowerY, upperZ);
        final Vector bottomFrontLeft = new Vector(upperX, lowerY, lowerZ);
        final Vector bottomFrontRight = new Vector(upperX, lowerY, upperZ);
        final Vector topBackLeft = new Vector(lowerX, upperY, lowerZ);
        final Vector topBackRight = new Vector(lowerX, upperY, upperZ);
        final Vector topFrontLeft = new Vector(upperX, upperY, lowerZ);
        final Vector topFrontRight = new Vector(upperX, upperY, upperZ);

        // Bottom Lines
        drawLine(player, bottomBackLeft, bottomFrontLeft, space, particle, dustOptions);
        drawLine(player, bottomBackRight, bottomFrontRight, space, particle, dustOptions);
        drawLine(player, bottomBackLeft, bottomBackRight, space, particle, dustOptions);
        drawLine(player, bottomFrontLeft, bottomFrontRight, space, particle, dustOptions);
        // Vertical Lines
        drawLine(player, bottomBackLeft, topBackLeft, space, particle, dustOptions);
        drawLine(player, bottomFrontLeft, topFrontLeft, space, particle, dustOptions);
        drawLine(player, bottomBackRight, topBackRight, space, particle, dustOptions);
        drawLine(player, bottomFrontRight, topFrontRight, space, particle, dustOptions);
        // Top Lines
        drawLine(player, topBackLeft, topFrontLeft, space, particle, dustOptions);
        drawLine(player, topBackRight, topFrontRight, space, particle, dustOptions);
        drawLine(player, topBackLeft, topBackRight, space, particle, dustOptions);
        drawLine(player, topFrontLeft, topFrontRight, space, particle, dustOptions);
    }

    /**
     * @deprecated The order of arguments is weird, use {@link ParticleUtils#drawLine(Player, Location, Location, double, Particle, Particle.DustOptions)}
     **/
    @Deprecated(forRemoval = true)
    @ParametersAreNonnullByDefault
    public static void drawLine(Player player, Particle particle, Location start, Location end, double space, @Nullable Particle.DustOptions dustOptions) {
        drawLine(player, start.toVector(), end.toVector(), space, particle, dustOptions);
    }

    @ParametersAreNonnullByDefault
    public static void drawLine(Player player, Location start, Location end, double space, Particle particle, @Nullable Particle.DustOptions dustOptions) {
        drawLine(player, start.toVector(), end.toVector(), space, particle, dustOptions);
    }

    @ParametersAreNonnullByDefault
    public static void drawLine(Player player, Location start, Location end, double space, Particle particle, @Nullable Particle.DustOptions dustOptions, double distance) {
        drawLine(player, start.toVector(), end.toVector(), space, particle, dustOptions, distance);
    }

    @ParametersAreNonnullByDefault
    public static void drawLine(Player player, Vector start, Vector end, double space, Particle particle, @Nullable Particle.DustOptions dustOptions) {
        drawLine(player, start, end, space, particle, dustOptions, start.distance(end));
    }

    @ParametersAreNonnullByDefault
    public static void drawLine(Player player, Vector start, Vector end, double space, Particle particle, @Nullable Particle.DustOptions dustOptions, double distance) {
        double currentPoint = 0;
        final Vector step = end.clone().subtract(start).normalize().multiply(space);
        start = start.clone();

        while (currentPoint < distance) {
            if (dustOptions != null) {
                player.spawnParticle(particle, start.getX(), start.getY(), start.getZ(), 1, dustOptions);
            } else {
                player.spawnParticle(particle, start.getX(), start.getY(), start.getZ(), 1);
            }
            currentPoint += space;
            start.add(step);
        }
    }

    /**
     * @deprecated The order of arguments is weird, use {@link ParticleUtils#highlightBlockCorners(Player, Block, Particle, Particle.DustOptions)}
     **/
    @Deprecated(forRemoval = true)
    public static void highlightBlock(Player player, Block block, Particle particle, Particle.DustOptions dustOptions) {
        highlightBlockCorners(player, block, particle, dustOptions);
    }

    public static void highlightBlockCorners(Player player, Block block, Particle particle, Particle.DustOptions dustOptions) {
        drawSquareCorners(player, block.getLocation(), 1, particle, dustOptions);
        drawSquareCorners(player, block.getLocation().add(0, 1, 0), 1, particle, dustOptions);
    }

    public static void drawSquareCorners(Player player, Location corner, int length, Particle particle, Particle.DustOptions dustOptions) {
        player.spawnParticle(particle, corner.getX(), corner.getY(), corner.getZ(), 1, dustOptions);
        player.spawnParticle(particle, corner.getX() + length, corner.getY(), corner.getZ(), 1, dustOptions);
        player.spawnParticle(particle, corner.getX() + length, corner.getY(), corner.getZ() + length, 1, dustOptions);
        player.spawnParticle(particle, corner.getX(), corner.getY(), corner.getZ() + length, 1, dustOptions);
    }

    public static void sphereIn(Location location, Particle particle) {
        sphereIn(location, particle, 1.0, false);
    }
    public static void sphereIn(Location location, Particle particle, double speed) {
        sphereIn(location, particle, speed, false);
    }
    public static void sphereIn(Location location, Particle particle, boolean randomOffset) {
        sphereIn(location, particle, 1.0, randomOffset);
    }
    public static void sphereIn(Location location, Particle particle, double speed, boolean randomOffset) {
        final World world = location.getWorld();
        for (double[] offsets : sphere) {
            final Location particleLocation = location.clone().add(offsets[0], offsets[1], offsets[2]).add(generateRandomOffset(randomOffset));
            final Vector direction = location.clone().subtract(particleLocation.clone()).toVector();
            world.spawnParticle(particle, particleLocation, 0, direction.getX(), direction.getY(), direction.getZ(), speed);
        }
    }
    public static void spheresUp(Location location, Particle particle) {
        final World world = location.getWorld();
        double[] speeds = new double[] {0.1, 0.15, 0.2, 0.25,};
        for (double[] offsets : sphere) {
            world.spawnParticle(particle, location.clone().add(offsets[0], offsets[1], offsets[2]), 0, 0, 5, 0, speeds[new Random().nextInt(0, speeds.length)]);
        }
    }
    public static void sphereOut(Location location, Particle particle) {
        sphereOut(location, particle, 0.25, false);
    }
    public static void sphereOut(Location location, Particle particle, double speed) {
        sphereOut(location, particle, speed, false);
    }
    public static void sphereOut(Location location, Particle particle, boolean randomOffset) {
        sphereOut(location, particle, 0.25, randomOffset);
    }
    public static void sphereOut(Location location, Particle particle, double speed, boolean randomOffset) {
        final World world = location.getWorld();
        location = location.clone().add(0, 0.5, 0);
        for (double[] offsets : sphere) {
            final Location particleLocation = location.clone().add(offsets[0], offsets[1], offsets[2]).add(generateRandomOffset(randomOffset));
            final Vector direction = particleLocation.clone().subtract(location.clone()).toVector();
            world.spawnParticle(particle, particleLocation, 0, direction.getX(), direction.getY(), direction.getZ(), speed);
        }
    }
    public static void sphere(Location location, Particle particle, double scale, boolean randomOffset) {
        final World world = location.getWorld();
        location = location.clone().add(0, 0.5, 0);
        for (double[] offsets : sphere) {
            final Location particleLocation = location.clone().add(offsets[0] * scale, offsets[1] * scale, offsets[2] * scale).add(generateRandomOffset(randomOffset));
            world.spawnParticle(particle, particleLocation, 0, 0, 0, 0);
        }
    }
}
