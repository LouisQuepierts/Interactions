package net.quepierts.interactions.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class AbstractFunction<T> {
    public abstract T getValue(Player player, Event event, Object... args);

    public abstract void execute(Player player, Event event, Object... args);

}
