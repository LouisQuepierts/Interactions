package net.quepierts.interactions.main.data.action;

import com.google.common.collect.ImmutableMap;
import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.api.IExecutable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActionManager {
    private static Map<ExecuteType, List<IExecutable>> executorMap;

    public static void init() {
        ImmutableMap.Builder<ExecuteType, List<IExecutable>> builder = new ImmutableMap.Builder<>();

        for (ExecuteType executeType : ExecuteType.value()) {
            builder.put(executeType, new ArrayList<>());
        }

        executorMap = builder.build();
    }

    public static void add(ExecuteType type, IExecutable executable) {
        executorMap.get(type).add(executable);
    }

    public static void clear() {
        for (List<IExecutable> value : executorMap.values()) {
            value.clear();
        }
    }

    public static void execute(ExecuteType type, Player player, Event event) {
        List<IExecutable> executors = executorMap.get(type);
        for (IExecutable executor : executors) {
            executor.execute(player, event, false);
        }
    }
}
