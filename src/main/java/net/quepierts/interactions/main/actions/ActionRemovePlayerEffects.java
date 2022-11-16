package net.quepierts.interactions.main.actions;

import net.quepierts.interactions.api.AbstractAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ActionRemovePlayerEffects extends AbstractAction {
    private final List<PotionEffectType> effects;

    public ActionRemovePlayerEffects(Object[] args) {
        super(args);
        this.effects = new ArrayList<>();

        for (Object arg : args) {
            if (arg instanceof String) {
                effects.add(PotionEffectType.getByName((String) arg));
            }
        }
    }

    @Override
    protected void func(Player player, Event event) {
        for (PotionEffectType effect : effects) {
            player.removePotionEffect(effect);
        }
    }
}
