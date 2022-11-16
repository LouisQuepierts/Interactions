package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.api.AbstractAction;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ActionFoodLevelManipulate extends AbstractAction {
    public static ActionFoodLevelManipulate getPlayer(Object[] args) {
        return new ActionFoodLevelManipulate(args, false);
    }

    public static ActionFoodLevelManipulate getTarget(Object[] args) {
        return new ActionFoodLevelManipulate(args, true);
    }

    private final int amount;
    private final boolean target;

    protected ActionFoodLevelManipulate(Object[] args, boolean target) {
        super(args);
        this.amount = (int) args[0];

        this.target = target;
    }

    @Override
    protected void func(Player player, Event event) {
        if (target && event instanceof EntityDamageByEntityEvent) {
            Entity entity = ((EntityDamageByEntityEvent) event).getEntity();
            if (entity instanceof Player) {
                ((Player) entity).setFoodLevel(Math.max(0, Math.min(20, ((Player) entity).getFoodLevel() + amount)));
            }
        } else {
            player.setFoodLevel(Math.max(0, Math.min(20, player.getFoodLevel() + amount)));
        }
    }
}
