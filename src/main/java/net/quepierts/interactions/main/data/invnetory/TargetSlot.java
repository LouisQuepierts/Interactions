package net.quepierts.interactions.main.data.invnetory;

import net.quepierts.interactions.Interactions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TargetSlot {
    private static final Map<Integer, TargetSlot> slotIDMap = new HashMap<>();
    private static final Map<String, TargetSlot> slotNameMap = new HashMap<>();

    private final int slotID;
    private final String name;

    public static void cleanUp() {
        slotIDMap.clear();
        slotNameMap.clear();
    }

    public static void register(String name, int slotID) {
        if (!slotIDMap.containsKey(slotID)) {
            new TargetSlot(slotID, name);
        }
    }

    @Nullable
    public static ItemStack getItem(Player player, TargetSlot targetSlot) {
        if (targetSlot.equals("hand")) {
            return player.getInventory().getItemInMainHand();
        } else if (targetSlot.slotID != -1) {
            return player.getInventory().getItem(targetSlot.slotID);
        }

        return null;
    }

    public static Collection<TargetSlot> getAvailableSlots() {
        return slotNameMap.values();
    }

    public static boolean isAvailableSlot(Object arg) {
        return slotNameMap.containsKey((String) arg);
    }

    public static boolean isAvailableSlot(String name) {
        return slotNameMap.containsKey(name);
    }

    public static boolean isAvailableSlot(int slotID) {
        return slotIDMap.containsKey(slotID);
    }

    public static TargetSlot getSlot(EquipmentSlot slot) {
        return slotNameMap.get(slot.name().toLowerCase());
    }

    public static TargetSlot getSlot(String name) {
        TargetSlot slot = slotNameMap.get(name.toLowerCase());
        if (slot == null) {
            Interactions.logger.warning("Illegal Slot Type: " + name.toLowerCase());
            return null;
        }

        return slot;
    }

    public static TargetSlot getSlot(int slotID) {
        return slotIDMap.get(slotID);
    }

    private TargetSlot(int slotID, String name) {
        this.slotID = slotID;
        this.name = name;

        slotNameMap.put(name, this);

        if (slotID != -1) {
            slotIDMap.put(slotID, this);
        }
    }

    public String getName() {
        return name;
    }

    public int getSlotID() {
        return slotID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return obj.equals(name);
        }
        return obj == this;
    }
}
