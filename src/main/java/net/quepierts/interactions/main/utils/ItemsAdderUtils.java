package net.quepierts.interactions.main.utils;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomEntity;
import dev.lone.itemsadder.api.CustomMob;
import dev.lone.itemsadder.api.CustomStack;
import net.quepierts.interactions.Interactions;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class ItemsAdderUtils {
    public static ItemStack getItemStack(String name) {
        CustomStack instance = CustomStack.getInstance(name);
        if (instance != null) {
            return instance.getItemStack();
        }
        return null;
    }

    public static String getItemName(ItemStack stack) {
        CustomStack iaStack = CustomStack.byItemStack(stack);

        return iaStack == null ? null : iaStack.getNamespacedID();
    }

    public static boolean isItem(String name) {
        return CustomStack.getInstance(name) != null;
    }

    public static boolean isBlock(String name) {
        return CustomBlock.getInstance(name) != null;
    }

    public static boolean matchBlock(Block block, String blockName) {
        CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);
        return customBlock != null && blockName.equalsIgnoreCase(customBlock.getNamespacedID());
    }

    public static void placeBlock(Location location, String name) {
        CustomBlock instance = CustomBlock.getInstance(name);
        if (instance != null) {
            instance.place(location);
            return;
        }
    }

    private static final Location origin = Interactions.getInstance().getServer().getWorlds().get(0).getBlockAt(0, 0, 0).getLocation();

    public static boolean isEntity(String id) {
        CustomEntity spawn = CustomEntity.spawn(id, origin);

        if (spawn != null) {
            spawn.destroy();
        }
        return spawn != null;
    }

    public static boolean matchEntity(Entity entity, String id) {
        CustomEntity customEntity = CustomEntity.byAlreadySpawned(entity);
        return customEntity != null && id.equals(customEntity.getNamespacedID());
    }
}
