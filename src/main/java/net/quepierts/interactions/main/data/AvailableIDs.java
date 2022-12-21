package net.quepierts.interactions.main.data;

import java.util.HashSet;
import java.util.Set;

public class AvailableIDs {
    private static final Set<String> items = new HashSet<>();
    private static final Set<String> blocks = new HashSet<>();
    private static final Set<String> entities = new HashSet<>();

    public static void cleanUp() {
        items.clear();
        blocks.clear();
        entities.clear();
    }

    public static void addItem(String id) {
        items.add(id);
    }

    public static void addBlock(String id) {
        blocks.add(id);
    }

    public static void addEntity(String id) {
        entities.add(id);
    }

    public static boolean availableItem(String id) {
        return items.contains(id);
    }

    public static boolean availableBlock(String id) {
        return blocks.contains(id);
    }

    public static boolean availableEntity(String id) {
        return entities.contains(id);
    }
}
