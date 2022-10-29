package net.quepierts.interactions.main.utils;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntryUtils {
    public static PotionEffect getPotionEffect(Object... args) {
        PotionEffectType type = PotionEffectType.getByName((String) args[0]);
        int duration = (int) args[1];
        int level = (int) args[2];

        return new PotionEffect(type, duration, level);
    }
}
