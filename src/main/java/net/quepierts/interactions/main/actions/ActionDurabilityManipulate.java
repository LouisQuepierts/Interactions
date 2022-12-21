package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import net.quepierts.interactions.main.data.invnetory.TargetSlot;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ActionDurabilityManipulate extends AbstractAction {
    private final TargetSlot slot;
    private final IMutableNumber<?> amount;
    private final boolean percentage;

    public ActionDurabilityManipulate(Object[] args) throws Exception {
        super(args);

        slot = TargetSlot.getSlot((String) args[0]);
        percentage = (boolean) args[2];

        amount = IMutableNumber.cast(args[1], Double.class, Float.class);
    }

    @Override
    protected void func(Player player, Event event) {
        ItemStack itemStack = TargetSlot.getItem(player, slot);

        if (itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemStack.getType().getMaxDurability() > 0) {
                if (itemMeta instanceof Damageable) {
                    Damageable damageable = (Damageable) itemMeta;
                    int result = damageable.getDamage();

                    if (percentage) {
                        result -= itemStack.getType().getMaxDurability() * amount.doubleValue();
                    } else {
                        result -= amount.intValue();
                    }

                    damageable.setDamage(result);
                    itemStack.setItemMeta(itemMeta);
                }
            }
        }
    }
}
