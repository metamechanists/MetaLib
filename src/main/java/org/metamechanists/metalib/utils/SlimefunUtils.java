package org.metamechanists.metalib.utils;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlimefunUtils {

    private static final Map<ItemGroup, List<Integer>> INDEX_OFFSETS = new HashMap<>();

    private static SlimefunItemStack errorItemStack(String id) {
        return new SlimefunItemStack(
                "MM_ERROR_" + id.toUpperCase(),
                Material.BARRIER,
                "&cIncorrect Id: " + id);
    }

    public static void loadWithPosition(SlimefunItem slimefunItem, int position) {
        final ItemGroup itemGroup = slimefunItem.getItemGroup();
        final List<SlimefunItem> items = itemGroup.getItems();
        final RecipeType recipeType = slimefunItem.getRecipeType();
        final ItemStack[] recipe = slimefunItem.getRecipe();
        final ItemStack output = slimefunItem.getRecipeOutput();

        // Calculate Index Difference
        final List<Integer> offsets = INDEX_OFFSETS.getOrDefault(itemGroup, new ArrayList<>());
        int difference = 0;

        for (int offset : offsets) {
            if (offset < position) {
                difference++;
            }
        }

        offsets.add(position);
        INDEX_OFFSETS.put(itemGroup, offsets);
        position += difference;

        if (position < 0 || position > items.size()) {
            slimefunItem.warn("Position is invalid, appending to the item group normally.");
            position = items.size() - 1;
        }

        if (!slimefunItem.isHidden()) {
            items.add(position, slimefunItem);
        }
        recipeType.register(recipe, output);
    }

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

    public static SlimefunItemStack getSlimefunItem(String id) {
        final SlimefunItem slimefunItem = Slimefun.getRegistry().getSlimefunItemIds().get(id);
        return slimefunItem != null ? (SlimefunItemStack) slimefunItem.getItem().clone() : errorItemStack(id);
    }

    public static SlimefunItemStack getSlimefunItem(String id, int amount) {
        return new SlimefunItemStack(getSlimefunItem(id), amount);
    }
}
