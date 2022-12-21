package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ConditionChance extends AbstractCondition {
    private final IMutableNumber<?> percentage;

    public ConditionChance(Object[] args) throws Exception {
        super(args);

        this.percentage = IMutableNumber.cast(args[0], Double.class, Float.class);
    }

    @Override
    protected boolean condition(Player player, Event event) {
        float nextFloat = Interactions.random.nextFloat();
        return percentage.compareTo(nextFloat) > 0;
    }
}
