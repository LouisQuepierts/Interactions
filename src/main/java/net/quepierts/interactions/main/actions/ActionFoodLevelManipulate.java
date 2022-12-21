package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ActionFoodLevelManipulate extends AbstractAction {
    public static ActionFoodLevelManipulate getPlayer(Object[] args) throws Exception {
        return new ActionFoodLevelManipulate(args, false);
    }

    public static ActionFoodLevelManipulate getTarget(Object[] args) throws Exception {
        return new ActionFoodLevelManipulate(args, true);
    }

    private final IMutableNumber<?> amount;
    private final boolean target;

    protected ActionFoodLevelManipulate(Object[] args, boolean target) throws Exception {
        super(args);
        this.amount = IMutableNumber.cast(args[0], Integer.class);

        this.target = target;
    }

    @Override
    protected void func(Player player, Event event) {
        if (target && event instanceof EntityDamageByEntityEvent) {
            Entity entity = ((EntityDamageByEntityEvent) event).getEntity();
            if (entity instanceof Player) {
                ((Player) entity).setFoodLevel(Math.max(0, Math.min(20, ((Player) entity).getFoodLevel() + amount.intValue())));
            }
        } else {
            player.setFoodLevel(Math.max(0, Math.min(20, player.getFoodLevel() + amount.intValue())));
        }
    }
}
