package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class ActionGiveItem extends AbstractAction {
    private final ItemStack itemStack;

    public ActionGiveItem(Object[] args) {
        super(args);

        itemStack = (ItemStack) args[0];
    }

    @Override
    protected void func(Player player, Event event) {
        player.getInventory().addItem();
    }
}
