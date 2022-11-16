package net.quepierts.interactions.main.data.action;

import java.util.*;

public final class ExecuteType {
    private static final Map<String, Integer> typeMap = new HashMap<>();

    public static void register(String name, int priority) {
        typeMap.put(name, priority);
    }

    public static Collection<String> value() {
        return typeMap.keySet();
    }

    public static int getPriority(String name) {
        return typeMap.get(name);
    }

    public static boolean isAvailableType(String name) {
        return typeMap.containsKey(name);
    }

    public static void cleanUp() {
        typeMap.clear();
    }
}
