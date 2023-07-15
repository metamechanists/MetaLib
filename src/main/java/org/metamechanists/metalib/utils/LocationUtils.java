package org.metamechanists.metalib.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.MainHand;
import org.bukkit.util.Vector;

/**
 * Some of this class is taken from ProjectKorra's General Methods Class & modified
 * <br>
 * <a href="https://github.com/ProjectKorra/ProjectKorra/blob/master/src/com/projectkorra/projectkorra/GeneralMethods.java#L817">(See that Here)</a>
 */
public class LocationUtils {
    /**
     * Gets a location with a specified distance away from the left side of a
     * location.
     *
     * @param location The origin location
     * @param distance The distance to the left
     * @return the location of the distance to the left
     */
    public static Location getLeftSide(final Location location, final double distance) {
        return location.clone().add(getSideVector(location, distance));
    }

    /**
     * Returns a location with a specified distance away from the right side of
     * a location.
     *
     * @param location The origin location
     * @param distance The distance to the right
     * @return the location of the distance to the right
     */
    public static Location getRightSide(final Location location, final double distance) {
        return location.clone().subtract(getSideVector(location, distance));
    }

    public static Vector getSideVector(final Location location, final double distance) {
        final float angle = (float) Math.toRadians(location.getYaw());
        return new Vector(Math.cos(angle), 0, Math.sin(angle)).normalize().multiply(distance);
    }

    public static Location getMainHandLocation(final Player player) {
        return getHandLocation(player, player.getMainHand() == MainHand.LEFT);
    }

    public static Location getOffHandLocation(final Player player) {
        return getHandLocation(player, player.getMainHand() == MainHand.RIGHT);
    }

    public static Location getHandLocation(final Player player, boolean isLeftHand) {
        final double y = 1.2 - (player.isSneaking() ? 0.4 : 0);
        final Location playerLocation = player.getLocation();
        final Location baseLocation = isLeftHand ? getLeftSide(playerLocation, .55) : getRightSide(playerLocation, .55);
        return baseLocation.add(0, y, 0).add(playerLocation.getDirection().multiply(0.8));
    }
}
