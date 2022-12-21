package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import net.quepierts.interactions.main.config.objects.VarData;
import net.quepierts.interactions.main.data.var.VarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ActionVarManipulate extends AbstractAction {
    private final VarData varData;
    private final String value;

    public ActionVarManipulate(Object[] args) {
        super(args);

        this.varData = (VarData) args[0];
        this.value = (String) args[1];
    }

    @Override
    protected void func(Player player, Event event) {
        VarManager.trySet(varData.global ? null : player, varData.varID, value);
    }
}
