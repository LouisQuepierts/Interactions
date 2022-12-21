package net.quepierts.interactions.main.utils;

import net.quepierts.interactions.main.data.AvailableIDs;
import net.quepierts.interactions.main.data.Dependency;
import net.quepierts.interactions.main.utils.entry.IPotionEffect;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import net.quepierts.interactions.main.utils.math.vector.Vector;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

public class EntryUtils {
    // Create a PotionEffect object by Object array
    public static IPotionEffect getPotionEffect(Object... args) throws Exception {
        return new IPotionEffect(PotionEffectType.getByName((String) args[0]),
                IMutableNumber.cast(args[1], Integer.class),
                IMutableNumber.cast(args[2], Integer.class));
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
        try {
            return EntityDamageEvent.DamageCause.valueOf(id);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isBlock(String id) {
        if (AvailableIDs.availableBlock(id)) {
            return true;
        }

        Material material = Material.getMaterial(id);
        boolean flag = material != null && material.isBlock();

        if (id.contains(":")) {
            if (Dependency.isItemsAdderLoaded()) {
                if (ItemsAdderUtils.isBlock(id)) {
                    flag = true;
                }
            }
        }

        if (flag) AvailableIDs.addBlock(id);

        return flag;
    }

    public static Vector getVector(Object... args) {
        return new Vector((IMutableNumber<?>) args[0], (IMutableNumber<?>) args[1], (IMutableNumber<?>) args[2]);
    }

    public static boolean isEntityType(String id) {
        boolean flag = EntityType.fromName(id) != null;

        if (id.contains(":")) {
            if (Dependency.isItemsAdderLoaded()) {
                if (ItemsAdderUtils.isEntity(id)) {
                    flag = true;
                }
            }
        }

        if (flag) AvailableIDs.addEntity(id);

        return flag;
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
