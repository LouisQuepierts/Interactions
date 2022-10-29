package net.quepierts.interactions.main;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.data.action.ActionManager;
import net.quepierts.interactions.main.data.action.ExecuteType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerLoadEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onIALoadData(ItemsAdderLoadDataEvent event) {

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity attackEntity = event.getDamager();

        if (attackEntity instanceof Player) {
            ActionManager.execute(ExecuteType.getByName("attack"), ((Player) attackEntity).getPlayer(), event);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            ActionManager.execute(ExecuteType.getByName("damaged"), ((Player) entity).getPlayer(), event);
        }
    }
}
