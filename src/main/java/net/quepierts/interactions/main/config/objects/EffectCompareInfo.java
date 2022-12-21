package net.quepierts.interactions.main.config.objects;

import net.quepierts.interactions.main.utils.IField;
import net.quepierts.interactions.main.utils.math.threshold.Threshold;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class EffectCompareInfo implements IField {
    public final PotionEffectType type;
    private final Threshold<?> amplifier;
    private final Threshold<?> duration;

    public EffectCompareInfo(Object[] args) throws Exception {
        this.type = PotionEffectType.getByName((String) args[0]);
        this.amplifier = Threshold.cast(args[1], Integer.class);
        this.duration = Threshold.cast(args[2], Integer.class);
    }

    public boolean compare(PotionEffect effect) {
        return effect.getType().equals(this.type) && amplifier.compare(effect.getAmplifier()) && duration.compare(effect.getDuration());
    }

    @Override
    public void update(@NotNull Player player) {
        amplifier.update(player);
        duration.update(player);
    }
}
