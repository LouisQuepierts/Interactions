package net.quepierts.interactions.main.utils;

import dev.lone.itemsadder.api.CustomStack;
import net.quepierts.interactions.main.data.Dependency;
import net.quepierts.interactions.main.data.invnetory.InventoryData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {
    public static ItemStack getItemStack(Object[] args) {
        String name = (String) args[0];
        int amount = (int) args[1];
        ItemStack itemStack = null;

        if (Dependency.isItemsAdderLoaded()) {
            CustomStack instance = CustomStack.getInstance(name);
            if (instance != null) {
                itemStack = instance.getItemStack();
            }
        } 
        
        if (itemStack == null) {
            itemStack = new ItemStack(Material.getMaterial(name));
        }

        itemStack.setAmount(amount);
        return itemStack;
    }

    // Get item name by itemStack
    public static String getItemName(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            return "AIR";
        }

        if (Dependency.isItemsAdderLoaded()) {
            CustomStack iaStack = CustomStack.byItemStack(itemStack);

            if (iaStack != null) {
                return iaStack.getNamespacedID();
            }
        }

        return itemStack.getType().toString();
    }

    //Define is target item is an available item
    public static boolean isAvailableItem(String itemName) {
        if (InventoryData.isAvailableItem(itemName)) {
            return true;
        }

        boolean flag = Material.getMaterial(itemName) != null;

        if (Dependency.isItemsAdderLoaded()) {
            if (CustomStack.getInstance(itemName) != null) {
                flag = true;
            }
        }

        if (flag) InventoryData.addItem(itemName);
        return flag;
    }

    public static boolean isAvailableItem(ItemStack itemStack) {
        return isAvailableItem(getItemName(itemStack));
    }
}
