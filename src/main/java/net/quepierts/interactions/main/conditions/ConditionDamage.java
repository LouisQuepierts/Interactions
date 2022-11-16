package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class ConditionDamage extends AbstractCondition {
    private final double threshold;
    private final boolean smaller;
    private final boolean equal;
    private final EntityDamageEvent.DamageCause damageCause;

    public ConditionDamage(Object[] args) {
        super(args);

        this.threshold = (double) args[0];
        this.smaller = (boolean) args[1];
        this.equal = (boolean) args[2];
        String cause = (String) args[3];
        this.damageCause = (cause == null ? null : EntityDamageEvent.DamageCause.valueOf(cause));
    }

    @Override
    protected boolean condition(Player player, Event event) {
        if (event instanceof EntityDamageEvent) {
            double amount = ((EntityDamageEvent) event).getDamage();
            boolean flag = damageCause == null || damageCause.equals(((EntityDamageEvent) event).getCause());
            return reverse != ((!this.smaller ?
                    (this.equal ? amount >= this.threshold : amount > this.threshold) :
                    (this.equal ? amount <= this.threshold : amount < this.threshold)) && flag);
        }
        return false;
    }
}
