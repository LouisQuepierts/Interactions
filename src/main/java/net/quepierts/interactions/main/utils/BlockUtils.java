package net.quepierts.interactions.main.utils;

import dev.lone.itemsadder.api.CustomBlock;
import net.quepierts.interactions.main.data.Dependency;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class BlockUtils {
    public static boolean isSame(Block block, String name) {
        boolean flag = name.equalsIgnoreCase(block.getType().name());

        if (!flag && Dependency.isItemsAdderLoaded()) {
            CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);
            flag = customBlock != null && name.equalsIgnoreCase(customBlock.getNamespacedID());
        }

        return flag;
    }

    public static void setBlock(Location location, String name) {
        if (Dependency.isItemsAdderLoaded()) {
            CustomBlock instance = CustomBlock.getInstance(name);
            if (instance != null) {
                instance.place(location);
                return;
            }
        }

        location.getBlock().setType(Material.getMaterial(name));
    }
}
