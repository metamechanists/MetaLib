
/*
 * Copyright (C) 2022 Idra - All Rights Reserved
 */

package org.metamechanists.metalib.base;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class HiddenItem extends SlimefunItem {
    public HiddenItem(ItemGroup itemGroup, SlimefunItemStack item) {
        super(itemGroup, item, RecipeType.NULL, new ItemStack[0]);
    }
    
    @Override
    public void register(@Nonnull SlimefunAddon slimefunAddon) {
        super.register(slimefunAddon);
        setHidden(true);
    }
}
