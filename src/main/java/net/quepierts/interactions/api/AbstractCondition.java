package net.quepierts.interactions.api;

import org.bukkit.entity.Player;

public abstract class AbstractCondition implements ICondition {
    private final boolean reverse;

    public AbstractCondition(boolean reverse) {
        this.reverse = reverse;
    }

    @Override
    public boolean reverse() {
        return reverse;
    }
}
