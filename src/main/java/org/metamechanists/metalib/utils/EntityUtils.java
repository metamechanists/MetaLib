package org.metamechanists.metalib.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EntityUtils {
    public static <E extends Entity> E spawnProjectile(Player player, Class<E> entity) {
        final Location location = player.getLocation();
        final float yaw = location.getYaw();
        final double x = Math.sin(yaw * Math.PI/180) * -1;
        final double z = Math.cos(yaw * Math.PI/180);
        return player.getWorld().spawn(location.add(x, 1.162, z), entity,
                e -> e.setVelocity(location.getDirection().multiply(2)));
    }
}
