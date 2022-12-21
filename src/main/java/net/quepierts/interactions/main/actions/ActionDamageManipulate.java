package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class ActionDamageManipulate extends AbstractAction {
    private final IMutableNumber<?> amount;
    private final boolean percentage;

    public ActionDamageManipulate(Object[] args) throws Exception {
        super(args);

        this.amount = IMutableNumber.cast(args[0], Double.class, Float.class);
        this.percentage = (boolean) args[1];
    }

    @Override
    protected void func(Player player, Event e) {
        if (e instanceof EntityDamageEvent) {
            double result;
            EntityDamageEvent event = (EntityDamageEvent) e;
            if (percentage) {
                result = 1.0d + this.amount.doubleValue();

                event.setDamage(Math.max(event.getDamage() * result, 0.0d));
            } else {
                result = event.getDamage() + this.amount.doubleValue();

                event.setDamage(Math.max(result, 0.0d));
            }
        }
    }
}
