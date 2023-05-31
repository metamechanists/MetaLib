package org.metamechanists.metalib.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unused")
public class ParticleUtils {
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

    public static void randomParticle(Location location, Particle particle, double radius, int count) {
        for(int i = 0; i < count; ++i) {
            randomParticle(location, particle, radius);
        }
    }

    public static void randomParticle(Location location, Particle particle, double radius) {
        double x = ThreadLocalRandom.current().nextDouble(-radius, radius + 0.1);
        double y = ThreadLocalRandom.current().nextDouble(-radius, radius + 0.1);
        double z = ThreadLocalRandom.current().nextDouble(-radius, radius + 0.1);
        location.getWorld().spawnParticle(particle, location.clone().add(x, y, z), 0, 0, 0, 0, 0);
    }

    @ParametersAreNonnullByDefault
    public static void drawLine(Player player, Particle particle, Location start, Location end, double space, @Nullable Particle.DustOptions dustOptions) {
        final double distance = start.distance(end);
        double currentPoint = 0;
        final Vector startVector = start.toVector();
        final Vector endVector = end.toVector();
        final Vector vector = endVector.clone().subtract(startVector).normalize().multiply(space);

        while (currentPoint < distance) {
            if (dustOptions != null) {
                player.spawnParticle(particle, startVector.getX(), startVector.getY(), startVector.getZ(), 1, dustOptions);
            } else {
                player.spawnParticle(particle, startVector.getX(), startVector.getY(), startVector.getZ(), 1);
            }
            currentPoint += space;
            startVector.add(vector);
        }
    }

    public static void highlightBlock(Player player, Block block, Particle particle, Particle.DustOptions dustOptions) {
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

    private static Vector generateRandomOffset(boolean setRandom) {
        return setRandom ? new Vector(RandomUtils.randomDouble(), RandomUtils.randomDouble(), RandomUtils.randomDouble()) : new Vector();
    }
}
