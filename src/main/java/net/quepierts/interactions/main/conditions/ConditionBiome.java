package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.utils.EntryUtils;
import net.quepierts.interactions.main.utils.math.vector.Vector;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ConditionBiome extends AbstractCondition {
    private final Biome biome;
    private final Vector vector;
    public ConditionBiome(Object[] args) {
        super(args);

        biome = EntryUtils.getBiome((String) args[0]);
        vector = (Vector) args[1];
    }

    @Override
    protected boolean condition(Player player, Event event) {
        return vector.addToLocation(player.getLocation()).getBlock().getBiome().equals(biome);
    }
}
