package org.metamechanists.metalib.base;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PositionedItem extends SlimefunItem {
    protected final int position;

    public PositionedItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int position) {
        super(itemGroup, item, recipeType, recipe);

        this.position = position;
    }

    public PositionedItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, @Nullable ItemStack recipeOutput, int position) {
        super(itemGroup, item, recipeType, recipe, recipeOutput);

        this.position = position;
    }

    protected PositionedItem(ItemGroup itemGroup, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe, int position) {
        super(itemGroup, item, id, recipeType, recipe);

        this.position = position;
    }

    @Override
    public void load() {
        final List<SlimefunItem> items = getItemGroup().getItems();
        if (this.position < 0 || this.position > items.size()) {
            warn("Position is invalid, appending to the item group normally.");
            super.load();
            return;
        }

        if (!hidden) {
            items.add(this.position, this);
        }
        getRecipeType().register(getRecipe(), getRecipeOutput());
    }
}