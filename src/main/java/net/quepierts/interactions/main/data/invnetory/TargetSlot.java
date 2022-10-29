package net.quepierts.interactions.main.data.invnetory;

import org.bukkit.inventory.EquipmentSlot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TargetSlot {
    private static final Map<Integer, TargetSlot> slotIDMap = new HashMap<>();
    private static final Map<String, TargetSlot> slotNameMap = new HashMap<>();

    private final int slotID;
    private final String name;

    static {
        new TargetSlot(40, "off_hand");
        new TargetSlot(-1, "hand");
        new TargetSlot(39, "head");
        new TargetSlot(38, "chest");
        new TargetSlot(37, "legs");
        new TargetSlot(36, "feet");
    }

    public static void init() {

    }

    public static Collection<TargetSlot> getAvailableSlots() {
        return slotNameMap.values();
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
        return slotNameMap.get(name.toLowerCase());
    }

    public static TargetSlot getSlot(int slotID) {
        return slotIDMap.get(slotID);
    }

    private TargetSlot(int slotID, String name) {
        this.slotID = slotID;
        this.name = name;

        slotNameMap.put(name, this);
        slotIDMap.put(slotID, this);
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
