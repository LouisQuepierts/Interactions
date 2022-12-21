package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import net.quepierts.interactions.main.data.invnetory.TargetSlot;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class ActionConsumeItem extends AbstractAction {
    private final TargetSlot slot;
    private final int amount;

    protected ActionConsumeItem(Object[] args) {
        super(args);

        slot = TargetSlot.getSlot((String) args[0]);
        amount = (int) args[1];
    }

    @Override
    protected void func(Player player, Event event) {
        ItemStack item = TargetSlot.getItem(player, slot);
        if (item != null) {
            int amount = item.getAmount();
            if (amount > 0) {
                amount = Math.max(amount - this.amount, 0);
                item.setAmount(amount);
            }
        }
    }
}
