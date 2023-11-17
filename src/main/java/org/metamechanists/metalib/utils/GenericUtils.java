package org.metamechanists.metalib.utils;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GenericUtils {
    public static void repeatCode(Plugin plugin, Runnable runnable, int duration) {
        new BukkitRunnable() {
            private int iterations = 0;

            @Override
            public void run() {
                if (iterations >= duration) {
                    cancel();
                    return;
                }

                runnable.run();
                iterations++;
            }
        }.runTaskTimer(plugin, 0, 1L);
    }
}
