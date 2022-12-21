package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.config.objects.VarData;
import net.quepierts.interactions.main.data.var.VarManager;
import net.quepierts.interactions.main.utils.math.threshold.Threshold;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ConditionVarLong extends AbstractCondition {
    private final VarData varData;
    private final Threshold<?> threshold;

    public ConditionVarLong(Object[] args) throws Exception {
        super(args);

        this.varData = (VarData) args[0];
        this.threshold = Threshold.cast(args[1], Long.class, Integer.class, Double.class);
    }

    @Override
    protected boolean condition(Player player, Event event) {
        long var = VarManager.getLong(varData.global ? null : player, varData.varID);
        return threshold.compare(var);
    }
}
