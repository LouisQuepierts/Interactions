package net.quepierts.interactions.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class AbstractCondition {
    protected final boolean reverse;

    public AbstractCondition(Object[] args) {
        this.reverse = (boolean) args[args.length - 1];
    }

    public final boolean matches(Player player, Event event) {
        return reverse != condition(player, event);
    }

    protected abstract boolean condition(Player player, Event event);
}
