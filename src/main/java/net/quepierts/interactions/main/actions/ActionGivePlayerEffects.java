package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import net.quepierts.interactions.main.utils.entry.IPotionEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ActionGivePlayerEffects extends AbstractAction {
    private final List<IPotionEffect> effects;

    public ActionGivePlayerEffects(Object[] args) {
        super(args);
        this.effects = new ArrayList<>();

        for (Object arg : args) {
            if (arg instanceof IPotionEffect) {
                effects.add((IPotionEffect) arg);
            }
        }
    }

    @Override
    protected void func(Player player, Event event) {
        for (IPotionEffect effect : effects) {
            player.addPotionEffect(effect.get());
        }
    }
}
