package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import net.quepierts.interactions.main.utils.EntryUtils;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class ActionApplyDamage extends AbstractAction {
    private final IMutableNumber<?> amount;
    private final boolean percentage;
    private final EntityDamageEvent.DamageCause damageCause;

    public ActionApplyDamage(Object[] args) throws Exception {
        super(args);

        this.amount = IMutableNumber.cast(args[0], Double.class, Float.class);
        this.percentage = (boolean) args[1];
        this.damageCause = EntryUtils.getDamageCause((String) args[2]);
    }

    @Override
    protected void func(Player player, Event event) {
        if (player.isDead()) return;

        double amount = this.amount.doubleValue();

        if (percentage) {
            amount *= player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        }

        player.damage(amount);
        player.setLastDamageCause(new EntityDamageEvent(player, damageCause, amount));
    }
}
