package net.quepierts.interactions.main.utils;

import dev.lone.itemsadder.api.CustomBlock;
import net.quepierts.interactions.main.data.Dependency;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class EntryUtils {
    // Create a PotionEffect object by Object array
    public static PotionEffect getPotionEffect(Object... args) {
        PotionEffectType type = PotionEffectType.getByName((String) args[0]);
        int level = (int) args[1];
        int duration = (int) args[2];

        return new PotionEffect(type, duration, level);
    }

    // Define is target effect name is an available effect type
    public static boolean isAvailableEffect(String id) {
        return PotionEffectType.getByName(id) != null;
    }

    // Define is target damageCause name is an available damageCause
    public static boolean isAvailableDamageCause(String id) {
        if (id == null) {
            return true;
        }

        return getDamageCause(id) != null;
    }

    // Get DamageCause by name
    public static EntityDamageEvent.DamageCause getDamageCause(String id) {
        for (EntityDamageEvent.DamageCause value : EntityDamageEvent.DamageCause.values()) {
            if (value.name().equalsIgnoreCase(id)) {
                return value;
            }
        }

        return null;
    }

    public static boolean isBlock(String id) {
        Material material = Material.getMaterial(id);
        boolean flag = material != null && material.isBlock();

        if (Dependency.isItemsAdderLoaded()) {
            if (CustomBlock.getInstance(id) != null) {
                flag = true;
            }
        }

        return flag;
    }

    public static Vector getVector(Object... args) {
        return new Vector((double) args[0], (double) args[1], (double) args[2]);
    }

    public static boolean isEntityType(String id) {
        return EntityType.fromName(id) != null;
    }

    public static Action getInteractAction(String id) {
        try {
            return Action.valueOf(id);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isInteractAction(String id) {
        return getInteractAction(id) != null;
    }

    public static BlockFace getBlockFace(String id) {
        try {
            return BlockFace.valueOf(id);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isBlockFace(String id) {
        return getBlockFace(id) != null;
    }

    public static Biome getBiome(String id) {
        try {
            return Biome.valueOf(id);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isBiome(String id) {
        return getBiome(id) != null;
    }
}
