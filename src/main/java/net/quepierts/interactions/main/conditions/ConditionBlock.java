package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class ConditionBlock extends AbstractCondition {
    public static ConditionBlock getPlayer(Object[] args) {
        return new ConditionBlock(args, false);
    }

    public static ConditionBlock getTarget(Object[] args) {
        return new ConditionBlock(args, true);
    }

    private final String block;
    private final Vector vector;
    private final boolean target;

    public ConditionBlock(Object[] args, boolean target) {
        super(args);

        this.block = (String) args[0];
        this.vector = (Vector) args[1];


        this.target = target;
    }

    @Override
    protected boolean condition(Player player, Event event) {
        if (target) {
            if (event instanceof EntityDamageEvent)
                return detect(((EntityDamageEvent) event).getEntity().getLocation());
        } else {
            return detect(player.getLocation());
        }


        return false;
    }

    private boolean detect(Location location) {
        location = location.add(vector);
        Block block = location.getBlock();

        return BlockUtils.isSame(block, this.block);
    }
}
