package net.quepierts.interactions.main.data.action;

import com.google.common.collect.ImmutableMap;
import net.quepierts.interactions.api.AbstractAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActionManager {
    private static Map<String , List<AbstractAction>> executorMap;

    public static void init() {
        ImmutableMap.Builder<String , List<AbstractAction>> builder = new ImmutableMap.Builder<>();

        for (String executeType : ExecuteType.value()) {
            builder.put(executeType, new ArrayList<>());
        }

        executorMap = builder.build();
    }

    public static void add(String type, AbstractAction executable) {
        executorMap.get(type).add(executable);
    }

    public static void execute(String  type, Player player, Event event) {
        List<AbstractAction> executors = executorMap.get(type);

        for (AbstractAction executor : executors) {
            executor.execute(player, event, false);
        }
    }
}
