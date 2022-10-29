package net.quepierts.interactions.main.utils;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {
    public static String getItemName(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            return "AIR";
        }
        CustomStack iaStack = CustomStack.byItemStack(itemStack);

        if (iaStack != null) {
            return iaStack.getNamespacedID();
        }

        return itemStack.getType().toString();
    }
}
