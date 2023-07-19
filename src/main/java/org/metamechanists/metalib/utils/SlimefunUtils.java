package org.metamechanists.metalib.utils;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.metamechanists.metalib.MetaLib;

public class SlimefunUtils {

    public static ItemGroup getItemGroup(String id) {
        final String[] pair = id.split(":");
        final String itemGroupId = pair[0];
        final String itemId = pair[1];
        for (ItemGroup itemGroup : Slimefun.getRegistry().getAllItemGroups()) {
            if (itemGroup.getKey().getNamespace().equals(itemGroupId) && itemGroup.getKey().getKey().equals(itemId)) {
                return itemGroup;
            }
        }
        return null;
    }

    private static SlimefunItemStack errorItemStack(String id) {
        return new SlimefunItemStack(
                "MM_ERROR_" + id.toUpperCase(),
                Material.BARRIER,
                "&cIncorrect Id: " + id);
    }

    public static SlimefunItemStack getSlimefunItem(String id) {
        final SlimefunItem slimefunItem = Slimefun.getRegistry().getSlimefunItemIds().get(id);
        return slimefunItem != null ? (SlimefunItemStack) slimefunItem.getItem().clone() : errorItemStack(id);
    }

    public static SlimefunItemStack getSlimefunItem(String id, int amount) {
        return new SlimefunItemStack(getSlimefunItem(id), amount);
    }
}
