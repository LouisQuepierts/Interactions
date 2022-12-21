package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import net.quepierts.interactions.main.config.objects.VarData;
import net.quepierts.interactions.main.data.var.VarManager;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ActionVarNumberManipulate extends AbstractAction {
    private final VarData varData;
    private final IMutableNumber<?> number;

    public ActionVarNumberManipulate(Object[] args) throws Exception {
        super(args);

        this.varData = (VarData) args[0];
        this.number = IMutableNumber.cast(args[1]);
    }

    @Override
    protected void func(Player player, Event event) {
        Player p = varData.global ? null : player;
        VarManager.getINumber(p, varData.varID).add(number);
    }
}
