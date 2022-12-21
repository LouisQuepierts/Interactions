package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.config.objects.VarData;
import net.quepierts.interactions.main.data.var.VarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ConditionVarString extends AbstractCondition {
    private final VarData varData;
    private final String value;
    private final boolean fullMatches;

    public ConditionVarString(Object[] args) {
        super(args);

        this.varData = (VarData) args[0];
        this.value = (String) args[1];
        this.fullMatches = (boolean) args[2];
    }

    @Override
    protected boolean condition(Player player, Event event) {
        String arg = VarManager.getString(varData.global ? null : player, varData.varID);
        return arg != null && fullMatches ? value.equals(arg) : arg.contains(value);
    }
}
