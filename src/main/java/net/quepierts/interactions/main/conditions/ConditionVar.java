package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.config.objects.VarData;
import net.quepierts.interactions.main.data.var.VarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ConditionVar extends AbstractCondition {
    private final VarData varData;
    private final String value;

    public ConditionVar(Object[] args) {
        super(args);

        this.varData = (VarData) args[0];
        this.value = (String) args[1];
    }

    @Override
    protected boolean condition(Player player, Event event) {
        String value = VarManager.getString(varData.global ? null : player, varData.varID);
        return this.value.equals(value);
    }
}
