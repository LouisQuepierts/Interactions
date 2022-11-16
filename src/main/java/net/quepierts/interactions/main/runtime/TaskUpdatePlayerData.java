package net.quepierts.interactions.main.runtime;

import net.quepierts.interactions.main.data.action.ActionManager;
import net.quepierts.interactions.main.data.action.ExecuteType;
import net.quepierts.interactions.main.data.invnetory.InventoryData;
import net.quepierts.interactions.main.data.invnetory.TargetSlot;
import net.quepierts.interactions.main.utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public class TaskUpdatePlayerData implements Runnable {
    private final JavaPlugin plugin;

    public TaskUpdatePlayerData(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        // Get all the online player in server
        Collection<? extends Player> onlinePlayers = plugin.getServer().getOnlinePlayers();
        if (!onlinePlayers.isEmpty()) {
            for (Player player : onlinePlayers) {
                if (!player.isDead()) {
                    updatePlayer(player);
                }
            }
        }
    }

    private void updatePlayer(Player player) {
        Collection<TargetSlot> availableSlots = TargetSlot.getAvailableSlots();
        PlayerInventory inventory = player.getInventory();
        for (TargetSlot slot : availableSlots) {
            if (slot.getSlotID() != -1) {
                InventoryData.updatePlayerInventory(player,
                        slot,
                        ItemUtils.getItemName(inventory.getItem(slot.getSlotID())));
            }
        }

        if (InventoryData.hasAvailableItems(player)) {
            ActionManager.execute("update", player, null);
        }
    }
}
