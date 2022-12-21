package net.quepierts.interactions.main.utils;

import net.quepierts.interactions.main.data.AvailableIDs;
import net.quepierts.interactions.main.data.Dependency;
import net.quepierts.interactions.main.utils.entry.IItemStack;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {
    public static IItemStack getItemStack(Object[] args) throws Exception {
        String name = (String) args[0];
        IMutableNumber<?> number = IMutableNumber.cast(args[1], Integer.class);
        ItemStack itemStack = null;

        if (Dependency.isItemsAdderLoaded()) {
            itemStack = ItemsAdderUtils.getItemStack(name);
        } 
        
        if (itemStack == null) {
            itemStack = new ItemStack(Material.getMaterial(name));
        }

        return new IItemStack(itemStack, number);
    }

    // Get item name by itemStack
    public static String getItemName(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            return "AIR";
        }

        if (Dependency.isItemsAdderLoaded()) {
            String name = ItemsAdderUtils.getItemName(itemStack);

            if (name != null) {
                return name;
            }
        }

        return itemStack.getType().toString();
    }

    //Define is target item is an available item
    public static boolean isAvailableItem(String itemName) {
        if (AvailableIDs.availableItem(itemName)) {
            return true;
        }

        boolean flag = Material.getMaterial(itemName) != null;

        if (itemName.contains(":")) {
            if (Dependency.isItemsAdderLoaded()) {
                if (ItemsAdderUtils.isItem(itemName)) {
                    flag = true;
                }
            }
        }

        if (flag) AvailableIDs.addItem(itemName);
        return flag;
    }

    public static boolean isAvailableItem(ItemStack itemStack) {
        return isAvailableItem(getItemName(itemStack));
    }
}
