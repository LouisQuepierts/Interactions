package net.quepierts.interactions.main.listener;

import dev.lone.itemsadder.api.Events.CustomBlockBreakEvent;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.data.action.ActionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ItemsAdderEventListener implements Listener {
    @EventHandler
    public void onIALoadData(ItemsAdderLoadDataEvent event) {
        Interactions.reload();
    }

    @EventHandler
    public void onCustomBlockBreak(CustomBlockBreakEvent event) {
        ActionManager.execute("harvest", event.getPlayer(), event);
    }
}
