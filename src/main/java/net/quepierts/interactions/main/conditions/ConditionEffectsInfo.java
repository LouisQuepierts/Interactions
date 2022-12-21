package net.quepierts.interactions.main.conditions;

import com.google.common.collect.ImmutableMap;
import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.config.objects.EffectCompareInfo;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class ConditionEffectsInfo extends AbstractCondition {
    public static ConditionEffectsInfo getPlayer(Object[] args) {
        return new ConditionEffectsInfo(args, false);
    }

    public static ConditionEffectsInfo getTarget(Object[] args) {
        return new ConditionEffectsInfo(args, true);
    }

    private final Map<PotionEffectType, EffectCompareInfo> map;
    private final boolean target;

    private ConditionEffectsInfo(Object[] args, boolean target) {
        super(args);

        ImmutableMap.Builder<PotionEffectType, EffectCompareInfo> builder = new ImmutableMap.Builder<>();


        for (Object arg : args) {
            if (arg instanceof EffectCompareInfo) {
                builder.put(((EffectCompareInfo) arg).type, (EffectCompareInfo) arg);
            }
        }

        map = builder.build();

        this.target = target;
    }

    @Override
    protected boolean condition(Player player, Event event) {
        if (target) {
            if (event instanceof EntityDamageEvent) {
                Entity entity = ((EntityDamageEvent) event).getEntity();
                if (entity instanceof LivingEntity) {
                    return compare((LivingEntity) entity);
                }
            }
        } else {
            return compare(player);
        }
        return false;
    }

    private boolean compare(LivingEntity entity) {
        for (PotionEffectType type : map.keySet()) {
            PotionEffect potionEffect = entity.getPotionEffect(type);

            if (potionEffect == null || !map.get(type).compare(potionEffect)) {
                return false;
            }
        }

        return true;
    }
}
