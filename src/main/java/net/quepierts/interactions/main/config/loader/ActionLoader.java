package net.quepierts.interactions.main.config.loader;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.api.AbstractAction;
import net.quepierts.interactions.main.config.Entry;
import net.quepierts.interactions.main.data.action.ActionManager;
import net.quepierts.interactions.main.data.action.ExecuteType;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ActionLoader { ;
    private static final List<String> executorTypes = new ArrayList<>(3);
    private static boolean multiTypes = false;

    public static void setProcess_requiredExecutorType(String type) {
        if (type == null) {
            return;
        }

        String executorType;
        try {
            executorType = executorTypes.get(0);
        } catch (IndexOutOfBoundsException e) {
            executorType = null;
        }

        if (executorType == null || ExecuteType.getPriority(type) < ExecuteType.getPriority(executorType)) {
            executorTypes.clear();
            executorTypes.add(type);
        } else if (multiTypes && ExecuteType.getPriority(type) == ExecuteType.getPriority(executorType)) {
            executorTypes.add(type);
        }
    }

    public static void setMultiTypes() {
        multiTypes = true;
    }

    public static int loadActions(final ConfigurationSection config) {
        int count = 0;
        Set<String> keys = config.getKeys(false);

        ConfigurationSection actionConfig;

        Entry entry;
        for (String key : keys) {
            String type = "action_" + key.split("_")[0];

            if (Entry.hasEntry(type)) {
                actionConfig = config.getConfigurationSection(key);
                if (!actionConfig.contains("conditions")) {
                    Interactions.logger.warning("Action [" + key + "] missing conditions");
                    continue;
                }

                entry = Entry.getInstance(type);

                if (entry.isMultiRoots()) {
                    setMultiTypes();
                }
                setProcess_requiredExecutorType(entry.getExecuteType());

                AbstractAction executor = (AbstractAction) entry.getObject(actionConfig);

                if (executor != null) {
//                    ConfigurationSection conditions = actionConfig.getConfigurationSection("conditions");
//                    List<AbstractCondition> conditionList = ConditionLoader.getConditions(conditions);
//
//                    executor.addConditions(conditionList);

                    for (String executorType : executorTypes) {
                        ActionManager.add(executorType, executor);
                    }
                    ++ count;
                }
            } else {
                Interactions.logger.warning("Illegal Action Type: " + key);
            }

            executorTypes.clear();
            multiTypes = false;
        }

        return count;
    }
}
