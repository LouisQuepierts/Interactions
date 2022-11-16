package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class ConditionHealth extends AbstractCondition {
    public static ConditionHealth getPlayer(Object[] args) {
        return new ConditionHealth(args, false);
    }

    public static ConditionHealth getTarget(Object[] args) {
        return new ConditionHealth(args, true);
    }

    private final boolean target;
    private final double threshold;
    private final boolean compare;
    private final boolean smaller;
    private final boolean equal;

    public ConditionHealth(Object[] args, boolean target) {
        super(args);

        this.target = target;
        this.threshold = (double) args[0];
        this.compare = (boolean) args[1];
        this.smaller = (boolean) args[2];
        this.equal = (boolean) args[3];
    }

    @Override
    protected boolean condition(Player player, Event event) {
        double amount = 0;

        if (target) {
            if (event instanceof EntityDamageEvent) {
                Entity entity = ((EntityDamageEvent) event).getEntity();
                if (entity instanceof LivingEntity) {
                    amount = ((LivingEntity) entity).getHealth();
                }
            }
        } else {
            amount = player.getHealth();
        }

        return reverse != (compare ? (!this.smaller ?
                (this.equal ? amount >= this.threshold : amount > this.threshold) :
                (this.equal ? amount <= this.threshold : amount < this.threshold)) :
                amount == threshold);
    }
}
