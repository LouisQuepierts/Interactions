package net.quepierts.interactions.main.data.invnetory;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class InventoryData implements Listener {
    @EventHandler
    public void playerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        defaultData(player);
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        playerInventoryData.remove(event.getPlayer().getUniqueId());
    }

    private static final Set<String> availableItems = new HashSet<>();
    private static final Map<UUID, Map<TargetSlot, String>> playerInventoryData = new HashMap<>();

    public static void init(Interactions plugin) {
        availableItems.clear();

        Collection<? extends Player> onlinePlayers = plugin.getServer().getOnlinePlayers();
        for (Player player : onlinePlayers) {
            UUID id = player.getUniqueId();
            if (!playerInventoryData.containsKey(id)) {
                defaultData(player);
            }
        }
    }

    public static void addItem(String itemName) {
        availableItems.add(itemName);
    }

    public static boolean isAvailableItem(String itemName) {
        return availableItems.contains(itemName);
    }

    public static boolean hasAvailableItems(Player player) {
        Map<TargetSlot, String> items = playerInventoryData.get(player.getUniqueId());

        for (String item : items.values()) {
            if (availableItems.contains(item)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPlayerContains(Player player, TargetSlot slot, String item) {
        if (playerInventoryData.containsKey(player.getUniqueId()) && slot != null) {
            String itemName = playerInventoryData.get(player.getUniqueId()).get(slot);

            if (itemName == null) {
                return false;
            }

            return itemName.equalsIgnoreCase(item);
        }

        return false;
    }

    public static boolean isEmpty(Player player, TargetSlot slot) {
        return isPlayerContains(player, slot, "AIR");
    }

    public static void updatePlayerInventory(Player player, TargetSlot slot, String item) {
        UUID playerID = player.getUniqueId();

        if (!isPlayerContains(player, slot, item)) {
            if (!playerInventoryData.containsKey(playerID)) {
                playerInventoryData.put(playerID, new HashMap<>());
            }
            playerInventoryData.get(playerID).put(slot, item);
        }
    }

    private static void defaultData(Player player) {
        HashMap<TargetSlot, String> value = new HashMap<>();
        value.put(TargetSlot.getSlot("hand"), ItemUtils.getItemName(player.getInventory().getItemInMainHand()));
        playerInventoryData.put(player.getUniqueId(), value);
    }
}
