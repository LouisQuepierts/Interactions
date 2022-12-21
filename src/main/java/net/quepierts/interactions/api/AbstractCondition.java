package net.quepierts.interactions.api;

import net.quepierts.interactions.main.utils.IField;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class AbstractCondition {
    protected final boolean reverse;
    private final IField[] fields;

    public AbstractCondition(Object[] args) {
        this.reverse = (boolean) args[args.length - 1];
        this.fields = IField.getFields(args);
    }

    public final boolean matches(Player player, Event event) {
        for (IField field : fields) {
            field.update(player);
        }
        return reverse != condition(player, event);
    }

    protected abstract boolean condition(Player player, Event event);
}
