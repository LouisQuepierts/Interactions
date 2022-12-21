package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.utils.EntityUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public abstract class ConditionRidingEntityType extends AbstractCondition {
    protected final String entityType;
    private ConditionRidingEntityType(Object[] args) {
        super(args);

        this.entityType = (String) args[0];
    }

    public static class CPlayer extends ConditionRidingEntityType {
        public CPlayer(Object[] args) {
            super(args);
        }

        @Override
        protected boolean condition(Player player, Event event) {
            return EntityUtils.isSame(player.getVehicle(), entityType);
        }
    }

    public static class CTarget extends ConditionRidingEntityType {
        public CTarget(Object[] args) {
            super(args);
        }

        @Override
        protected boolean condition(Player player, Event event) {
            if (event instanceof EntityDamageByEntityEvent) {
                return EntityUtils.isSame(((EntityDamageByEntityEvent) event).getEntity().getVehicle(), entityType);
            }
            return false;
        }
    }

    public static class CAttacker extends ConditionRidingEntityType {
        public CAttacker(Object[] args) {
            super(args);
        }

        @Override
        protected boolean condition(Player player, Event event) {
            if (event instanceof EntityDamageByEntityEvent) {
                return EntityUtils.isSame(((EntityDamageByEntityEvent) event).getDamager().getVehicle(), entityType);
            }
            return false;
        }
    }
}
