package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class ActionDamageManipulate extends AbstractAction {
    private final double amount;
    private final boolean percentage;

    public ActionDamageManipulate(Object[] args) {
        super(args);

        this.amount = (double) args[0];
        this.percentage = (boolean) args[1];
    }

    @Override
    protected void func(Player player, Event e) {
        if (e instanceof EntityDamageEvent) {
            double result;
            EntityDamageEvent event = (EntityDamageEvent) e;
            if (percentage) {
                result = 1 + this.amount;

                if (result > 0) {
                    event.setDamage(event.getDamage() * result);
                } else {
                    event.setDamage(0);
                }
            } else {
                result = event.getDamage() + this.amount;

                if (result > 0) {
                    event.setDamage(result);
                } else {
                    event.setDamage(0);
                }
            }
        }
    }
}
