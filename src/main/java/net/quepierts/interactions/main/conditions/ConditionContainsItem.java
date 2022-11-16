package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.data.invnetory.InventoryData;
import net.quepierts.interactions.main.data.invnetory.TargetSlot;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ConditionContainsItem extends AbstractCondition {
    private final TargetSlot slot;
    private final String item;

    public ConditionContainsItem(Object[] args) {
        super(args);
        this.slot = TargetSlot.getSlot((String) args[0]);
        this.item = (String) args[1];
    }

    @Override
    public boolean condition(Player player, Event event) {
        boolean playerContains = InventoryData.isPlayerContains(player, slot, item);
        return playerContains;
    }
}
