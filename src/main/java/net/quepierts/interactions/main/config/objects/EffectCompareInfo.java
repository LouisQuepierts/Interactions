package net.quepierts.interactions.main.config.objects;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectCompareInfo {
    public final PotionEffectType type;
    public final int amplifier;
    public final int duration;

    public final boolean smallerAmplifier;
    public final boolean smallerDuration;


    public EffectCompareInfo(Object[] args) {
        this.type = PotionEffectType.getByName((String) args[0]);
        this.amplifier = (int) args[1];
        this.duration = (int) args[2];
        this.smallerAmplifier = (boolean) args[3];
        this.smallerDuration = (boolean) args[4];
    }

    public boolean compare(PotionEffect effect) {
        return effect.getType().equals(this.type) &&
                smallerAmplifier == (effect.getAmplifier() < this.amplifier) &&
                smallerDuration == (effect.getDuration() < this.duration);
    }
}
