package net.quepierts.interactions.api;

import net.quepierts.interactions.main.config.Entry;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Collection;

public interface IExecutable {
    void execute(Player player, Event event, boolean bypass);

    void addConditions(Collection<ICondition> conditions);
}
