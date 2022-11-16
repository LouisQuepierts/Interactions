package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.utils.BlockUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

public class ConditionBreakBlock extends AbstractCondition {
    private final String block;
    public ConditionBreakBlock(Object[] args) {
        super(args);

        this.block = (String) args[0];
    }

    @Override
    protected boolean condition(Player player, Event event) {
        if (event instanceof BlockBreakEvent) {
            return BlockUtils.isSame(((BlockBreakEvent) event).getBlock(), block);
        }
        return false;
    }
}
