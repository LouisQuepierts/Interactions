package net.quepierts.interactions.api;

import net.quepierts.interactions.main.config.Entry;
import org.bukkit.entity.Player;

public interface ICondition {
    boolean reverse();

    boolean condition(Player player);

    default boolean matches(Player player) {
        return reverse() != condition(player);
    }
}
