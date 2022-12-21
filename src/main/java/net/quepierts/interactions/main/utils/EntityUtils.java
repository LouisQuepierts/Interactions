package net.quepierts.interactions.main.utils;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.data.Dependency;
import org.bukkit.entity.Entity;

public class EntityUtils {
    public static boolean isSame(Entity entity, String id) {
        if (entity == null) return false;
        boolean flag = id.equalsIgnoreCase(entity.getType().toString());

        if (!flag && Dependency.isItemsAdderLoaded()) {
            if (ItemsAdderUtils.matchEntity(entity, id)) flag = true;
        }

        return flag;
    }
}
