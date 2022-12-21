package net.quepierts.interactions.main.listener;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.data.Dependency;
import net.quepierts.interactions.main.data.action.ActionManager;
import net.quepierts.interactions.main.data.invnetory.InventoryData;
import net.quepierts.interactions.main.data.invnetory.TargetSlot;
import net.quepierts.interactions.main.data.var.VarManager;
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
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class EventListener implements Listener {
    private final Interactions interactions;

    public EventListener(Interactions interactions) {
        this.interactions = interactions;
    }

    @EventHandler
    public void onPluginLoad(PluginEnableEvent event) {
        switch (event.getPlugin().getName()) {
            case "ItemsAdder":
                Dependency.setIaLoaded(true);
                Interactions.logger.info("ItemsAdder Dependency Loaded");
                interactions.getServer().getPluginManager().registerEvents(new ItemsAdderEventListener(), interactions);
                break;
        }
    }

    @EventHandler
    public void onPluginUnload(PluginDisableEvent event) {
        switch (event.getPlugin().getName()) {
            case "ItemsAdder":
                Dependency.setIaLoaded(false);
                break;
        }
    }

    @EventHandler
    public void playerLogin(PlayerLoginEvent event) {
        InventoryData.playerLogin(event);
        VarManager.add(event.getPlayer());
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        InventoryData.playerQuit(event);
        VarManager.erase(event.getPlayer());
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
        EntityDamageEvent lastDamageCause = event.getEntity().getLastDamageCause();
        if (lastDamageCause instanceof EntityDamageByEntityEvent) {
            Entity damager = ((EntityDamageByEntityEvent) lastDamageCause).getDamager();
            if (damager instanceof Player) {
                ActionManager.execute("kill", (Player) damager, event);
            }
        }
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

        if (entity instanceof Player && entity.getLastDamageCause() != null) {
            ActionManager.execute("damaged", ((Player) entity).getPlayer(), event);
        }
    }

    @EventHandler
    public void onPlayerHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        InventoryData.updatePlayerInventory(player, TargetSlot.getSlot("hand"), ItemUtils.getItemName(player.getInventory().getItem(event.getNewSlot())));
    }
}
