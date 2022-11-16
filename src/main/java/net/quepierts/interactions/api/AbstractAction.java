package net.quepierts.interactions.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public abstract class AbstractAction {
    private final List<AbstractCondition> conditions;

    protected AbstractAction(Object[] args) {
        this.conditions = (List<AbstractCondition>) args[args.length - 1];
    }
    
    public void execute(Player player, Event event, boolean bypass) {
        if (!bypass) {
            for (AbstractCondition condition : conditions) {
                if (!condition.matches(player, event)) {
                    return;
                }
            }
        }

        this.func(player, event);
    }

    protected abstract void func(Player player, Event event);
}
