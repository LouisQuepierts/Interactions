package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class ActionGiveEffect extends AbstractAction {
    private final List<PotionEffect> effects;

    public ActionGiveEffect(Object... args) {
        this.effects = new ArrayList<>();

        for (Object arg : args) {
            if (arg instanceof PotionEffect) {
                effects.add((PotionEffect) arg);
            }
        }
    }

    @Override
    protected void func(Player player, Event event) {
        player.addPotionEffects(effects);
    }
}
