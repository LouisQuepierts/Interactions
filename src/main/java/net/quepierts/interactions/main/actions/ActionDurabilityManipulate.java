package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.api.AbstractAction;
import net.quepierts.interactions.main.data.invnetory.TargetSlot;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ActionDurabilityManipulate extends AbstractAction {
    private final TargetSlot slot;
    private final double amount;
    private final boolean percentage;

    public ActionDurabilityManipulate(Object[] args) {
        super(args);

        slot = TargetSlot.getSlot((String) args[0]);
        percentage = (boolean) args[2];

        amount = percentage ? Math.min(1, Math.max(0, (double) args[1])) : (double) args[1];
    }

    @Override
    protected void func(Player player, Event event) {
        if (event instanceof EntityDamageEvent) {
            Entity entity = ((EntityDamageEvent) event).getEntity();
            if (entity instanceof Zombie) {

            }
        }
        ItemStack itemStack = TargetSlot.getItem(player, slot);

        if (itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemStack.getType().getMaxDurability() > 0) {
                if (itemMeta instanceof Damageable) {
                    Damageable damageable = (Damageable) itemMeta;
                    int result = damageable.getDamage();

                    if (percentage) {
                        result -= itemStack.getType().getMaxDurability() * amount;
                    } else {
                        result -= amount;
                    }

                    damageable.setDamage(result);
                    itemStack.setItemMeta(itemMeta);
                }
            }
        }
    }
}
