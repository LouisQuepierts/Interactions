package net.quepierts.interactions.main.utils.entry;

import net.quepierts.interactions.main.utils.IField;
import net.quepierts.interactions.main.utils.IString;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IDamageCause implements IField {
    private final IString value;

    public IDamageCause(Object[] args) {
        this.value = (IString) args[0];
    }


    @Override
    public void update(@NotNull Player player) {
        value.update(player);
    }
}
