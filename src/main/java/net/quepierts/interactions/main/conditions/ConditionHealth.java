package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.utils.math.threshold.Threshold;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class ConditionHealth extends AbstractCondition {
    public static ConditionHealth getPlayer(Object[] args) throws Exception {
        return new ConditionHealth(args, false);
    }

    public static ConditionHealth getTarget(Object[] args) throws Exception {
        return new ConditionHealth(args, true);
    }

    private final boolean target;
    private final Threshold<?> threshold;
    private final boolean compare;

    public ConditionHealth(Object[] args, boolean target) throws Exception {
        super(args);

        this.target = target;
        this.threshold = Threshold.cast(args[0], Double.class);
        this.compare = (boolean) args[1];
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

        return reverse != (compare ? threshold.compare(amount) : threshold.equals(amount));
    }
}
