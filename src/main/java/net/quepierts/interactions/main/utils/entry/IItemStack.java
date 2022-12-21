package net.quepierts.interactions.main.utils.entry;

import net.quepierts.interactions.main.utils.IField;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class IItemStack implements IField {
    final ItemStack itemStack;
    final IMutableNumber<?> amount;
    public IItemStack(ItemStack itemStack, IMutableNumber<?> amount) {
        this.itemStack = itemStack;
        this.amount = amount;
    }

    public ItemStack get() {
        itemStack.setAmount(amount.intValue());
        return itemStack;
    }

    @Override
    public void update(@NotNull Player player) {
        amount.update(player);
    }
}
