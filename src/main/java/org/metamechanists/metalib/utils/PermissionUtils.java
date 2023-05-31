package org.metamechanists.metalib.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class PermissionUtils {
    public static boolean modifyPermission(Player player, String permission, boolean add) {
        return Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "lp user " + player.getDisplayName() + " permission set " + permission + " " + add);
    }
}
