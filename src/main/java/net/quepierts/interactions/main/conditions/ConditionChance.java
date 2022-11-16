package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.api.AbstractCondition;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ConditionChance extends AbstractCondition {
    private final double percentage;

    public ConditionChance(Object[] args) {
        super(args);

        this.percentage = (double) args[0];
    }

    @Override
    protected boolean condition(Player player, Event event) {
        float f = Interactions.random.nextFloat();

        return f < percentage;
    }
}
