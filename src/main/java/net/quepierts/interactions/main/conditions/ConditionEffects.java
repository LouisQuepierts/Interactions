package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ConditionEffects extends AbstractCondition {
    public static ConditionEffects getPlayer(Object[] args) {
        return new ConditionEffects(args, false);
    }

    public static ConditionEffects getTarget(Object[] args) {
        return new ConditionEffects(args, true);
    }

    private final List<PotionEffectType> effects;
    private final boolean target;

    private ConditionEffects(Object[] args, boolean target) {
        super(args);
        this.effects = new ArrayList<>();

        for (Object arg : args) {
            if (arg instanceof String) {
                effects.add(PotionEffectType.getByName((String) arg));
            }
        }

        this.target = target;
    }

    @Override
    protected boolean condition(Player player, Event event) {
        if (target) {
            if (event instanceof EntityDamageEvent) {
                Entity entity = ((EntityDamageEvent) event).getEntity();
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    for (PotionEffectType effect : effects) {
                        if (!livingEntity.hasPotionEffect(effect)) {
                            return false;
                        }
                    }
                }
            }
        } else {
            for (PotionEffectType effect : effects) {
                if (!player.hasPotionEffect(effect)) {
                    return false;
                }
            }
        }
        return true;
    }
}
