package net.quepierts.interactions.main.utils.entry;

import net.quepierts.interactions.main.utils.IField;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import net.quepierts.interactions.main.utils.math.number.IPointedNumber;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class IPotionEffect implements IField {
    private final PotionEffectType type;
    private final IMutableNumber<?> level;
    private final IMutableNumber<?> duration;

    private final PotionEffect potionEffect;
    private final boolean sta;

    public IPotionEffect(PotionEffectType type, IMutableNumber<?> level, IMutableNumber<?> duration) {
        this.type = type;
        this.level = level;
        this.duration = duration;

        if (!(duration instanceof IPointedNumber && level instanceof IPointedNumber)) {
            potionEffect = new PotionEffect(type, duration.intValue(), level.intValue());
            sta = true;
        } else {
            potionEffect = null;
            sta = false;
        }
    }

    public PotionEffect get() {
        if (sta) {
            return potionEffect;
        } else {
            return new PotionEffect(type, duration.intValue(), level.intValue());
        }
    }

    @Override
    public void update(@NotNull Player player) {
        level.update(player);
        duration.update(player);
    }
}
