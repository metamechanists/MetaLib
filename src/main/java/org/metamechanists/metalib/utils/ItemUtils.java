package org.metamechanists.metalib.utils;

import io.github.bakedlibs.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.NonNull;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;

import static io.github.bakedlibs.dough.items.ItemUtils.canStack;

@SuppressWarnings("unused")
public class ItemUtils {
    private static final NamespacedKey cooldownKey = new NamespacedKey("metalib", "cooldown");

    public static ItemStack makeEnchanted(ItemStack itemStack) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DESTROYS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack orAir(ItemStack itemStack) {
        return itemStack == null ? new ItemStack(Material.AIR) : itemStack;
    }

    @ParametersAreNonnullByDefault
    public static void addOrDropItemMainHand(Player player, ItemStack itemStack) {
        if (player.getInventory().getItemInMainHand().getType().isEmpty()) {
            player.getInventory().setItemInMainHand(itemStack);
            return;
        }
        addOrDropItem(player, itemStack);
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

            if (!canStack(inventoryStack, itemStack)) {
                continue;
            }

            freeSpace += inventoryStack.getMaxStackSize() - inventoryStack.getAmount();
        }
        return freeSpace;
    }

    @NonNull
    public static String getItemName(ItemStack itemStack) {
        if (itemStack.getItemMeta() != null && itemStack.getItemMeta().hasDisplayName()) {
            return itemStack.getItemMeta().getDisplayName();
        }

        return ChatColor.WHITE + ChatUtils.humanize(itemStack.getType().name());
    }

    public static String toId(String string) {
        final StringBuilder id = new StringBuilder();
        for (char character : ChatColor.stripColor(string).replace(" ", "_").toCharArray()) {
            if (Character.isLetterOrDigit(character)) {
                id.append(character);
            }
        }
        return id.toString();
    }

    public static void fillMenuSlots(ChestMenu chestMenu, int[] slots, ItemStack itemStack) {
        for (int slot : slots) {
            chestMenu.addItem(slot, itemStack, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    public static boolean onCooldown(ItemStack itemStack) {
        return itemStack.getItemMeta() == null
                || PersistentDataAPI.getLong(itemStack.getItemMeta(), cooldownKey, System.currentTimeMillis()) > System.currentTimeMillis();
    }
    public static long getCooldown(ItemStack itemStack) {
        return itemStack.getItemMeta() == null
                ? 0
                : PersistentDataAPI.getLong(itemStack.getItemMeta(), cooldownKey, 0);
    }
    public static void startCooldown(ItemStack itemStack, double seconds) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }

        PersistentDataAPI.setLong(itemMeta, cooldownKey, System.currentTimeMillis() + (long) (seconds * 1000));
        itemStack.setItemMeta(itemMeta);
    }
}
