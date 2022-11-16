package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ConditionWorldName extends AbstractCondition {
    private final String name;
    public ConditionWorldName(Object[] args) {
        super(args);

        this.name = (String) args[0];
    }

    @Override
    protected boolean condition(Player player, Event event) {
        return name.equals(player.getWorld().getName());
    }
}
