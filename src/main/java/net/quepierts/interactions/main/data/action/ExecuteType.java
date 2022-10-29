package net.quepierts.interactions.main.data.action;

import javax.annotation.Nullable;
import java.util.*;

public final class ExecuteType {
    private static final Map<String, ExecuteType> executeTypeMap = new HashMap<>();

    private final String name;
    private final int priority;

    private ExecuteType(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }

    public String name() {
        return name;
    }

    public static void register(String name, int priority) {
        executeTypeMap.put(name, new ExecuteType(name, priority));
    }

    public static Collection<ExecuteType> value() {
        return executeTypeMap.values();
    }

    @Nullable
    public static ExecuteType getByName(String name) {
        return executeTypeMap.get(name);
    }
}
