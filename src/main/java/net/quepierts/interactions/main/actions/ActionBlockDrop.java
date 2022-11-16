package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ActionBlockDrop extends AbstractAction {
    private final ItemStack itemStack;
    private final boolean isSet;
    public ActionBlockDrop(Object[] args) {
        super(args);

        this.itemStack = (ItemStack) args[0];
        this.isSet = (boolean) args[1];
    }

    @Override
    protected void func(Player player, Event event) {
        if (event instanceof BlockBreakEvent) {
            if (isSet) {
                ((BlockBreakEvent) event).setDropItems(false);
            }

            Location location = ((BlockBreakEvent) event).getBlock().getLocation();
            player.getWorld().dropItem(location, itemStack);
        }
    }
}
