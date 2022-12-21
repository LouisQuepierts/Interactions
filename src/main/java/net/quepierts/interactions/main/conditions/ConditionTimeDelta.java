package net.quepierts.interactions.main.conditions;

import net.quepierts.interactions.api.AbstractCondition;
import net.quepierts.interactions.main.data.var.VarManager;
import net.quepierts.interactions.main.data.var.VarType;
import net.quepierts.interactions.main.utils.Time;
import net.quepierts.interactions.main.utils.math.threshold.Threshold;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ConditionTimeDelta extends AbstractCondition {
    private final String timer;
    private final Threshold<?> threshold;

    public ConditionTimeDelta(Object[] args) throws Exception {
        super(args);

        this.timer = "TimeDelta_Timer_" + args[0];
        this.threshold = Threshold.cast(args[1], Integer.class, Long.class);

        if (!VarManager.register(timer, "0l", VarType.NUMBER, false)) {
            throw new Exception();
        }
    }

    @Override
    protected boolean condition(Player player, Event event) {
        long current = Time.getRuntimeTicks();
        long last = VarManager.getLong(player, timer);

        long delta = current - last;
        if (threshold.compare(delta)) {

            VarManager.setNumber(player, timer, current);
            return true;
        }

        return false;
    }
}
