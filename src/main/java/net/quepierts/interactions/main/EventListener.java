package net.quepierts.interactions.main;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import net.quepierts.interactions.main.data.action.ActionManager;
import net.quepierts.interactions.main.data.invnetory.InventoryData;
import net.quepierts.interactions.main.data.invnetory.TargetSlot;
import net.quepierts.interactions.main.utils.ItemUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onIALoadData(ItemsAdderLoadDataEvent event) {

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ActionManager.execute("interact", event.getPlayer(), event);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        ActionManager.execute("harvest", event.getPlayer(), event);
    }

    @EventHandler
    public void onEntityDead(EntityDeathEvent event) {

    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity attackEntity = event.getDamager();

        if (attackEntity instanceof Player) {
            ActionManager.execute("attack", ((Player) attackEntity).getPlayer(), event);
        }

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            ActionManager.execute("damaged", ((Player) entity).getPlayer(), event);
        }
    }

    @EventHandler
    public void onPlayerHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        InventoryData.updatePlayerInventory(player, TargetSlot.getSlot("hand"), ItemUtils.getItemName(player.getInventory().getItem(event.getNewSlot())));
    }
}
