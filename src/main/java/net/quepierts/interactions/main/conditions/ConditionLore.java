package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ConditionLore extends AbstractCondition {
    private final List<String> lore;
    public ConditionLore(Object[] args) {
        super(args);

        this.lore = (List<String>) args[0];
    }

    @Override
    protected boolean condition(Player player, Event event) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.hasItemMeta()) {
            ItemMeta itemMeta = itemInMainHand.getItemMeta();
            if (itemMeta.hasLore()) {
                return itemMeta.getLore().containsAll(lore);
            }
        }
        return false;
    }
}
