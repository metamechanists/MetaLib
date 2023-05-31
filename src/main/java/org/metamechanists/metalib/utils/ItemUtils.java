package org.metamechanists.metalib.utils;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;

@SuppressWarnings("unused")
public class ItemUtils {
    private final Plugin plugin;

    public ItemUtils(Plugin plugin) {
        this.plugin = plugin;
    }

    private ItemGroup errorGroup() {
        return new ItemGroup(
                new NamespacedKey(plugin, "error_group"),
                new CustomItemStack(Material.BARRIER, "&cError Group"));
    }

    public ItemGroup getItemGroup(String id) {
        final String[] pair = id.split(":");
        final String itemGroupId = pair[0];
        final String itemId = pair[1];
        for (ItemGroup itemGroup : Slimefun.getRegistry().getAllItemGroups()) {
            if (itemGroup.getKey().getNamespace().equals(itemGroupId) && itemGroup.getKey().getKey().equals(itemId)) {
                return itemGroup;
            }
        }
        return errorGroup();
    }

    private static SlimefunItemStack errorItemStack(String id) {
        return new SlimefunItemStack(
                "MM_ERROR_" + id.toUpperCase(),
                Material.BARRIER,
                "&cIncorrect Id: " + id);
    }

    public static ItemStack makeEnchanted(ItemStack itemStack) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DESTROYS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static SlimefunItemStack getSlimefunItem(String id) {
        final SlimefunItem slimefunItem = Slimefun.getRegistry().getSlimefunItemIds().get(id);
        return slimefunItem != null ? (SlimefunItemStack) slimefunItem.getItem().clone() : errorItemStack(id);
    }

    public static SlimefunItemStack getSlimefunItem(String id, int amount) {
        return new SlimefunItemStack(getSlimefunItem(id), amount);
    }

    public static ItemStack orAir(ItemStack itemStack) {
        return itemStack == null ? new ItemStack(Material.AIR) : itemStack;
    }

    @ParametersAreNonnullByDefault
    public static void addOrDropItem(Player player, ItemStack... itemStacks) {
        final HashMap<Integer, ItemStack> remaining = player.getInventory().addItem(itemStacks);
        if (remaining.size() > 0) {
            for (ItemStack itemStack : remaining.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            }
        }
    }

    public static void addItem(Inventory inventory, ItemStack itemStack) {
        final ItemStack[] itemStacks = inventory.getStorageContents();
        for (int i = 0; i < itemStacks.length; i++) {
            final ItemStack inventoryStack = itemStacks[i];
            if (inventoryStack == null || inventoryStack.getType().isAir()) {
                itemStacks[i] = new CustomItemStack(itemStack);
                break;
            }

            if (!io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils.canStack(inventoryStack, itemStack)) {
                continue;
            }

            final int count = Math.min(itemStack.getAmount(), inventoryStack.getMaxStackSize() - inventoryStack.getAmount());
            inventoryStack.setAmount(inventoryStack.getAmount() + count);
            itemStack.setAmount(itemStack.getAmount() - count);

            if (itemStack.getAmount() == 0) {
                break;
            }
        }
        inventory.setStorageContents(itemStacks);
    }

    public static int freeSpace(Inventory inventory, ItemStack itemStack) {
        int freeSpace = 0;
        for (ItemStack inventoryStack : inventory.getStorageContents()) {
            if (inventoryStack == null || inventoryStack.getType().isAir()) {
                freeSpace += itemStack.getMaxStackSize();
                continue;
            }

            if (!io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils.canStack(inventoryStack, itemStack)) {
                continue;
            }

            freeSpace += inventoryStack.getMaxStackSize() - inventoryStack.getAmount();
        }
        return freeSpace;
    }

    @NotNull
    public static String getItemName(ItemStack itemStack) {
        if (itemStack.getItemMeta() != null && itemStack.getItemMeta().hasDisplayName()) {
            return itemStack.getItemMeta().getDisplayName();
        }

        return ChatColor.WHITE + ChatUtils.humanize(itemStack.getType().name());
    }

    public static String toId(String string) {
        final StringBuilder id = new StringBuilder();
        for (char character : net.md_5.bungee.api.ChatColor.stripColor(string).replace(" ", "_").toCharArray()) {
            if (Character.isLetterOrDigit(character)) {
                id.append(character);
            }
        }
        return id.toString();
    }
}
