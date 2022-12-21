package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import net.quepierts.interactions.main.utils.entry.IItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ActionGiveItem extends AbstractAction {
    private final IItemStack itemStack;

    public ActionGiveItem(Object[] args) {
        super(args);

        itemStack = (IItemStack) args[0];
    }

    @Override
    protected void func(Player player, Event event) {
        player.getInventory().addItem(itemStack.get());
    }
}
