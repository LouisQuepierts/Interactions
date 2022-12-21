package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.utils.EntryUtils;
import net.quepierts.interactions.main.utils.math.threshold.Threshold;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class ConditionDamage extends AbstractCondition {
    private final Threshold threshold;

    private final EntityDamageEvent.DamageCause damageCause;

    public ConditionDamage(Object[] args) throws Exception {
        super(args);

        this.threshold = Threshold.cast(args[0], Double.class);
        this.damageCause = EntryUtils.getDamageCause((String) args[1]);
    }

    @Override
    protected boolean condition(Player player, Event event) {
        if (event instanceof EntityDamageEvent) {
            double amount = ((EntityDamageEvent) event).getFinalDamage();
            boolean flag = damageCause == null || damageCause.equals(((EntityDamageEvent) event).getCause());
            return reverse != (threshold.compare(amount) && flag);
        }
        return false;
    }
}
