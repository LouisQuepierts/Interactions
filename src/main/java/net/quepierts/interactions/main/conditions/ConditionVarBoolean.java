package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.config.objects.VarData;
import net.quepierts.interactions.main.data.var.VarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ConditionVarBoolean extends AbstractCondition {
    private final VarData varData;
    private final boolean value;

    public ConditionVarBoolean(Object[] args) {
        super(args);

        this.varData = (VarData) args[0];
        this.value = Boolean.parseBoolean((String) args[1]);
    }

    @Override
    protected boolean condition(Player player, Event event) {
        return value == (VarManager.getBoolean(varData.global ? null : player, varData.varID));
    }
}
