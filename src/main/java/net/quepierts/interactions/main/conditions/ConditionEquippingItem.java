package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.data.invnetory.InventoryData;
import net.quepierts.interactions.main.data.invnetory.TargetSlot;
import org.bukkit.entity.Player;

public class ConditionEquippingItem extends AbstractCondition {
    private final TargetSlot slot;
    private final String item;

    public ConditionEquippingItem(Object[] objects) {
        super((Boolean) objects[2]);
        this.slot = TargetSlot.getSlot((String) objects[0]);
        this.item = (String) objects[1];
    }

    @Override
    public boolean condition(Player player) {
        return InventoryData.isPlayerContains(player, slot, item);
    }
}
