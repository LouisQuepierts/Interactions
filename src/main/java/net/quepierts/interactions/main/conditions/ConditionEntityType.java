package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.utils.EntityUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ConditionEntityType extends AbstractCondition {
    public static ConditionEntityType getTarget(Object[] args) {
        return new ConditionEntityType(args, true);
    }

    public static ConditionEntityType getAttacker(Object[] args) {
        return new ConditionEntityType(args, false);
    }

    private final String entityType;
    private final boolean target;

    private ConditionEntityType(Object[] args, boolean target) {
        super(args);

        this.entityType = (String) args[0];
        this.target = target;
    }

    @Override
    protected boolean condition(Player player, Event event) {
        if (event instanceof EntityDamageByEntityEvent) {
            Entity entity;
            if (target) {
                entity = ((EntityDamageByEntityEvent) event).getEntity();
            } else {
                entity = ((EntityDamageByEntityEvent) event).getDamager();
            }

            return EntityUtils.isSame(entity, this.entityType);
        }
        return false;
    }
}
