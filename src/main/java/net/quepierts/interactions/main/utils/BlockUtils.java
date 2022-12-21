package net.quepierts.interactions.main.utils;

import net.quepierts.interactions.main.data.Dependency;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockUtils {
    public static boolean isSame(Block block, String name) {
        boolean flag = name.equalsIgnoreCase(block.getType().name());

        if (!flag && Dependency.isItemsAdderLoaded()) {
            if (ItemsAdderUtils.matchBlock(block, name)) flag = true;
        }

        return flag;
    }

    public static void setBlock(Location location, String name) {
        if (Dependency.isItemsAdderLoaded()) {
            ItemsAdderUtils.placeBlock(location, name);
        }

        location.getBlock().setType(Material.getMaterial(name));
    }
}
