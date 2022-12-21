package net.quepierts.interactions.api;

import net.quepierts.interactions.main.utils.IField;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public abstract class AbstractAction {
    private final AbstractCondition[] conditions;
    private final IField[] fields;

    protected AbstractAction(Object[] args) {
        this.conditions = ((List<AbstractCondition>) args[args.length - 1]).toArray(new AbstractCondition[0]);
        this.fields = IField.getFields(args);
    }
    
    public void execute(Player player, Event event, boolean bypass) {
        if (!bypass) {
            for (AbstractCondition condition : conditions) {
                if (!condition.matches(player, event)) {
                    return;
                }
            }
        }

        for (IField updatable : fields) {
            updatable.update(player);
        }

        this.func(player, event);
    }

    protected abstract void func(Player player, Event event);
}
