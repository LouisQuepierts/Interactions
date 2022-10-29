package net.quepierts.interactions.api;

import net.quepierts.interactions.Interactions;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractAction implements IExecutable {
    private final List<ICondition> conditions;

    protected AbstractAction() {
        conditions = new ArrayList<>();
    }

    @Override
    public void execute(Player player, Event event, boolean bypass) {
        if (!bypass) {
            for (ICondition condition : conditions) {
                if (!condition.matches(player)) {
                    return;
                }
            }
        }

        this.func(player, event);
    }

    @Override
    public void addConditions(Collection<ICondition> conditions) {
        this.conditions.addAll(conditions);
    }

    protected abstract void func(Player player, Event event);
}
