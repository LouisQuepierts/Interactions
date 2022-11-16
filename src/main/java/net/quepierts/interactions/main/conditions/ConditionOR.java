package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class ConditionOR extends AbstractCondition {
    private final List<AbstractCondition> conditionList;
    public ConditionOR(Object[] args) {
        super(args);

        this.conditionList = (List<AbstractCondition>) args[0];
    }

    @Override
    protected boolean condition(Player player, Event event) {
        for (AbstractCondition condition : conditionList) {
            if (condition.matches(player, event)) {
                return true;
            }
        }
        return false;
    }
}
