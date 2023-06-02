
/*
 * Copyright (C) 2022 Idra - All Rights Reserved
 */

package org.metamechanists.metalib.interfaces;

import org.bukkit.block.Block;

@FunctionalInterface
public interface BlockRunnable {
    boolean run(Block block);
}