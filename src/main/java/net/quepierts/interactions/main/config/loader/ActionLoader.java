package net.quepierts.interactions.main.config.loader;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.api.ICondition;
import net.quepierts.interactions.api.IExecutable;
import net.quepierts.interactions.main.config.Entry;
import net.quepierts.interactions.main.data.action.ActionManager;
import net.quepierts.interactions.main.data.action.ExecuteType;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Set;

public class ActionLoader {
    private static ExecuteType process_requiredExecutorType = null;

    public static void setProcess_requiredExecutorType(ExecuteType type) {
        if (type == null) {
            return;
        }
        if (process_requiredExecutorType == null || type.priority() < process_requiredExecutorType.priority()) {
            process_requiredExecutorType = type;
        }
    }

    public static ExecuteType getProcess_requiredExecutorType() {
        return process_requiredExecutorType;
    }

    public static void loadActions(final ConfigurationSection config) throws Exception {
        Set<String> keys = config.getKeys(false);

        ConfigurationSection actionConfig;

        Entry<?> entry;
        for (String key : keys) {
            String type = "action_" + key.split("_")[0];

            if (Entry.hasEntry(type)) {
                actionConfig = config.getConfigurationSection(key);
                if (!actionConfig.contains("conditions")) {
                    Interactions.logger.warning("Action [" + key + "] missing conditions");
                    continue;
                }

                entry = Entry.getInstance(type);

                setProcess_requiredExecutorType(entry.getExecuteType());

                IExecutable executor = (IExecutable) entry.getObject(actionConfig);

                if (executor != null) {
                    Interactions.logger.info(executor.getClass().getName());
                    ConfigurationSection conditions = actionConfig.getConfigurationSection("conditions");
                    List<ICondition> conditionList = ConditionLoader.getConditions(conditions);

                    executor.addConditions(conditionList);

                    ActionManager.add(process_requiredExecutorType, executor);
                }
            }

            process_requiredExecutorType = null;
        }
    }
}
