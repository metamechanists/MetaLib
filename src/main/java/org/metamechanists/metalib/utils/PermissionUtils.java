package org.metamechanists.metalib.utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class PermissionUtils {
    private static final LuckPerms luckperms = LuckPermsProvider.get();

    public static boolean modifyPermission(Player player, String permission, boolean value) {
        final User user = luckperms.getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            return false;
        }

        final PermissionNode node = PermissionNode.builder(permission).value(value).build();
        return user.data().add(node).wasSuccessful();
    }
}
