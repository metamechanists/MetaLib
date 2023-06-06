package org.metamechanists.metalib.utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class PermissionUtils {
    private static final LuckPerms luckperms = LuckPermsProvider.get();

    public static boolean modifyPermission(Player player, String permission, boolean value) {
        PermissionNode node = PermissionNode.builder(permission).value(value).build();
        User user = luckperms.getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            return false;
        }
        DataMutateResult result = user.data().add(node);
        return result.wasSuccessful();
    }
}
